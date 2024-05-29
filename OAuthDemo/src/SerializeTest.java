import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.Location;

public class SerializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		AddPlace ap = new AddPlace();
		ap.setAccuracy(50);
		ap.setAddress("14/80, Market Lane, NY");
		ap.setLanguage("German");
		ap.setName("My Home");
		ap.setPhone_number("(+91) 98-4802-2191");
		ap.setWebsite("http://google.com");
		
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park")	;
		myList.add("shop");
		ap.setTypes(myList);
		
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		ap.setLocation(loc);
		
		Response res=given().queryParam("key", "qaclick123").body(ap)
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response();
		
		String responseString = res.asString();
		System.out.println(responseString);
	}

}
