package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import factories.PlaywrightFactory;
import org.junit.jupiter.api.*;

public class firstTest {


    @Test
    public void loginTest() {
        Page page = PlaywrightFactory.getPage();
        String home = "https://www.saucedemo.com";
        page.navigate(home, new Page.NavigateOptions().setTimeout(0)
                                                      .setWaitUntil(WaitUntilState.NETWORKIDLE));
        System.out.println(page.title());
        Assertions.assertEquals(page.title(), "Swag Labs");

        String username = "standard_user";
        String password = "secret_sauce";

        page.fill("#user-name", username);
        page.fill("#password", password);
        page.click("#login-button");
       // Assertions.assertEquals(page.title(), "Products");
    }
}
