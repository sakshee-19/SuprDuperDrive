package com.udacity.jwdnd.course1.cloudstorage.flow;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
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
public class NoteFlowTest {
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
    public void testCreateNote() throws InterruptedException {
        setUp();
        assertEquals("Home", driver.getTitle());

        homePage.createNewNote("test note","TODOS");
        assertEquals("Result", driver.getTitle());

        resultPage = new ResultPage(driver);
        wait.until(ExpectedConditions.titleIs("Result"));
        resultPage.goToHome1();
        if(!driver.getTitle().equals("Home"))
            driver.get(initUrl+"/home");

        wait.until(ExpectedConditions.visibilityOf(homePage.getNotesTab()));
        try { Thread.sleep(1000); } catch (Exception e){ }
        homePage.openNote();
        wait.until(ExpectedConditions.visibilityOf(homePage.getUserTable()));
        Notes note = homePage.getFirstNote();

        try { Thread.sleep(1000); } catch (Exception e){ }
        assertEquals("TODOS", note.getNoteDescription() );
        assertEquals("test note", note.getNoteTitle() );
    }

    @Test
    @Order(2)
    public void testEditNote() throws InterruptedException {
//        homePage.createNewNote("test note","TODOS");
//        resultPage = new ResultPage(driver);
//        resultPage.goToHome1();
        testLogin();
        System.out.println("**START** testEditNote");
        System.out.println(driver.getCurrentUrl());
        driver.get(initUrl+"/home");
        System.out.println(driver.getCurrentUrl());
        if(!driver.getTitle().equals("Home"))
            driver.get(initUrl+"/home");

        wait.until(ExpectedConditions.titleIs("Home"));
        wait.until(ExpectedConditions.visibilityOf(homePage.getNotesTab()));
        homePage.openNote();

        wait.until(ExpectedConditions.visibilityOf(homePage.getEditNoteBtn()));
        homePage.editNote("test note", "TODOS2");

        resultPage = new ResultPage(driver);
        wait.until(ExpectedConditions.titleIs("Result"));
        resultPage.goToHome1();

        if(!driver.getTitle().equals("Home"))
            driver.get(initUrl+"/home");

        wait.until(ExpectedConditions.visibilityOf(homePage.getNotesTab()));
        try { Thread.sleep(1000); } catch (Exception e){ }
        homePage.openNote();
        wait.until(ExpectedConditions.visibilityOf(homePage.getUserTable()));

        Notes note = homePage.getFirstNote();
        assertEquals("TODOS2", note.getNoteDescription() );
        assertEquals("test note", note.getNoteTitle() );

    }

    @Test
    @Order(3)
    public void testDeleteNote(){
//        homePage.createNewNote("test note","TODOS");
//        resultPage = new ResultPage(driver);
//        marker = wait.until(webDriver -> webDriver.findElement(By.id("continue")));
//        resultPage.goToHome1();
        testLogin();
        if(!driver.getTitle().equals("Home"))
            driver.get(initUrl+"/home");
        try { Thread.sleep(1000); } catch (Exception e){ }
        wait.until(ExpectedConditions.visibilityOf(homePage.getNotesTab()));
        homePage.openNote();
        wait.until(ExpectedConditions.visibilityOf(homePage.getDeleteNote()));
        homePage.deleteNoteClick();

        resultPage = new ResultPage(driver);
        wait.until(ExpectedConditions.titleIs("Result"));
        resultPage.goToHome1();
        if(!driver.getTitle().equals("Home"))
            driver.get(initUrl+"/home");
        wait.until(ExpectedConditions.visibilityOf(homePage.getNotesTab()));
        homePage.openNote();
        try { Thread.sleep(1000); } catch (Exception e){ }

        assertThrows(NoSuchElementException.class, ()->homePage.getFirstNote());

    }
}
