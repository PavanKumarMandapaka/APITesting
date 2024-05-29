import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.reUsableMethods;


public class APIBasics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Add Place
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		//calling jsons from another class
		//.body(Payload.addPlace())
		
		//Static JSON file		
		//reading the external static json file as bytes through Files and then converting bytes to string 
		.body(new String(Files.readAllBytes(Paths.get("D:\\APITesting\\APITestingTutorial\\src\\files\\addPlace.json"))))
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.extract().asString();
		
		System.out.print(response + "\n");
		JsonPath js=new JsonPath(response);
		String place_id = js.getString("place_id");
		System.out.println(place_id);
		
		//Update Place
		String newAddress = "70 winter walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js1 = reUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
	}

}
