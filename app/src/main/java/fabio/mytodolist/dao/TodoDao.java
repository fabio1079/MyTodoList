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


    public long saveTodo(final Todo todo) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final ContentValues contentValues = this.getDatabaseValues(todo);

        final long result = db.insert(TABLE_NAME, null, contentValues);

        return result;
    }


    public Todo getTodo(final long id) {
        final String SELECT_TODO_QUERY = "SELECT * FROM "+TABLE_NAME+" WHERE id = ?";
        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor todoSelectCursor = db.rawQuery(SELECT_TODO_QUERY, new String[]{Long.toString(id)});

        todoSelectCursor.moveToFirst();
        final Todo todo = this.getTodoFromCursor(todoSelectCursor);

        return todo;
    }


    public ArrayList<Todo> getAllTodos() {
        final Cursor todosCursor = this.selectAllCursor();
        ArrayList<Todo> todos = new ArrayList<Todo>();

        while (todosCursor.moveToNext()) {
            final Todo todo = this.getTodoFromCursor(todosCursor);
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

    public boolean updateTodo(final int id, final boolean done) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMNS_NAMES[1], done);

        final long result = db.update(TABLE_NAME, contentValues, "id = "+id, null);

        return this.getQueryResultAsBoolean(result);
    }


    public boolean deleteTodo(final Todo todo) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final int result = db.delete(TABLE_NAME, "id = "+todo.getId(), null);

        if (result > 0) {
            return true;
        } else {
            return false;
        }
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


    private Todo getTodoFromCursor(Cursor todoCursor) {
        final int id = todoCursor.getInt(0);
        final String text = todoCursor.getString(1);
        final int done = todoCursor.getInt(2);
        final Todo todo = new Todo(id, text, done != 0);

        return todo;
    }
}
