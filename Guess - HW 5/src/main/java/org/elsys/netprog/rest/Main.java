package org.elsys.netprog.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

import org.json.simple.JSONObject;

public class Main {

	public static void main(String[] args) {
		final String url = "http://localhost:8080/jersey-rest-homework/game/";
		System.out.println("A stat file will be generated");
		System.out.print("Number of hashes to crack: ");
		Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		System.out.println("Starting new game");
		try{
			PrintWriter writer = new PrintWriter(dtf.format(LocalDateTime.now()) + "-Stats.txt");
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			int length = 0;
			double startTime = 0;;
			double currTime, totalTime = 0;
			int tries = 0, totalTries = 0;
			for(int i = 0; i < count; i++) {
				startTime = System.nanoTime();
				URL get_url = new URL(url + "view");
				HttpURLConnection get = null;
				get = (HttpURLConnection) get_url.openConnection();
				get.setRequestMethod("GET");
				int code = get.getResponseCode();
				if(code != 200)
				{
					writer.println("Error code: " + code);
					break;
				}
				JSONObject response = get_param(get.getInputStream()); 
			    
				length = Integer.parseInt(response.get("length").toString());
				String hash = new String(Base64.getUrlDecoder().decode(response.get("hash").toString()));
				String hashM;
				boolean guessed = false;
				//String inpoto = response.get("input").toString();
				writer.println("=======================================================================================================");
				writer.println("Server Hash: " + hash + ", Length: " + length);
				while (!guessed) {
					try {
						tries++;
						byte[] bytesOfInput = new byte[length];
						new Random().nextBytes(bytesOfInput);
						String input = new String(bytesOfInput);
						md.update(bytesOfInput);
						hashM = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
						//System.out.println("HashM: " + hashM + " hash: " + hash + " " + hashM.equals(hash) + " " + hashM.length() + " " +  hash.length());
						//System.out.println(input + " " + inpoto + " " + input.equals(inpoto) );
						if(hashM.equals(hash)) {
							String encoded = Base64.getUrlEncoder().encodeToString(bytesOfInput);
							HttpURLConnection post = null;
						    URL post_url = new URL(url + "check/" + encoded);
						    post = (HttpURLConnection) post_url.openConnection();
						    post.setRequestMethod("POST");
						    post.connect();
							if(post.getResponseCode() == 200) {
								guessed = true;
								currTime = System.nanoTime() - startTime;
								currTime /= 1000000000.0;
								writer.println("SUCCESS Hash: " + hashM + ", Input: " + input);
								writer.println("Number of tries: " + tries + ", Time elapsed: " + currTime + " sec.");
								totalTime += currTime;
								totalTries += tries;
								tries = 0;
								currTime = 0;
							} else {
								writer.println("FAIL Hash: " + hashM + ", Input: " + input);
							}
							
						}
					}catch( Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			writer.println("=======================================================================================================");
			writer.println("Input length: " + length + ", Num of hashes: " + count + ", Avg. Time: " + (totalTime / count) + " sec." + ", Hashes/second: " + (totalTries / totalTime));
			writer.println("=======================================================================================================");
			writer.close();
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
