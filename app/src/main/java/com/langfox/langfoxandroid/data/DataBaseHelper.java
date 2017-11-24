package com.langfox.langfoxandroid.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by pengchengliu on 05/10/2017.
 */

//public class DataBaseHelper extends SQLiteOpenHelper {
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "language.db";
    private static final int    DATABASE_VERSION = 16;

    public static final String TABLE_NAME = "language";

    private Dao<Language, Integer> languageDao = null;
    private Dao<User, Integer> userDao = null;
    private RuntimeExceptionDao<User, ?> m;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Language.class);
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Language.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        m = getRuntimeExceptionDao(User.class);
        return userDao;
    }

    public Dao<Language, Integer> getLanguageDao() throws SQLException {
        if (languageDao == null) {
            languageDao = getDao(Language.class);
        }
        return languageDao;
    }

    public void getLanguagesFromDb() {
        try {
            List<Language> languages = this.getLanguageDao().queryForAll();
            LanguageHelper.setLanguages(languages);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Language getLanguageFromDb(Integer languageId) {
        try {
            Language language = this.getLanguageDao().queryForId(languageId);
            return language;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void printAllUsers() {
        try {
            userDao = this.getUserDao();
            final List<User> users = userDao.queryForAll();
            for (User user : users) {
                Log.d("langfoxApp", "user: " + user.getUserId()
                        + " / " + user.getEmail()
                        + " / " + user.getUiLanguage().getName()
                        + " / " + user.getUiLanguage().getLanguageId());
            }
        } catch (SQLException e) {
            Log.d("langfoxApp", "userDao EXCEPTION 2");
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        languageDao = null;
        userDao = null;
        super.close();
    }

    private static DataBaseHelper sDatabaseHelper;

    public static DataBaseHelper getInstance() {
        return sDatabaseHelper;
    }

}