package org.elsys.netprog.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elsys.netprog.rest.Game;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/game")
public class GameController {
	static ArrayList<Game> games = new ArrayList<Game>();
	@POST
	@Path("/startGame")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response startGame() throws URISyntaxException{
		//TODO: Add your code here
		Game g = new Game(games.size() == 0 ? 0 : games.size(), 4);
		games.add(g);
		return Response.created(new URI("/games")).entity(g.getGameId()).build();
	}
	
	@PUT
	@Path("/guess/{id}/{guess}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(@PathParam("id") String gameId, @PathParam("guess") String guess) throws Exception{
		int status = 404;
		Game currGame = null;
		JSONObject JSONResult = null;
		for(Game g : games) {
			System.out.println(g.getGameId().equals(gameId));
			System.out.println(g.getGameId() + " " + gameId);
			if(g.getGameId().equals(gameId)) {
				status = 200;
				currGame = g;
			}
		}
		int guessI = 0;
		try {
			guessI = Integer.parseInt(guess);
			if(checkDupes(guessI) || guessI < 1000 || guessI > 9999) {
				status = 400;
			}
		}catch(Exception e) {
			status = 400;
		}
		if(status == 200) {
			currGame.tryGuess(guess);
			JSONResult = currGame.json();
		}
		return Response.status(status).entity(JSONResult).build();
	}
	
	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response getGames(){
		JSONArray allGames  = new JSONArray();
		for(Game g : games) {
			try {
				allGames.add(g.jsonArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Response.status(200).entity(allGames).build();
	}
	
	public boolean checkDupes(int n) { 
		boolean[] digits = new boolean[10];
		int i;
		while(n > 0) {
			i = n%10;
			if(digits[i])
				return true;
			digits[i] = true;
			n /= 10;
		}
		return false;
	}
	
}
