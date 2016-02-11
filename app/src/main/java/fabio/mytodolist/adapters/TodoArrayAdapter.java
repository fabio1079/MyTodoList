package fabio.mytodolist.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import fabio.mytodolist.R;
import fabio.mytodolist.dao.TodoDao;
import fabio.mytodolist.models.Todo;

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

        todoSwitch.setText(todo.getText());
        todoSwitch.setChecked(todo.isDone());

        todoSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Switch clickSwitch = (Switch) v;
                todo.setDone(clickSwitch.isChecked());
                TodoDao todoDao = new TodoDao(getContext());

                final boolean saved = todoDao.updateTodo(todo);

                if (saved) {
                    Toast.makeText(getContext(), "Todo data updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Could no save update", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }
}
