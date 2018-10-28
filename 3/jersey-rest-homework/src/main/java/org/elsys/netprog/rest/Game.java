package org.elsys.netprog.rest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.json.simple.JSONObject;

public class Game {
	private String gameId = "";
	private int cowsN, bullsN;
	private int turnsCount;
	private boolean success;
	private int secret = 0;
	private int secretL;
	public Game(int gameId, int secretL) {
		Random rand = new Random();
		this.gameId = "" + gameId;
		this.cowsN = 0;
		this.bullsN = 0;
		this.turnsCount = 0;
		this.success = false;
		this.secretL = secretL;
		ArrayList<Integer> pool = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		int id;
		for(int i = 0; i < secretL; i++) {
			do {
				id =  rand.nextInt(pool.size() - 1);
			} while(pool.get(id) == 0);
			this.secret = this.secret * 10 + pool.remove(id);
		}
	}
	
	public String getGameId() {
		return gameId;
	}
	
	public void tryGuess(String guess) {
		turnsCount++;
		bullsN = 0;
		cowsN = 0;
		String targetS = Integer.toString(secret);
		for(int i = 0; i < secretL; i++) {
			if(guess.charAt(i) == targetS.charAt(i)) {
				bullsN++;
			}else if(targetS.contains(guess.charAt(i) + "")){
				cowsN++;
			}
		}
		if(bullsN == secretL) {
			success = true;
		}
	}
	
	public JSONObject json() {
		JSONObject obj = new JSONObject();
		obj.put("gameId", gameId);
		obj.put("cowsNumber", cowsN);
		obj.put("bullsNumber", bullsN);
		obj.put("turnsCount", turnsCount);
		obj.put("success", success);
		return obj;
	}
	
	public JSONObject jsonArray() {
		JSONObject obj = new JSONObject();
		obj.put("gameId", gameId);
		obj.put("turnsCount", turnsCount);
		obj.put("secret", success ? Integer.toString(secret) : "****");
		obj.put("success", success);
		return obj;
	}
}
