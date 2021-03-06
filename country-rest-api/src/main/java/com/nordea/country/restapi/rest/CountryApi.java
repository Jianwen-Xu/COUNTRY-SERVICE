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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nordea.country.restapi.model.CountryAllResponse;
import com.nordea.country.restapi.model.CountryModel;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/countries")
@RestController
public class CountryApi {
	@Value("${country.api.baseUrl}")
	private String countryApi;

	@Value("${country.api.getAll}")
	private String getAll;

	@Value("${country.api.getByName}")
	private String getByName;

	@GetMapping
	public List<CountryAllResponse> findAllInFlux() {
		try {
			List<CountryAllResponse> countryList = new ArrayList<>();
			RestTemplate restTemplate = new RestTemplate();
			CountryModel[] result = restTemplate.getForObject(countryApi + getAll, CountryModel[].class);
			Flux.fromIterable(Arrays.asList(result))
					.map(countryModel -> new CountryAllResponse(countryModel.getName(), countryModel.getCountry_code()))
					.log().subscribe(countryList::add);
			return countryList;
		}
		catch (RestClientException e) {
			throw new ResourceNotFoundException("Country List Not Found");
		}
	}

	@GetMapping("/{name}")
	public List<CountryModel> findByNameInFlux(@PathVariable("name") String name) {
		try {
			List<CountryModel> countryList = new ArrayList<>();
			RestTemplate restTemplate = new RestTemplate();
			CountryModel[] result = restTemplate.getForObject(countryApi + getByName + name, CountryModel[].class);
			Flux.fromIterable(Arrays.asList(result)).log().subscribe(countryList::add);
			return countryList;
		}
		catch (RestClientException e) {
			throw new ResourceNotFoundException("Country " + name + " Not Found");
		}
	}

}
