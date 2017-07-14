package introsde.rest.ehealth.resources;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.Random;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

@Stateless
@LocalBean
@Path("/exercizeStorage")
public class ExercizeStorage {
	
	static String serverUri = "http://localhost:5703/sdelab/exercizeAdapter";
	
	public static Response makeRequest(String path, String mediaType, String method, 
			String input){

		URI server = UriBuilder.fromUri(serverUri).build();
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(server);
		Response response = null;
		if(method=="get")
		response = service.path(path)
				.request(mediaType).accept(mediaType)
				.get(Response.class);

		return response;

	}
	

	public String chooseExercize(JSONObject objExercize){
		System.out.println("arrExercize"+objExercize.get("exercizes"));
		int rnd = new Random().nextInt(((JSONArray) objExercize.get("exercizes")).length());
		if (!((JSONArray)objExercize.get("exercizes")).get(rnd).equals("<p>.</p>")
				&& !((JSONArray) objExercize.get("exercizes")).get(rnd).equals(""))
			return (String) ((JSONArray) objExercize.get("exercizes")).get(rnd);
		else {
			return chooseExercize(objExercize);
		}
	}
	
	public String responseToString(Response response){
		

		String contentExercize = response.readEntity(String.class);
		System.out.println("contentExercize"+contentExercize);
		JSONObject objExercize = new JSONObject(contentExercize);
		System.out.println("objExercize"+objExercize);
		System.out.println("arrExercize"+objExercize.get("exercizes"));
		
		String content = chooseExercize(objExercize).toString();
		
		JSONObject newObjExercize = new JSONObject();
		newObjExercize.append("exercize", content);
		
		return newObjExercize.toString();
		/*		
		String contentExercize = response.readEntity(String.class);
		JSONObject objExercize = new JSONObject(contentExercize);
		JSONArray arrExercize = new JSONArray(objExercize.get("results"));
		
		String content = chooseExercize(arrExercize).toString();
		
		JSONObject newObjExercize = new JSONObject();
		newObjExercize.append("exercize", content);
		
		return newObjExercize.toString();
		*/
	}
	
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public String getExercize() 
    		throws ParserConfigurationException, SAXException, IOException {
	
		String path = "";
        System.out.println(this.getClass()+"pre");	
        Response response = makeRequest(path, MediaType.APPLICATION_JSON, "get", "");
        String exercizeString = responseToString(response);
        System.out.println(this.getClass()+"pos");	
        
        return exercizeString;

    }

}
