package testPackage;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import helper.Appendix;
import static helper.Appendix.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ZipCodeTest {
	
	@BeforeTest
	public void setUp() {
		RestAssured.baseURI = "http://api.zippopotam.us";
	}
	
	
	@Test(dataProvider="countryCodeData", dataProviderClass=Appendix.class)
	public void checkCountryName(String code, String country, String zip){
		
		//This test is to verify that info API returns is for queried country and zipCode
		//pass country code and zipcode from dataProvider into basePath
		RestAssured.basePath = "/"+code+"/"+zip;
		
		String body =
		when().get()
		
		.then()
		.statusCode(200).log().all().extract().body().asString();
		
		JsonPath js = getJson(body);
		
		//Extract country name and zipcode from API response
		String countryInResponse = js.getString("country");
		String zipCodeInResponse = js.getString("'post code'");
		
		Assert.assertEquals(countryInResponse, country, "country name mismatch");
		Assert.assertEquals(zipCodeInResponse, zip, "zipcode mismatch");
		
	}
	
	
	@Test(dataProvider="zipCodeCountData", dataProviderClass=Appendix.class)
	public void zipCodeCount(String code, String minZip, String maxZip, int count) {
		//This is to verify that the number of valid zipCodes in range as as stated in documentation
		//only for numeric zipCodes that don't start with 0
		//country code, number of zipCodes, min zipCode and max zipCode are parameters
		
		
		int minZipInt = Integer.parseInt(minZip);
		int maxZipInt = Integer.parseInt(maxZip);
		int countOfZipCodes = 0;

		
		for(int zip = minZipInt; zip <= maxZipInt; zip++) {
		RestAssured.basePath = "/"+code+"/"+zip;
		
		String body =
		when().get()
		
		.then()
		.extract().body().asString();
		
		JsonPath js = getJson(body);
		
		//This checks if zipCode queried is valid and increments countOfZipCodes
		if(!(js.getString("'post code'")==null)) {
			countOfZipCodes++;
		}
			
		} //end of for loop
		
		Assert.assertEquals(countOfZipCodes, count, "number of valid zipCodes in range does not match" );
		System.out.println("count of valid zipCodes in range is "+countOfZipCodes);
	}

}
