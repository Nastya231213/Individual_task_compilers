package parser;

import lexer.Token;
import lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int position = 0;
    private final List<SyntaxError> syntaxErrors = new ArrayList<>();

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<SyntaxError> parse() {
        while (!isAtEnd()) {
            if (!parseStatement()) {
                Token token = peek();
                syntaxErrors.add(new SyntaxError("Unexpected token: " + token.getValue() + " at (" + token.getLine() + ":" + token.getColumn() + ")"));
                advance();
            }
        }
        return syntaxErrors;
    }

    private boolean parseStatement() {
        if (match(TokenType.KEYWORD, "begin")) {
            while (!match(TokenType.KEYWORD, "end") && !isAtEnd()) {
                if (!parseStatement()) {
                    return false;
                }
            }
            return true;
        } else if (match(TokenType.KEYWORD, "if")) {
            if (!matchType(TokenType.IDENTIFIER)) return false;
            if (!match(TokenType.OPERATOR, ">", "<", "=", "<=", ">=", "<>")) return false;
            if (!(matchType(TokenType.IDENTIFIER) || matchType(TokenType.NUMBER))) return false;
            if (!match(TokenType.KEYWORD, "then")) return false;
            return parseStatement();
        } else if (matchType(TokenType.IDENTIFIER)) {
            if (match(TokenType.OPERATOR, ":=")) {
                return matchType(TokenType.NUMBER, TokenType.STRING, TokenType.IDENTIFIER);
            }
        }
        return false;
    }

    private boolean match(TokenType type, String... values) {
        if (isAtEnd()) return false;
        Token token = peek();
        if (token.getType() != type) return false;
        if (values.length > 0) {
            for (String val : values) {
                if (token.getValue().equals(val)) {
                    advance();
                    return true;
                }
            }
            return false;
        }
        advance();
        return true;
    }

    private boolean matchType(TokenType... types) {
        if (isAtEnd()) return false;
        Token token = peek();
        for (TokenType type : types) {
            if (token.getType() == type) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token peek() {
        return tokens.get(position);
    }

    private boolean isAtEnd() {
        return position >= tokens.size();
    }

    private void advance() {
        if (!isAtEnd()) position++;
    }
}
