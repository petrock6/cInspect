package cinspect.inspector;

import java.util.HashMap;
import java.util.Map;

import cinspect.exceptions.UnimplementedFunctionException;
import cinspect.inspector.statuses.SQLInspectorStatus;
import cinspect.inspector.statuses.UDRJSInspectorStatus;
import cinspect.web.WebRequester;
import cinspect.web.WebResource;
import cinspect.web.WebResponse;

public class UDRJSInspector implements Inspector {

	
	String[] UDR_JS_Indicator = {"window.location.href=\"http://example.com\";", "window.location.href=\"http://example.com\" ;",
			"window.location.href= \"http://example.com\";", "window.location.href =\"http://example.com\";",
			"window.location.href= \"http://example.com\" ;", "window.location.href = \"http://example.com\" ;",
			"window.location.href = \"http://example.com\";", "window.location.href =\"http://example.com\" ;",
			"window.location.href=\'http://example.com\';", "window.location.href=\'http://example.com\' ;",
			"window.location.href= \'http://example.com\';", "window.location.href =\'http://example.com\';",
			"window.location.href= \'http://example.com\' ;", "window.location.href = \'http://example.com\' ;",
			"window.location.href = \'http://example.com\';", "window.location.href =\'http://example.com\' ;",
			"window.location.replace(\"http://example.com\");", "window.location.replace( \"http://example.com\");",
			"window.location.replace(\"http://example.com\" );", "window.location.replace( \"http://example.com\" );",
			"window.location.replace(\'http://example.com\');", "window.location.replace( \'http://example.com\');",
			"window.location.replace(\'http://example.com\' );", "window.location.replace( \'http://example.com\' );",
			"window.location=\"http://example.com\";", "window.location =\"http://example.com\";",
			"window.location= \"http://example.com\";", "window.location=\"http://example.com\" ;",
			"window.location = \"http://example.com\";", "window.location =\"http://example.com\" ;",
			"window.location= \"http://example.com\" ;", "window.location = \"http://example.com\" ;",
			"window.location=\'http://example.com\';", "window.location =\'http://example.com\';",
			"window.location= \'http://example.com\';", "window.location=\'http://example.com\' ;",
			"window.location = \'http://example.com\';", "window.location =\'http://example.com\' ;",
			"window.location= \'http://example.com\' ;", "window.location = \'http://example.com\' ;",
			"document.location.href=\"http://example.com\";", "document.location.href=\"http://example.com\" ;",
			"document.location.href= \"http://example.com\";", "document.location.href =\"http://example.com\";",
			"document.location.href= \"http://example.com\" ;", "document.location.href = \"http://example.com\" ;",
			"document.location.href = \"http://example.com\";", "document.location.href =\"http://example.com\" ;",
			"document.location.href=\'http://example.com\';", "document.location.href=\'http://example.com\' ;",
			"document.location.href= \'http://example.com\';", "document.location.href =\'http://example.com\';",
			"document.location.href= \'http://example.com\' ;", "document.location.href = \'http://example.com\' ;",
			"document.location.href = \'http://example.com\';", "document.location.href =\'http://example.com\' ;",
			"document.location.replace(\"http://example.com\");", "document.location.replace( \"http://example.com\");",
			"document.location.replace(\"http://example.com\" );", "document.location.replace( \"http://example.com\" );",
			"document.location.replace(\'http://example.com\');", "document.location.replace( \'http://example.com\');",
			"document.location.replace(\'http://example.com\' );", "document.location.replace( \'http://example.com\' );",
			"document.location=\"http://example.com\";", "document.location =\"http://example.com\";",
			"document.location= \"http://example.com\";", "document.location=\"http://example.com\" ;",
			"document.location = \"http://example.com\";", "document.location =\"http://example.com\" ;",
			"document.location= \"http://example.com\" ;", "document.location = \"http://example.com\" ;",
			"document.location=\'http://example.com\';", "document.location =\'http://example.com\';",
			"document.location= \'http://example.com\';", "document.location=\'http://example.com\' ;",
			"document.location = \'http://example.com\';", "document.location =\'http://example.com\' ;",
			"document.location= \'http://example.com\' ;", "document.location = \'http://example.com\' ;",};
	
	@Override
	public Map<String, VulnerabilityAssessment> isVulnerable(WebResource resource) {
		resource.getInspectStatus().setUdrjs(UDRJSInspectorStatus.CURRENTLY_INSPECTING);
		//Copies the existing resource so as to not modify the original version
		Map<String, String> parameters = resource.getParameters();
		Map<String, VulnerabilityAssessment> assessment = new HashMap<String, VulnerabilityAssessment>();
		
		for(Map.Entry<String, String> entry: parameters.entrySet())
		{
			String originalValue = entry.getValue();
			//System.out.println("Mutating " + entry.getKey());
			entry.setValue("http://example.com");
			WebResource myResource = new WebResource(resource, parameters);
			//System.out.println("Query Permutation: " + myResource.getParametersAsEncodedString());
			
			try
			{
				WebResponse response = WebRequester.requestResource(myResource);
				String content = response.getContent().toLowerCase();
				//System.out.println(content);
				for(String indicator : UDR_JS_Indicator)
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
		resource.getInspectStatus().setUdrjs(UDRJSInspectorStatus.INSPECTED);
		// TODO Auto-generated method stub
		return assessment;
	}

}
