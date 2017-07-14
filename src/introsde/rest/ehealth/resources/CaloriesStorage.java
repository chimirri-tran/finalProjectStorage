package introsde.rest.ehealth.resources;

import java.io.IOException;
import java.net.URI;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.glassfish.jersey.client.ClientConfig;
import org.xml.sax.SAXException;

@Stateless
@LocalBean
@Path("/caloriesStorage")
public class CaloriesStorage {
	
	static String serverUri = "http://localhost:5703/sdelab/caloriesAdapter";
	
	public static Response makeRequest(String path, String mediaType, String method,
			String paramName, String paramValue){
		URI server = UriBuilder.fromUri(serverUri).build();
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(server);
		Response response = null;
		if(method=="get")
		response = service.path(path)
				.queryParam(paramName, paramValue)
				.request(mediaType).accept(mediaType)
				.get(Response.class);

		return response;

	}
	
	public String responseToString(Response response){
		
		String content = response.readEntity(String.class);
		return content;
	}
	
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public String getCalories(@DefaultValue("apple") @QueryParam("food") String food) 
    		throws ParserConfigurationException, SAXException, IOException {

		String paramName = "food";		
		String paramValue = food;	
		String path = "";
        System.out.println(this.getClass()+"pre");
        Response response = makeRequest(path, MediaType.APPLICATION_JSON, "get",
        		paramName, paramValue);
        System.out.println(this.getClass()+"pos");	
        String caloriesString = responseToString(response);
        
        return caloriesString;

    }

}