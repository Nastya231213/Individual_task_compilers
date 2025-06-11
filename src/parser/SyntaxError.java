package parser;

public class SyntaxError {
    private final String error;

    public SyntaxError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "Syntax Error: " + error;
    }
}
