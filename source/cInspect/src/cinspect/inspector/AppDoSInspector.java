package cinspect.inspector;

import java.util.HashMap;
import java.util.Map;

import cinspect.exceptions.UnimplementedFunctionException;
import cinspect.web.WebRequester;
import cinspect.web.WebResource;
import cinspect.web.WebResponse;

public class AppDoSInspector implements Inspector {
	
	String[] AppDosIndicators = {"e", "9999999"};
	@Override
	public Map<String, VulnerabilityAssessment> isVulnerable(WebResource resource) {
		
		Map<String, String> parameters = resource.getParameters();
		Map<String, VulnerabilityAssessment> assessment = new HashMap<String, VulnerabilityAssessment>();
	
		for(Map.Entry<String, String> entry : parameters.entrySet()) {
			//We need to retain the original value for this permutation. tl;dr we don't want to modify more than one parameter at a time. 
			String originalValue = entry.getValue();
			
			//System.out.println("Mutating " + entry.getKey());
			
			entry.setValue("e"); //set value to known common string
			
			WebResource myResource = new WebResource(resource, parameters); //create a deep copy of the resource, but use the same parameters!
			try {
				//Using the request time for this. 
				//I don't actually care about the data, I just want the time difference.
				
				double time = WebRequester.requestTime(myResource);
				//System.out.println(time);
				
				//If you want to debug, print this. 
				//System.out.println(content); 
				
				if(time>10000){
					assessment.put(entry.getKey(), VulnerabilityAssessment.LIKELY_VULNERABLE);
				}
				
			} catch (UnimplementedFunctionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Set the map back to it's original value.
			entry.setValue(originalValue);
			
		}
		
		//Return the vulnerability assessment for each parameter. 
		return assessment;
	}

}
