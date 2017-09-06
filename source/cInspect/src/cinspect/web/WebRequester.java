package cinspect.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import cinspect.exceptions.UnimplementedFunctionException;

public class WebRequester {
	
	public WebResponse requestResource(WebResource resource) throws UnimplementedFunctionException {
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
				connection = null;
				//TODO: SET HEADERS
				//TODO: HANDLE COOKIES
				throw new UnimplementedFunctionException("Fatal error: POST is not implemented yet.");
			} else {
				throw new UnimplementedFunctionException("Fatal error: " + resource.getRequestType().toString() + " is an unsupported HTTP request type.");
			}
			
			//TODO: CLOSE THIS SCANNER SOMEHOW 
			responseString = readEntireStreamToString(connection);
			responseCode = connection.getResponseCode();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return new WebResponse(responseCode, responseString);
	}
	
	
	private String readEntireStreamToString(HttpURLConnection connection) {
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
	
	/*String urlParameters  = "param1=a&param2=b&param3=c";
	byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
	int    postDataLength = postData.length;
	String request        = "http://example.com/index.php";
	URL    url            = new URL( request );
	HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
	conn.setDoOutput( true );
	conn.setInstanceFollowRedirects( false );
	conn.setRequestMethod( "POST" );
	conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
	conn.setRequestProperty( "charset", "utf-8");
	conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
	conn.setUseCaches( false );
	try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
	   wr.write( postData );
	}*/
	
	
	//todo: handle cookies

}
