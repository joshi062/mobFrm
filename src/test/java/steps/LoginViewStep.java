package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;


public class LoginViewStep extends LoginViewStep1 {

    AppiumDriverLocalService appiumService = AppiumDriverLocalService.buildDefaultService();


    @Before
    public void startAppiumServer() throws IOException {
        int port = 4723;
        String nodeJS_Path = "C:/Program Files/NodeJS/node.exe";
        String appiumJS_Path = "C:/Program Files (x86)/Appium/resources/app/node_modules/appium/lib/appium.js";
        String osName = System.getProperty("os.name");
        if (osName.contains("Mac")) {
            appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File(("/usr/local/bin/node"))).withAppiumJS(new File(("/usr/local/bin/appium"))).withIPAddress("0.0.0.0").usingPort(port).withArgument(GeneralServerFlag.SESSION_OVERRIDE).withLogFile(new File("build/appium.log")));
        } else if (osName.contains("Windows")) {
            System.out.println("INSIDES");
            appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File(nodeJS_Path)).withAppiumJS(new File(appiumJS_Path)).withIPAddress("0.0.0.0").usingPort(port).withArgument(GeneralServerFlag.SESSION_OVERRIDE).withLogFile(new File("build/appium.log")));
        }

    }





    @Given("^User is on the login page$")
    public void userIsOnTheLoginPage() throws Throwable {
        appiumService.start();
        DesiredCapabilities capabilities = DesiredCapabilities.android();
        capabilities.setCapability("appium-version", "v1.9.1");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "5.0");
        capabilities.setCapability("deviceName", "192.168.235.101:5555");
        capabilities.setCapability("fullReset", true);
        capabilities.setCapability("newCommandTimeout", 120);
      //  capabilities.setCapability("appWaitActivity", "SplashActivity");
        capabilities.setCapability("app", "C:\\Application\\EriBank.apk");

        this.appiumDriver = new AppiumDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        this.appiumDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);


        System.out.println("LoginStepBeforeClass run pass.........." + this.appiumDriver);
        By loginBtn = By.id("loginButton");
        Assert.assertTrue(this.appiumDriver.findElement(loginBtn).isDisplayed());
        System.out.println("@User is on the login page run pass..........");
    }

    @And("^User enters Credentials to LogIn$")
    public void userEntersCredentialsToLogIn(DataTable usercredentials) throws Throwable {
        System.out.println("----ENTERING CREDENTIALS-------");
        List<List<String>> data = usercredentials.raw();
        By userName = By.id("usernameTextField");
        By passWord = By.id("passwordTextField");
        this.appiumDriver.findElement(userName).sendKeys(data.get(0).get(0));
        new WebDriverWait(this.appiumDriver, 60).until(ExpectedConditions.presenceOfElementLocated(userName));
        this.appiumDriver.findElement(passWord).sendKeys(data.get(0).get(1));
        new WebDriverWait(this.appiumDriver, 30).until(ExpectedConditions.presenceOfElementLocated(passWord));
        System.out.println("User enters Credentials to LogIn run pass..........");
    }


    @And("^I press \"([^\"]*)\"$")
    public void iPress(String arg0) throws Throwable {
        Thread.sleep(1000);
        this.appiumDriver.findElement(By.xpath("//*[@text='Login']")).click();
        System.out.println(" I press run pass..........");
    }


    @Then("^\"([^\"]*)\" button is Visible$")
    public void buttonIsVisible(String arg0) throws Throwable {

        By logoutBtn = By.id("logoutButton");
        Assert.assertTrue(this.appiumDriver.findElement(logoutBtn).isDisplayed());
        System.out.println("button is Visible run pass..........");
    }

    @After
    public void tearDown() throws Exception {
        this.appiumDriver.quit();
        appiumService.stop();
    }

}
