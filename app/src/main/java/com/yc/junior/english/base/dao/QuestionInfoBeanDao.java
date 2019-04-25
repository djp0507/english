package com.yc.junior.english.base.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yc.junior.english.speak.model.bean.QuestionInfoBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "QUESTION_INFO_BEAN".
*/
public class QuestionInfoBeanDao extends AbstractDao<QuestionInfoBean, String> {

    public static final String TABLENAME = "QUESTION_INFO_BEAN";

    /**
     * Properties of entity QuestionInfoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ItemType = new Property(0, int.class, "itemType", false, "ITEM_TYPE");
        public final static Property Id = new Property(1, String.class, "id", true, "ID");
        public final static Property ReadId = new Property(2, String.class, "readId", false, "READ_ID");
        public final static Property CnSentence = new Property(3, String.class, "cnSentence", false, "CN_SENTENCE");
        public final static Property EnSentence = new Property(4, String.class, "enSentence", false, "EN_SENTENCE");
        public final static Property Voice_url = new Property(5, String.class, "voice_url", false, "VOICE_URL");
        public final static Property Title = new Property(6, String.class, "title", false, "TITLE");
        public final static Property IsShowSpeak = new Property(7, boolean.class, "isShowSpeak", false, "IS_SHOW_SPEAK");
        public final static Property IsShowResult = new Property(8, boolean.class, "isShowResult", false, "IS_SHOW_RESULT");
        public final static Property SpeakResult = new Property(9, boolean.class, "speakResult", false, "SPEAK_RESULT");
        public final static Property Percent = new Property(10, String.class, "percent", false, "PERCENT");
    }


    public QuestionInfoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public QuestionInfoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"QUESTION_INFO_BEAN\" (" + //
                "\"ITEM_TYPE\" INTEGER NOT NULL ," + // 0: itemType
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 1: id
                "\"READ_ID\" TEXT," + // 2: readId
                "\"CN_SENTENCE\" TEXT," + // 3: cnSentence
                "\"EN_SENTENCE\" TEXT," + // 4: enSentence
                "\"VOICE_URL\" TEXT," + // 5: voice_url
                "\"TITLE\" TEXT," + // 6: title
                "\"IS_SHOW_SPEAK\" INTEGER NOT NULL ," + // 7: isShowSpeak
                "\"IS_SHOW_RESULT\" INTEGER NOT NULL ," + // 8: isShowResult
                "\"SPEAK_RESULT\" INTEGER NOT NULL ," + // 9: speakResult
                "\"PERCENT\" TEXT);"); // 10: percent
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"QUESTION_INFO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, QuestionInfoBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getItemType());
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String readId = entity.getReadId();
        if (readId != null) {
            stmt.bindString(3, readId);
        }
 
        String cnSentence = entity.getCnSentence();
        if (cnSentence != null) {
            stmt.bindString(4, cnSentence);
        }
 
        String enSentence = entity.getEnSentence();
        if (enSentence != null) {
            stmt.bindString(5, enSentence);
        }
 
        String voice_url = entity.getVoice_url();
        if (voice_url != null) {
            stmt.bindString(6, voice_url);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(7, title);
        }
        stmt.bindLong(8, entity.getIsShowSpeak() ? 1L: 0L);
        stmt.bindLong(9, entity.getIsShowResult() ? 1L: 0L);
        stmt.bindLong(10, entity.getSpeakResult() ? 1L: 0L);
 
        String percent = entity.getPercent();
        if (percent != null) {
            stmt.bindString(11, percent);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, QuestionInfoBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getItemType());
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String readId = entity.getReadId();
        if (readId != null) {
            stmt.bindString(3, readId);
        }
 
        String cnSentence = entity.getCnSentence();
        if (cnSentence != null) {
            stmt.bindString(4, cnSentence);
        }
 
        String enSentence = entity.getEnSentence();
        if (enSentence != null) {
            stmt.bindString(5, enSentence);
        }
 
        String voice_url = entity.getVoice_url();
        if (voice_url != null) {
            stmt.bindString(6, voice_url);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(7, title);
        }
        stmt.bindLong(8, entity.getIsShowSpeak() ? 1L: 0L);
        stmt.bindLong(9, entity.getIsShowResult() ? 1L: 0L);
        stmt.bindLong(10, entity.getSpeakResult() ? 1L: 0L);
 
        String percent = entity.getPercent();
        if (percent != null) {
            stmt.bindString(11, percent);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1);
    }    

    @Override
    public QuestionInfoBean readEntity(Cursor cursor, int offset) {
        QuestionInfoBean entity = new QuestionInfoBean( //
            cursor.getInt(offset + 0), // itemType
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // readId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // cnSentence
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // enSentence
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // voice_url
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // title
            cursor.getShort(offset + 7) != 0, // isShowSpeak
            cursor.getShort(offset + 8) != 0, // isShowResult
            cursor.getShort(offset + 9) != 0, // speakResult
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // percent
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, QuestionInfoBean entity, int offset) {
        entity.setItemType(cursor.getInt(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setReadId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCnSentence(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setEnSentence(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setVoice_url(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTitle(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIsShowSpeak(cursor.getShort(offset + 7) != 0);
        entity.setIsShowResult(cursor.getShort(offset + 8) != 0);
        entity.setSpeakResult(cursor.getShort(offset + 9) != 0);
        entity.setPercent(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final String updateKeyAfterInsert(QuestionInfoBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(QuestionInfoBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(QuestionInfoBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
