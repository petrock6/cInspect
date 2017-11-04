package cinspect.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jenkov.crawler.mt.io.CrawlerMT;
import com.jenkov.crawler.util.SameWebsiteOnlyFilter;

import cinspect.inspector.VulnerabilityAssessment;
import cinspect.inspector.XSSInspector;
import cinspect.GUI.GUI;
import cinspect.inspector.AppDoSInspector;
import cinspect.inspector.LFIInspector;
import cinspect.inspector.RCEInspector;
import cinspect.inspector.RFIInspector;
import cinspect.inspector.SQLInspector;
import cinspect.inspector.TimeSQLInspector;
import cinspect.inspector.UDRJSInspector;
import cinspect.web.ResourceRequestType;
import cinspect.web.WebDatabase;
import cinspect.web.WebResource;

import cinspect.GUI.*;

public class Main extends GUI{
	
	static String[] globalArgs;
	
	public static void main(String[] args) {
		globalArgs = args;
		launch(globalArgs);
	}

	public static void testSQLInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testSQLInspector(resources);
	}
	
	private static void testSQLInspector(List<WebResource> resources) {
		SQLInspector inspector = new SQLInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//System.out.println("");
				GUI.print(resource.getUrlPath() + " SQL vulnerable !!!");
			} else {
				//System.out.println(resource.getUrlPath() + " not SQL vulnerable");
			}
		}
	}

	public static void testRCEInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testRCEInspector(resources);
	}
	
	private static void testRCEInspector(List<WebResource> resources) {
		RCEInspector inspector = new RCEInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//System.out.println("");
				GUI.print(resource.getUrlPath() + " RCE vulnerable !!!");
			}
			//else
			//	System.out.println(resource.getUrlPath() + " not RCE vulnerable");
		}	
	}
	
	public static void testLFIInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testLFIInspector(resources);
	}
	
	private static void testLFIInspector(List<WebResource> resources) {
		LFIInspector inspector = new LFIInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//System.out.println("");
				GUI.print(resource.getUrlPath() + " LFI vulnerable !!!");
			}
			//else
			//	System.out.println(resource.getUrlPath() + " not LFI vulnerable");
		}	
	}
	
	public static void testXSSInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testXSSInspector(resources);
	}
	
	private static void testXSSInspector(List<WebResource> resources) {
		XSSInspector inspector = new XSSInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//System.out.println("");
				GUI.print(resource.getUrlPath() + " XSS vulnerable !!!");
			}
			//else
			//	System.out.println(resource.getUrlPath() + " not XSS vulnerable");
		}	
	}
	
	public static void testRFIInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testRFIInspector(resources);
	}
	
	private static void testRFIInspector(List<WebResource> resources) {
		RFIInspector inspector = new RFIInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//System.out.println("");
				GUI.print(resource.getUrlPath() + " RFI vulnerable !!!");
			}
			//else
			//	System.out.println(resource.getUrlPath() + " not RFI vulnerable");
		}	
	}
	
	public static void testTimeSQLInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testTimeSQLInspector(resources);
	}
	
	private static void testTimeSQLInspector(List<WebResource> resources) {
		TimeSQLInspector inspector = new TimeSQLInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//System.out.println("");
				GUI.print(resource.getUrlPath() + " TimeSQL vulnerable !!!");
			} else {
				//System.out.println(resource.getUrlPath() + " not TimeSQL vulnerable");
			}
		}
	}
	
	public static void testUDRJSInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testUDRJSInspector(resources);
	}
	
	private static void testUDRJSInspector(List<WebResource> resources) {
		UDRJSInspector inspector = new UDRJSInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//System.out.println("");
				System.out.println(resource.getUrlPath() + " UDRJS vulnerable !!!");
			} else {
				//System.out.println(resource.getUrlPath() + " not UDRJS vulnerable");
			}
		}
	}
	
	public static void testAppDoSInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testAppDoSInspector(resources);
	}
	
	private static void testAppDoSInspector(List<WebResource> resources) {
		AppDoSInspector inspector = new AppDoSInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//System.out.println("");
				System.out.println(resource.getUrlPath() + " AppDoS vulnerable !!!");
			} else {
				//System.out.println(resource.getUrlPath() + " not AppDoS vulnerable");
			}
		}
	}
	
}
