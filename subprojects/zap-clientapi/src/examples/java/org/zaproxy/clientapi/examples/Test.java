package org.zaproxy.clientapi.examples;

import java.util.ArrayList;
import java.util.List;

import org.zaproxy.clientapi.core.Alert;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.Alert.Confidence;
import org.zaproxy.clientapi.core.Alert.Risk;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO List
		// High priority
		// * Start ZAP in background - task still waits! } Need docs?
		// * Get checkAlerts to work with inner elements } 
		// Medium - tidy up
		// * Create min zapapi.jar
		// * Correct way of installing in Eclipse
		// Docs etc
		// * Full wave reg test
		// * Full wavsep reg test
		// * Documentation
		// Publicise
		// * Blog, tweet etc etc
		// * Work out priorities for extending api
		// * Complete tasks - more for internal use than anything else

		List<Alert> ignoreAlerts = new ArrayList<>(2);
		ignoreAlerts.add(new Alert("Cookie set without HttpOnly flag", null, Risk.Low, Confidence.Medium, null, null));
		ignoreAlerts.add(new Alert(null, null, Risk.Low, Confidence.Medium, null, null));
		
		try {
			(new ClientApi("localhost", 8090)).checkAlerts(ignoreAlerts, null );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Alert> requireAlerts = new ArrayList<>(1);
		//ignoreAlerts.add(new Alert(null, null, null, null, null, null));
		requireAlerts.add(new Alert("Not present", null, Risk.Low, Confidence.Medium, null, null));
		try {
			(new ClientApi("localhost", 8090)).checkAlerts(ignoreAlerts, requireAlerts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}
