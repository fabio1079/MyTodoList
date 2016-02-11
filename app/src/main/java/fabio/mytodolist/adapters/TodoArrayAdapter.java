package fabio.mytodolist.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import fabio.mytodolist.R;
import fabio.mytodolist.models.Todo;

public class TodoArrayAdapter extends ArrayAdapter<Todo> {
    public TodoArrayAdapter(Context context, int resource) {
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

        todoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                todo.setDone(isChecked);
            }
        });

        return convertView;
    }
}
