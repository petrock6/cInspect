package cinspect.web;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

/**
 * A WebResource, which is basically a glorified GET or POST request. Use this with {@link WebRequester} to obtain data from the web server. 
 *  
 * @author Austin Bentley <ab6d9@mst.edu>
 */
public class WebResource {
	private ResourceRequestType requestType; //What method are we using for this URL?
	private String urlPath; //What is the file we are requesting from the server?
	private Map<String, String> parameters; //What is the query, or parameters, we are passing to the file?
	
	private ResourceCrawlStatus crawlStatus; //Has this URL been crawled for URLs yet?
	private ResourceInspectStatus inspectStatus; //Has this URL been inspected for vulnerabilites yet?
	

	/**
	 * Create a {@link WebResource} object with a {@link ResourceRequestType}, a complete URL path (not including parameters), and parameters as a {@link Map}. 
	 * @param requestType - {@link ResourceRequestType} - The type of request (whether or not it is GET, POST, HEAD, etc...)
	 * @param urlPath - {@link String} - The complete URL path (not including parameters.)
	 * @param parameters - {@link Map} - The parameters to the URL, as a Map. If there are none, then the map is empty (non-null.) 
	 */
	public WebResource(ResourceRequestType requestType, String urlPath, Map<String,String> parameters) {
		this.requestType = requestType;
		this.urlPath = urlPath;
		this.parameters = parameters;
		
		this.crawlStatus = ResourceCrawlStatus.IS_NOT_CRAWLED;
		this.inspectStatus = new ResourceInspectStatus();
	}
	
	/**
	 * Create a {@link WebResource} object with a {@link ResourceRequestType} and a complete URL path with parameters. 
	 * 
	 * @param requestType - {@link ResourceRequestType} - The type of request (whether or not it is GET, POST, HEAD, etc...)
	 * @param completeURL - {@link String} - The complete URL (including parameters, even if it is a POST URL!)
	 */
	public WebResource(ResourceRequestType requestType, String completeURL) {
		this.requestType = requestType;
		this.crawlStatus = ResourceCrawlStatus.IS_NOT_CRAWLED;
		this.inspectStatus = new ResourceInspectStatus();
		
		URI uri = null;
		try {
			uri = new URI(completeURL);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.urlPath = uri.getScheme() + "://" + uri.getAuthority() + uri.getPath();
		this.parameters = getQueryParameters(uri.getQuery());
	}
	
	/**
	 * A deep copy constructor for {@link WebResource}. 
	 * @param rhs
	 */
	public WebResource(WebResource rhs) {
		this.requestType = rhs.requestType;
		this.urlPath = rhs.urlPath;
		this.parameters = rhs.parameters;
		this.crawlStatus = rhs.crawlStatus;
		this.inspectStatus = rhs.inspectStatus;
	}
	
	/**
	 * A deep copy constructor for {@link WebResource}, however the parameters may change. 
	 * @param rhs
	 * @param parameters
	 */
	public WebResource(WebResource rhs, Map<String, String> parameters) {
		this.requestType = rhs.requestType;
		this.urlPath = rhs.urlPath;
		this.parameters = parameters;
		this.crawlStatus = rhs.crawlStatus;
		this.inspectStatus = rhs.inspectStatus;
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
			//result += pair.getValue();
			try {
				result += URLEncoder.encode(pair.getValue(), java.nio.charset.StandardCharsets.UTF_8.name());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		while(it.hasNext()) {
			pair = (Map.Entry<String, String>)it.next();
			
			result += "&";
			result += pair.getKey();
			if(!pair.getValue().equals("")) {
				result += "=";
				//result += pair.getValue();
				try {
					result += URLEncoder.encode(pair.getValue(), java.nio.charset.StandardCharsets.UTF_8.name());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		/*
		try {
			return URLEncoder.encode(result, java.nio.charset.StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return result;
	}
	
	/**
	 * Return the {@link ResourceRequestType} associated with the {@link WebResource}. 
	 * @return {@link ResourceRequestType}
	 */
	public ResourceRequestType getRequestType() {
		return requestType;
	}

	/**
	 * Set the {@link ResourceRequestType} associated with the {@link WebResource}. 
	 * @param requestType
	 */
	public void setRequestType(ResourceRequestType requestType) {
		this.requestType = requestType;
	}

	/**
	 * Returns the {@link Map} of parameters associated with the {@link WebResource}. 
	 * @return {@link Map}
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Sets the {@link Map} of parameters associated with the {@link WebResource}. 
	 * @param parameters
	 */
	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Gets the URL path of the {@link WebResource} -- does not contain parameters. To get parameters, use {@link setParameters}. 
	 * @return {@link String}
	 */
	public String getUrlPath() {
		//System.out.println("URL PATH : " + urlPath);
		return urlPath.replace("?", "");
	}

	/**
	 * Sets the URL path of the {@link WebResource} -- do not include parameters. 
	 * @param urlPath
	 */
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	/**
	 * Returns tthe {@link ResourceCrawlStatus} associated with the {@link WebResource}. 
	 * @return
	 */
	public ResourceCrawlStatus getCrawlStatus() {
		return crawlStatus;
	}

	/**
	 * Sets the {@link ResourceCrawlStatus} associated with the {@link WebResource}. 
	 * @param crawlStatus
	 */
	public void setCrawlStatus(ResourceCrawlStatus crawlStatus) {
		this.crawlStatus = crawlStatus;
	}

	/**
	 * Gets the {@link ResourceInspectStatus} associated with the {@link WebResource}. 
	 * @return
	 */
	public ResourceInspectStatus getInspectStatus() {
		return inspectStatus;
	}

	/**
	 * Sets the {@link ResourceInspectStatus} associated with the {@link WebResource}. 
	 * @param inspectStatus
	 */
	public void setInspectStatus(ResourceInspectStatus inspectStatus) {
		this.inspectStatus = inspectStatus;
	}
	
	/**
	 * Provided a HTTP query as a {@link String}, this function will decode it into it's key-value pairs as a {@link Map}. 
	 * @param query - {@link String} - The query of the URL (or POSTDATA). [Traditionally, this is whatever follows the ? in a URL.]
	 * @return {@link Map} - The mapping of the parameters. 
	 */
	private Map<String, String> getQueryParameters(String query) {
		Map<String,String> keyValues = new HashMap<String, String>();
		
		if(query != null && !query.equals("")) {
			String[] keyValuePairs = query.split("&");
			for(String keyValuePair : keyValuePairs) {
				int assignmentIndex = keyValuePair.indexOf("=");
				try {
					keyValues.put(URLDecoder.decode(keyValuePair.substring(0, assignmentIndex), "UTF-8"), URLDecoder.decode(keyValuePair.substring(assignmentIndex+1), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
				// 	TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return keyValues;
	}
	
	public boolean equals(WebResource rhs) {		
		return this.requestType.equals(rhs.getRequestType()) && this.urlPath.equals(rhs.getUrlPath()) && this.parameters.equals(rhs.getParameters());
	}
	
	
}
