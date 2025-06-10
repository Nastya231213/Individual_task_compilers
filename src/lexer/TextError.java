package lexer;

public class TextError {
    private final String error;

    public TextError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "Error: " + error;
    }
}
