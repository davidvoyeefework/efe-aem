package com.efe.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.commons.Externalizer;
import com.efe.core.bean.LocationResponse;
import com.efe.core.bean.jsonld.Address;
import com.efe.core.bean.jsonld.ContactPoint;
import com.efe.core.bean.jsonld.Geo;
import com.efe.core.bean.jsonld.JsonLd;
import com.efe.core.models.multifield.SocialLink;
import com.efe.core.services.SeoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SeoUtil {

	private SeoUtil() {
	}

	public static String getLocationSEO(SlingHttpServletRequest request, Externalizer externalizer,
			LocationResponse locationResponse, SeoService seoService) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setPrettyPrinting().create();
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
		jsonLd.setUrl1(externalizer.publishLink(resolver, resolver.map(request.getPathInfo())));

		Geo geo = new Geo();
		geo.setType(seoService.getGeoType());
		geo.setLatitude(locationResponse.getLatitude());
		geo.setLongitude(locationResponse.getLongitude());
		jsonLd.setGeo(geo);

		return gson.toJson(jsonLd);

	}

	public static String getOrgSEO(Externalizer externalizer, SlingHttpServletRequest request,
			ResourceResolver resolver, SeoService seoService, List<SocialLink> socialLinks) {

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setPrettyPrinting().create();
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
		if (Objects.nonNull(logoVal)) {
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
			
			if(!socialLinksList.isEmpty()) {
				jsonLd.setSameAs(socialLinksList);
			}

		}

		jsonLd.setContactPoint(contactPoints);

		return gson.toJson(jsonLd);
	}

	/**
	 * Method to return attribute string value
	 * 
	 * @param request
	 * @param attribute
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
