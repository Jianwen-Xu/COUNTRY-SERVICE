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

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * @author Jianwen Xu
 */
public class CountryModel {
	private String name;
	@JsonAlias("alpha2Code")
	private String country_code;
	private String capital;
	private Integer population;
	@JsonAlias("flag")
	private String flag_file_url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public Integer getPopulation() {
		return population;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}

	public String getFlag_file_url() {
		return flag_file_url;
	}

	public void setFlag_file_url(String flag_file_url) {
		this.flag_file_url = flag_file_url;
	}
}
