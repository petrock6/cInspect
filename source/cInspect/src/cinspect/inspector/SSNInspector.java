package cinspect.inspector;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cinspect.exceptions.UnimplementedFunctionException;
import cinspect.web.WebRequester;
import cinspect.web.WebResource;

public class SSNInspector implements Inspector {

	String pattern = "[0-9]{3}(\\-|\\ )[0-9]{2}(\\-|\\ )[0-9]{4}";  
	Pattern tester = Pattern.compile(pattern);
	
	@Override
	public Map<String, VulnerabilityAssessment> isVulnerable(WebResource resource) {
		
		
		Map<String, VulnerabilityAssessment> assessment = new HashMap<String, VulnerabilityAssessment>();
		WebResource myResource = new WebResource(resource, resource.getParameters());
		String content;
		
		try {
			content = WebRequester.requestResource(myResource).getContent();
			Matcher matcher = tester.matcher(content);
			
			if(matcher.find()) {
				assessment.put("N/A", VulnerabilityAssessment.LIKELY_VULNERABLE);
			}
		} catch (UnimplementedFunctionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return assessment;
	}

}
