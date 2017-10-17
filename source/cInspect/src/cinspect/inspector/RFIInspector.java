package cinspect.inspector;

import java.util.HashMap;
import java.util.Map;

import cinspect.exceptions.UnimplementedFunctionException;
import cinspect.web.WebRequester;
import cinspect.web.WebResource;
import cinspect.web.WebResponse;

public class RFIInspector implements Inspector {

  //Not necessarily just in PHP code here, but is most likely
  String[] RFIIndicators = {"domain in examples without prior coordination or asking for permission.</p>"};
	
  public Map<String, VulnerabilityAssessment> isVulnerable(WebResource resource) {
  /*
   * We can tell if a web page is vulnerable to Remote File Inclusion if 
   * 
   * Here's what this code does:
   * 1. Mutates a GET/POST parameter.
   * 2. Launches the request with the modified parameter. (You can tell if something is SQL Injection vulnerable by tacking a ' at the end.)
   * 3. Determines if it is vulnerable by looking at the indicator(s) above.
   * 4. If it is, it adds it to the assessment mapping.
   * 5. Continue until there are no more parameters to mutate. 
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
	 
	 
	 entry.setValue("http://example.com"); //add to attempt to identify a vulnerability.
			
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
	   for(String indicator : RFIIndicators) {
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
				
    return assessment;
	
  }

}
