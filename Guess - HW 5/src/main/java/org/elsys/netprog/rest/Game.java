package org.elsys.netprog.rest;

import java.util.Random;

import javax.xml.bind.DatatypeConverter;

import java.io.UnsupportedEncodingException;
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
		try {
			generate();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generate() throws UnsupportedEncodingException
	{
		try {
			bytesOfInput = new byte[length];
			new Random().nextBytes(bytesOfInput);
			input = new String(bytesOfInput);
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			md.update(bytesOfInput);
			hash = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean checkInput(String encoded) throws UnsupportedEncodingException {
		String guess = new String(Base64.getUrlDecoder().decode(encoded));
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
		return Base64.getUrlEncoder().encodeToString(hash.getBytes());
	}

}
