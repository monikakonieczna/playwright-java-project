package models;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;
    private final Locator loginButton;
    private final Locator usernameInput;
    private final Locator passwordInput;


    public LoginPage(Page page) {
        this.page = page;
        this.loginButton = page.locator("#login-button");
        this.passwordInput = page.locator("");
        this.usernameInput = page.locator("");
    }

    public void navigate() {
        page.navigate("https://www.saucedemo.com");
    }

    public ProductsPage login(String username, String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
        return new ProductsPage(page);
    }

}
