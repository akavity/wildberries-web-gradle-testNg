package org.akavity.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ResourceBundle;
import java.util.logging.Level;

import static com.codeborne.selenide.Browsers.CHROME;
import static com.codeborne.selenide.Selenide.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OldBaseTest {
//    private final ResourceBundle bundle = ResourceBundle.getBundle("test_framework");
//    private final String URL = bundle.getString("path_to_url");
//
//    @BeforeAll
//    public void start() {
//        SelenideLogger.addListener("allure", new AllureSelenide()
//                .screenshots(true)
//                .savePageSource(true)
//                .enableLogs(LogType.BROWSER, Level.ALL));
//
//        Configuration.browserSize = "1920x1080";
//        Configuration.timeout = 10_000;
//        Configuration.browser = CHROME;
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-notifications"); // Disable notifications
//        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
//    }
//
//    @BeforeEach
//    public void goToUrl() {
//        open(URL);
//    }
//
//    @AfterEach
//    public void clearData() {
//        clearBrowserLocalStorage();
//        clearBrowserCookies();
//        closeWebDriver();
//    }
}
