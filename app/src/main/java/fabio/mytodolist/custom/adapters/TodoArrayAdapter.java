package fabio.mytodolist.custom.adapters;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import java.util.ArrayList;

import fabio.mytodolist.R;
import fabio.mytodolist.custom.listeners.TodoSwitchOnClickListener;
import fabio.mytodolist.models.beans.Todo;

public class TodoArrayAdapter extends ArrayAdapter<Todo> {
    public TodoArrayAdapter(final Context context, int resource) {
        super(context, resource);
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Todo todo = this.getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_array_adapter, parent, false);
        }

        final Switch todoSwitch = (Switch) convertView.findViewById(R.id.todoSwitch);

        this.setSwitchProperties(todoSwitch, todo);
        this.setSwitchOnClickListener(todoSwitch, todo);

        return convertView;
    }


    public ArrayList<Todo> getAllTodos() {
        final int todosCount = getCount();
        final ArrayList<Todo> todos = new ArrayList<Todo>();

        for (int i = 0; i < todosCount; ++i) {
            final Todo todo = getItem(i);
            todos.add(todo);
        }

        return todos;
    }

    public ArrayList<Todo> getAllTodos(boolean done) {
        final int todosCount = getCount();
        final ArrayList<Todo> todos = new ArrayList<Todo>();

        for (int i = 0; i < todosCount; ++i) {
            final Todo todo = getItem(i);

            if (todo.isDone() == done) {
                todos.add(todo);
            }
        }

        return todos;
    }


    private void setSwitchProperties(final Switch todoSwitch, final Todo todo) {
        todoSwitch.setText(todo.getText());
        todoSwitch.setChecked(todo.isDone());

        if (todoSwitch.isChecked()) {
            todoSwitch.setPaintFlags(todoSwitch.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            todoSwitch.setPaintFlags(0);
        }
    }


    private void setSwitchOnClickListener(final Switch todoSwitch, final Todo todo) {
        todoSwitch.setOnClickListener(new TodoSwitchOnClickListener(todo.getId()));
    }
}
