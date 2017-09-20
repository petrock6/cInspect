package cinspect.web;

/**
 * When a {@link WebResource} is requested by the {@link WebRequester}, this object is returned, which contains the HTTP code and the HTTP content. 
 * @author Austin Bentley <ab6d9@mst.edu>
 */
public class WebResponse {
	private int code;
	private String content;
	
	WebResponse(int code, String content) {
		this.code = code;
		this.content = content;
	}

	/**
	 * @return The HTTP code associated with the response from the web server. 
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the HTTP code associated with the HTTP response.
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return The HTTP content associated with the response. 
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the HTTP content associated with the response. 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
