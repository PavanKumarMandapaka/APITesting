import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.reUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String Response=given().header("Content-Type","application/json").body(Payload.AddBook(isbn, aisle))
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js=reUsableMethods.rawToJson(Response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData(){
	return new Object[][] {{"qwerty","1234"},{"Rohit","4545"},{"BillionDreams","1010"}};
	}
}
