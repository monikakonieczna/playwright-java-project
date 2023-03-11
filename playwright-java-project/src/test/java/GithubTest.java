import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public class GithubTest {

    private String home = "https://github.com/";
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium()
                            .launch();
    }

    @BeforeEach
    public void createContextAndPage() {
        context = browser
                .newContext();
        page = context.newPage();
    }

    @AfterEach
    public void closeContext() {
        context.close();
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    @Test
    public void pwTestWithContext() {
        page.navigate(home);
        System.out.println(page.title());
        Assertions.assertEquals(page.title(), "GitHub: Let’s build from here · GitHub");
    }
}
