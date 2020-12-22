package com.udacity.jwdnd.course1.cloudstorage.page;

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

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

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

    @FindBy(className = "noteTitle")
    private WebElement firstNoteTitle;

    @FindBy(className = "noteDescription")
    private WebElement firstNoteDescription;

    @FindBy(id = "addNoteBtn")
    private WebElement openNoteModalBtn;

    @FindBy(className = "editNote")
    private WebElement editNoteBtn;



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

    public void logout(){
        logoutBtn.click();
    }

    public void openNote() {
        notesTab.click();
        try
        {
            Thread.sleep(1000);
        } catch (Exception e){

        }

    }

    public void createNewNote(String title, String desc) {
        notesTab.click();
        try
        {
            Thread.sleep(1000);
        } catch (Exception e){

        }
        System.out.println(openNoteModalBtn.getText());
        openNoteModalBtn.click();
        try
        {
            Thread.sleep(1000);
        } catch (Exception e){

        }
        this.noteTitle.sendKeys(title);
        this.noteDesc.sendKeys(desc);
        saveNote.click();
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
        } catch (Exception e){

        }
    }

    public void deleteNoteClick(){
        System.out.println(deleteNote.getText());
        deleteNote.click();
    }

//    @FindBy(id = "logoutBtn")
//    private WebElement logoutBtn;
//
//    @FindBy(id = "logoutBtn")
//    private WebElement logoutBtn;
}
