import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import POJO.Api;
import POJO.WebAutomation;
import POJO.getCourse;
import io.restassured.path.json.JsonPath;

public class oAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] expectedCourses = {"Selenium Webdriver Java","Cypress","Protractor"};
		// Server Authorization
		String authorizationServerResponse = given()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").log().all().when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").then().extract().response()
				.asString();
		System.out.println(authorizationServerResponse);
		JsonPath js = new JsonPath(authorizationServerResponse);
		String accessToken = js.getString("access_token");

		// Get Course Details
		getCourse courseDetailsResponse = given().queryParam("access_token", accessToken).log().all().when()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").then().extract().response()
				.as(getCourse.class);
		// System.out.println(courseDetailsResponse);
		System.out.println(courseDetailsResponse.getLinkedIn());
		System.out.println(courseDetailsResponse.getInstructor());
		System.out.println(courseDetailsResponse.getCourses().getApi().get(1).getPrice());

		List<Api> apiCourses = courseDetailsResponse.getCourses().getApi();
		for (Api a : apiCourses) {
			if (a.getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java")) {
				System.out.println(a.getCourseTitle() + " : " + a.getPrice());
			}
		}

		ArrayList<String> actualCourses= new ArrayList<String>();
		List<WebAutomation> webAutomationCourses = courseDetailsResponse.getCourses().getWebAutomation();
		for(int i=0;i<webAutomationCourses.size();i++) {
			actualCourses.add(webAutomationCourses.get(i).getCourseTitle());
		}
		List<String> temp= Arrays.asList(expectedCourses);
		System.out.println(actualCourses);
		Assert.assertTrue(temp.equals(actualCourses));
		
	}
}
