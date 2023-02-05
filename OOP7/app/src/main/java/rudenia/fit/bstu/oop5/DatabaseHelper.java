package rudenia.fit.bstu.oop5;


import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "test6.db"; // название бд
    private static final int SCHEMA = 5; // версия базы данных
    static final String TABLE = "users"; // название таблицы в бд
    // названия столбцов
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_SELECTION = "selection";
    //----------------------------------------------------------------------------------------
    static final String COLUMN_ING = "ing";
    static final String COLUMN_RECEPT = "recept";
    static final String COLUMN_TIME = "time";
    static final String COLUMN_COST = "cost";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,selection TEXT, ing TEXT,recept TEXT,time INTEGER,cost INTEGER);");
        /*db.execSQL("CREATE TABLE users (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME
                + " TEXT, " + COLUMN_SELECTION + " TEXT," + COLUMN_ING + "TEXT," + COLUMN_RECEPT + "TEXT,"+ COLUMN_TIME + "INTEGER,"+ COLUMN_COST + "INTEGER);");*/
        // добавление начальных данных
       /* db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                +","+  COLUMN_SELECTION +","+ COLUMN_ING +","+ COLUMN_RECEPT +","+ COLUMN_TIME +","+ COLUMN_COST +") VALUES ('a','b','c','d',100,200);");*/
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}

