package cinspect.inspector;

import java.util.HashMap;
import java.util.Map;

import cinspect.exceptions.UnimplementedFunctionException;
import cinspect.inspector.statuses.RCEInspectorStatus;
import cinspect.inspector.statuses.SQLInspectorStatus;
import cinspect.web.WebRequester;
import cinspect.web.WebResource;
import cinspect.web.WebResponse;
//TODO look for a better command then uptime. Find one with a unique string
public class RCEInspector implements Inspector {
	String[] RCEIndicators = {"load averages"};
	@Override
	public Map<String, VulnerabilityAssessment> isVulnerable(WebResource resource) {
		resource.getInspectStatus().setRce(RCEInspectorStatus.CURRENTLY_INSPECTING);
		Map<String, String> parameters = resource.getParameters();
		Map<String, VulnerabilityAssessment> assessment = new HashMap<String, VulnerabilityAssessment>();
		
		//We need to mutate every parameter of the request. 
		for(Map.Entry<String, String> entry : parameters.entrySet()) {
			//We need to retain the original value for this permutation. tl;dr we don't want to modify more than one parameter at a time. 
			String originalValue = entry.getValue();
			
			//System.out.println("Mutating " + entry.getKey());
			
			entry.setValue(originalValue + "&&uptime"); //add a unix command to detect code execution
						
			WebResource myResource = new WebResource(resource, parameters); //create a deep copy of the resource, but use the same parameters!
			
			//System.out.println("Query Permutation: " + myResource.getParametersAsEncodedString()); //Show the new query we are requesting!
			
			//Request the permutation. Check if this specific permutation is vulnerable. 
			try {
				//Create the connection, grab the data.
				WebResponse response = WebRequester.requestResource(myResource);
				String content = response.getContent().toLowerCase();
				
				//If you want to debug, print this. 
				//System.out.println(content); 
				
				//Search the data retrieved for any indicators:
				for(String indicator : RCEIndicators) {
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
		
		
		resource.getInspectStatus().setRce(RCEInspectorStatus.INSPECTED);
		//Return the vulnerability assessment for each parameter. 
		return assessment;
	}

}