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

package com.nordea.country.restapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryAllResponse {
	@JsonProperty("name")
	private String name;
	@JsonProperty("country_code")
	private String country_code;

	public CountryAllResponse(String name, String country_code) {
		this.name = name;
		this.country_code = country_code;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	String getCountry_code() {
		return country_code;
	}

	void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
}
