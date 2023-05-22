package com.efe.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import com.adobe.cq.wcm.core.components.models.NavigationItem;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.efe.core.bean.ArticleAuthor;
import com.efe.core.bean.Articles;
import com.efe.core.bean.Certifications;
import com.efe.core.bean.Education;
import com.efe.core.bean.LocationResponse;
import com.efe.core.bean.PlannerResponse;
import com.efe.core.bean.PrimaryOffice;
import com.efe.core.bean.jsonld.Address;
import com.efe.core.bean.jsonld.Answer;
import com.efe.core.bean.jsonld.Author;
import com.efe.core.bean.jsonld.ContactPoint;
import com.efe.core.bean.jsonld.Geo;
import com.efe.core.bean.jsonld.ItemListElement;
import com.efe.core.bean.jsonld.JsonLd;
import com.efe.core.bean.jsonld.KnowsAbout;
import com.efe.core.bean.jsonld.MainEntity;
import com.efe.core.bean.jsonld.WorksFor;
import com.efe.core.constants.Constants;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.models.multifield.FAQ;
import com.efe.core.models.multifield.SocialLink;
import com.efe.core.services.EfeService;
import com.efe.core.services.RestService;
import com.efe.core.services.SeoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class SeoUtil.
 */
public class SeoUtil {

	/** The Constant TITLE. */
	public static final String TITLE = "title";

	/** The Constant JSON_LD. */
	public static final String JSON_LD = "jsonLd";

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
	 * Gets the bread crumb SEO schema.
	 *
	 * @param seoService         the seo service
	 * @param request            the request
	 * @param externalizer       the externalizer
	 * @param items              the items
	 * @param showSelectorAsLeaf the show selector as leaf
	 * @param selectorIndex      the selector index
	 * @return the bread crumb SEO schema
	 */
	public static String getBreadCrumbSEOSchema(SeoService seoService, SlingHttpServletRequest request,
			Externalizer externalizer, Collection<NavigationItem> items, boolean showSelectorAsLeaf,
			int selectorIndex) {

		Gson gson = getGsonInstance();
		JsonLd jsonLd = new JsonLd();
		jsonLd.setContext(seoService.getContextUrl());
		jsonLd.setType(seoService.getBreadCrumbType());

		int index = 1;
		List<ItemListElement> itemListElements = new ArrayList<>();
		for (NavigationItem item : items) {
			ItemListElement element = new ItemListElement();
			element.setItem(item.getLink().getExternalizedURL());
			element.setName(item.getTitle());
			element.setType(seoService.getBreadCrumbItemType());
			element.setPosition(index++);
			itemListElements.add(element);
		}

		if (showSelectorAsLeaf) {
			String[] selectors = request.getRequestPathInfo().getSelectors();
			if (selectors.length > 0 && selectors.length > selectorIndex) {
				String selector = selectors[selectorIndex];
				String pagePath = externalizer.publishLink(request.getResourceResolver(), request.getPathInfo());
				ItemListElement element = new ItemListElement();
				element.setType(seoService.getBreadCrumbItemType());
				element.setName(selector);
				element.setItem(pagePath);
				element.setPosition(index);
				itemListElements.add(element);
			}
		}
		jsonLd.setItemListElement(itemListElements);
		return gson.toJson(jsonLd);
	}

	/**
	 * Gets the article schema.
	 *
	 * @param seoService       the seo service
	 * @param efeService 
	 * @param externalizer     the externalizer
	 * @param resourceResolver the resource resolver
	 * @param currentPage      the current page
	 * @param article          the article
	 * @return the article schema
	 */
	public static String getArticleSchema(SeoService seoService, EfeService efeService, Externalizer externalizer,
			ResourceResolver resourceResolver, Page currentPage, Articles article) {

		Gson gson = getGsonInstance();
		JsonLd jsonLd = new JsonLd();
		jsonLd.setContext(seoService.getContextUrl());
		jsonLd.setType("BlogPosting");

		jsonLd.setHeadline(removeElementsNUnescapeHTML(article.getTitle()));
		jsonLd.setAlternativeHeadline(removeElementsNUnescapeHTML(article.getSubtitle()));
		jsonLd.setDescription(removeElementsNUnescapeHTML(article.getArticleSummary()));

		if (StringUtils.isNotEmpty(article.getHeroImage())) {
			jsonLd.setImage(externalizer.publishLink(resourceResolver, article.getHeroImage()));
		}
		
		jsonLd.setUrl(externalizer.publishLink(resourceResolver, currentPage.getPath()));
		jsonLd.setDatePublished(EFEUtil.formatDate(Constants.DATE_FORMAT_MONTH_DAY_YEAR,
				Constants.DATE_FORMAT_YEAR_MONTH_DAY, article.getDatePublished()));
		jsonLd.setDateModified(EFEUtil.formatDate(Constants.DATE_FORMAT_MONTH_DAY_YEAR,
				Constants.DATE_FORMAT_YEAR_MONTH_DAY, article.getDateUpdated()));
		
		List<Author> authors = new ArrayList<>();
		if (Objects.nonNull(article.getPlannerResponse()) && !article.getPlannerResponse().isEmpty()) {
			for(PlannerResponse plannerResponse: article.getPlannerResponse()) {
				authors.add(populatePlannerDetails(seoService, efeService, externalizer, resourceResolver, plannerResponse));			
			}
		} 
		
		if (Objects.nonNull(article.getArticleAuthors()) && !article.getArticleAuthors().isEmpty()) {
			for(ArticleAuthor articleAuthor: article.getArticleAuthors()) {
				authors.add(populateRegularAuthor(seoService, externalizer, resourceResolver, articleAuthor));			
			}
		}
		
		if(!authors.isEmpty()) {
			jsonLd.setAuthor(authors);
		}
		
		return gson.toJson(jsonLd);
	}

