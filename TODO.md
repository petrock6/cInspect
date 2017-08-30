Special note: When I say URL, it could be either a POST or GET URL. Both need to be supported. 
A web spider 	
	A web spider is required to gather all of the URLs relevant to the website in a recursive nature.
	It should make a URL list which is continuously updated and can be accessed by all other classes inside of the program. 
	URLs added to this list should *only* be from the targeted domain. If you are spidering all of the documents off of example.com, they should only be off example.com. 
	The spider should not download anything which is not a true web document -- i.e., no images, zip files, etc. 
	Try to make a spider which will submit forms with junk data. This will save us a bit of hassle in the long run. See “a form submitter” for reasons why. 
	The amount of difficulty points depends on how you implement it. I would highly recommend using an external library to do this.
	This is absolutely the highest priority.

GUI
	A point and click interface for the tool.
	More discussion is required to assess the amount of points associated with this. We’ll need to know if we want just a “insert domain here and click scan” tool or if we want a bunch of progress bars and lists or what.
	This is very low priority.

URL Deduplicator 
	There is a small chance that the spider could run into a “barrel shift” loop. 
	For example:
		http://example.com/test.php?id=5&search=true&name=austin
		http://example.com/test.php?search=true&id=5&name=austin
		http://example.com/test.php?name=austin&search=true&id=5
	To avoid this, make a map/hash table of all the keys and compare them to the tables of all the other URLs. 
	This is low-medium priority.
	
Grabbing cookies from a browser 
	This will allow us to bypass things like login forms.
	May need to detect if the spider accidentally clicks a ‘logout’ link.
	Need to be able to paste in cookies in the tool from a JavaScript command like alert(escape(document.cookie)), which shows all of the cookies for the website.
	For every HTTP request sent to the webserver, the cookie should be sent with it.
	This is medium-high priority.
	
A form scraper 
	Some content on the website can only be accessed with the submission of a form (like a search button or a login button.)
	Forms and links are defined differently in HTML, so the spider may not pick up on this. 
	When a form is detected, submit it with some junk (or predefined) data, and then send the resulting HTML to the spider for it to process it. 
	This is medium priority.
	
Local File Inclusion 
	Given a URL, a function will determine whether or not the URL is vulnerable to Local File Inclusion by testing every parameter in the URL. 
	It should test for both Linux and Windows.
	There should be a list of “indicators” available on the internet. I’ll have to do some research to find these but I’ll get them to you. 
	This is required and fairly simple
	
Remote File Inclusion 
	Given a URL, a function will determine whether or not the URL is vulnerable to Remote File Inclusion by testing every parameter in the URL.
	This one is very simple.
	This is absolutely required and probably the simplest. 
	
Application Denial of Service 
	Given a URL, a function will mutate the URL parameters and see if there is a very significant difference in response time.
	Some failures may not result in slow connectivity, some failures may result in quickly responding error pages. Preferably both should be detected, but the first may be the only one possible. 
	This is not required but would be very nice. Could either be simple or difficult.
	
Remote Code Execution (visible) 
	Given a URL, a function will determine whether or not the URL is vulnerable to Remote Code Execution by testing every parameter in the URL. 
	Testing should work on both Windows and Linux.
	This is required and may have a small amount of difficulty.
	
Remote Code Execution (invisible) 
	Given a URL, a function will determine whether or not the URL is vulnerable to Remote Code Execution by testing every parameter in the URL. 
	Testing should work on both Windows and Linux.
	This is not required but it would be pretty nice. Would be a bit more difficult than RCE visible.
	
SQL Injection (regular)
	Given a URL, a function will determine whether or not the URL is vulnerable to SQL injection by testing every parameter in the URL.
	This is required and may have some difficulty. 
	
SQL Injection (blind)
	Given a URL, a function will determine whether or not the URL is vulnerable to SQL injection by testing every parameter in the URL. 
	This is not required as it may be fairly difficult to detect.
	
XSS Injection 
	Given a URL, a function will determine whether or not the URL is vulnerable to XSS injection by testing every parameter in the URL.
	This is required and fairly simple to detect, but multiple tests will need to be made.
	
SSN/credit card finder
	Given a URL, a function will determine whether or not the URL contents contain SSN or credit card information.
	Should be doable with regex. 
	This is required and probably the easiest of all modules. 
	
Multithreading
	We will need to design this to be multithreaded. 
	Java is pretty good at multithreading and even nonthreaded applications should be trivial to modify to be multithreaded. 
