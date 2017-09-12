package cinspect.crawler;

import cinspect.web.WebDatabase;
import cinspect.web.WebResource;

public class CrawlerThread {
	public void crawl() {
		WebResource resource = WebDatabase.getUncrawledResource();
		
		//TODO; implement flow diagram
	}
}
