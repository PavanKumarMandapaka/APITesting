import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.Location;

public class SpecBuilderTest {

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
		
		RequestSpecification reqSpecBuilder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();
		ResponseSpecification resSpecBuilder = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification req=given().spec(reqSpecBuilder).body(ap);
		
		Response response = req.when().post("/maps/api/place/add/json")
		.then().log().all().spec(resSpecBuilder).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
	}

}
