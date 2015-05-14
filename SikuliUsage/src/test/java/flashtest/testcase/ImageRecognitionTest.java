package flashtest.testcase;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.lightbody.bmp.proxy.ProxyServer;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import flashtest.driver.WebDriverFactory;
import flashtest.utils.TestUtils;

public class ImageRecognitionTest {
	protected WebDriver webDriver;
    private static ProxyServer server;
    private static Proxy proxy;
    private static final String playButton = "src/test/resources/images/play_button.png";
    private static final String pauseButton = "src/test/resources/images/pause_button.png";
    private static final String logo = "src/test/resources/images/logo.png";
	
	@BeforeMethod
	public void setup() throws Exception {
        server = new ProxyServer(4444);
        server.start();
        proxy = new Proxy();
        server.newHar("http://sqadays.com/en/index");
		webDriver = WebDriverFactory.getInstance(proxy);
		webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	@Test
	public void checkFlashVideoInteraction() throws IOException, InterruptedException {
		webDriver.get("http://sqadays.com/en/index");
		WebElement image = webDriver.findElement(By.cssSelector(".conf-info"));
		WebDriverWait wait = new WebDriverWait(webDriver, 15);
		wait.until(ExpectedConditions.visibilityOf(image));
		JavascriptExecutor jse = (JavascriptExecutor)webDriver;
		jse.executeScript("window.scrollBy(0,2500)", "");
		
		//TestUtils flashVideo = new TestUtils();
		// Find play button and click on it
		TestUtils.findAndInteractWithElement(playButton);		
		// Hover mouse cursor over logo image
		TestUtils.findAndInteractWithElement(logo);
		// Check if a proper request was sent
		Assert.assertTrue(TestUtils.printAllInterceptedRequests(server));

      
		// Find pause button and click on it
        TestUtils.findAndInteractWithElement(pauseButton);
		// Check if a proper request was sent
		Assert.assertTrue(TestUtils.printAllInterceptedRequests(server));
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		if (webDriver != null) {
			WebDriverFactory.killDriverInstance();
	        server.stop();
		}
	}
}
