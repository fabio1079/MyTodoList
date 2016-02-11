package fabio.mytodolist.models;


public class Todo {
    public final String TEXT_ERROR_WHEN_EMPTY = "The given text is empty";
    public final String TEXT_ERROR_WHEN_TOO_BIG = "The given text is bugger than 60 characters";

    private String text;
    private boolean done;

    public Todo(final String text) {
        this.setText(text);
        this.setDone(false);
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) throws Error {
        if (text.isEmpty()) {
            throw new Error(TEXT_ERROR_WHEN_EMPTY);
        } else if (text.length() > 60) {
            throw new Error(TEXT_ERROR_WHEN_TOO_BIG);
        } else {
            this.text = text;
        }
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }
}
