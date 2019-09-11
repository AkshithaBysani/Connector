package bandana.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(MyOperations.class)
@ConnectionProviders(MyConnectionProvider.class)
public class MyConfiguration {

  @Parameter
  private String acessKey;
  @Parameter
  private String secretId;
  //private enum zone;
  
public void setAcessKey(String acessKey) {
	this.acessKey = acessKey;
}
public String getAcessKey() {
	return acessKey;
}
public String getSecretId() {
	return secretId;
}
public void setSecretId(String secretId) {
	this.secretId = secretId;
}
 
}
