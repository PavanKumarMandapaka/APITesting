package files;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

import io.restassured.path.json.JsonPath;

public class GraphQLDemo {

	public static void main(String[] args) {
		String response=given().log().all().header("Content-Type","application/json")
		.body("query\r\n"
				+ ": \r\n"
				+ "\"query ($characterId: Int!, $locationId: Int!) {\\n  character(characterId: $characterId) {\\n    name\\n    type\\n    status\\n    location {\\n      id\\n    }\\n  }\\n  location(locationId: $locationId) {\\n    name\\n    dimension\\n    residents {\\n      id\\n    }\\n  }\\n  episodes(filters: {episode: \\\"prime\\\"}) {\\n    result {\\n      id\\n      name\\n      air_date\\n    }\\n  }\\n  characters(filters: {name: \\\"Robin\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n      id\\n    }\\n  }\\n}\\n\"\r\n"
				+ "variables\r\n"
				+ ": \r\n"
				+ "{characterId: 7840, locationId: 9095}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().extract().response().asString();
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String characterName = js.getString("data.character.name");
		System.out.println(characterName);
		assertEquals(characterName, "Baskin Robin");
		

	}

}
