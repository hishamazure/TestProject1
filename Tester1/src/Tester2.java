import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class Tester2 {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		
		
		
		
		String latitude1= "31.95522";
		String longitute1 = "35.94503";
		
		String latitude2= "32.0668425";
		String longitute2 = "36.0885771";
		
		String myJSON2 = "{\"locations\":[["+longitute1+","+latitude1+"],["+longitute2+","+latitude2+"]],\"metrics\":[\"distance\"],\"units\":\"km\"}";
		
		
		
		//OSFactory.enableHttpLoggingFilter(true);
		
		Client client = ClientBuilder.newClient();
		Entity<String> payload = Entity.json(myJSON2);
		Response response = client.target("https://api.openrouteservice.org/v2/matrix/driving-car")
		  .request()
		  .header("Authorization", "5b3ce3597851110001cf624806334f9f20a644969d2bbc120f02dc2d")
		  .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
		  .header("Content-Type", "application/json; charset=utf-8")
		  .post(payload);

		System.out.println("status: " + response.getStatus());
		System.out.println("headers: " + response.getHeaders());
		System.out.println("body:" + response.readEntity(String.class));
		

	}
/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Client client = ClientBuilder.newClient();
		Entity<String> payload = Entity.json({"locations":[[9.70093,48.477473],[9.207916,49.153868],[37.573242,55.801281],[115.663757,38.106467]]});
		Response response = client.target("https://api.openrouteservice.org/v2/matrix/driving-car")
		  .request()
		  .header("Authorization", "your-api-key")
		  .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
		  .header("Content-Type", "application/json; charset=utf-8")
		  .post(payload);

		System.out.println("status: " + response.getStatus());
		System.out.println("headers: " + response.getHeaders());
		System.out.println("body:" + response.readEntity(String.class));
		

	}*/

}