	/**
	 * Populate regular author.
	 *
	 * @param seoService the seo service
	 * @param externalizer the externalizer
	 * @param resourceResolver the resource resolver
	 * @param article the article
	 * @return the author
	 */
	private static Author populateRegularAuthor(SeoService seoService, Externalizer externalizer, ResourceResolver resourceResolver,
			ArticleAuthor articleAuthor) {
		
		Author author = new Author();
		author.setName(articleAuthor.getName());
		author.setJobTitle(articleAuthor.getTitle());			
		if(StringUtils.isNotEmpty(articleAuthor.getPhoto())) {
			author.setImage(externalizer.publishLink(resourceResolver, articleAuthor.getPhoto()));
		}
		
		WorksFor worksFor = new WorksFor();
		worksFor.setName(seoService.getSiteName());
		author.setWorksFor(worksFor);
		
		return author;
	}

	/**
	 * Populate planner details.
	 *
	 * @param seoService the seo service
	 * @param efeService the efe service
	 * @param externalizer the externalizer
	 * @param resourceResolver the resource resolver
	 * @param article the article
	 * @return the author
	 */
	private static Author populatePlannerDetails(SeoService seoService, EfeService efeService, Externalizer externalizer,
			ResourceResolver resourceResolver, PlannerResponse planner) {
		
		Author author = new Author();
		author.setName(StringUtils.isNotEmpty(planner.getLastName())
				? planner.getFirstName().concat(" ").concat(planner.getLastName())
				: planner.getFirstName());

		author.setJobTitle(planner.getTitle());

		String url = LinkUtil.getFormattedLink(efeService.getPlannerBioPageUrl() + PlannerLocationConstants.DOT
				+ planner.getFirstName() + PlannerLocationConstants.DOT + planner.getLastName()
				+ PlannerLocationConstants.DOT + planner.getId(), resourceResolver);
		
		if(StringUtils.isNotEmpty(url)) {
			author.setUrl(externalizer.publishLink(resourceResolver, url));	
		}
				
		if (Objects.nonNull(planner.getPrimaryOffice())) {
			PrimaryOffice office = planner.getPrimaryOffice();
			Address address = new Address();
			address.setType(seoService.getAdressType());
			address.setAddressLocality(office.getCity());
			address.setStreetAddress(office.getName());
			address.setPostalCode(office.getZip());
			address.setAddressRegion(office.getState());
			author.setAddress(address);
		}
			
		if(Objects.nonNull(planner.getCertifications()) && !planner.getCertifications().isEmpty()) {
			KnowsAbout knowsAbout = new KnowsAbout();
			List<String> certifications = new ArrayList<>();
			for(Certifications certification: planner.getCertifications()) {
				certifications.add(certification.getName());
			}
			knowsAbout.setName(certifications);
			author.setKnowsAbout(knowsAbout);
		}
		
		if(Objects.nonNull(planner.getEducation()) && !planner.getEducation().isEmpty()) {
			StringBuilder educations = new StringBuilder();
			
			Iterator<Education> itr = planner.getEducation().iterator();
			while(itr.hasNext()) {
				Education education = itr.next();
				
				String degree = education.getDegree();
				String university = education.getUniversity();
				
				educations.append(degree).append(" - ").append(university);
				if(itr.hasNext()) {
					educations.append(", ");
				}
			}
			author.setHasCredential(educations.toString());
		}
		
		if(StringUtils.isNotEmpty(planner.getDesktopImageUrl())) {
			author.setImage(externalizer.publishLink(resourceResolver, planner.getDesktopImageUrl()));
		}
		
		WorksFor worksFor = new WorksFor();
		worksFor.setName(seoService.getSiteName());
		author.setWorksFor(worksFor);
		return author;
	}

