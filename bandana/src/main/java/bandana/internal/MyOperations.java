package bandana.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import java.util.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


;/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class MyOperations{
	
	static String completeUrl="";
  
  @MediaType(value = ANY, strict = false)
  public void createDomain(@Config MyConfiguration configuration, @Connection MyConnection connection, String domainName) throws Exception {
	  String createDomainUrl="&DomainName="+domainName+"&Action=CreateDomain";
	  String baseUrl=connection.getSignedUrl();
	  completeUrl=baseUrl+createDomainUrl;
	  sendGet(completeUrl);
	  
	  //System.out.println(URL);
   // return fin;
  }
/*  public class HttpURLConnectionExample {

	  	//private final String USER_AGENT = "Mozilla/5.0";

	  	//public static void main(String[] args) throws Exception {

	  		HttpURLConnectionExample http = new HttpURLConnectionExample();

	  		System.out.println("Testing 1 - Send Http GET request");
	  		http.sendGet();

	  	// HTTP GET request
	*/  	public String sendGet(String completeUrl1) throws Exception {

	  		//String url = "http://www.google.com/search?q=mkyong";
	  		
	  		URL obj = new URL(completeUrl1);
	  		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	  		// optional default is GET
	  		con.setRequestMethod("GET");

	  		//add request header
	  		//con.setRequestProperty("User-Agent", USER_AGENT);

	  		int responseCode = con.getResponseCode();
	  		System.out.println("\nSending 'GET' request to URL : " +completeUrl1);
	  		System.out.println("Response Code : " + responseCode);

	  		BufferedReader in = new BufferedReader(
	  		        new InputStreamReader(con.getInputStream()));
	  		String inputLine;
	  		StringBuffer response = new StringBuffer();

	  		while ((inputLine = in.readLine()) != null) {
	  			response.append(inputLine);
	  		}
	  		in.close();

	  		//print result
	  		 return response.toString();
	  		 

	  	}
 
 /*   @MediaType(value = ANY, strict = false)
    public String GetAttributes(@Config MyConfiguration configuration, @Connection MyConnection connection, String domainName,String itemname,String attributename,String consistentread){
      return "Using Configuration [" + configuration.getAcessKey() + "] with Connection id [" + connection.getSignedUrl() + "]";
    }  
   @MediaType(value = ANY, strict = false)
   public String DeleteAttributes(@Config MyConfiguration configuration, @Connection MyConnection connection, String domainName) {
   return "Using Configuration [" + configuration.getAcessKey() + "] with Connection id [" + connection.getSignedUrl() + "]";
      } 
    @MediaType(value = ANY, strict = false)
    public String DeleteDomain(@Config MyConfiguration configuration, @Connection MyConnection connection, String domainName){
    	String URL1="DomainName="+domainName+"& Action=DeleteDomain";
  	  System.out.println(URL);
  	  String fin="";
  	  for(int i=0;i<URL.length();i++)
  	  {
  		 String[] res= URL.split("?");
  		  fin=res[0]+"?"+URL1+res[1];
  	  }
      return fin;
  
        }   
          @MediaType(value = ANY, strict = false)
          public String PutAttributes(@Config MyConfiguration configuration, @Connection MyConnection connection, String domainName){
          return "Using Configuration [" + configuration.getAcessKey() + "] with Connection id [" + connection.getSignedUrl() + "]";
          }
          @MediaType(value = ANY, strict = false)
          public String ListDomains(@Config MyConfiguration configuration, @Connection MyConnection connection, int maxnodomain,String nexttoken){
        	  
        	  String URL1="Action=listDomain";
        	  System.out.println(URL);
        	  String fin="";
        	  for(int i=0;i<URL.length();i++)
        	  {
        		 String[] res= URL.split("?");
        		  fin=res[0]+"?"+URL1+res[1];
        	  }
            return fin;
          } */
          @MediaType(value = ANY, strict = false)
          public String BatchPutAttributes(@Config MyConfiguration configuration, @Connection MyConnection connection, String domainName){
            return "Using Configuration [" + configuration.getAcessKey() + "] with Connection id [" + connection.getSignedUrl() + "]";
          } 
          @MediaType(value = ANY, strict = false)
          public String Select(@Config MyConfiguration configuration, @Connection MyConnection connection, String domainName){
            return "Using Configuration [" + configuration.getAcessKey() + "] with Connection id [" + connection.getSignedUrl() + "]";
          }
  
}
