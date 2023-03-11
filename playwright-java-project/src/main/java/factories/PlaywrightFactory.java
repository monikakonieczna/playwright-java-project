package factories;

import com.microsoft.playwright.*;
import dataProvider.ConfigEnums;
import dataProvider.ConfigReader;

import java.nio.file.Paths;

public class PlaywrightFactory {
    public static final ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
    public static final ThreadLocal<BrowserType> browserTypeThread = new ThreadLocal<>();
    public static final ThreadLocal<Browser> browserThread = new ThreadLocal<>();
    public static final ThreadLocal<BrowserContext> browserContextThread = new ThreadLocal<>();
    public static final ThreadLocal<Page> pageThread = new ThreadLocal<>();

    public static synchronized Page getPage() {
        if (playwrightThread.get() == null) {
            Playwright playwright = Playwright.create();
            playwrightThread.set(playwright);
            Page page = createPage(playwright);
            pageThread.set(page);
        }
        return pageThread.get();
    }

    public static synchronized Page createPage(Playwright playwright) {
        String browserName = ConfigReader.getRunValueFromConfig(ConfigEnums.BROWSERNAME);
        BrowserType browserType = getBrowserType(playwright, browserName);

        //Browser = browserType.launch(setDefaultLaunchOptions());
        Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));

        Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions();
        newContextOptions.acceptDownloads = true;

        BrowserContext context = browser.newContext(newContextOptions);
        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("loginAuthToken.json")));

        browserTypeThread.set(browserType);
        browserThread.set(browser);
        browserContextThread.set(context);
        return context.newPage();
    }
// private static BrowserType.LaunchOptions setDefaultLaunchOptions() {
//         return new BrowserType.LaunchOptions().setHeadless(false);
//     }


    private static synchronized BrowserType getBrowserType(Playwright playwright, String browserName) {
        switch (browserName) {
            case "chromium":
                return playwright.chromium();
            case "webkit":
                return playwright.webkit();
            case "firefox":
                return playwright.firefox();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static synchronized void closePage() {
        Playwright playwright = playwrightThread.get();
        Page page = pageThread.get();
        if (playwright != null) {
            page.close();
            pageThread.remove();
            playwright.close();
            playwrightThread.remove();
        }

    }
}
