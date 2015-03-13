package flashtest.utils;

import java.io.File;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.ProxyServer;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;

public class TestUtils {

	public static void findAndInteractWithElement(String imagePath) {
		// Find an image location
		ScreenRegion s = new DesktopScreenRegion();
		Target button = new ImageTarget(new File(imagePath));
		ScreenRegion r = s.find(button);
		// Create mouse object
		final Mouse mouse = new DesktopMouse();
		/*
		 * Perform mouse action, depending on desired flash video element
		 */
		if (imagePath.contains("logo")) {
			mouse.drop(r.getCenter());
		} else {
			mouse.click(r.getCenter());
		}
	}

	public static boolean printAllInterceptedRequests(ProxyServer server) {
		String request = "https://f.vimeocdn.com/js_opt/vimeo/modules/utils/vuid.min.js";
		Har har = server.getHar();
		for (HarEntry entry : har.getLog().getEntries()) {
			if (entry.getRequest().getUrl().contains(request)) {
				return true;
			}
		}
		return false;
	}
}
