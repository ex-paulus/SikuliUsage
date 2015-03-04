package flashtest.testcase;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import net.lightbody.bmp.proxy.ProxyServer;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import flashtest.driver.WebDriverFactory;
import flashtest.utils.TestUtils;

public class ImageRecognitionTest {
	protected WebDriver webDriver;
    private static ProxyServer server;
    private static Proxy proxy;
	
	@BeforeMethod
	public void setup() throws Exception {
        server = new ProxyServer(4444);
        server.start();
        proxy = server.seleniumProxy();
        server.newHar("http://sqadays.com/en/index");
		webDriver = WebDriverFactory.getInstance(proxy);
		webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	@Test
	public void checkFlashVideoInteraction() throws IOException, InterruptedException {
		webDriver.get("http://sqadays.com/en/index");
		JavascriptExecutor jse = (JavascriptExecutor)webDriver;
		jse.executeScript("window.scrollBy(0,2500)", "");
		
		TestUtils flashVideo = new TestUtils();
		// Find play button and click on it
		flashVideo.findAndInteractWithElement("src/test/resources/images/play_button.png");		
		// Hover mouse cursor over logo image
		flashVideo.findAndInteractWithElement("src/test/resources/images/logo.png");
		// Check if a proper request was sent
		Assert.assertTrue(flashVideo.printAllInterceptedRequests(server));
      
		// Find pause button and click on it
        flashVideo.findAndInteractWithElement("src/test/resources/images/pause_button.png");
		// Check if a proper request was sent
		Assert.assertTrue(flashVideo.printAllInterceptedRequests(server));
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		if (webDriver != null) {
			WebDriverFactory.killDriverInstance();
	        server.stop();
		}
	}
}
