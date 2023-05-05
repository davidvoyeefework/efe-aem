package com.efe.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.commons.Externalizer;
import com.efe.core.bean.LocationResponse;
import com.efe.core.bean.jsonld.Address;
import com.efe.core.bean.jsonld.Answer;
import com.efe.core.bean.jsonld.ContactPoint;
import com.efe.core.bean.jsonld.Geo;
import com.efe.core.bean.jsonld.JsonLd;
import com.efe.core.bean.jsonld.MainEntity;
import com.efe.core.models.multifield.FAQ;
import com.efe.core.models.multifield.SocialLink;
import com.efe.core.services.SeoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class SeoUtil.
 */
public class SeoUtil {

	/** The Constant REGEX_HTML_ELEMENTS. */
	private static final String REGEX_HTML_ELEMENTS = "<[^>]*>|\n|\t";

	/**
	 * Instantiates a new seo util.
	 */
	private SeoUtil() {
	}

	/**
	 * Gets the location SEO.
	 *
	 * @param request          the request
	 * @param externalizer     the externalizer
	 * @param locationResponse the location response
	 * @param seoService       the seo service
	 * @return the location SEO
	 */
	public static String getLocationSEO(SlingHttpServletRequest request, Externalizer externalizer,
			LocationResponse locationResponse, SeoService seoService) {
		Gson gson = getGsonInstance();
		JsonLd jsonLd = new JsonLd();
		jsonLd.setType(seoService.getBusinessType());
		jsonLd.setName(seoService.getSiteName());
		jsonLd.setContext(seoService.getContextUrl());

		String contactNumber = getStringValueFromRequest(request, "contactNumber");
		if (Objects.nonNull(contactNumber)) {
			jsonLd.setTelePhone(contactNumber);
		}

		Address address = new Address();
		String streetAddres = Stream.of(locationResponse.getAddress1(), locationResponse.getAddress2())
				.filter(s -> s != null && !s.isEmpty()).collect(Collectors.joining(", "));

		address.setType(seoService.getAdressType());
		address.setStreetAddress(streetAddres);
		address.setPostalCode(locationResponse.getZip());
		address.setAddressRegion(locationResponse.getState());
		address.setAddressLocality(locationResponse.getCity());
		jsonLd.setAddress(address);

		ResourceResolver resolver = request.getResourceResolver();

		jsonLd.setUrl(externalizer.publishLink(resolver, request.getPathInfo()));

		Geo geo = new Geo();
		geo.setType(seoService.getGeoType());
		geo.setLatitude(locationResponse.getLatitude());
		geo.setLongitude(locationResponse.getLongitude());
		jsonLd.setGeo(geo);

		return gson.toJson(jsonLd);

	}

	/**
	 * Gets the org SEO.
	 *
	 * @param externalizer the externalizer
	 * @param request      the request
	 * @param resolver     the resolver
	 * @param seoService   the seo service
	 * @param socialLinks  the social links
	 * @return the org SEO
	 */
	public static String getOrgSEO(Externalizer externalizer, SlingHttpServletRequest request,
			ResourceResolver resolver, SeoService seoService, List<SocialLink> socialLinks) {

		Gson gson = getGsonInstance();
		JsonLd jsonLd = new JsonLd();
		jsonLd.setName(seoService.getSiteName());
		jsonLd.setContext(seoService.getContextUrl());
		jsonLd.setType(seoService.getOrgType());
		jsonLd.setUrl(externalizer.publishLink(resolver, request.getPathInfo()));

		String logoVal = getStringValueFromRequest(request, "logo");
		if (Objects.nonNull(logoVal)) {
			jsonLd.setLogo(externalizer.publishLink(resolver, logoVal));
		}

		List<ContactPoint> contactPoints = new ArrayList<>();
		ContactPoint contactPoint = new ContactPoint();
		contactPoint.setType(seoService.getContactType());
		contactPoint.setContactOption(seoService.getContactOption());
		contactPoint.setContactType(seoService.getContactTypeName());
		contactPoint.setAreaServed(seoService.getAreaServed());

		String contactNumber = getStringValueFromRequest(request, "contactNumber");
		if (Objects.nonNull(contactNumber)) {
			contactPoint.setTelephone(contactNumber);
		}
		contactPoints.add(contactPoint);

		if (Objects.nonNull(socialLinks) && !socialLinks.isEmpty()) {
			List<String> socialLinksList = new ArrayList<>();
			for (SocialLink link : socialLinks) {
				if (StringUtils.isNotBlank(link.getLink())) {
					socialLinksList.add(link.getLink());
				}
			}

			if (!socialLinksList.isEmpty()) {
				jsonLd.setSameAs(socialLinksList);
			}

		}

		jsonLd.setContactPoint(contactPoints);

		return gson.toJson(jsonLd);
	}

	/**
	 * Gets the faq schema.
	 *
	 * @param seoService the seo service
	 * @param faqList    the faq list
	 * @return the faq schema
	 */
	public static String getFaqSchema(SeoService seoService, List<FAQ> faqList) {

		Gson gson = getGsonInstance();
		JsonLd jsonLd = new JsonLd();
		jsonLd.setContext(seoService.getContextUrl());
		jsonLd.setType(seoService.getFaqType());

		List<MainEntity> mainEntities = faqList.stream()
				.filter(faq -> StringUtils.isNotEmpty(faq.getQuestion()) && StringUtils.isNotEmpty(faq.getAnswer()))
				.map(faq -> {
					final MainEntity mainEntity = new MainEntity();
					mainEntity.setType(seoService.getQuestionType());
					mainEntity.setName(removeElementsNUnescapeHTML(faq.getQuestion()));

					final Answer answer = new Answer();
					answer.setType(seoService.getAnswerType());
					answer.setText(removeElementsNUnescapeHTML(faq.getAnswer()));
					mainEntity.setAcceptedAnswer(answer);
					return mainEntity;
				}).collect(Collectors.toList());
		jsonLd.setMainEntity(mainEntities);
		return gson.toJson(jsonLd);
	}

	/**
	 * Gets the gson instance.
	 *
	 * @return the gson instance
	 */
	private static Gson getGsonInstance() {
		GsonBuilder builder = new GsonBuilder();
		return builder.disableHtmlEscaping().setPrettyPrinting().create();
	}

	/**
	 * Removes the elements N unescape HTML.
	 *
	 * @param input the input
	 * @return the string
	 */
	private static String removeElementsNUnescapeHTML(String input) {
		return StringEscapeUtils.unescapeHtml4(input.replaceAll(REGEX_HTML_ELEMENTS, ""));
	}

	/**
	 * Method to return attribute string value.
	 *
	 * @param request   the request
	 * @param attribute the attribute
	 * @return attribute value
	 */
	private static String getStringValueFromRequest(SlingHttpServletRequest request, String attribute) {
		String attributeValue = null;
		if (Objects.nonNull(request.getAttribute(attribute))) {
			attributeValue = (String) request.getAttribute(attribute);

		}
		return attributeValue;
	}

}
