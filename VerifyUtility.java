package common;

import java.time.Instant;

import com.hcsc.automation.framework.Status;
import com.hcsc.automation.framework.selenium.SeleniumReport;

/**
 * This class is used to verify results.
 * @author U440513
 *
 */
public class VerifyUtility {
	
	/**
	 * Verifies results between actual string and expected string
	 * @param expected The expected string object.
	 * @param actual The actual string object.
	 * @param report The Report
	 * @param passStepName The pass Step Name
	 * @param passStepDescription THe pass description
	 * @param failStepName The failed step name
	 * @param failStepDescription The failed step description.
	 */
	public static void VerifyResult(final String expected, 
			final String actual, 
			final SeleniumReport report,
			final String passStepName, 
			final String passStepDescription, 
			final String failStepName, 
			final String failStepDescription) {
		
		if (expected.toLowerCase().equals(actual.toLowerCase())) {
			report.updateTestLog(passStepName, passStepDescription,
					Status.PASS);
		} else {

			report.updateTestLog(failStepName, failStepDescription,
					Status.FAIL);
		}
	}
	
	
	/**
	 * Verifies results between actual string and expected string
	 * @param expected The expected string object.
	 * @param actual The actual string object.
	 * @param report The Report
	 * @param passStepName The pass Step Name
	 * @param passStepDescription THe pass description
	 * @param failStepName The failed step name
	 * @param failStepDescription The failed step description.
	 */
	public static void VerifyResultContains(final String expected, 
			final String actual, 
			final SeleniumReport report,
			final String passStepName, 
			final String passStepDescription, 
			final String failStepName, 
			final String failStepDescription) {
		
		if (actual.toLowerCase().contains(expected.toLowerCase())) {
			report.updateTestLog(passStepName, passStepDescription,
					Status.PASS);
		} else {

			report.updateTestLog(failStepName, failStepDescription,
					Status.FAIL);
		}
	}
	

	
	/**
	 * Verifies results between actual object and expected object
	 * @param expected The expected object.
	 * @param actual The actual object.
	 * @param report The Report
	 * @param passStepName The pass Step Name
	 * @param passStepDescription THe pass description
	 * @param failStepName The failed step name
	 * @param failStepDescription The failed step description.
	 */
	public static void VerifyResult(final Object expected, 
			final Object actual, 
			final SeleniumReport report,
			final String passStepName, 
			final String passStepDescription, 
			final String failStepName, 
			final String failStepDescription) {
		
		if (expected.equals(actual)) {
			report.updateTestLog(passStepName, passStepDescription,
					Status.PASS);
		} else {

			report.updateTestLog(failStepName, failStepDescription,
					Status.FAIL);
		}
	}
	
	
	
	/**
	 * Verifies actual values starts with expected string
	 * @param expected The expected string object.
	 * @param actual The actual string object.
	 * @param report The Report
	 * @param passStepName The pass Step Name
	 * @param passStepDescription THe pass description
	 * @param failStepName The failed step name
	 * @param failStepDescription The failed step description.
	 */
	public static boolean VerifyStartsWith(final String expected, 
			final String actual, 
			final SeleniumReport report,
			final String passStepName, 
			final String passStepDescription, 
			final String failStepName, 
			final String failStepDescription) {
		boolean res = false;
		if (actual.toLowerCase().startsWith(expected.toLowerCase())) {
			report.updateTestLog(passStepName, passStepDescription,
					Status.PASS);
			res = true;
		} else {

			report.updateTestLog(failStepName, failStepDescription,
					Status.FAIL);
		}
		return res;
	}
	/**
	 * Verifies actual values starts with expected string
	 * @param expected The expected string object.
	 * @param actual The actual string object.
	 * @param report The Report
	 * @param passStepName The pass Step Name
	 * @param passStepDescription THe pass description
	 * @param failStepName The failed step name
	 * @param failStepDescription The failed step description.
	 */
	public static boolean VerifyStartsWith(final String expected, 
			final String actual, 
			final SeleniumReport report,
			final String passStepName, 
			final String passStepDescription) {
		boolean res = false;
		if (actual.toLowerCase().startsWith(expected.toLowerCase())) {
			report.updateTestLog(passStepName, passStepDescription,
					Status.PASS);
			res = true;
		} 
		return res;
	}
	
	public static void VerifyDates(final String expectedDate, 
			final String actualDate, 
			final SeleniumReport report,
			final String passStepName, 
			final String passStepDescription, 
			final String failStepName, 
			final String failStepDescription) {
	
	}
}
