package at.danceandfun.util;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


public class PasswordBean {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty
    private String oldPassword;

    @Size(min = 5, message = "{size.min}")
    private String newPasswordFirst;

    @Size(min = 5, message = "{size.min}")
    private String newPasswordSecond;

    private String errorMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPasswordFirst() {
        return newPasswordFirst;
    }

    public void setNewPasswordFirst(String newPasswordFirst) {
        this.newPasswordFirst = newPasswordFirst;
    }

    public String getNewPasswordSecond() {
        return newPasswordSecond;
    }

    public void setNewPasswordSecond(String newPasswordSecond) {
        this.newPasswordSecond = newPasswordSecond;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toString() {
        return "ID: " + id + " old: " + oldPassword + " new: "
                + newPasswordFirst + " message: " + errorMessage;
    }

}
