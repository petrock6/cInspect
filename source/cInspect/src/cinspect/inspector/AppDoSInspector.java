package cinspect.inspector;

import java.util.Map;

import cinspect.web.WebResource;

public class AppDoSInspector implements Inspector {

	@Override
	public Map<String, VulnerabilityAssessment> isVulnerable(WebResource resource) {
		// TODO Auto-generated method stub
		return null;
		
		/*
		 Use crawler to find all instances in which I can post
		 
		 How to post in Jsoup:
		 https://stackoverflow.com/questions/23320498/how-to-post-form-login-using-jsoup
		 
		 Post various different things, like the letter e or common short strings.
		 
		 Measure response time for connecting to URL
		 https://stackoverflow.com/questions/34294270/using-jsoup-connect-in-a-loop-the-first-request-is-always-much-slower-than-al
		 
		 If timeout, then vulnerable?
		 
		 This is a bit difficult to check every form on a website.
		 It won't necessarily work with username and password stuff.
		 I'll have to check simple things like search bars, etc.
		 */
	}

}
