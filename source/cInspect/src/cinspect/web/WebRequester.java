package cinspect.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import cinspect.exceptions.UnimplementedFunctionException;

public class WebRequester {
	
	public static WebResponse requestResource(WebResource resource) throws UnimplementedFunctionException {
		HttpURLConnection connection;
		//WebResponse responseObject;
		
		int responseCode = -1;
		String responseString = null;
		
		try {
			if(resource.getRequestType() == ResourceRequestType.GET) {
				connection = (HttpURLConnection) new URL(resource.getUrlPath() + "?" + resource.getParametersAsEncodedString()).openConnection();

				//TODO: SET HEADERS
				//TODO: HANDLE COOKIES
			} else if(resource.getRequestType() == ResourceRequestType.POST) {
				connection = (HttpURLConnection) new URL(resource.getUrlPath()).openConnection();
				String parameters = resource.getParametersAsEncodedString();
				
				connection.setDoOutput(true);
				connection.setInstanceFollowRedirects(false);
				connection.setUseCaches(false);
				
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Length", Integer.toString(parameters.length()));
				
				//TODO: SET HEADERS
				//TODO: HANDLE COOKIES
				throw new UnimplementedFunctionException("Fatal error: POST is not implemented yet.");
			} else {
				throw new UnimplementedFunctionException("Fatal error: " + resource.getRequestType().toString() + " is an unsupported HTTP request type.");
			}
			
			responseString = readEntireStreamToString(connection);
			responseCode = connection.getResponseCode();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return new WebResponse(responseCode, responseString);
	}
	
	
	private static String readEntireStreamToString(HttpURLConnection connection) {
		Scanner scanner;
		String returnString = null;
		
		try {
			scanner = new Scanner(connection.getInputStream(), "UTF-8");
			returnString = scanner.useDelimiter("\\A").next();
			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return returnString;
	}

}
