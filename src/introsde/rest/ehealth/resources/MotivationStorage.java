package introsde.rest.ehealth.resources;

import java.io.IOException;
import java.net.URI;

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
import org.json.JSONObject;
import org.xml.sax.SAXException;

@Stateless
@LocalBean
@Path("/motivationStorage")
public class MotivationStorage {
	
	static String serverUri = "http://localhost:5703/sdelab/motivationAdapter";

	
	public static Response makeRequest(String path, String mediaType, String method, String input){

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
	
	public String requestToString(Response response){

		String content = response.readEntity(String.class);
        return content;
	}
	
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public String getMotivation() throws ParserConfigurationException, SAXException, IOException {
	
		String result = "ERROR";		
		String path = "";
        System.out.println("Getting motivation");		
        Response response = makeRequest(path, MediaType.APPLICATION_JSON, "get", result);
        String motivationString = requestToString(response);
        System.out.println(this.getClass()+" post");
        
        return motivationString;

    }
}
