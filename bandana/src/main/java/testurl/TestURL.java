package testurl;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.mule.runtime.extension.api.annotation.param.MediaType;

public class TestURL {

                                             
                                                //private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
                                                //private static final String REQUEST_URI = "/";
                                                //private static final String REQUEST_METHOD = "GET";
                                               // private static final String AWS_ACCESS_KEY_ID = "IAZUS3KLGE2G5G3RH6"; //config.getAWSAcessKeyID();
                                               // private static final String AWS_SECRET_KEY_ID = "D3qiFUYuJLLeOqVu6aoOp1kgo4EE/YvQX9KqmrW2";//config.getAWSSecretAcessKey();
                                                //private static final String AWS_ENDPOINT = "sdb.amazonaws.com";
                                                //private static final String PROPERTIES_FILE_PATH = "common.properties";

                                               // private SecretKeySpec secretKeySpec = null;
                                               // private Mac mac = null;
                                                //private InputStream inputStream = null;
                                               // private Properties properties = null;
                                                
                                                /* "Version" : "2009-04-15",
                         "SignatureVersion" : "2",
                         "SignatureMethod" : "HmacSHA256"*/


                                               /* public TestURL() {
                                                                try {
                                                                                //inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH);
                                                                                //properties = new Properties();
                                                                                //properties.load(inputStream);
                                           
                                                                                //byte[] secretyKeyBytes = ((String) properties.get(AWS_SECRET_KEY_ID)).getBytes(UTF8_CHARSET);
                                                                               // byte[] secretyKeyBytes = AWS_SECRET_KEY_ID.getBytes(SimpleDBConstant.UTF8_CHARSET);
                                                                	//secretKeySpec = new SecretKeySpec(secretyKeyBytes, HMAC_SHA256_ALGORITHM);
                                                                    //mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
                                                                   // mac.init(secretKeySpec);       
                                                                                
                                                                } catch (Exception e) {
                                                                                e.printStackTrace();
                                                                }
                                                }*/

                                                //------------------------------------signedRequest

