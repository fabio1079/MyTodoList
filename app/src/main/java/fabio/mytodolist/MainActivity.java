package fabio.mytodolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fabio.mytodolist.custom.adapters.TodoArrayAdapter;
import fabio.mytodolist.models.dao.TodoDao;
import fabio.mytodolist.models.beans.Todo;

public class MainActivity extends AppCompatActivity {
    private EditText userTodoEditText;
    private Button addTodoButton;
    private ListView userTodoListView;
    private Button deleteTodosButton;


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
        this.deleteTodosButton = (Button) findViewById(R.id.deleteTodosButton);

        this.userTodoListView.setAdapter(new TodoArrayAdapter(getApplicationContext(), R.layout.todo_array_adapter));
    }


    private void setViewEvents() {
        this.setAddTodoButtonOnClickListener();
        this.setDeleteTodosButtonOnClickEvent();
    }


    private void fillTodoList() {
        final TodoDao todoDao = new TodoDao(getApplicationContext());
        final ArrayList<Todo> todos = todoDao.getAllTodos();
        final TodoArrayAdapter todoArrayAdapter = (TodoArrayAdapter) this.userTodoListView.getAdapter();

        todoArrayAdapter.addAll(todos);
    }


    private void setAddTodoButtonOnClickListener() {
        this.addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String todoText = userTodoEditText.getText().toString();
                final TodoArrayAdapter todoArrayAdapter = (TodoArrayAdapter) userTodoListView.getAdapter();

                try {
                    final Todo todoToSave = new Todo(todoText);
                    final TodoDao todoDao = new TodoDao(getApplicationContext());
                    final long savedTodoId = todoDao.saveTodo(todoToSave);

                    if (savedTodoId != -1) {
                        final Todo savedTodo = todoDao.getTodo(savedTodoId);
                        todoArrayAdapter.add(savedTodo);
                        userTodoEditText.setText("");
                    }
                } catch (Error err) {
                    Toast.makeText(getApplicationContext(), err.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void setDeleteTodosButtonOnClickEvent() {
        this.deleteTodosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TodoArrayAdapter todoArrayAdapter = (TodoArrayAdapter) userTodoListView.getAdapter();
                final ArrayList<Todo> todos = todoArrayAdapter.getAllTodos(true);
                final TodoDao todoDao = new TodoDao(getApplicationContext());

                for (final Todo todo: todos) {
                    final boolean deleted = todoDao.deleteTodo(todo);

                    if (deleted) {
                        todoArrayAdapter.remove(todo);
                    }
                }
            }
        });
    }
}
