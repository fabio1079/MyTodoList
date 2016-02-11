package fabio.mytodolist.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoListDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo_list.db";
    private static final int DATABASE_VERSION = 1;

    public TodoListDatabaseHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(final SQLiteDatabase db) {
        this.createTableTodos(db);
    }


    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS 'todos'");
        this.onCreate(db);
    }


    private void createTableTodos(final SQLiteDatabase db) {
        final String TODOS_TABLE_SQL = "CREATE TABLE 'todos' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "'text' TEXT NOT NULL," +
                "'done' INTEGER NOT NULL DEFAULT (0))";

        db.execSQL(TODOS_TABLE_SQL);
    }
}
