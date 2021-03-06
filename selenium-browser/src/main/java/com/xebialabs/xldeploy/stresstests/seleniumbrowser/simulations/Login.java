package com.xebialabs.xldeploy.stresstests.seleniumbrowser.simulations;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** XL Deploy Login. */
public class Login extends SimulationBase {

    // Optional parameter key names
    private static final String LOGIN_URL_KEY = "baseUrl";
    private static final String USERNAME_KEY = "username";
    private static final String PASS_KEY = "password";

    // Default values
    private static final String XL_DEPLOY_LOCAL_LOGIN = "http://localhost:4516/#/login";
    private static final String DEFAULT_CREDENTIALS = "admin";

    @Override
    public void simulate(WebDriver driver) {
        String loginUrl = (System.getProperty(LOGIN_URL_KEY) == null || System.getProperty(LOGIN_URL_KEY).isEmpty())
                ? XL_DEPLOY_LOCAL_LOGIN : System.getProperty(LOGIN_URL_KEY);
        String username = (System.getProperty(USERNAME_KEY) == null || System.getProperty(USERNAME_KEY).isEmpty())
                ? DEFAULT_CREDENTIALS : System.getProperty(USERNAME_KEY);
        String pass = (System.getProperty(PASS_KEY) == null || System.getProperty(PASS_KEY).isEmpty())
                ? DEFAULT_CREDENTIALS : System.getProperty(PASS_KEY);

        // launch ChromeDriver and direct it to the XL Deploy Login URL
        driver.get(loginUrl);

        //login to XL-Deploy
        driver.findElement(By.xpath("//form//input[@name='username']")).sendKeys(username);
        driver.findElement(By.xpath("//form//input[@name='password']")).sendKeys(pass);
        driver.findElement(By.xpath("//form//button[@type='submit']")).click();

        performAssertion(driver);
    }

    /** Perform assertions regarding landing page after login. */
    @Override
    protected void performAssertion(WebDriver driver) {
        WebElement loggedInAs = driver.findElement(By.cssSelector("span.logged-in-as"));
        Assert.assertNotNull(loggedInAs);

        WebElement loggedInAsUsername = driver.findElement(By.cssSelector("span.logged-in-as span.username"));
        Assert.assertNotNull(loggedInAsUsername);
    }

}
