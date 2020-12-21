package com.udacity.jwdnd.course1.cloudstorage.dbentities;

public class CredentialsForm {
    private Integer credentialId;
    private String url;
    private String username;
    private String password;
    private Integer userId;
    private String displayPassword;

    public String getDisplayPassword() {
        return displayPassword;
    }

    public void setDisplayPassword(String displayPassword) {
        this.displayPassword = displayPassword;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CredentialsForm{" +
                "credentialId=" + credentialId +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userId=" + userId +
                ", displayPassword='" + displayPassword + '\'' +
                '}';
    }
}
