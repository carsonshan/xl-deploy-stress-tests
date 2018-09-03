package com.xebialabs.xldeploy.stresstests.seleniumbrowser.simulations;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.logging.Logger;

/** XL Deploy -> Applications ->  Import -> From computer: "POST /deployit/package/upload/test-dar-1.0.dar" */
public class ImportApplication extends SimulationBase {

    private static final Logger LOGGER = Logger.getLogger(ImportApplication.class.getName());

    @Override
    public void simulate(ChromeDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 60);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-id='Applications']/div/span")));
        driver.findElementByXPath("//*[@data-id='Applications']/div/span").click();
        driver.findElementByXPath("//*[@data-id='Applications']/div/i[@data-id='Applications']").click();
        driver.findElementByXPath("//li[@data-path='import']/a").click();
        driver.findElementByXPath("//li[@data-path='import->fromComputer']/a").click();

        File testPackage = new File(getClass().getClassLoader().getResource("test-dar-1.0.dar").getFile());
        driver.findElementByXPath("//input[@name='ajax_upload_file_input']").sendKeys(testPackage.getAbsolutePath());
        driver.findElementByXPath("//button[@class='xl-button xl-primary']").click();

        performAssertion(driver);
        driver.findElementByXPath("//button[@class='xl-button xl-cancel']").click();
    }

    /** Perform assertions on application package import. */
    @Override
    protected void performAssertion(ChromeDriver driver) {
        WebElement progressBarSuccess = driver.findElementByCssSelector("div.progress-status.success");
        Assert.assertNotNull(progressBarSuccess);

        LOGGER.info(progressBarSuccess.getText());
    }

}
