package cinspect.inspector;

import java.util.Map;

import cinspect.web.WebResource;

public interface Inspector {
	public Map<String, VulnerabilityAssessment> isVulnerable(WebResource resource);
}
