package cinspect.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jenkov.crawler.mt.io.CrawlerMT;
import com.jenkov.crawler.util.SameWebsiteOnlyFilter;

import cinspect.inspector.VulnerabilityAssessment;
import cinspect.inspector.XSSInspector;
import cinspect.inspector.statuses.AppDoSInspectorStatus;
import cinspect.inspector.statuses.CCInspectorStatus;
import cinspect.inspector.statuses.LFIInspectorStatus;
import cinspect.inspector.statuses.RCEInspectorStatus;
import cinspect.inspector.statuses.RFIInspectorStatus;
import cinspect.inspector.statuses.SQLInspectorStatus;
import cinspect.inspector.statuses.SSNInspectorStatus;
import cinspect.inspector.statuses.TimedSQLInspectorStatus;
import cinspect.inspector.statuses.UDRJSInspectorStatus;
import cinspect.inspector.statuses.XSSInspectorStatus;
import cinspect.GUI.GUI;
import cinspect.inspector.AppDoSInspector;
import cinspect.inspector.CCInspector;
import cinspect.inspector.LFIInspector;
import cinspect.inspector.RCEInspector;
import cinspect.inspector.RFIInspector;
import cinspect.inspector.SQLInspector;
import cinspect.inspector.SSNInspector;
import cinspect.inspector.TimeSQLInspector;
import cinspect.inspector.UDRJSInspector;
import cinspect.web.ResourceRequestType;
import cinspect.web.WebDatabase;
import cinspect.web.WebResource;

import cinspect.GUI.*;

public class Main extends GUI{
	
	static String[] globalArgs;
	
	public static ArrayList<Thread> threads = new ArrayList<Thread>();
	
	public static void main(String[] args) {
		globalArgs = args;
		launch(globalArgs);
	}

	public static void spawnThreads(int numThreads, boolean sql, boolean rce, boolean lfi, boolean xss, boolean rfi, boolean tsql, boolean udrjs, boolean appdos, boolean phpinfo, boolean ccssn) {
		Runnable r = new Runnable() {
			public void run() {
				try {
					spawnThread(sql, rce, lfi, xss, rfi, tsql, udrjs, appdos, phpinfo, ccssn);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		for(int i = 0; i < numThreads; i++) 
			threads.add(new Thread(r));
		
		for(Thread t : threads)
			t.start();
	}
	
	public static void spawnThread(boolean sql, boolean rce, boolean lfi, boolean xss, boolean rfi, boolean tsql, boolean udrjs, boolean appdos, boolean phpinfo, boolean ccssn) throws InterruptedException {
		for(WebResource resource : WebDatabase.getDatabase()) {
			if(!resource.getParameters().isEmpty() ) {
				if(sql && resource.getInspectStatus().getSql() == SQLInspectorStatus.NOT_INSPECTED) {
					GUI.print("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : SQL Injection\n"); 
					testSQLInspector(resource);
					Thread.sleep(cinspect.GUI.GUI.getRequestDelay());
				}
				if(rce && resource.getInspectStatus().getRce() == RCEInspectorStatus.NOT_INSPECTED) {
					GUI.print("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : RCE Injection\n");
					testRCEInspector(resource);
					Thread.sleep(cinspect.GUI.GUI.getRequestDelay());
				}
				if(lfi && resource.getInspectStatus().getLfi() == LFIInspectorStatus.NOT_INSPECTED) {
					GUI.print("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : LFI\n");
					testLFIInspector(resource);
					Thread.sleep(cinspect.GUI.GUI.getRequestDelay());
				}
				if(xss && resource.getInspectStatus().getXss() == XSSInspectorStatus.NOT_INSPECTED) {
					GUI.print("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : XSS Injection\n");
					testXSSInspector(resource);
					Thread.sleep(cinspect.GUI.GUI.getRequestDelay());
				}
				if(rfi && resource.getInspectStatus().getRfi() == RFIInspectorStatus.NOT_INSPECTED) {
					GUI.print("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : RFI\n");
					testRFIInspector(resource);
					Thread.sleep(cinspect.GUI.GUI.getRequestDelay());
				}
				if(tsql && resource.getInspectStatus().getTimedSQL() == TimedSQLInspectorStatus.NOT_INSPECTED) {
					GUI.print("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : TimeSQL Injection\n");
					testTimeSQLInspector(resource);
					Thread.sleep(cinspect.GUI.GUI.getRequestDelay());
				}
				if(udrjs && resource.getInspectStatus().getUDRJS() == UDRJSInspectorStatus.NOT_INSPECTED) {
					GUI.print("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : UDR\n");
					testUDRJSInspector(resource);
					Thread.sleep(cinspect.GUI.GUI.getRequestDelay());
				}
				if(appdos && resource.getInspectStatus().getAppDoS() == AppDoSInspectorStatus.NOT_INSPECTED){
					GUI.print("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : Application DoS\r");
					testAppDoSInspector(resource);
					Thread.sleep(cinspect.GUI.GUI.getRequestDelay());
				}
			}
			/*
			if(phpinfo) {
				GUI.print("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : phpinfo()\r");
				main.testPhpinfoInspector(resource);
			}*/
			
			if(ccssn && resource.getInspectStatus().getCc() == CCInspectorStatus.NOT_INSPECTED) {
				GUI.print("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : CC\n");
				testCCInspector(resource);
				Thread.sleep(cinspect.GUI.GUI.getRequestDelay());
			}
			if(ccssn && resource.getInspectStatus().getSsn() == SSNInspectorStatus.NOT_INSPECTED) {
				GUI.print("Testing : " + resource.getUrlPath() + "?" + resource.getParametersAsEncodedString() + " : SSN\n");
				testSSNInspector(resource);
				Thread.sleep(cinspect.GUI.GUI.getRequestDelay());
			}
			//GUI.print("\n");
		}	
		GUI.print("--- DONE ---");
	}
	
	public static void testSQLInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testSQLInspector(resources);
	}
	
	private static void testSQLInspector(List<WebResource> resources) {
		SQLInspector inspector = new SQLInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//GUI.print("");
				GUI.print(resource.getUrlPath() + " SQL vulnerable !!!");
			} else {
				//GUI.print(resource.getUrlPath() + " not SQL vulnerable");
			}
		}
	}

