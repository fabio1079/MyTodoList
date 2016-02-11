package fabio.mytodolist.custom.listeners;

import android.graphics.Paint;
import android.view.View;
import android.widget.Switch;

import fabio.mytodolist.models.beans.Todo;
import fabio.mytodolist.models.dao.TodoDao;

public class TodoSwitchOnClickListener implements View.OnClickListener {
    private final Todo todo;

    public TodoSwitchOnClickListener(final Todo todo) {
        super();

        this.todo = todo;
    }

    @Override
    public void onClick(View v) {
        final Switch clickSwitch = (Switch) v;
        final TodoDao todoDao = new TodoDao(clickSwitch.getContext());

        final boolean saved = todoDao.updateTodo(this.todo.getId(), clickSwitch.isChecked());

        if (saved) {
            this.todo.setDone(clickSwitch.isChecked());

            if (clickSwitch.isChecked()) {
                clickSwitch.setPaintFlags(clickSwitch.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                clickSwitch.setPaintFlags(0);
            }
        }
    }
}
