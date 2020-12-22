package com.udacity.jwdnd.course1.cloudstorage.flow;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.dbentities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialFlowTest {
    @LocalServerPort
    private Integer port;

    private String initUrl;

    private static WebDriver driver;

    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private ResultPage resultPage;
    static WebDriverWait wait;


    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @BeforeEach
    public void set(){
        initUrl = "http://localhost:" + port;
        signupPage = new SignupPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        resultPage = new ResultPage(driver);
    }

    public void testLogin(){
        driver.get(initUrl + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
        loginPage.login("saku", "saku");
        wait.until(ExpectedConditions.urlToBe(initUrl + "/home"));
        Assertions.assertEquals("Home", driver.getTitle());
    }

    public void setUp() throws InterruptedException {
        initUrl = "http://localhost:" + port;
        wait = new WebDriverWait(driver, 10);

        driver.get(initUrl+"/signup");
        signupPage.signUp("saku","saku", "sakshee", "jain");
        Thread.sleep(1000);

        testLogin();
        Thread.sleep(1000);
    }

    @AfterAll
    public static void  afterAll(){
        driver.quit();
    }


    @Test
    @Order(1)
    public void testCreateCredentials() throws InterruptedException {
        setUp();

        assertEquals("Home", driver.getTitle());

        homePage.createNewCredential("sa@adobe.com","sak", "saku");
        assertEquals("Result", driver.getTitle());

        resultPage = new ResultPage(driver);
        wait.until(ExpectedConditions.titleIs("Result"));

        resultPage.goToHome1();
        if(!driver.getTitle().equals("Home"))
            driver.get(initUrl+"/home");

        wait.until(ExpectedConditions.visibilityOf(homePage.getCredTab()));
        try { Thread.sleep(1000); } catch (Exception e){ }
        homePage.openCredTab();

        wait.until(ExpectedConditions.visibilityOf(homePage.getCredentialTable()));
        CredentialsForm credentialsForm = homePage.getFirstCredential();

        assertEquals("sa@adobe.com", credentialsForm.getUrl() );
        assertEquals("sak", credentialsForm.getUsername() );
        assertNotNull(credentialsForm.getPassword() );
    }

    @Test
    @Order(2)
    public void testEditCredential(){
        System.out.println("**START edit");
        testLogin();

        if(!driver.getTitle().equals("Home"))
            driver.get(initUrl+"/home");

        wait.until(ExpectedConditions.visibilityOf(homePage.getCredTab()));
        try { Thread.sleep(1000); } catch (Exception e){ }
        homePage.openCredTab();
        wait.until(ExpectedConditions.visibilityOf(homePage.getFirstEditCredBtn()));
        homePage.editCredentials("joc@gmail.com", "sak", "saku");

        resultPage = new ResultPage(driver);
        wait.until(ExpectedConditions.titleIs("Result"));
        resultPage.goToHome1();

        if(!driver.getTitle().equals("Home"))
            driver.get(initUrl+"/home");

        wait.until(ExpectedConditions.visibilityOf(homePage.getCredTab()));
        try { Thread.sleep(1000); } catch (Exception e){ }
        homePage.openCredTab();

        wait.until(ExpectedConditions.visibilityOf(homePage.getCredentialTable()));
        CredentialsForm credentialsForm = homePage.getFirstCredential();

        assertEquals("joc@gmail.com", credentialsForm.getUrl() );
        assertEquals("sak", credentialsForm.getUsername() );
        assertNotNull(credentialsForm.getPassword() );
    }

    @Test
    @Order(3)
    public void testDeleteCredential(){
        testLogin();
        if(!driver.getTitle().equals("Home"))
            driver.get(initUrl+"/home");

        wait.until(ExpectedConditions.visibilityOf(homePage.getCredTab()));
        try { Thread.sleep(1000); } catch (Exception e){ }
        homePage.openCredTab();

        wait.until(ExpectedConditions.visibilityOf(homePage.getDeleteCredBtn1()));
        homePage.deleteCredClick();

        resultPage = new ResultPage(driver);
        wait.until(ExpectedConditions.titleIs("Result"));
        resultPage.goToHome1();

        if(!driver.getTitle().equals("Home"))
            driver.get(initUrl+"/home");

        wait.until(ExpectedConditions.visibilityOf(homePage.getCredTab()));
        homePage.openCredTab();

        assertThrows(NoSuchElementException.class, ()->homePage.getFirstCredential());
    }
}
