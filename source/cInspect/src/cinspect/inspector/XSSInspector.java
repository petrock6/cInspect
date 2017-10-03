package cinspect.inspector;

import java.util.HashMap;
import java.util.Map;

import cinspect.exceptions.UnimplementedFunctionException;
import cinspect.web.WebRequester;
import cinspect.web.WebResource;
import cinspect.web.WebResponse;

public class XSSInspector implements Inspector {

	
	String[] XSSIndicator = {"___cinspect___indicator__'_\"_<_>_", "___cinspect__indicator__\\'__\\\"_<_>_"};
	
	@Override
	public Map<String, VulnerabilityAssessment> isVulnerable(WebResource resource) {
		
		//Copies the existing resource so as to not modify the original version
		Map<String, String> parameters = resource.getParameters();
		Map<String, VulnerabilityAssessment> assessment = new HashMap<String, VulnerabilityAssessment>();
		
		for(Map.Entry<String, String> entry: parameters.entrySet())
		{
			String originalValue = entry.getValue();
			//System.out.println("Mutating " + entry.getKey());
			entry.setValue(originalValue + "___cinspect___indicator__'_\"_<_>_");
			WebResource myResource = new WebResource(resource, parameters);
			//System.out.println("Query Permutation: " + myResource.getParametersAsEncodedString());
			
			try
			{
				WebResponse response = WebRequester.requestResource(myResource);
				String content = response.getContent().toLowerCase();
				//System.out.println(content);
				for(String indicator : XSSIndicator)
				{
					if(content.contains(indicator.toLowerCase()))
					{
						//System.out.println("Definitive Vulnerability Detected: Parameter is " + entry.getKey() + "!");
						assessment.put(entry.getKey(), VulnerabilityAssessment.VULNERABLE);
						//System.out.println("HELLO");
					}
				}
			} catch (UnimplementedFunctionException e)
			{
				e.printStackTrace();
			}
			entry.setValue(originalValue);
		}
		// TODO Auto-generated method stub
		return assessment;
	}

}
