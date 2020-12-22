package com.udacity.jwdnd.course1.cloudstorage.flow;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
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
    WebDriverWait wait;
    WebElement marker;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void setUp(){
        initUrl = "http://localhost:" + port;
    }

    @AfterAll
    public static void  afterAll(){
        driver.quit();
    }

    @Test
    public void userSupportTest() {

        driver.get(initUrl+"/signup");
        signupPage = new SignupPage(driver);

        signupPage.signUp("saku","saku", "sakshee", "jain");
        assertEquals("You successfully signed up! Please continue to the login page.", signupPage.getSuccessMsg());

        signupPage.clickLogin();

        loginPage = new LoginPage(driver);

        loginPage.login("saku", "sakus");
        System.out.println(driver.getCurrentUrl());

        assertEquals("Invalid username or password", loginPage.getErrorMsg());
        assertTrue(driver.getCurrentUrl().endsWith("/login?error"));
        assertFalse(driver.getCurrentUrl().endsWith("/home"));

        loginPage.login("saku", "saku");

        assertTrue(driver.getCurrentUrl().endsWith("/home"));

        homePage = new HomePage(driver);

        homePage.logout();
        assertTrue(driver.getTitle().equals("Login"));

        driver.get(initUrl+"/home");
        assertTrue(driver.getTitle().equals("Login"));

    }

}
