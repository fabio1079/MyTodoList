package fabio.mytodolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fabio.mytodolist.adapters.TodoArrayAdapter;
import fabio.mytodolist.dao.TodoDao;
import fabio.mytodolist.dao.TodoListDatabaseHelper;
import fabio.mytodolist.models.Todo;

public class MainActivity extends AppCompatActivity {
    private EditText userTodoEditText;
    private Button addTodoButton;
    private ListView userTodoListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setViewElements();
        this.setViewEvents();
        this.fillTodoList();
    }


    private void setViewElements() {
        this.userTodoEditText = (EditText) findViewById(R.id.userTodoEditText);
        this.addTodoButton = (Button) findViewById(R.id.addTodoButton);
        this.userTodoListView = (ListView) findViewById(R.id.userTodoListView);

        this.userTodoListView.setAdapter(new TodoArrayAdapter(getApplicationContext(), R.layout.todo_array_adapter));
    }


    private void setViewEvents() {
        this.addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String todoText = userTodoEditText.getText().toString();
                final TodoArrayAdapter todoArrayAdapter = (TodoArrayAdapter) userTodoListView.getAdapter();

                try {
                    final Todo todo = new Todo(todoText);
                    todoArrayAdapter.add(todo);

                    userTodoEditText.setText("");

                    final TodoDao todoDao = new TodoDao(getApplicationContext());

                    final boolean saved = todoDao.saveTodo(todo);
                } catch (Error err) {
                    Toast.makeText(getApplicationContext(), err.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void fillTodoList() {
        final TodoDao todoDao = new TodoDao(getApplicationContext());
        final ArrayList<Todo> todos = todoDao.getAllTodos();
        final TodoArrayAdapter todoArrayAdapter = (TodoArrayAdapter) this.userTodoListView.getAdapter();

        todoArrayAdapter.addAll(todos);
    }
}