	public static void testRCEInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testRCEInspector(resources);
	}
	
	private static void testRCEInspector(List<WebResource> resources) {
		RCEInspector inspector = new RCEInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//GUI.print("");
				GUI.print(resource.getUrlPath() + " RCE vulnerable !!!");
			}
			//else
			//	GUI.print(resource.getUrlPath() + " not RCE vulnerable");
		}	
	}
	
	public static void testLFIInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testLFIInspector(resources);
	}
	
	private static void testLFIInspector(List<WebResource> resources) {
		LFIInspector inspector = new LFIInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//GUI.print("");
				GUI.print(resource.getUrlPath() + " LFI vulnerable !!!");
			}
			//else
			//	GUI.print(resource.getUrlPath() + " not LFI vulnerable");
		}	
	}
	
	public static void testXSSInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testXSSInspector(resources);
	}
	
	private static void testXSSInspector(List<WebResource> resources) {
		XSSInspector inspector = new XSSInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//GUI.print("");
				GUI.print(resource.getUrlPath() + " XSS vulnerable !!!");
			}
			//else
			//	GUI.print(resource.getUrlPath() + " not XSS vulnerable");
		}	
	}
	
	public static void testRFIInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testRFIInspector(resources);
	}
	
	private static void testRFIInspector(List<WebResource> resources) {
		RFIInspector inspector = new RFIInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//GUI.print("");
				GUI.print(resource.getUrlPath() + " RFI vulnerable !!!");
			}
			//else
			//	GUI.print(resource.getUrlPath() + " not RFI vulnerable");
		}	
	}
	
	public static void testTimeSQLInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testTimeSQLInspector(resources);
	}
	
	private static void testTimeSQLInspector(List<WebResource> resources) {
		TimeSQLInspector inspector = new TimeSQLInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//GUI.print("");
				GUI.print(resource.getUrlPath() + " TimeSQL vulnerable !!!");
			} else {
				//GUI.print(resource.getUrlPath() + " not TimeSQL vulnerable");
			}
		}
	}
	
	public static void testUDRJSInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testUDRJSInspector(resources);
	}
	
	private static void testUDRJSInspector(List<WebResource> resources) {
		UDRJSInspector inspector = new UDRJSInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//GUI.print("");
				GUI.print(resource.getUrlPath() + " UDRJS vulnerable !!!");
			} else {
				//GUI.print(resource.getUrlPath() + " not UDRJS vulnerable");
			}
		}
	}
	
	public static void testAppDoSInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testAppDoSInspector(resources);
	}
	
	private static void testAppDoSInspector(List<WebResource> resources) {
		AppDoSInspector inspector = new AppDoSInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//GUI.print("");
				GUI.print(resource.getUrlPath() + " AppDoS vulnerable !!!");
			} else {
				//GUI.print(resource.getUrlPath() + " not AppDoS vulnerable");
			}
		}
	}
	
	public static void testCCInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testCCInspector(resources);
	}
	
	private static void testCCInspector(List<WebResource> resources) {
		CCInspector inspector = new CCInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//GUI.print("");
				GUI.print(resource.getUrlPath() + " CC vulnerable !!!");
			} else {
				//GUI.print(resource.getUrlPath() + " not CC vulnerable");
			}
		}
	}
	
	public static void testSSNInspector(WebResource resource) {
		ArrayList<WebResource> resources = new ArrayList<WebResource>();
		resources.add(resource);
		testSSNInspector(resources);
	}
	
	private static void testSSNInspector(List<WebResource> resources) {
		SSNInspector inspector = new SSNInspector();
		for(WebResource resource : resources) {
			Map<String, VulnerabilityAssessment> assessment = inspector.isVulnerable(resource);
			
			if(!assessment.isEmpty()) {
				//GUI.print("");
				GUI.print(resource.getUrlPath() + " SSN vulnerable !!!");
			} else {
				//GUI.print(resource.getUrlPath() + " not SSN vulnerable");
			}
		}
	}
	

	
}
