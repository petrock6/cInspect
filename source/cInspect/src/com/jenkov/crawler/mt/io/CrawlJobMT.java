package com.jenkov.crawler.mt.io;

import com.jenkov.crawler.util.UrlNormalizer;

import cinspect.web.ResourceRequestType;
import cinspect.web.WebDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jakob Jenkov and Emmanuel John
 */
public class CrawlJobMT implements Runnable {

    protected CrawlerMT crawler    = null;
    protected String  urlToCrawl = null;
    
    public CrawlJobMT(String urlToCrawl, CrawlerMT crawler) {
        this.urlToCrawl = urlToCrawl;
        this.crawler    = crawler;
    }
    
    @Override
    public void run(){
        try{
            crawl();
        }catch(Exception ex){
            
        }
    }
    public void crawl() throws IOException{

        URL url = new URL(this.urlToCrawl);

        URLConnection urlConnection = null;
        try {
            urlConnection = url.openConnection();

            try (InputStream input = urlConnection.getInputStream()) {

                Document doc      = Jsoup.parse(input, "UTF-8", "");
                Elements elements = doc.select("a");
                
                ResourceRequestType requestType = null; //this is gonig to have to be moved later.  
                String formUrl = ""; //this is going to have to be moved later. 
                HashMap<String, String> parameters = new HashMap<String,String>(); //this is going to have to be moved later. 

                String baseUrl = url.toExternalForm();
                for(Element element : elements){
                    String linkUrl       = element.attr("href");
                    String normalizedUrl = UrlNormalizer.normalize(linkUrl, baseUrl);
                    crawler.linksQueue.put(normalizedUrl);
                    
                    //System.out.println(" - "+normalizedUrl);
                    
                }
                Elements forms = doc.select("form");
                for(Element felement : forms){
                    String method       = felement.attr("method");
                    String action       = felement.attr("action");
                    
                    
                    System.out.print(" \tMethod: "+method);
                    System.out.println(" \tAction: "+action + "( " + this.urlToCrawl);
                    System.out.println(felement.outerHtml());
                    
                    if(method.toLowerCase().equals("get")) {//this is going to have to be moved.
                    	requestType = ResourceRequestType.GET;//this is going to have to be moved.
                    } else if(method.toLowerCase().equals("post")) {//this is going to have to be moved.
                    	requestType = ResourceRequestType.POST;//this is going to have to be moved.
                    } else {//this is going to have to be moved.
                    	requestType = ResourceRequestType.GET; //if no method detected, default to get.   
                    }//this is going to have to be moved.
                    
                    formUrl = trimURLFileName(this.urlToCrawl);//this is going to have to be moved.
                    formUrl += action;//this is going to have to be moved. 
                    formUrl += "?";//this is going to have to be moved.
                    
                    Elements inputs = felement.select("input");
                    for(Element ielement : inputs){
                        String type       = ielement.attr("type");
                        String name       = ielement.attr("name");
                        String value       = ielement.attr("value");
                        
                        //System.out.println(" \tType: "+type);
                        //System.out.println(" \tName: "+name);
                        //System.out.println(" \tValue: "+value);
                        //System.out.println("");
                        
                        if(value.equals("")) 
                        	value = "__CINSPECT__NO__VALUE__"; //there probably si a better solution to thsi. 
                        
                        
                        parameters.put(name, value); //this is going to have to be moved.
                        
                    }
                    
                }
                /*
                //TODO: @Sam 
                //Needs to only search for inputs in the form above. 
                Elements inputs = doc.select("input");
                for(Element ielement : inputs){
                    String type       = ielement.attr("type");
                    String name       = ielement.attr("name");
                    String value       = ielement.attr("value");
                    
                    //System.out.println(" \tType: "+type);
                    //System.out.println(" \tName: "+name);
                    //System.out.println(" \tValue: "+value);
                    //System.out.println("");
                    
                    if(value.equals("")) 
                    	value = "__CINSPECT__NO__VALUE__"; //there probably si a better solution to thsi. 
                    
                    
                    parameters.put(name, value); //this is going to have to be moved.
                    
                }*/
                
                if(requestType != null && !formUrl.isEmpty() && !parameters.isEmpty()) {
                	WebDatabase.addResource(requestType, formUrl, parameters);//this is going to have to be moved.
                }
                
                if(crawler.barrier.getNumberWaiting()==1){
                    crawler.barrier.await();
                    
                }

            } catch (IOException e) {
                throw new RuntimeException("Error connecting to URL", e);
            } catch (InterruptedException ex) {
                Logger.getLogger(CrawlJobMT.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException ex) {
                Logger.getLogger(CrawlJobMT.class.getName()).log(Level.SEVERE, null, ex);
            } 
        } catch(IOException e) {
            throw new RuntimeException("Error connecting to URL", e);
        }
    }

    private String trimURLFileName(String url) {
    	String ret = "";
    	try {
			URI uri = new URI(url);
			ret = uri.getScheme() + "://" + uri.getAuthority() + uri.getPath();
			
			String fileName = url.substring(url.lastIndexOf('/') + 1);
			return ret.replace(fileName, "");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return ret;
    }
    
}
