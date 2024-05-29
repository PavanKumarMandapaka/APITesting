package demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.Orders;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ECommerceAPITest {

	public static void main(String[] args) {  
		
		//Login
		RequestSpecification req =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("pavan.kumar@gmail.com");
		loginRequest.setUserPassword("Rithika@34");
		
		RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginRequest);
		LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login")
		.then().log().all().extract().response().as(LoginResponse.class);
		String authorizationToken = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		
		//Add Product
		RequestSpecification addProductBaseReq =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",authorizationToken).build();
		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
		.param("productName", "Elephant")
		.param("productAddedBy", userId).param("productCategory", "fashion")
		.param("productSubCategory", "shirts").param("productPrice", "11500")
		.param("productDescription", "Lenova").param("productFor", "men")
		.multiPart("productImage",new File("D:\\APITesting\\Elephant_Toy.jpeg"));
		
		String addProductResponse= reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.getString("productId");
		System.out.println(productId);

		//Create Order
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productId);
		
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		orderDetailsList.add(orderDetails);
		Orders orders = new Orders();
		orders.setOrders(orderDetailsList);
		
		RequestSpecification reqCreateOrder= given().log().all().spec(req).header("Authorization",authorizationToken).body(orders);
		String createOderResponse=reqCreateOrder.when().post("/api/ecom/order/create-order")
				.then().log().all().extract().response().asString();
		System.out.println(createOderResponse);
		
		//Delete Product
		RequestSpecification DeleteProductReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",authorizationToken).build();
		RequestSpecification reqDeleteProduct=given().log().all().spec(DeleteProductReq).pathParam("productId", productId);
		String deleteProductMessageResponse=reqDeleteProduct.when().delete("/api/ecom/product/delete-product/{productId}")
		.then().log().all().extract().response().jsonPath().getString("message");
		
		assertEquals("Product Deleted Successfully",deleteProductMessageResponse);
	}

}
