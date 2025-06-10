package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Lexer {
    private final String input;
    private int pos = 0;
    private int line = 1;
    private int col = 0;
    private final List<Token> tokens = new ArrayList<>();
    private final List<LexicalError> errors = new ArrayList<>();
    private final List<TextError> textErrors = new ArrayList<>();

    private static final Set<String> KEYWORDS = Set.of(
            "begin", "end", "var", "if", "then", "else", "while", "do", "procedure", "function",
            "true", "false", "and", "or", "not"
    );

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        while (pos < input.length()) {
            char current = peek();
            try {
                if (Character.isWhitespace(current)) {
                    consumeWhitespace();
                } else if (current == '{') {
                    consumeComment();
                } else if (Character.isLetter(current)) {
                    consumeIdentifierOrKeyword();
                } else if (Character.isDigit(current)) {
                    consumeNumber();
                } else if (current == '\'') {
                    consumeString();
                } else if (isOperator(current)) {
                    addToken(TokenType.OPERATOR, String.valueOf(advance()));
                } else if (isDelimiter(current)) {
                    addToken(TokenType.DELIMITER, String.valueOf(advance()));
                } else {
                    throw new RuntimeException("Unknown character: " + current);
                }
            } catch (Exception e) {
                LexicalError le = new LexicalError(e.getMessage(), line, col);
                errors.add(le);
                textErrors.add(new TextError(le.toString()));
                advance();
            }
        }
        return tokens;
    }

    public List<LexicalError> getErrors() {
        return errors;
    }

    public List<TextError> getTextErrors() {
        return textErrors;
    }

    private void consumeWhitespace() {
        while (pos < input.length() && Character.isWhitespace(peek())) {
            if (peek() == '\n') {
                line++;
                col = 0;
            }
            advance();
        }
    }

    private void consumeComment() {
        int startCol = col;
        advance(); // skip '{'
        while (pos < input.length() && peek() != '}') {
            if (peek() == '\n') {
                line++;
                col = 0;
            }
            advance();
        }
        if (pos < input.length()) {
            advance(); // skip '}'
        } else {
            LexicalError le = new LexicalError("Unclosed comment", line, startCol);
            errors.add(le);
            textErrors.add(new TextError(le.toString()));
        }
    }

    private void consumeIdentifierOrKeyword() {
        int start = pos;
        int startCol = col;
        while (pos < input.length() && (Character.isLetterOrDigit(peek()) || peek() == '_')) {
            advance();
        }
        String word = input.substring(start, pos);
        TokenType type = KEYWORDS.contains(word.toLowerCase()) ? TokenType.KEYWORD : TokenType.IDENTIFIER;
        tokens.add(new Token(type, word, line, startCol));
    }

    private void consumeNumber() {
        int start = pos;
        int startCol = col;
        boolean dotSeen = false;
        while (pos < input.length() && (Character.isDigit(peek()) || (!dotSeen && peek() == '.'))) {
            if (peek() == '.') dotSeen = true;
            advance();
        }
        tokens.add(new Token(TokenType.NUMBER, input.substring(start, pos), line, startCol));
    }

    private void consumeString() {
        int startCol = col;
        StringBuilder sb = new StringBuilder();
        advance(); // skip opening '
        while (pos < input.length() && peek() != '\'') {
            sb.append(advance());
        }
        if (pos < input.length()) {
            advance(); // skip closing '
            tokens.add(new Token(TokenType.STRING, sb.toString(), line, startCol));
        } else {
            LexicalError le = new LexicalError("Unclosed string literal", line, startCol);
            errors.add(le);
            textErrors.add(new TextError(le.toString()));
        }
    }

    private char peek() {
        return input.charAt(pos);
    }

    private char advance() {
        char c = input.charAt(pos++);
        col++;
        return c;
    }

    private void addToken(TokenType type, String value) {
        tokens.add(new Token(type, value, line, col));
    }

    private boolean isOperator(char c) {
        return "+-*/:=<>".indexOf(c) != -1;
    }

    private boolean isDelimiter(char c) {
        return ";,().".indexOf(c) != -1;
    }
}
