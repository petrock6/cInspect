package cinspect.main;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cinspect.inspector.VulnerabilityAssessment;
import cinspect.inspector.XSSInspector;
import cinspect.inspector.LFIInspector;
import cinspect.inspector.RCEInspector;
import cinspect.inspector.RFIInspector;
import cinspect.inspector.SQLInspector;
import cinspect.inspector.AppDoSInspector;
import cinspect.web.ResourceRequestType;
import cinspect.web.WebResource;

public class Main {

	public static void main(String[] args) {
		/**
		 * Welcome to the playground, yay.
		 * 
		 * Once you are done prototyping, make sure nothing is broken and commit it. 
		 * If you commit something that is broken you are probably wasting both our time. 
		 * Don't push it without asking me first, please. 
		 */
		
		//Create our inspector object. 
		SQLInspector inspector = new SQLInspector(); 
		RCEInspector rceInspector = new RCEInspector();
		
		ArrayList<WebResource> resources = new ArrayList();
		
		resources.add(new WebResource(ResourceRequestType.GET, "http://192.168.1.49/vulnerabilites/LFI_test.php?file=blah&submitButon=Show+File"));
		resources.add(new WebResource(ResourceRequestType.GET, "http://192.168.1.49/vulnerabilites/RCE_test_get.php?host=example.com&submitButton=Ping+host"));
		resources.add(new WebResource(ResourceRequestType.GET, "http://192.168.1.49/vulnerabilites/RFI_test.php?file=TESTER&submitButton=Show+File"));
		resources.add(new WebResource(ResourceRequestType.GET, "http://192.168.1.49/vulnerabilites/SQLi_test.php?id=htns&submitButton=Show+Member"));
		resources.add(new WebResource(ResourceRequestType.GET, "http://192.168.1.49/vulnerabilites/XSS_test.php?name=htns&submitButton=Display+Name"));
		resources.add(new WebResource(ResourceRequestType.GET, "http://192.168.1.49/vulnerabilites/AppDoS_test.php?password=uoifadjkvn&submitButton=Search"));
		
		testSQLInspector(resources);
		System.out.println("\n\n\n");
		testRCEInspector(resources);
		System.out.println("\n\n\n");
		testLFIInspector(resources);
		System.out.println("\n\n\n");
		testXSSInspector(resources);
		System.out.println("\n\n\n");
		//testRFIInspector(resources);
		System.out.println("\n\n\n");
		testAppDoSInspector(resources);
		System.out.println("\n\n\n");
		
	}
	
	private static void testSQLInspector(List<WebResource> resources) {
		SQLInspector inspector = new SQLInspector();
		System.out.println("Testing: SQLInspector");
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				System.out.println(resource.getUrlPath() + " SQL vulnerable !!!");
			} else {
				System.out.println(resource.getUrlPath() + " not SQL vulnerable");
			}
		}
	}
	
	private static void testRCEInspector(List<WebResource> resources) {
		RCEInspector inspector = new RCEInspector();
		System.out.println("Testing: RCEInspector");
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty())
				System.out.println(resource.getUrlPath() + " RCE vulnerable !!!");
			else
				System.out.println(resource.getUrlPath() + " not RCE vulnerable");
		}	
	}
	
	private static void testLFIInspector(List<WebResource> resources) {
		LFIInspector inspector = new LFIInspector();
		
		System.out.println("Testing: LFIInspector");
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty())
				System.out.println(resource.getUrlPath() + " LFI vulnerable !!!");
			else
				System.out.println(resource.getUrlPath() + " not LFI vulnerable");
		}	
	}
	
	private static void testXSSInspector(List<WebResource> resources) {
		XSSInspector inspector = new XSSInspector();
		
		System.out.println("Testing: XSSInspector");
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty())
				System.out.println(resource.getUrlPath() + " XSS vulnerable !!!");
			else
				System.out.println(resource.getUrlPath() + " not XSS vulnerable");
		}	
	}
	
	private static void testRFIInspector(List<WebResource> resources) {
		RFIInspector inspector = new RFIInspector();
		
		System.out.println("Testing: RFIInspector");
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty())
				System.out.println(resource.getUrlPath() + " RFI vulnerable !!!");
			else
				System.out.println(resource.getUrlPath() + " not RFI vulnerable");
		}	
	}
	
	private static void testAppDoSInspector(List<WebResource> resources) {
		AppDoSInspector inspector = new AppDoSInspector();
		
		System.out.println("Testing: AppDoSInspector");
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty())
				System.out.println(resource.getUrlPath() + " AppDoS vulnerable !!!");
			else
				System.out.println(resource.getUrlPath() + " not AppDoS vulnerable");
		}	
	}
	
	
	
}
