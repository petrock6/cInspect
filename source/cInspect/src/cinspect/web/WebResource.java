package cinspect.web;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import cinspect.web.*;

public class WebResource {
	private ResourceRequestType requestType; //What method are we using for this URL?
	private String urlPath; //What is the file we are requesting from the server?
	private HashMap<String, String> parameters; //What is the query, or parameters, we are passing to the file?
	
	private ResourceCrawlStatus crawlStatus; //Has this URL been crawled for URLs yet?
	private ResourceInspectStatus inspectStatus; //Has this URL been inspected for vulnerabilites yet?
	
	
	public WebResource(ResourceRequestType requestType, String urlPath, HashMap<String,String> parameters) {
		this.requestType = requestType;
		this.urlPath = urlPath;
		this.parameters = parameters;
		
		this.crawlStatus = ResourceCrawlStatus.IS_NOT_CRAWLED;
		this.inspectStatus = new ResourceInspectStatus();
	}
	
	public WebResource(ResourceRequestType requestType, String completeURL) {
		this.requestType = requestType;
		this.crawlStatus = ResourceCrawlStatus.IS_NOT_CRAWLED;
		this.inspectStatus = new ResourceInspectStatus();
		
		URI uri;
		try {
			uri = new URI(completeURL);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		urlPath = uri.getScheme() + "://" + uri.getAuthority() + uri.getPath();
		
		String query = uri.getQuery();
		//TODO: use URLEncodedUtils to decompose query string
		//SEE: https://stackoverflow.com/questions/13592236/parse-a-uri-string-into-name-value-collection
	}
	
	/**
	 * Takes the parameters and returns them as an encoded string, suitable
	 * for HTTP communications. 
	 * @return {@link String} of encoded HTTP data. 
	 */
	public String getParametersAsEncodedString() {
		String result = ""; //may need to convert this to stringbuilder for performance reasons.
		Iterator<Entry<String, String>> it = parameters.entrySet().iterator();
		Map.Entry<String, String> pair;
		
		try {
			pair = (Map.Entry<String, String>)it.next();
		} catch(NoSuchElementException e) {
			return "";
		}
		
		result += pair.getKey();
		if(!pair.getValue().equals("")) {
			result += "=";
			result += pair.getValue();
		}
		
		
		while(it.hasNext()) {
			pair = (Map.Entry<String, String>)it.next();
			
			result += "&";
			result += pair.getKey();
			if(!pair.getValue().equals("")) {
				result += "=";
				result += pair.getValue();
			}
		}
		
		try {
			return URLEncoder.encode(result, java.nio.charset.StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public ResourceRequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(ResourceRequestType requestType) {
		this.requestType = requestType;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public ResourceCrawlStatus getCrawlStatus() {
		return crawlStatus;
	}

	public void setCrawlStatus(ResourceCrawlStatus crawlStatus) {
		this.crawlStatus = crawlStatus;
	}

	public ResourceInspectStatus getInspectStatus() {
		return inspectStatus;
	}

	public void setInspectStatus(ResourceInspectStatus inspectStatus) {
		this.inspectStatus = inspectStatus;
	}
	
	
	
	
	
}
