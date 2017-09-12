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

public class WebResource {
	private ResourceRequestType requestType; //What method are we using for this URL?
	private String urlPath; //What is the file we are requesting from the server?
	private Map<String, String> parameters; //What is the query, or parameters, we are passing to the file?
	
	private ResourceCrawlStatus crawlStatus; //Has this URL been crawled for URLs yet?
	private ResourceInspectStatus inspectStatus; //Has this URL been inspected for vulnerabilites yet?
	
	
	public WebResource(ResourceRequestType requestType, String urlPath, Map<String,String> parameters) {
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
	
	public WebResource(WebResource rhs) {
		this.requestType = rhs.requestType;
		this.urlPath = rhs.urlPath;
		this.parameters = rhs.parameters;
		this.crawlStatus = rhs.crawlStatus;
		this.inspectStatus = rhs.inspectStatus;
	}
	
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
	
	public ResourceRequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(ResourceRequestType requestType) {
		this.requestType = requestType;
	}

	public Map<String, String> getParameters() {
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
	
	private Map<String, String> getQueryParameters(String query) {
		Map<String,String> keyValues = new HashMap<String, String>();
		
		String[] keyValuePairs = query.split("&");
		for(String keyValuePair : keyValuePairs) {
			int assignmentIndex = keyValuePair.indexOf("=");
			try {
				keyValues.put(URLDecoder.decode(keyValuePair.substring(0, assignmentIndex), "UTF-8"), URLDecoder.decode(keyValuePair.substring(assignmentIndex+1), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return keyValues;
	}
	
	
	
	
}
