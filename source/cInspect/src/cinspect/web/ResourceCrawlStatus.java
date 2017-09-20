package cinspect.web;

/**
 * An enum class which specifies the current crawling status of the URL. Used to make sure we don't double-crawl or run into multithreading issues. 
 * @author ROTP
 *
 */
public enum ResourceCrawlStatus {
	IS_NOT_CRAWLED,
	IS_CRAWLING,
	IS_CRAWLED
}
