package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.dbentities.Notes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id = "logoutBtn")
    private WebElement logoutBtn;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    public WebElement getNotesTab() {
        return notesTab;
    }

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDesc;

    @FindBy(id = "saveNote")
    private WebElement saveNote;

    @FindBy(id = "note1")
    private WebElement deleteNote;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credTab;

    public WebElement getCredTab() {
        return credTab;
    }

    @FindBy(className = "noteTitle")
    private WebElement firstNoteTitle;

    @FindBy(className = "noteDescription")
    private WebElement firstNoteDescription;

    @FindBy(id = "addNoteBtn")
    private WebElement openNoteModalBtn;

    @FindBy(id = "addNewCredBtn")
    private WebElement addNewCredBtn;

    @FindBy(id = "userTable")
    private WebElement userTable;

    public WebElement getUserTable() {
        return userTable;
    }

    @FindBy(className = "editNote")
    private WebElement editNoteBtn;

    public WebElement getEditNoteBtn() {
        return editNoteBtn;
    }

    @FindBy(className = "editCred")
    private WebElement firstEditCredBtn;

    @FindBy(className = "deleteCred")
    private WebElement deleteCredBtn1;

    @FindBy(id = "credential-url")
    private WebElement credUrl;

    @FindBy(id = "credential-username")
    private WebElement credUser;

    @FindBy(id = "credential-password")
    private WebElement credPass;

    @FindBy(id = "credFormSubmit")
    private WebElement credSubmit;


    @FindBy(className = "cred-url")
    private WebElement firstCredUrl;

    @FindBy(className = "cred-user")
    private WebElement firstCredUser;

    @FindBy(className = "cred-pass")
    private WebElement firstCredPass;


    public HomePage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

    public Notes getFirstNote() {
        Notes result = new Notes();
        result.setNoteTitle(firstNoteTitle.getText());
        result.setNoteDescription(firstNoteDescription.getText());
        return result;
    }

    public CredentialsForm getFirstCredential() {
        CredentialsForm result = new CredentialsForm();
        result.setUsername(firstCredUser.getText());
        result.setUrl(firstCredUrl.getText());
        result.setPassword(firstCredPass.getText());
        return result;
    }

    public void logout(){
        try { Thread.sleep(1000); } catch (Exception e){ }
        logoutBtn.click();
    }

    public void openNote() {
        notesTab.click();
    }

    public void openCredTab() {
        System.out.println(credTab.getAttribute("aria-selected"));
        if(!credTab.getAttribute("aria-selected").equals("true"))
            credTab.click();
        try { Thread.sleep(1000); } catch (Exception e){ }
    }

    public void createNewNote(String title, String desc) {
        notesTab.click();
        try { Thread.sleep(1000); } catch (Exception e){ }
        System.out.println(openNoteModalBtn.getText());
        openNoteModalBtn.click();
        try { Thread.sleep(1000); } catch (Exception e){ }
        this.noteTitle.sendKeys(title);
        this.noteDesc.sendKeys(desc);
        saveNote.click();
    }


    public void createNewCredential(String url, String user, String pass) {
        openCredTab();
        System.out.println(addNewCredBtn.getText());
        addNewCredBtn.click();
        try { Thread.sleep(1000); } catch (Exception e){ }
        this.credUrl.sendKeys(url);
        this.credPass.sendKeys(pass);
        this.credUser.sendKeys(user);
        credSubmit.click();
    }

    public void editNote(String title, String desc){
        try
        {
            notesTab.click();
            Thread.sleep(1000);
            editNoteBtn.click();
            Thread.sleep(1000);
            this.noteTitle.clear();
            this.noteDesc.clear();
            this.noteTitle.sendKeys(title);
            this.noteDesc.sendKeys(desc);
            saveNote.click();
        } catch (Exception e){ }
    }


    public void editCredentials(String url, String user, String pass){
        try
        {
            credTab.click();
            Thread.sleep(1000);
            firstEditCredBtn.click();
            Thread.sleep(1000);
            this.credUrl.clear();
            this.credUser.clear();
            this.credPass.clear();
            this.credUrl.sendKeys(url);
            this.credUser.sendKeys(user);
            this.credPass.sendKeys(pass);

            Thread.sleep(1000);
            credSubmit.click();
        } catch (Exception e){ }
    }

    public void deleteNoteClick(){
        System.out.println(deleteNote.getText());
        deleteNote.click();
    }

    public void deleteCredClick(){
        System.out.println(deleteCredBtn1.getText());
        deleteCredBtn1.click();
    }

    public WebElement getLogoutBtn() {
        return logoutBtn;
    }

    public WebElement getNoteTitle() {
        return noteTitle;
    }

    public WebElement getNoteDesc() {
        return noteDesc;
    }

    public WebElement getSaveNote() {
        return saveNote;
    }

    public WebElement getDeleteNote() {
        return deleteNote;
    }

    public WebElement getFirstNoteTitle() {
        return firstNoteTitle;
    }

    public WebElement getFirstNoteDescription() {
        return firstNoteDescription;
    }

    public WebElement getOpenNoteModalBtn() {
        return openNoteModalBtn;
    }

    public WebElement getAddNewCredBtn() {
        return addNewCredBtn;
    }

    public WebElement getFirstEditCredBtn() {
        return firstEditCredBtn;
    }

    public WebElement getDeleteCredBtn1() {
        return deleteCredBtn1;
    }

    public WebElement getCredUrl() {
        return credUrl;
    }

    public WebElement getCredUser() {
        return credUser;
    }

    public WebElement getCredPass() {
        return credPass;
    }

    public WebElement getCredSubmit() {
        return credSubmit;
    }

    public WebElement getFirstCredUrl() {
        return firstCredUrl;
    }

    public WebElement getFirstCredUser() {
        return firstCredUser;
    }

    public WebElement getFirstCredPass() {
        return firstCredPass;
    }

    public WebElement getCredentialTable() {
        return credentialTable;
    }
    //    @FindBy(id = "logoutBtn")
//    private WebElement logoutBtn;
//
//    @FindBy(id = "logoutBtn")
//    private WebElement logoutBtn;
}
