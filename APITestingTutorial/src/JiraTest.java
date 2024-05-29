import static io.restassured.RestAssured.*;
import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import files.reUsableMethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	@Test
	public  void jiraWorking() throws Exception{
		// TODO Auto-generated method stub
		RestAssured.baseURI="http://localhost:8080";
		
		SessionFilter sf=new SessionFilter();
		//Handling HTTPS Certification validation using relaxedHTTPSValidation()
		String sessionCreation=given().relaxedHTTPSValidation().log().all().header("Content-Type","application/json")
				.body(Payload.createSession()).filter(sf)
				.when().post("/rest/auth/1/session")
				.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(sessionCreation);
		
		//Create Session and extract SessionID from the generated response
		/*
		 * JsonPath js= reUsableMethods.rawToJson(sessionCreation); String sessionID =
		 * js.get("session.value");
		 */
		
		String newComment="Hi, this is comment no.2";
		//Add Comment
		  String addCommentResponse=given().pathParam("issueId","10202").log().all()
				  .header("Content-Type","application/json")
				  //Extracted sessionID through above generated response
				  //.cookie("JSESSIONID",sessionID)
				  .body(Payload.AddComment(newComment)).filter(sf)
		  .post("/rest/api/2/issue/{issueId}/comment")
		  .then().assertThat().statusCode(201).extract().response().asString();
		  System.out.println(addCommentResponse);
		 JsonPath js1=reUsableMethods.rawToJson(addCommentResponse);
		 String commentId = js1.get("id");
		  
		//Add Attachment
		  given().header("X-Atlassian-Token", "no-check").filter(sf).pathParam("issueId","10202")
		  .header("Content-Type","multipart/form-data")
		  .multiPart("file",new File("jira.txt"))  
		  .when().post("/rest/api/2/issue/{issueId}/attachments")
		  .then().assertThat().statusCode(200);
		  
		  //Get IssueDetails
		  String issueDetailsResponse=given().filter(sf).pathParam("issueId","10202").queryParam("fields", "comment")
				  .when().get("rest/api/2/issue/{issueId}")
				  .then().log().all().extract().response().asString();
		  System.out.println(issueDetailsResponse);
			 
		  JsonPath js2=reUsableMethods.rawToJson(issueDetailsResponse);
		  int commentCount = js2.getInt("fields.comment.comments.size()");
		  for(int i=0;i<commentCount;i++) {
			  String commentIdIssue =  js2.get("fields.comment.comments["+i+"].id");
			  if(commentIdIssue.equalsIgnoreCase(commentId)) {
				String message = js2.get("fields.comment.comments["+i+"].body"); 
				Assert.assertEquals(message,newComment);
			  }
		  }
	}

}
