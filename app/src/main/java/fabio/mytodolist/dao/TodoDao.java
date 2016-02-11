package fabio.mytodolist.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fabio.mytodolist.models.Todo;

public class TodoDao extends TodoListDatabaseHelper {
    private static final String TABLE_NAME = "todos";
    private static final String[] COLUMNS_NAMES = {
      "text", "done"
    };

    public TodoDao(Context context) {
        super(context);
    }


    public boolean saveTodo(final Todo todo) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final ContentValues contentValues = this.getDatabaseValues(todo);

        final long result = db.insert(TABLE_NAME, null, contentValues);

        return this.getQueryResultAsBoolean(result);
    }


    public ArrayList<Todo> getAllTodos() {
        final Cursor todosCursor = this.selectAllCursor();
        ArrayList<Todo> todos = new ArrayList<Todo>();

        while (todosCursor.moveToNext()) {
            final int id = todosCursor.getInt(0);
            final String text = todosCursor.getString(1);
            final int done = todosCursor.getInt(2);
            final Todo todo = new Todo(id, text, done != 0);

            todos.add(todo);
        }

        return todos;
    }


    public boolean updateTodo(final Todo todo) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final ContentValues contentValues = this.getDatabaseValues(todo);
        final long result = db.update(TABLE_NAME, contentValues, "id = "+todo.getId(), null);

        return this.getQueryResultAsBoolean(result);
    }


    private Cursor selectAllCursor() {
        final String SELECT_SQL = "SELECT * FROM "+TABLE_NAME;
        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor todosCursor = db.rawQuery(SELECT_SQL, null);

        return todosCursor;
    }


    private ContentValues getDatabaseValues(final Todo todo) {
        final ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMNS_NAMES[0], todo.getText());

        int doneAsInt = 0;
        if (todo.isDone()) {
            doneAsInt = 1;
        }
        contentValues.put(COLUMNS_NAMES[1], doneAsInt);

        return contentValues;
    }


    private boolean getQueryResultAsBoolean(final long queryQesult) {
        if (queryQesult != -1) {
            return true;
        } else {
            return false;
        }
    }
}
