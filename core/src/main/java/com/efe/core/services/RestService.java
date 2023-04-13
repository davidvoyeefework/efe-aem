package com.efe.core.services;

/**
 * The Interface RestService
 *
 */
public interface RestService {

	/**
	 * Gets the data.
	 *
	 * @param token the token
	 * @return the data
	 */
	String getData(String apiUrl, String authHeader);

}
