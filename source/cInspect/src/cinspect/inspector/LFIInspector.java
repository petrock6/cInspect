package cinspect.inspector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cinspect.exceptions.UnimplementedFunctionException;
import cinspect.inspector.statuses.LFIInspectorStatus;
import cinspect.inspector.statuses.SQLInspectorStatus;
import cinspect.web.WebRequester;
import cinspect.web.WebResource;
import cinspect.web.WebResponse;

public class LFIInspector implements Inspector {

	//$FreeBSD is indicator because if vulnerable to LFI, this inspector will open the /etc/passwd file, which contains this phrase (in FreeBSD)
	String[] LFIIndicators = {"$FreeBSD:"};
	
	public Map<String, VulnerabilityAssessment> isVulnerable(WebResource resource) {
		resource.getInspectStatus().setLfi(LFIInspectorStatus.CURRENTLY_INSPECTING);
		/*
		 * We can tell if a URL is vulnerable to LFI by attempting to traverse directories and include in /etc/passwd file
		 * 
		 * 1. Mutates a GET/POST parameter.
		 * 2. Launches the request with the modified parameter. 
		 * 3. Determines if it is vulnerable by looking at the indicator above.
		 * 4. If it is, it adds it to the assessment mapping.
		 * 5. Continue until there are no more parameters to mutate. 
		 */
		
		//First, create a copy of the existing resource. We don't want to accidentally modify the WebDatabase.
		Map<String, String> parameters = resource.getParameters();
		Map<String, VulnerabilityAssessment> assessment = new HashMap<String, VulnerabilityAssessment>();
		
		//mutate every parameter of the request. 
		for(Map.Entry<String, String> entry : parameters.entrySet()) {

			//We need to retain the original value for this permutation. 
			String originalValue = entry.getValue();
			
			
			entry.setValue("../../../../../../../../../../../../../../../../etc/passwd"); //replace parameter with LFI attempt
			
			WebResource myResource = new WebResource(resource, parameters); //create a deep copy of the resource, but use the same parameters
			
			
			//Request the permutation. Check if this specific permutation is vulnerable. 
			try {
				//Create the connection, grab the data.
				WebResponse response = WebRequester.requestResource(myResource);
				String content = response.getContent().toLowerCase();
				
				//Search the data retrieved for any indicators:
				for(String indicator : LFIIndicators) {
					//We see an indicator -- it's vulnerable!!!
					if(content.contains(indicator.toLowerCase())) {
						//System.out.println("LIKELY VULNERABILITY DETECTED : parameter is " + entry.getKey() + "!");
						assessment.put(entry.getKey(), VulnerabilityAssessment.LIKELY_VULNERABLE);
					}
				}
				
			} catch (UnimplementedFunctionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Set the map back to it's original value.
			entry.setValue(originalValue);
			
		}
		
		resource.getInspectStatus().setLfi(LFIInspectorStatus.INSPECTED);
		return assessment;
		

	}

}