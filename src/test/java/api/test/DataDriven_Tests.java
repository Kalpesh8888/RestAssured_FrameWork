package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import api.utilities.DataProviders;

public class DataDriven_Tests {
	
	public Logger logger; // for logs
	
	@Test(priority = 1, dataProvider = "Data",dataProviderClass = DataProviders.class)
	public void testPostuser(String userID, String userName,String firstname,String lastname,String useremail,String pwd,String phone) 
	{
		        //logs
		logger= LogManager.getLogger(this.getClass());
				logger.debug("debugging.....");
		
		  
		 User  userpayload = new User();
		 
	       userpayload.setId(Integer.parseInt(userID));
	       userpayload.setUsername(userName);
	       userpayload.setFirstName(firstname);
	       userpayload.setLastName(lastname);
	       userpayload.setEmail(useremail);
	       userpayload.setPassword(pwd);
	       userpayload.setPhone(phone);
	    
	  logger.info("********** Creating user (Post Req.) ***************");
	    
	     Response response = UserEndPoints.createUser(userpayload); 
	  	 response.then().log().all(); 
	  	 
	  	Assert.assertEquals(response.getStatusCode(),200);
	  
	  	logger.info("**********User is creatged  ***************"); 	
	}
	
	@Test(priority = 2, dataProvider = "UserNames",dataProviderClass = DataProviders.class)
	public void getuser(String userName) 
	{
		logger.info("********** Reading User Info (Get Req.) ***************");
		
		Response response = UserEndPoints.readUser(userName);
		 response.then().log().all(); 
		Assert.assertEquals(response.getStatusCode(),200);
	
	logger.info("**********User info  is displayed ***************");	
	}
	
	
	@Test(priority = 3, dataProvider = "UserNames",dataProviderClass = DataProviders.class)
	public void deleteuser(String userName) 
	 {
		logger.info("**********   Deleting User (Delete req. )  ***************");
		
		Response response = UserEndPoints.deleteUser(userName);
		
		Assert.assertEquals(response.getStatusCode(),200);
	
		logger.info("********** User deleted ***************");
		
	 }
	
}
