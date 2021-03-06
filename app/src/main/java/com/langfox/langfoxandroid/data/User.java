package com.langfox.langfoxandroid.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Jens Wilke on 22.11.2017.
 */

@DatabaseTable(tableName = User.TABLE_NAME_USER, daoClass = CustomDao.class)
public class User {

    public static final String TABLE_NAME_USER = "person";

    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_UI_LANGUAGE = "ui_language";


    @DatabaseField(id = true, columnName = COLUMN_USER_ID) //, generatedId = true
    private Integer userId;

    @DatabaseField(columnName = COLUMN_EMAIL)
    private String email;

    @DatabaseField(foreign = true, columnName = COLUMN_UI_LANGUAGE)
    private Language uiLanguage;

    public User() {
    }

    public Integer getUserId() {
        return userId;
    }

    public User setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Language getUiLanguage() {
        return uiLanguage;
    }

    public User setUiLanguage(Language uiLanguage) {
        this.uiLanguage = uiLanguage;
        return this;
    }
}
