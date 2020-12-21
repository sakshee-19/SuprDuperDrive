package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResultPage {
    @FindBy(id = "login")
    private WebElement login;

    @FindBy(id = "continue")
    private WebElement continueBtn;

    @FindBy(id = "continue2")
    private WebElement continue2;

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
