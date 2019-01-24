package org.elsys.netprog.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

import org.json.simple.JSONObject;

public class Main {

	public static void main(String[] args) {
		final String url = "http://localhost:8091/jersey-rest-homework/game/";
		
		
		System.out.println("Starting new game");
		try{
			while(true) {
				URL get_url = new URL(url + "view");
				HttpURLConnection get = null;
				get = (HttpURLConnection) get_url.openConnection();
				get.setRequestMethod("GET");
				int code = get.getResponseCode();
				JSONObject response = get_param(get.getInputStream()); 
			    
				int length = Integer.parseInt(response.get("length").toString());
				String hash = new String(Base64.getDecoder().decode(response.get("hash").toString()), Charset.forName("UTF-8"));
				String hashM;
				boolean guessed = false;
				
				System.out.println("Server Hash: " + hash + " Length: " + length);
				while (!guessed) {
					try {
						byte[] bytesOfInput = new byte[length];
						new Random().nextBytes(bytesOfInput);
						String input = new String(bytesOfInput, Charset.forName("UTF-8"));
						
						MessageDigest md;
						md = MessageDigest.getInstance("MD5");
						hashM = new String(md.digest(bytesOfInput), Charset.forName("UTF-8"));
						if(hashM.equals(hash)) {
							String encoded = Base64.getEncoder().encodeToString(bytesOfInput);
							HttpURLConnection post = null;
						    URL post_url = new URL(url + "check/" + encoded);
						    post = (HttpURLConnection) post_url.openConnection();
						    post.setRequestMethod("POST");
						    post.connect();
							if(post.getResponseCode() == 200) {
								guessed = true;
								System.out.println("SUCCESS hash: " + hashM + " input: " + input);
							} else {
								System.out.println("FAIL hash: " + hashM + " input: " + input + " " + encoded);
							}
							
						}
					}catch( Exception e)
					{
						e.printStackTrace();
					}
				}
	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static JSONObject get_param(InputStream im) throws ParseException, IOException {
		BufferedReader rd = new BufferedReader(new 	InputStreamReader(im));
	    StringBuilder response = new StringBuilder();
	    String line;
	    while ((line = rd.readLine()) != null) {
	      response.append(line);
	      response.append('\r');
	    }
	    JSONParser parser = new JSONParser();
	    return (JSONObject) parser.parse(response.toString());
	}
	
}
