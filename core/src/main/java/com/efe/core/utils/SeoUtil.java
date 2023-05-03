package com.efe.core.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.efe.core.bean.LocationResponse;
import com.efe.core.bean.jsonld.Address;
import com.efe.core.bean.jsonld.Geo;
import com.efe.core.bean.jsonld.JsonLd;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SeoUtil {

	private SeoUtil() {
	}

	public static String getLocationSEO(LocationResponse locationResponse) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setPrettyPrinting().create();
		JsonLd jsonLd = new JsonLd();
		jsonLd.setType("FinancialService");
		jsonLd.setName("Edelman Financial Engines");
		jsonLd.setTelePhone("+1-833-752-6333");

		Address address = new Address();
		String streetAddres = Stream.of(locationResponse.getAddress1(), locationResponse.getAddress2())
				.filter(s -> s != null && !s.isEmpty()).collect(Collectors.joining(","));

		address.setStreetAddress(streetAddres);
		address.setPostalCode(locationResponse.getZip());
		address.setAddressRegion(locationResponse.getState());
		address.setAddressLocality(locationResponse.getCity());
		jsonLd.setAddress(address);

		Geo geo = new Geo();
		geo.setLatitude(locationResponse.getLatitude());
		geo.setLongitude(locationResponse.getLongitude());
		jsonLd.setGeo(geo);

		return gson.toJson(jsonLd);

	}

}
