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

package com.nordea.country.webgateway;

import java.time.Duration;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebGatewayApplicationTests {
	@LocalServerPort
	private int port;
	private WebDriver driver;

	@Test
	void contextLoads() {
	}

	@BeforeAll
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void setUp() {
		driver = new ChromeDriver();
	}

	@AfterEach
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void e2eTest() {
		// Open the homepage
		driver.get("http://localhost:" + port);
		Assertions.assertEquals("Web Gateway", driver.getTitle());
		// Check the app
		WebElement app = driver.findElement(By.id("app"));
		Assertions.assertTrue(app.isDisplayed());
		// Check the countries list
		WebElement firstCountry = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds())
				.until(driver -> driver.findElement(By.className("md-table-cell-container")));
		Assertions.assertTrue(firstCountry.isDisplayed());
		Assertions.assertEquals("A", firstCountry.getText().substring(0, 1));
		// Check the By Name tab and search button
		List<WebElement> tabElements = driver.findElements(By.className("md-tab-label"));
		WebElement allTab = tabElements.get(0);
		WebElement nameTab = tabElements.get(1);
		nameTab.click();
		WebElement searchBtn = driver.findElement(By.cssSelector("button.md-primary"));
		Assertions.assertTrue(searchBtn.isDisplayed());
		// Check the value of the Name input
		WebElement searchInput = driver.findElement(By.tagName("input"));
		Assertions.assertEquals("Finland", searchInput.getAttribute("value"));
		// Click search button and check the result
		searchBtn.click();
		WebElement nameCountry = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds())
				.until(driver -> driver.findElement(By.cssSelector("span.md-list-item-text")));
		Assertions.assertEquals("F", nameCountry.getText().substring(0, 1));
		// Switch back to the all list
		allTab.click();
		List<WebElement> countries = driver.findElements(By.className("md-table-cell-container"));
		Assertions.assertTrue(countries.size() > 0);
	}

}
