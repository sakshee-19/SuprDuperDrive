package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(tagName = "form")
    private WebElement loginForm;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "signup-link")
    private WebElement signUpLink;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id = "logout")
    private WebElement logout;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void login(String username, String pass){
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(pass);
        loginForm.submit();
    }

    public void goToSignUpPage(){
        signUpLink.click();
    }
    public String getErrorMsg() {
        return errorMsg.getText();
    }
    public String getLogoutMsg() {
        return logout.getText();
    }
}
