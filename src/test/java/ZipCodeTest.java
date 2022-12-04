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

}
