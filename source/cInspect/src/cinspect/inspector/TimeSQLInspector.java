package cinspect.inspector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cinspect.exceptions.UnimplementedFunctionException;
import cinspect.web.WebRequester;
import cinspect.web.WebResource;
import cinspect.web.WebResponse;

public class TimeSQLInspector implements Inspector {

	public Map<String, VulnerabilityAssessment> isVulnerable(WebResource resource) {
		/*
		 * We can tell if a URL is vulnerable to Time based SQL injection if we add a command to sleep and the response takes longer
		 *  
		 * 
		 * Special note: You shouldn't have any print statements when you push your code, these are added for clarification purposes.
		 * The vulnerabilites will be printed out by the main thread of the program once you return the hashmap. 
		 */
		//First, create a copy of the existing resource. We don't want to accidentally modify the WebDatabase.
		Map<String, String> parameters = resource.getParameters();
		Map<String, VulnerabilityAssessment> assessment = new HashMap<String, VulnerabilityAssessment>();
		//We need to mutate every parameter of the request. 
		for(Map.Entry<String, String> entry : parameters.entrySet()) {
			//We need to retain the original value for this permutation. tl;dr we don't want to modify more than one parameter at a time. 
			String originalValue = entry.getValue();
			
			//System.out.println("Mutating " + entry.getKey());
			
			entry.setValue(originalValue + " or SLEEP(10)"); //there may need to be different commands for diferent types of servers
			//the value for sleep has to be hard coded variables wouldnt work, if time is changed make sure to change below
			
			WebResource myResource = new WebResource(resource, parameters); //create a deep copy of the resource, but use the same parameters!
			
			//System.out.println("Query Permutation: " + myResource.getParametersAsEncodedString()); //Show the new query we are requesting!
			
			//Request the permutation. Check if this specific permutation is vulnerable. 
			try {
				double difference = WebRequester.requestTime(myResource);
				//this will output responce time for debugging
				////System.out.println("Responce Time in milliseconds: " + difference);
				if(difference >= 2*1000) { // 10 is used in sleep command and 1000 is for seconds to milliseconds coversion
					//if time is moved from milliseconds change the 1000
					//if time in sleep is changed then the 10 must be changed
					//System.out.println("LIKELY VULNERABILITY DETECTED : parameter is " + entry.getKey() + "!");
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