package app;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import lexer.Lexer;
import lexer.Token;
import lexer.LexicalError;
import parser.Parser;
import parser.SyntaxError;

public class Main {
    public static void main(String[] args) throws Exception {
        String code = Files.readString(Paths.get("src/resources/text.pas"));
        Lexer lexer = new Lexer(code);

        List<Token> tokens = lexer.tokenize();
        tokens.forEach(System.out::println);

        List<LexicalError> lexicalErrors = lexer.getErrors();
        if (!lexicalErrors.isEmpty()) {
            System.out.println("\nLexical Errors:");
            lexicalErrors.forEach(System.out::println);
            return;
        }

        Parser parser = new Parser(tokens);
        List<SyntaxError> syntaxErrors = parser.parse();

        if (!syntaxErrors.isEmpty()) {
            System.out.println("\nSyntax Errors:");
            syntaxErrors.forEach(System.out::println);
        } else {
            System.out.println("\nParsing completed successfully!");
        }
    }
}
