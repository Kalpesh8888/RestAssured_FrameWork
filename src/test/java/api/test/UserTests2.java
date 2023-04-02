package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;


import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {

	 Faker faker;
	 User userpayload;
	 
	 public Logger logger;  // for logs
	 
	@BeforeTest
	public void setup() {
		
		faker= new Faker() ;
	    userpayload = new User();
	 
	   userpayload.setId(faker.idNumber().hashCode());
	   userpayload.setUsername(faker.name().username());
	   userpayload.setFirstName(faker.name().firstName());
	   userpayload.setLastName(faker.name().lastName());
	   userpayload.setEmail(faker.internet().safeEmailAddress());
	   userpayload.setPassword(faker.internet().password(5, 10));
	   userpayload.setPhone(faker.phoneNumber().cellPhone());
	  
	       //logs
	 		logger= LogManager.getLogger(this.getClass());
	 		
	 		logger.debug("debugging.....");
	
	
	}
	
	@Test(priority=1)
	public void testPostUser() 
	{
		logger.info("********** Creating user (Post Req.) ***************");
		
	  Response response = UserEndPoints2.createUser(userpayload); 
	  response.then().log().all(); 
		
	  Assert.assertEquals(response.getStatusCode(),200);
	  
	  logger.info("**********User is creatged  ***************");
	}
	
	@Test(priority=2)
	public void testGetUserByName()
	{
		logger.info("********** Reading User Info (Get Req.) ***************");
		
		Response response=UserEndPoints2.readUser(this.userpayload.getUsername());
		response.then().log().all();
	    response.statusCode();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("**********User info  is displayed ***************");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		
		logger.info("********** Updating User (Put Req.) ***************");
		
		   //update data using payload
		   userpayload.setFirstName(faker.name().firstName());
		   userpayload.setLastName(faker.name().lastName());
		   userpayload.setEmail(faker.internet().safeEmailAddress());
	
		   Response response=UserEndPoints2.updateUser(this.userpayload.getUsername(),userpayload);
		   response.then().log().body().statusCode(200);      //chai Assertion 
		   response.then().log().all();
		   
			
			Assert.assertEquals(response.getStatusCode(),200);
	
			logger.info("********** User updated ***************");
			
			//Checking data after update
			Response responseAfterupdate=UserEndPoints2.readUser(this.userpayload.getUsername());
			
			Assert.assertEquals(responseAfterupdate.getStatusCode(),200);
			
			
			}
	
	
	@Test(priority=4)
	public void testDeleteUserByName() {
	
		logger.info("**********   Deleting User (Delete req. )  ***************");
		
		Response response=UserEndPoints2.deleteUser(this.userpayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);
	
		logger.info("********** User deleted ***************");
   }
	
}