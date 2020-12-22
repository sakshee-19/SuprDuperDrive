package com.udacity.jwdnd.course1.cloudstorage.flow;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Notes;
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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteFlowTest {
    @LocalServerPort
    private Integer port;

    private String initUrl;

    private static WebDriver driver;

    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private ResultPage resultPage;
    WebDriverWait wait;
    WebElement marker;



    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        initUrl = "http://localhost:" + port;
        wait = new WebDriverWait(driver, 10);

        driver.get(initUrl+"/signup");
        signupPage = new SignupPage(driver);

        signupPage.signUp("saku","saku", "sakshee", "jain");
        Thread.sleep(1000);

        signupPage.clickLogin();
        Thread.sleep(1000);
        System.out.println(driver.getCurrentUrl());
        loginPage = new LoginPage(driver);
        loginPage.login("saku", "saku");
        Thread.sleep(1000);
        homePage = new HomePage(driver);
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());
    }

    @AfterAll
    public static void  afterAll(){
        driver.quit();
    }

    @Test
    public void testCreateNote()  {
        assertEquals("Home", driver.getTitle());

        homePage.createNewNote("test note","TODOS");
        assertEquals("Result", driver.getTitle());

        resultPage = new ResultPage(driver);
        marker = wait.until(webDriver -> webDriver.findElement(By.id("continue")));
        resultPage.goToHome1();

        marker = wait.until(webDriver -> webDriver.findElement(By.id("contentDiv")));
        homePage.openNote();
        try { Thread.sleep(1000); } catch (Exception e){ }
        Notes note = homePage.getFirstNote();
        assertEquals("TODOS", note.getNoteDescription() );
        assertEquals("test note", note.getNoteTitle() );

    }

    @Test
    public void testEditNote(){
        homePage.createNewNote("test note","TODOS");
        resultPage = new ResultPage(driver);
        marker = wait.until(webDriver -> webDriver.findElement(By.id("continue")));
        resultPage.goToHome1();

        marker = wait.until(webDriver -> webDriver.findElement(By.id("contentDiv")));
        homePage.openNote();
        try { Thread.sleep(1000); } catch (Exception e){ }

        homePage.editNote("test note", "TODOS2");
        resultPage = new ResultPage(driver);
        marker = wait.until(webDriver -> webDriver.findElement(By.id("continue")));
        resultPage.goToHome1();

        marker = wait.until(webDriver -> webDriver.findElement(By.id("contentDiv")));
        homePage.openNote();

        try { Thread.sleep(1000); } catch (Exception e){ }
        Notes note = homePage.getFirstNote();

        assertEquals("TODOS2", note.getNoteDescription() );
        assertEquals("test note", note.getNoteTitle() );

    }

    @Test
    public void testDeleteNote(){
        homePage.createNewNote("test note","TODOS");
        resultPage = new ResultPage(driver);
        marker = wait.until(webDriver -> webDriver.findElement(By.id("continue")));
        resultPage.goToHome1();
        marker = wait.until(webDriver -> webDriver.findElement(By.id("contentDiv")));
        homePage.openNote();
        try { Thread.sleep(5000); } catch (Exception e){ }
        homePage.openNote();

        homePage.deleteNoteClick();
        resultPage = new ResultPage(driver);
        marker = wait.until(webDriver -> webDriver.findElement(By.id("continue")));
        resultPage.goToHome1();

        try { Thread.sleep(1000); } catch (Exception e){ }
        driver.get(initUrl+"/home");
        System.out.println(driver.getCurrentUrl());
        homePage.openNote();
        try { Thread.sleep(1000); } catch (Exception e){ }

        assertThrows(NoSuchElementException.class, ()->homePage.getFirstNote());

    }
}
