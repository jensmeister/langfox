package com.langfox.langfoxandroid.data;

import android.provider.BaseColumns;

/**
 * Created by pengchengliu on 07/10/2017.
 */
//define the schema of database
public final class LangfoxContract {

    private LangfoxContract(){}

    public static final class LanguageEntry implements BaseColumns{

        public final static String TABLE_NAME = "language";
        public final static String COLUMN_LANGUAGE_ID = "language_id";
        public final static String COLUMN_LANGUAGE_NAME = "name";
        public final static String COLUMN_LANGUAGE_NATIVE_NAME = "native_name";
        public final static String COLUMN_LANGUAGE_LANGUAGE_ISO1 = "language_iso1";
        public final static String COLUMN_LANGUAGE_LANGUAGE_ISO2b = "language_iso2b";
        public final static String COLUMN_LANGUAGE_RANK = "rank";
        public final static String COLUMN_LANGUAGE_STATUS = "status";

    }

}
