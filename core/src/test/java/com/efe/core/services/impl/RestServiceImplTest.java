package com.efe.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The class RestServiceImplTest
 *
 */
@ExtendWith(MockitoExtension.class)
class RestServiceImplTest {

	/**
	 * Mocked RestServiceImpl
	 */
	@Mock
	private RestServiceImpl restService;

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *
	 */
	@BeforeEach
	void setUp() throws Exception {
		restService = new RestServiceImpl();
	}

	/**
	 * Test the Json Response
	 * 
	 */
	@Test
	void testGetData() throws IOException {
		String apiUrl = "https://jsonplaceholder.typicode.com/todos/1";
		String result = restService.getData(apiUrl, null);
		assertNotNull(result);
	}

	@Test
	void testGetDataNotNull() {
		String apiUrl = "https://jsonplaceholder.typicode.com/todos/1";
		String result = restService.getData(apiUrl, "header");
		// assertNotNull(result);
	}
}