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

package com.nordea.country.rest;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.nordea.country.model.CountryRestModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jianwen Xu
 */
@RequestMapping("/countries")
@RestController
public class CountryRestApi {

	@Value("${country.api.url}")
	private String countryApi;

	@Value("${country.getAll}")
	private String getAll;

	@Value("${country.getByName}")
	private String getByName;

	@GetMapping
	public String findAll() throws JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		CountryRestModel[] result = restTemplate.getForObject(countryApi + getAll, CountryRestModel[].class);
		ObjectMapper om = getFieldFilter(true);
		return om.writeValueAsString(Arrays.asList(result));
	}

	@GetMapping("/{name}")
	public String findByName(@PathVariable("name") String name) throws JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		CountryRestModel[] result = restTemplate.getForObject(countryApi + getByName + name, CountryRestModel[].class);
		ObjectMapper om = getFieldFilter(false);
		return om.writeValueAsString(Arrays.asList(result));
	}

	// filter the return data for different apis
	private ObjectMapper getFieldFilter(boolean isCountry) {
		ObjectMapper om = new ObjectMapper();
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		if (isCountry) {
			filterProvider.addFilter("countryFilter",
					SimpleBeanPropertyFilter.filterOutAllExcept("name", "country_code"));
		}
		else {
			filterProvider.addFilter("countryFilter",
					SimpleBeanPropertyFilter.serializeAll());
		}
		om.setFilterProvider(filterProvider);
		return om;
	}
}
