package helper;
import org.testng.annotations.DataProvider;

import io.restassured.path.json.JsonPath;

public class Appendix {

	public static JsonPath getJson(String string)
	{
		return new JsonPath(string);
	}
	
	@DataProvider(name="countryCodeData")
	public static Object[][] countryCode()
	{
		//this returns the country code, name and zipCode
		
		return new Object[][] {{"us","United States","70050"}, {"au", "Australia", "0200"}, {"dk","Denmark", "0800"}};
	}
			
		
	@DataProvider(name="zipCodeCountData")
	public static Object[][] zipCodeCount()
	{
//		country code, lower limit, higher limit, number of valid zipCodes
		return new Object[][] {
			{"mq", "97200", "97290", 29},
			{"gy","97312","97360",8},
			{"li","9485", "9498", 13}
		
		};
	}
}
