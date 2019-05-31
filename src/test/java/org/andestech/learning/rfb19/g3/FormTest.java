package org.andestech.learning.rfb19.g3;


import com.sun.deploy.util.Waiter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class FormTest
{
    private WebDriver wd = null;
    private ChromeOptions chromeOptions;

    @BeforeClass
    public void initData(){
    System.setProperty("webdriver.chrome.driver",
            "E:\\selenium_drivers\\chromedriver_2.46.628402.exe");

//        System.setProperty("webdriver.chrome.driver",
//                "E:\\selenium_drivers\\chromedriver_74.0.3729.6.exe");
    // chromedriver_2.46.628402.exe
    System.out.println("+++ Class: " + this);

    chromeOptions = new ChromeOptions();

    chromeOptions.addArguments("--user-data-dir=C:\\Users\\and\\AppData\\Local\\Chromium\\User Data");
    chromeOptions.setBinary("E:\\progs\\chrome-win\\chrome.exe");

    chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
     //chromeOptions.setHeadless(true);


    }


    public static boolean isAlertPresent(WebDriver wd){

            WebDriverWait wait = new WebDriverWait(wd, 0);
            try {
                wait.until(ExpectedConditions.alertIsPresent());
                return true;
            } catch (TimeoutException ex) {
                return false;
            }
    }

    @Test
    public void positiveLoginTest() throws InterruptedException
    {
        wd = new ChromeDriver(chromeOptions);

        Wait<WebDriver> wait1 = new WebDriverWait(wd,5);
        Wait<WebDriver> wait2 = new FluentWait<>(wd).withTimeout(Duration.ofSeconds(5)).
                pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);

        wd.get("http://andestech.org/learning/rfb18/");
        wait1.until( x -> x.findElement(By.linkText("Login"))).click();

        String loginName = "ppetrov", loginPass = "P@ssw0rd22";


        wd.findElement(By.name("reset")).click();

        WebElement login = wd.findElement(By.id("login"));
        login.sendKeys(loginName);
        wd.findElement(By.id("pass")).sendKeys(loginPass);


        login.submit();

       if(isAlertPresent(wd)) {
          Alert alert = wd.switchTo().alert();
          String info = alert.getText();
          alert.accept();

          Assert.fail(info);

       }

        String headerText = wd.findElement(By.tagName("header")).getText();
        System.out.println(headerText);

        Cookie cookie = wd.manage().getCookieNamed("loginOk");
        String cookieData = cookie.getValue();

        Assert.assertTrue(cookieData.indexOf(loginName) != -1 && headerText.indexOf(loginName) != -1, "Smth wrong");

        Thread.sleep(2000);

        wait1.until( x -> x.findElement(By.linkText("Logout"))).click();

    }






    @Test
    public void formTest() throws InterruptedException
    {
        wd = new ChromeDriver(chromeOptions);
        //    wd.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);

        // wd.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);

//        Wait<WebDriver> wait1 = new WebDriverWait(wd,5).withTimeout(Duration.ofSeconds(5)).
//                pollingEvery(Duration.ofSeconds(1));


        Wait<WebDriver> wait1 = new WebDriverWait(wd,5);

        Wait<WebDriver> wait2 = new FluentWait<>(wd).withTimeout(Duration.ofSeconds(5)).
                pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);





    }



    @AfterClass
    public void tearDown()
    {
      if(wd != null) wd.quit();
      System.out.println("--- Class: " + this);
    }

}
