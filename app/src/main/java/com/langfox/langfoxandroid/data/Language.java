package com.langfox.langfoxandroid.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Jens Wilke on 22/11/2017.
 */


@DatabaseTable(tableName = Language.TABLE_NAME_LANGUAGE, daoClass = CustomDao.class)
public class Language {

    public static final String IMAGE_ROOT = "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/flags/";

    public static final String TABLE_NAME_LANGUAGE = "language";

    public static final String COLUMN_LANGUAGE_ID = "language_id";
    public static final String COLUMN_LANGUAGE_NAME = "name";
    public static final String COLUMN_LANGUAGE_NATIVE_NAME = "native_name";
    public static final String COLUMN_LANGUAGE_LANGUAGE_ISO1 = "language_iso1";
    public static final String COLUMN_LANGUAGE_LANGUAGE_ISO2b = "language_iso2b";
    public static final String COLUMN_LANGUAGE_RANK = "rank";
    public static final String COLUMN_LANGUAGE_STATUS = "status";

    //@DatabaseField(columnName = COLUMN_LANGUAGE_ID, generatedId = true)
    @DatabaseField(id = true, columnName = COLUMN_LANGUAGE_ID) //id = true avoids error: does not have an id field
    private Short languageId;

    @DatabaseField(columnName = COLUMN_LANGUAGE_NAME)
    private String name;

    @DatabaseField(columnName = COLUMN_LANGUAGE_NATIVE_NAME)
    private String nativeName;

    @DatabaseField(columnName = COLUMN_LANGUAGE_LANGUAGE_ISO1)
    private String iso6391;

    @DatabaseField(columnName = COLUMN_LANGUAGE_LANGUAGE_ISO2b)
    private String iso6392B;

    @DatabaseField(columnName = COLUMN_LANGUAGE_RANK)
    private short rank;

    @DatabaseField(columnName = COLUMN_LANGUAGE_STATUS)
    private short status;

    public Language() {
        // Don't forget the empty constructor, needed by ORMLite.
    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public String getNativeName() {
        return nativeName;
    }

    public String getIso6391() {
        return iso6391;
    }

    public String getIso6392B() {
        return iso6392B;
    }

    public short getRank() {
        return rank;
    }

    public short getStatus() {
        return status;
    }

    public String getImageURL() {
        return IMAGE_ROOT + iso6391 + ".svg";
    }

}