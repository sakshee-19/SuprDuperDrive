package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {
    @FindBy(id = "login")
    private WebElement login;

    @FindBy(id = "continue")
    private WebElement continueBtn;

    @FindBy(id = "continue2")
    private WebElement continue2;

    public ResultPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void goToLogin() {
        login.click();
    }

    public void goToHome1() {
        this.continueBtn.click();
    }

    public void goToHomeFromError2() {
        this.continue2.click();
    }
}
