package bandana.internal;


import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import testurl.TestURL;


/**
 * This class (as it's name implies) provides connection instances and the functionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class MyConnectionProvider extends testurl.TestURL implements PoolingConnectionProvider<MyConnection> {

	String URL="";
  private final Logger LOGGER = LoggerFactory.getLogger(MyConnectionProvider.class);
  
 // TestURL turl = new TestURL();
  
  /* A parameter that is always required to be configured.
  */
  @Parameter
  private String acessKey;
  
  @Parameter
  private String secretId;

 /**
  * A parameter that is not required to be configured by the user.
  */
 // @DisplayName("AccessID")
  //@Parameter
  //@Optional(defaultValue = "100")
  //private int optionalParameter;
  //@DisplayName("SecretKey")
  
  

  @Override
  public MyConnection connect() throws ConnectionException {
	  
	  //TO DO 
      
	try {
		URL = constructRequest(acessKey,secretId);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      System.out.println(URL);
       

	  //---------------------------------------------------------------------------------------------------------------------------------------------------------
	  
	  MyConnection connection = new MyConnection();
	  
	  connection.setSignedUrl(URL);
    return connection;
  }

  @Override
  public void disconnect(MyConnection conn) {
    try {
    	conn.invalidate();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting ["  + "]: " + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(MyConnection connection) {
	  /*if(connection.getSignedUrl()!=null && !connection.getSignedUrl().equals("")) {
		  return ConnectionValidationResult.success();
	  }else {*/
	  
	  String[] signedstuff = URL.split("=");
      String reqdstuff = signedstuff[signedstuff.length-1];
      
      if(reqdstuff!=null && reqdstuff!="")
      {
    	  System.out.println("Success!!");
    	  return ConnectionValidationResult.success();
      }
      else 
      {
		  return ConnectionValidationResult.failure("Invalid Credentials", new Exception());
      }
  }
}
