package com.udacity.jwdnd.course1.cloudstorage.flow;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserFlowTest {

    @LocalServerPort
    private Integer port;

    private String initUrl;

    private static WebDriver driver;

    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;
    static WebDriverWait wait;
    WebElement marker;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);

    }

    @BeforeEach
    public void setUp(){
        initUrl = "http://localhost:" + port;
        signupPage = new SignupPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    @AfterAll
    public static void  afterAll(){
        driver.quit();
    }

    @Test
    public void userSupportTest() {

        driver.get(initUrl+"/signup");
        signupPage = new SignupPage(driver);

        signupPage.signUp("sak","sak", "sakshee", "jain");
        assertEquals("Login", driver.getTitle());

        loginPage.login("saku", "sakus");
        System.out.println(driver.getCurrentUrl());

        assertEquals("Invalid username or password", loginPage.getErrorMsg());
        assertTrue(driver.getCurrentUrl().endsWith("/login?error"));
        assertFalse(driver.getCurrentUrl().endsWith("/home"));

        loginPage.login("sak", "sak");
        assertTrue(driver.getCurrentUrl().endsWith("/home"));

        wait.until(ExpectedConditions.visibilityOf(homePage.getLogoutBtn()));
        homePage.logout();
        System.out.println(driver.getCurrentUrl());
        assertTrue(driver.getTitle().equals("Login"));

        driver.get(initUrl+"/home");
        assertTrue(driver.getTitle().equals("Login"));

    }

}
