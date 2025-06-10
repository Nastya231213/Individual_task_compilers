package test;


import lexer.Lexer;
import lexer.LexicalError;
import lexer.Token;

import java.util.List;

public class LexerTest {
    public static void main(String[] args) {
        testSimplePascal();
        testEdgeCases();
        testLogicalExpressions();
    }

    private static void testSimplePascal() {
        System.out.println("=== Test: Simple Pascal ===");
        String code = """
            { Simple Pascal test }
            var a, b: integer;
            begin
                a := 10;
                b := a + 20 * (5 - 3);
                if b > 15 then
                    a := a + 1;
                else
                    b := b - 1;
            end.
        """;
        runLexer(code);
    }

    private static void testEdgeCases() {
        System.out.println("\n=== Test: Edge Cases ===");
        String code = """
            { Edge cases test }
            var str: string;
            str := 'Unclosed string
            { Unclosed comment
            x := 100;
        """;
        runLexer(code);
    }

    private static void testLogicalExpressions() {
        System.out.println("\n=== Test: Logical Expressions ===");
        String code = """
            var x: boolean;
            begin
                x := true and false or not true;
            end.
        """;
        runLexer(code);
    }

    private static void runLexer(String code) {
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        tokens.forEach(System.out::println);

        List<LexicalError> errors = lexer.getErrors();
        if (!errors.isEmpty()) {
            System.out.println("Errors:");
            errors.forEach(System.out::println);
        }
    }

}
