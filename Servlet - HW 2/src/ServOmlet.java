

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServOmlet
 */
@WebServlet("/ServOmlet")
public class ServOmlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, String> queries = new HashMap<String, String>();
    /**
     * Default constructor. 
     */
    public ServOmlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"  <head>\r\n" + 
				"    <meta charset=\"utf-8\">\r\n" +
				"  </head>\r\n" +
				"  <body>\r\n" +
				"    <form action = \"ServOmlet\" method = \"POST\">\r\n" +
	            "      Key: <input type = \"text\" name = \"key\">\r\n" +
	            "      <br/>\r\n" +
	            "      Value: <input type = \"text\" name = \"value\">\r\n" +
	            "      <input type = \"submit\" value = \"Submit\">\r\n" +
	            "    </form>\r\n");
		out.println("    <table>\r\n" +
	            "      <tbody>\r\n" +
				"        <tr>\r\n" +
	            "          <th> Key </th>\r\n" + 
				"          <th> Value </th>\r\n" + 
	            "        </tr>\r\n");
		for(Map.Entry<String, String> entry : queries.entrySet()) {
	    	  String key = entry.getKey();
	    	  String value = entry.getValue();
	    	  out.println("        <tr>\r\n" +
	    			  "          <td>" + key + "</td>\r\n" +
	    			  "          <td>" + value + "</td>\r\n" +
	    			  "        </tr>\r\n");
		}
		out.println("      </tbody>\r\n" +
				"    </table>" + 
				"  </body>\r\n" +
	            "</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		queries.put(key, value);
		doGet(request, response);
	}

}
