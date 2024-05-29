package demo;

import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;

public class OauthTest {

	public static void main(String[] args) throws InterruptedException {
		
		//Automating below code is now not possible bcoz of google security issues, so manually browser should be launched and url should be copied
		
		/*
		 * WebDriverManager.chromedriver().setup(); WebDriver driver = new
		 * ChromeDriver(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php"
		 * ); driver.findElement(By.xpath("//input[@type='email']")).sendKeys(
		 * "pavankumar2427617@gmail.com");
		 * driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
		 * Thread.sleep(3000);
		 * driver.findElement(By.xpath("//input[@type='password']")).sendKeys("xxxxx");
		 * driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER
		 * ); Thread.sleep(2000);
		 * driver.findElement(By.xpath("//span[contains(text(),'Continue')]")).click();
		 * Thread.sleep(4000); String url = driver.getCurrentUrl();
		 */
			 
		 
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdLIrYeMLlJlpu7gvRZKrQ_2ET8yImgfIj90R8Y4jp84rA3dk3WrofdI1N7D8zEUYMp_pQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		String partialCode =url.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println("code="+code);
		
		String accessTokenResponse=given().urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");
		System.out.println("accessToken="+accessToken);
		
		
		String respone=given()
				.queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(respone);
	}

}
