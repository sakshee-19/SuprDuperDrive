package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(tagName = "form")
    private WebElement signUpForm;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "login-link2")
    private WebElement loginLink;

    @FindBy(id = "success-msg")
    private WebElement success;

    @FindBy(id = "error-msg")
    private WebElement error;

    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void signUp(String username, String password, String firstname, String lastname) {
        inputFirstName.sendKeys(firstname);
        inputLastName.sendKeys(lastname);
        inputPassword.sendKeys(password);
        inputUsername.sendKeys(username);
        signUpForm.submit();
    }

    public void clickLogin(){
        loginLink.click();
    }

    public String getErrorMsg(){
        return error.getText();
    }
    public String getSuccessMsg(){
        return success.getText();
    }

    public void setErrorMsg(String msg){
        error.sendKeys(msg);
    }
    public void setSuccessMsg(String msg){
        success.sendKeys(msg);
    }

}