	/**
	 * Gets the video seo.
	 *
	 * @param restService   the rest service
	 * @param seoService    the seo service
	 * @param externalizer  the externalizer
	 * @param request       the request
	 * @param videoId       the video id
	 * @param fileReference the file reference
	 * @return the video seo
	 */
	public static JsonObject getVideoSeo(RestService restService, SeoService seoService, Externalizer externalizer,
			SlingHttpServletRequest request, String videoId, String fileReference) {

		JsonObject videoDetailsJson = new JsonObject();

		if (StringUtils.isEmpty(seoService.getYoutubeAPIUrl())) {
			return videoDetailsJson;
		}

		String api = seoService.getYoutubeAPIUrl().replace("{videoid}", videoId);
		String response = restService.getData(api, null);

		if (StringUtils.isNotEmpty(response)) {
			JsonLd jsonLd = new JsonLd();
			jsonLd.setContext(seoService.getContextUrl());
			jsonLd.setType(seoService.getVideoType());

			if (StringUtils.isNotEmpty(fileReference)) {
				jsonLd.setThumbnailUrl(externalizer.publishLink(request.getResourceResolver(), fileReference));
			}

			JsonElement rootElement = JsonParser.parseString(response);
			if (rootElement != null && rootElement.isJsonObject()) {
				JsonObject rootJsonObject = rootElement.getAsJsonObject();

				if (rootJsonObject.has("items")) {
					JsonArray items = rootElement.getAsJsonObject().getAsJsonArray("items");
					populateVideoSchema(jsonLd, videoId, videoDetailsJson, items);
				}
			}
		}
		return videoDetailsJson;
	}

	/**
	 * Populate video schema.
	 *
	 * @param jsonLd  the json ld
	 * @param videoId the video id
	 * @param object  the object
	 * @param items   the items
	 */
	private static void populateVideoSchema(JsonLd jsonLd, String videoId, JsonObject object, JsonArray items) {

		if (!items.isEmpty() && items.get(0).isJsonObject()) {
			Gson gson = getGsonInstance();

			JsonObject snippet = items.get(0).getAsJsonObject().getAsJsonObject("snippet");
			getYoutubeSnippetInfo(jsonLd, videoId, object, snippet);

			JsonObject contentDetails = items.get(0).getAsJsonObject().getAsJsonObject("contentDetails");
			getDurationDetails(jsonLd, contentDetails);

			object.addProperty(JSON_LD, gson.toJson(jsonLd));
		}
	}

	/**
	 * Gets the youtube snippet info.
	 *
	 * @param jsonLd  the json ld
	 * @param videoId the video id
	 * @param object  the object
	 * @param snippet the snippet
	 * @return the youtube snippet info
	 */
	private static JsonLd getYoutubeSnippetInfo(JsonLd jsonLd, String videoId, JsonObject object, JsonObject snippet) {
		if (null != snippet) {
			String title = snippet.has(TITLE) ? snippet.get(TITLE).getAsString() : StringUtils.EMPTY;

			String desc = snippet.has("description") ? snippet.get("description").getAsString() : StringUtils.EMPTY;
			String publishedAt = snippet.has("publishedAt") ? snippet.get("publishedAt").getAsString()
					: StringUtils.EMPTY;

			jsonLd.setName(title);
			jsonLd.setDescription(desc);
			jsonLd.setUploadDate(publishedAt);
			jsonLd.setEmbedUrl("https://www.youtube.com/embed/" + videoId);
			object.addProperty(TITLE, title);
		}
		return jsonLd;
	}

	/**
	 * Gets the duration details.
	 *
	 * @param jsonLd         the json ld
	 * @param contentDetails the content details
	 * @return the duration details
	 */
	private static void getDurationDetails(JsonLd jsonLd, JsonObject contentDetails) {
		if (null != contentDetails && null != jsonLd) {
			String duration = contentDetails.has("duration") ? contentDetails.get("duration").getAsString()
					: StringUtils.EMPTY;

			jsonLd.setDuration(duration);
		}
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
		String formattedString = StringUtils.EMPTY;
		if (null != input) {
			formattedString = StringEscapeUtils.unescapeHtml4(input.replaceAll(REGEX_HTML_ELEMENTS, "").trim());
		}
		return formattedString;
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
