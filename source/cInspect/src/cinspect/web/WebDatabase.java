package cinspect.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cinspect.inspector.statuses.AppDoSInspectorStatus;
import cinspect.inspector.statuses.CCInspectorStatus;
import cinspect.inspector.statuses.LFIInspectorStatus;
import cinspect.inspector.statuses.PHPInfoInspectorStatus;
import cinspect.inspector.statuses.RCEInspectorStatus;
import cinspect.inspector.statuses.RFIInspectorStatus;
import cinspect.inspector.statuses.SQLInspectorStatus;
import cinspect.inspector.statuses.SSNInspectorStatus;
import cinspect.inspector.statuses.XSSInspectorStatus;

public class WebDatabase {
	private static List<WebResource> resourceDatabase = new ArrayList<WebResource>();
	
	/**
	 * Create a new database of {@link WebResource}s.
	 */
	/*
	WebDatabase() {
		resourceDatabase = new ArrayList<WebResource>();
	}*/
	
	public static List<WebResource> getDatabase() {
		System.out.print("You shouldnt use this function unless you really know what youre doing");
		return resourceDatabase;
	}
	
	/**
	 * Add a {@link WebResource} to the database. 
	 * @param resource
	 */
	public synchronized static void addResource(WebResource resource) {
		for(WebResource resourceIter : resourceDatabase) {
			if(resource.equals(resourceIter))
				return;
		}
		resource.setCrawlStatus(ResourceCrawlStatus.IS_NOT_CRAWLED);
		resource.setInspectStatus(new ResourceInspectStatus());
		resourceDatabase.add(resource);
	}
	
	public synchronized static void addResource(ResourceRequestType requestType, String completeURL) {
		WebResource resource = new WebResource(requestType, completeURL);
		
		for(WebResource resourceIter : resourceDatabase) {
			if(resource.equals(resourceIter))
				return;
		}		
		
		resource.setCrawlStatus(ResourceCrawlStatus.IS_NOT_CRAWLED);
		resource.setInspectStatus(new ResourceInspectStatus());		
		resourceDatabase.add(resource);
	}
	
	public synchronized static void addResource(ResourceRequestType requestType, String urlPath, HashMap<String,String> parameters) {
		WebResource resource = new WebResource(requestType, urlPath, parameters);
		
		for(WebResource resourceIter : resourceDatabase) {
			if(resource.equals(resourceIter))
				return;
		}		
		
		resource.setCrawlStatus(ResourceCrawlStatus.IS_NOT_CRAWLED);
		resource.setInspectStatus(new ResourceInspectStatus());		
		resourceDatabase.add(resource);
	}
	
	/**
	 * Get a {@link WebResource} which hasn't been crawled yet.
	 * @return An uncrawled {@link WebResource}, or null if there is none uncrawled in database.
	 */
	public synchronized static WebResource getUncrawledResource() {
		for(WebResource resource : resourceDatabase) {
			if(resource.getCrawlStatus() == ResourceCrawlStatus.IS_NOT_CRAWLED)
				return resource;
		}
		
		return null;
	}
	
	/**
	 * Get a {@link WebResource} which hasn't had some module ran on it yet.
	 * @return A not-inspector {@link WebResource}, or null if all URLs in DB have been inspected for all vulns.
	 */
	public synchronized static WebResource getUnscannedResource() {
		for(WebResource resource : resourceDatabase) {
			ResourceInspectStatus inspectStatus = resource.getInspectStatus();
			
			if(inspectStatus.getAppDoS() == AppDoSInspectorStatus.NOT_INSPECTED
			|| inspectStatus.getCc() == CCInspectorStatus.NOT_INSPECTED
			|| inspectStatus.getLfi() == LFIInspectorStatus.NOT_INSPECTED
			|| inspectStatus.getPhpinfo() == PHPInfoInspectorStatus.NOT_INSPECTED
			|| inspectStatus.getRce() == RCEInspectorStatus.NOT_INSPECTED
			|| inspectStatus.getRfi() == RFIInspectorStatus.NOT_INSPECTED
			|| inspectStatus.getSql() == SQLInspectorStatus.NOT_INSPECTED
			|| inspectStatus.getSsn() == SSNInspectorStatus.NOT_INSPECTED
			|| inspectStatus.getXss() == XSSInspectorStatus.NOT_INSPECTED)
				return resource;
		}
		
		return null;
	}
	
	public synchronized static void printDatabase() {
		
		for(WebResource resource : resourceDatabase) {
			String entry = resource.getRequestType().name(); 
			entry += " ";
			entry += resource.getUrlPath();
			entry += resource.getParametersAsEncodedString();
			System.out.println(entry);
		}
	}
	
	//only inspect one vuln class on one url at a time, otherwise appdos module is useless!
	//we should never remove a resource because we don't want to keep rescanning the same urls. 
}
