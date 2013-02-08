package net.press.pa;

import java.io.Serializable;

/**
 * Description: Fire an HTTP get + parameters, and receive a response.  
 *
 * @author <a href="ralph.hodgson@pa.press.net">Ralph Hodgson</a>
 *
 */
public class HttpBean implements Serializable
{
//	private static final long serialVersionUID = -8375884869789946614L;
//
//	private static final Log log = LogFactory.getLog(HttpBean.class);	
//	
//	private static String BASE_URL = "http://pa055440:8080/people-api"; // Get from DB
//	
//	public String executeRequest(String entity, String function, Map<String, String> params) 
//	 	throws Exception 
//	 {
//	        HttpClient httpclient = new DefaultHttpClient();
//	        HttpGet httpget = new HttpGet(BASE_URL + '/' + entity + '/' + function);
//
//	        log.info("executing request " + httpget.getURI());
//
//	        // Add parameters.
//	        for (String key: params.keySet() )
//	        {
//	        	httpget.getParams().setParameter(key, params.get(key) );
//	        }
//
//	        // Create the response handler.
//	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
//	        String responseBody = httpclient.execute(httpget, responseHandler);
//	        
//	        // Ensure immediate deallocation of all system resources when done.
//	        httpclient.getConnectionManager().shutdown(); 
//	        
//	        return responseBody;
//	    }
}
