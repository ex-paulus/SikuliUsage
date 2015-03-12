package flashtest.driver;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * A factory that returns a singleton of WebDriver object.
 */
public class WebDriverFactory {

	private static FirefoxDriver webDriver;

	private WebDriverFactory() {

	}

	/**
	 * Gets the single instance of WebDriverFactory.
	 * @param proxy 
	 *
	 * @return single instance of WebDriverFactory
	 * @throws Exception
	 * the exception of invalid browser property
	 */
	public static WebDriver getInstance(Proxy proxy) throws Exception {
		if (webDriver == null) {
			String PROXY = "localhost:" + 4444;
	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        proxy.setSslProxy(PROXY);
	        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	        capabilities.setCapability(CapabilityType.PROXY, proxy);
	        webDriver = new FirefoxDriver(capabilities);
		}
		return webDriver;
	}
	
	/**
	 * Kill driver instance.
	 * 
	 * @throws Exception
	 */
	public static void killDriverInstance() throws Exception {
		webDriver.quit();
		webDriver = null;	
	}
}