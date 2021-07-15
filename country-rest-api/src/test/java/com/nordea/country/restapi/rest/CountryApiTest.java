/*
 * Copyright 2021-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nordea.country.restapi.rest;

import com.nordea.country.restapi.model.CountryModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountryApi.class)
public class CountryApiTest {

	@Autowired
	private MockMvc mvc;

	private CountryModel countryFI;

	@BeforeEach
	void setUp() {
		countryFI = new CountryModel();
		countryFI.setName("Finland");
		countryFI.setCountry_code("FI");
	}

	@Test
	void shouldFindAllCountries() throws Exception {
		mvc.perform(get("/countries").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].name").exists())
				.andExpect(jsonPath("$[0].country_code").exists());
	}

	@Test
	void shouldFindCountriesByName() throws Exception {
		mvc.perform(get("/countries/Finland").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$[0].name").value(countryFI.getName()))
				.andExpect(jsonPath("$[0].country_code").value(countryFI.getCountry_code()))
				.andExpect(jsonPath("$[0].capital").exists())
				.andExpect(jsonPath("$[0].population").exists())
				.andExpect(jsonPath("$[0].flag_file_url").exists());
	}
}
