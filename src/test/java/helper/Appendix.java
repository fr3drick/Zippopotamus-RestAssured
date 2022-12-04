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
			
			
}
