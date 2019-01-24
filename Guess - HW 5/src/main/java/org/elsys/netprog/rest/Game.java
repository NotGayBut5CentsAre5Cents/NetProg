package org.elsys.netprog.rest;

import java.util.Random;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class Game {
	private String input;
	private int length;
	private String hash; 
	private byte bytesOfInput[];
	
	public Game(int length) {
		this.length = length;
		generate();
	}
	
	public void generate()
	{
		try {
			bytesOfInput = new byte[length];
			new Random().nextBytes(bytesOfInput);
			input = new String(bytesOfInput, Charset.forName("UTF-8"));
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			hash = new String(md.digest(bytesOfInput), Charset.forName("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean checkInput(String encoded) {
		String guess = new String(Base64.getDecoder().decode(encoded), Charset.forName("UTF-8"));
		if(input.equals(guess)) {
			generate();
			return true;
		}
		return false;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
	
	public String getHash() {
		return Base64.getEncoder().encodeToString(hash.getBytes());
	}

}
