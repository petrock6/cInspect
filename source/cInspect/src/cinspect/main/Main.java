package cinspect.main;

import java.util.HashMap;
import java.util.Map;

import cinspect.inspector.VulnerabilityAssessment;
import cinspect.inspector.SQLInspector;
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
		
		//This information will be filled out by the crawler. 
		//You will only need to code this for prototyping purposes.
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("id", "1");
		parameters.put("submitButton", "Show Member");
		WebResource resource = new WebResource(ResourceRequestType.GET, "http://192.168.1.49/vulnerabilites/SQLi_test.php", parameters); //note your IP will be different.
		//End of information that will be filled out by the crawler. Assume you only have "resource". 
		
		Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
		
	}

}
