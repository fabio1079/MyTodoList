package fabio.mytodolist.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import fabio.mytodolist.R;
import fabio.mytodolist.models.Todo;

public class TodoArrayAdapter extends ArrayAdapter<Todo> {
    public TodoArrayAdapter(Context context, int resource, Todo[] objects) {
        super(context, resource, objects);
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Todo todo = this.getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_array_adapter, parent, false);
        }

        final TextView todoText = (TextView) convertView.findViewById(R.id.todoTextView);

        todoText.setText(todo.getText());

        return convertView;
    }
}
