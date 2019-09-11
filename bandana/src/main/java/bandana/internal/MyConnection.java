package bandana.internal;


/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class MyConnection {

  private String signedUrl;

 /* public MyConnection(String signedUrl) {
    this.signedUrl = signedUrl;
  }*/
  public MyConnection() {
	   // this.signedUrl = signedUrl;
	  }




  public String getSignedUrl() {
	return signedUrl;
}
  
  public void setSignedUrl(String signedUrl) {
	this.signedUrl=signedUrl;
}


public void invalidate() {
    this.setSignedUrl(null);
  }
}
