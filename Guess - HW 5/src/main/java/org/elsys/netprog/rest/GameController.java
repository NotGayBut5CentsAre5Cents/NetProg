package org.elsys.netprog.rest;


import java.net.URISyntaxException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;


@Path("/game")
public class GameController {
	public static Game game = new Game(2);
	
	@POST
	@Path("/check/{guess}") 
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response check(@PathParam("guess") String guess) throws URISyntaxException{
		Response r;
		if(game.checkInput(guess)) {
			r = Response.status(200).build();
		} else {
			r = Response.status(406).build(); 
		}
		return r;
	}
	
	@GET
	@Path("/view")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response view() {
		JSONObject arr = new JSONObject();
		arr.put("length", game.getLength());
		arr.put("hash", game.getHash());
		return Response.status(200).entity(arr).build();
	}
}
