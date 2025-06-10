package test;

import lexer.Lexer;
import lexer.Token;
import lexer.TextError;

import java.util.List;

public class TextErrorTest {
    public static void main(String[] args) {
        testUnclosedString();
        testUnclosedComment();
        testUnknownSymbol();
        testCombinedErrors();
    }

    private static void testUnclosedString() {
        System.out.println("\n=== Test: Unclosed String ===");
        String code = "var s: string;\ns := 'Unclosed";
        runLexer(code);
    }

    private static void testUnclosedComment() {
        System.out.println("\n=== Test: Unclosed Comment ===");
        String code = "{ This is an unclosed comment\nx := 10;";
        runLexer(code);
    }

    private static void testUnknownSymbol() {
        System.out.println("\n=== Test: Unknown Symbol ===");
        String code = "x := 100 @ 200;";
        runLexer(code);
    }

    private static void testCombinedErrors() {
        System.out.println("\n=== Test: Combined Errors ===");
        String code = "begin\n  y := 'Test\n  { comment\n  z := 10 @;\nend.";
        runLexer(code);
    }

    private static void runLexer(String code) {
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        System.out.println("Tokens:");
        tokens.forEach(System.out::println);

        List<TextError> textErrors = lexer.getTextErrors();
        if (!textErrors.isEmpty()) {
            System.out.println("Text Errors:");
            textErrors.forEach(System.out::println);
        }
    }
}
