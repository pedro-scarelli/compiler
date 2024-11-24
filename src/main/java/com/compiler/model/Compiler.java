package com.compiler.model;

import java.io.File;

public class Compiler {

    private Lexical lexical;
    private Syntatic syntatic;
    private Semantic semantic;
    private String inputText;
    private String resultFeedback;
    private File sourceCode;

    private FileManager fileManager;

    private final String SUCCESS_MESSAGE = "programa compilado com sucesso";

    private static final String EOF = "EOF";
    private static final String STRING_CONSTANT = "constante_string";

    public Compiler(String inputText, File sourceCode, FileManager fileManager) {
        this.lexical = new Lexical();
        this.syntatic = new Syntatic();
        this.semantic = new Semantic();

        this.sourceCode = sourceCode;
        this.fileManager = fileManager;

        this.inputText = inputText;
        lexical.setInput(inputText);
        compileSourceCodeAndGenerateObjectCode();
    }

    public String getResultFeedback() { 
        return resultFeedback;
    }

    public void compileSourceCodeAndGenerateObjectCode() {
        try {
            syntatic.parse(lexical, semantic);
            fileManager.createObjectCodeFile(semantic.getObjectCode(), sourceCode);
            resultFeedback = SUCCESS_MESSAGE;
        } catch ( LexicalError lexicalError ) {
            treatLexicalError(lexicalError);
        } catch ( SyntaticError syntaticError ) {
            treatSyntaticError(syntaticError);		
        } catch ( SemanticError e ) {
            resultFeedback = "Erro semantico";
        }
    }

    private void treatLexicalError(LexicalError lexicalError) {
        var line = getLineFromPosition(lexicalError.getPosition());
        resultFeedback = "Erro na linha " + line + " - " + lexicalError.getMessage();
    }

    private void treatSyntaticError(SyntaticError syntaticError) {
        var line = getLineFromPosition(syntaticError.getPosition());
        var lexeme = syntaticError.getLexeme();
        lexeme = getLexemeOrEOF(syntaticError.getPosition(), lexeme);
        resultFeedback = "Erro na linha " + line + " - " + "encontrado " + lexeme + " " + syntaticError.getMessage();
    }

    private int getLineFromPosition(int position) {
        var line = 1;
        for (int i = 0; i < position; i++) {
            if (inputText.charAt(i) == '\n') {
                line++;
            }
        }
        return line;
    }
    
    private String getLexemeOrEOF(int position, String lexeme) {
        var inputTextTrimmed  = inputText.trim();
        if (isEndOfFile(position, inputTextTrimmed)) return EOF;
        
        if(lexeme == null) {
            lexeme = extractLexemeFromPosition(position, inputTextTrimmed);
        }
        
        if(IsLexemeString(lexeme)) return STRING_CONSTANT;
        
        return lexeme;
    }
    
    private boolean isEndOfFile(int position, String inputTextTrimmed) {
        return position == inputTextTrimmed.length();
    }

    private String extractLexemeFromPosition(int position, String inputTextTrimmed) {  
        var lexeme = new StringBuilder();  
        lexeme.append(inputTextTrimmed.charAt(position));
        
        for(int i = position + 1; i < inputText.length(); i++){
            var currentChar = inputTextTrimmed.charAt(i);
            if (!Character.isWhitespace(currentChar) && currentChar != '\n'){
                lexeme.append(currentChar);
                continue;
            }
            break;
        }

       return lexeme.toString();
    }
    
    private boolean IsLexemeString(String errorCause) {
        return errorCause.charAt(0) == '\"' || errorCause.charAt(0) == '\'';
    }
}
