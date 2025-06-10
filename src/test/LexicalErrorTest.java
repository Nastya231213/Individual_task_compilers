package test;


import lexer.Lexer;
import lexer.LexicalError;
import lexer.Token;

import java.util.List;

public class LexicalErrorTest {
    public static void main(String[] args) {
        testUnclosedString();
        testUnclosedComment();
        testUnknownSymbol();
        testCombinedErrors();
    }

    private static void testUnclosedString() {
        System.out.println("\n=== Lexical Error Test: Unclosed String ===");
        String code = "var s: string;\ns := 'Oops";
        runLexer(code);
    }

    private static void testUnclosedComment() {
        System.out.println("\n=== Lexical Error Test: Unclosed Comment ===");
        String code = "{ Not closed\ny := 2;";
        runLexer(code);
    }

    private static void testUnknownSymbol() {
        System.out.println("\n=== Lexical Error Test: Unknown Symbol ===");
        String code = "a := 5 $ 7;";
        runLexer(code);
    }

    private static void testCombinedErrors() {
        System.out.println("\n=== Lexical Error Test: Combined ===");
        String code = "begin\n  s := 'Hi\n  { missing end\n  r := 9 % 1;\nend.";
        runLexer(code);
    }

    private static void runLexer(String code) {
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        System.out.println("Tokens:");
        tokens.forEach(System.out::println);

        List<LexicalError> lexicalErrors = lexer.getErrors();
        if (!lexicalErrors.isEmpty()) {
            System.out.println("Lexical Errors:");
            lexicalErrors.forEach(System.out::println);
        }
    }
}