												@MediaType(value = ANY, strict = false)
                                                public static String constructRequest(String AWS_ACCESS_KEY_ID,String AWS_SECRET_KEY_ID) throws Exception{
                                                	
                                                	
                                                    
                                                                String signedRequest = "";
                                                
                                                                Map<String, String> parametrMap = new HashMap<String, String>();
                                                                parametrMap.put("SignatureMethod","HmacSHA256");
                                                                parametrMap.put("SignatureVersion","2");
                                                                
                                                                parametrMap.put("Version","2009-04-15");
                                                                
                                                                
                                                                try {
                                                                                TestURL simpleDbSignatureHelper = new TestURL();
                                                                                
                                                                               //System.out.println(parametrMap+"===================="+AWS_ACCESS_KEY_ID);
                                                                                signedRequest = simpleDbSignatureHelper.sign(parametrMap, AWS_ACCESS_KEY_ID,AWS_SECRET_KEY_ID);
                                                                } catch (Exception e) {
                                                                                e.printStackTrace();
                                                                }
                                                                return signedRequest;
                                                }
                //URL---------------------------------------------------------------------------
												@MediaType(value = ANY, strict = false)
                                                public String sign(Map<String, String> params, String AWS_ACCESS_KEY_ID, String AWS_SECRET_KEY_ID) {
                                                                params.put("AWSAccessKeyId", AWS_ACCESS_KEY_ID);
                                                                params.put("Timestamp", timestamp());

                                                                SortedMap<String, String> sortedParamMap = new TreeMap<String, String>(params);
                                                                String canonicalQS = canonicalize(sortedParamMap);
                                                                String toSign = SimpleDBConstant.REQUEST_METHOD + "\n" + SimpleDBConstant.AWS_ENDPOINT + "\n" + SimpleDBConstant.REQUEST_URI
                                                                                                + "\n" + canonicalQS;
                                                                //System.out.println("toSign::::::::::::::"+toSign);
                                                                String hmac = hmac(toSign, AWS_SECRET_KEY_ID);
                                                                String sig = percentEncodeRfc3986(hmac);
                                                                String url = "http://" + SimpleDBConstant.AWS_ENDPOINT + SimpleDBConstant.REQUEST_URI + "?" + canonicalQS
                                                                                                + "&Signature=" + sig;

                                                                return url;
                                                }

                                                
                   //Signature-------------------------------------------------------
                                                private String hmac(String stringToSign, String AWS_SECRET_KEY_ID) {
                                                                String signature = null;
                                                                byte[] data;
                                                                byte[] rawHmac;
                                                                try {
                                                                	byte[] secretyKeyBytes = AWS_SECRET_KEY_ID.getBytes(SimpleDBConstant.UTF8_CHARSET);
                                                                	final SecretKeySpec secretKeySpec = new SecretKeySpec(secretyKeyBytes, SimpleDBConstant.HMAC_SHA256_ALGORITHM);
                                                                	Mac mac = Mac.getInstance(SimpleDBConstant.HMAC_SHA256_ALGORITHM);
                                                                    mac.init(secretKeySpec);
                                                                                data = stringToSign.getBytes(SimpleDBConstant.UTF8_CHARSET);
                                                                                rawHmac = mac.doFinal(data);
                                                                                //Base64 encoder = new Base64();
                                                                                //signature = new String(encoder.encode(rawHmac));
                                                                                signature= rawHmac.toString();
                                                                } catch (UnsupportedEncodingException e) {
                                                                                throw new RuntimeException(SimpleDBConstant.UTF8_CHARSET + " is unsupported!", e);
                                                                }catch (Exception e) {
                                                                	e.printStackTrace();
                                                                   //throw new RuntimeException("Unable to get Mac");
                                                    }
                                                                return signature;
                                                }
                //Time Stamp-------------------------------------------------------------
                                                private String timestamp() {
                                                                String timestamp = null;
                                                                Calendar cal = Calendar.getInstance();
                                                                DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                                                dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
                                                                timestamp = dfm.format(cal.getTime());
                                                                return timestamp;
                                                }

                                                
                // Canonicalize---------------------------------------------------------                            
                                                private String canonicalize(SortedMap<String, String> sortedParamMap) {
                                                                if (sortedParamMap.isEmpty()) {
                                                                                return "";
                                                                }
                                                                StringBuffer buffer = new StringBuffer();
                                                                Iterator<Map.Entry<String, String>> iter = sortedParamMap.entrySet().iterator();
                                                                while (iter.hasNext()) {
                                                                                Map.Entry<String, String> kvpair = iter.next();
                                                                                buffer.append(percentEncodeRfc3986(kvpair.getKey()));
                                                                                buffer.append("=");
                                                                                buffer.append(percentEncodeRfc3986(kvpair.getValue()));
                                                                                if (iter.hasNext()) {
                                                                                                buffer.append("&");
                                                                                }
                                                                }
                                                                String canonical = buffer.toString();
                                                                return canonical;
                                                }

                                                
                                                //Encode------------------------------------------------------
                                                private String percentEncodeRfc3986(String s) {
                                                                String out;
                                                                try {
                                                                                out = URLEncoder.encode(s, SimpleDBConstant.UTF8_CHARSET).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
                                                                } catch (UnsupportedEncodingException e) {
                                                                                out = s;
                                                                }
                                                                return out;
                                                }
                                                

                public static void main(String[] args) throws Exception {
                                // TODO Auto-generated method stub
                               //TestURL demo = new TestURL();
                                //System.out.println(demo);
                                String URL= TestURL.constructRequest("IAZUS3KLGE2G5G3RH6","D3qiFUYuJLLeOqVu6aoOp1kgo4EE/YvQX9KqmrW2");
                               System.out.println(URL);

                }

}

