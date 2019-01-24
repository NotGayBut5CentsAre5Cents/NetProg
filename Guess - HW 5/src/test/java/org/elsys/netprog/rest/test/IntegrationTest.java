package org.elsys.netprog.rest.test;

import java.security.MessageDigest;

import org.junit.Assert;

import static io.restassured.RestAssured.*;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class IntegrationTest {

	private final String serverBaseAddress = "jersey-rest-homework/game";
	
	@Test
	public void playGame() {
		RestAssured.port = 8081;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.defaultParser = Parser.JSON;
		RestAssured.baseURI = "http://localhost";
		
		System.out.println("Starting new game");
			Response get = given().basePath(serverBaseAddress).get("view");
			get.then().statusCode(200);
			String hash = get.body().jsonPath().getString("hash");
			String hashM, guess;
			boolean guessed = false;
			while (!guessed)
			{
				try {
					guess = "aaaaaaa";
					MessageDigest md;
					md = MessageDigest.getInstance("MD5");
					hashM = new String(md.digest(guess.getBytes()), "UTF8");
					System.out.println(hashM + " " +  hash);
					if(hashM.equals(hash) && given().basePath(serverBaseAddress).post("check/" + guess + "/" + hashM)
					.getStatusCode() == 200)
					{
						guessed = true;
					}
				}catch( Exception e)
				{
					e.printStackTrace();
				}
			}
		Assert.assertEquals(guessed, true);
	}
	
}
