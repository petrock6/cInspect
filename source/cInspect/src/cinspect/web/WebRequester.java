package cinspect.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import cinspect.exceptions.UnimplementedFunctionException;

/**
 * This is a static class which, when given a {@link WebResource}, the class will request the resource and obtain the data associated with it. 
 * @author Austin Bentley <ab6d9@mst.edu>
 */
public class WebRequester {
	
	/**
	 * Given a {@link WebResource}, this function will request the resource and return a {@link WebResponse} object (which itself contains the content of the response from the server.)
	 * @param resource - {@link WebResource} - The resource to request from the server. 
	 * @return {@link WebResponse} - The response from the server, containing the HTTP code and the response content. 
	 * @throws UnimplementedFunctionException - Thrown if for some reason the requested {@link WebResource} is not a GET or POST request. 
	 */
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
				//System.out.println("Warning: POST is not implemented yet.");
				//throw new UnimplementedFunctionException("Fatal error: POST is not implemented yet.");
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
	
	public static double requestTime(WebResource resource) throws UnimplementedFunctionException {
		//sends back the response time
		HttpURLConnection connection;
		//WebResponse responseObject;
		
		int responseCode = -1;
		String responseString = null;
		double difference = -1;
		long start_time = System.nanoTime();
		
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
				//throw new UnimplementedFunctionException("Fatal error: POST is not implemented yet.");
			} else {
				throw new UnimplementedFunctionException("Fatal error: " + resource.getRequestType().toString() + " is an unsupported HTTP request type.");
			}
			
			responseString = readEntireStreamToString(connection);
			responseCode = connection.getResponseCode();
			long end_time = System.nanoTime(); 
			difference = (end_time - start_time) / 1e6;// response time in milliseconds
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return difference;
	}
	
	
	private static String readEntireStreamToString(HttpURLConnection connection) {
		Scanner scanner;
		String returnString = null;
		
		try {
			scanner = new Scanner(connection.getInputStream(), "UTF-8");
			returnString = scanner.useDelimiter("\\A").next();
			scanner.close();
		} catch(FileNotFoundException e) {
			return "";
		} catch (IOException e) {
			return "";
			//e.printStackTrace();
		}
		 
		 return returnString;
	}

}
