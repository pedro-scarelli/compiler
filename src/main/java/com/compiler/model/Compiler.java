package com.compiler.model;

public class Compiler {

    private Lexico lexical;
    private Sintatico syntatic;
    private Semantico semantic;
    private String inputText;
    private String result;

    private static final String EOF = "EOF";
    private static final String STRING_CONSTANT = "constante_string";

    public Compiler(String inputText) {
        this.inputText = inputText;
        lexical.setInput(inputText);
        compileSourceCodeAndGenerateObjectCode();
    }

    public String getResult() { 
        return result;
    }

    public void compileSourceCodeAndGenerateObjectCode() {
        try {
            syntatic.parse(lexical, semantic);
            result = "programa compilado com sucesso";
        } catch ( LexicalError lexicalError ) {
            result = treatLexicalError(lexicalError);
        } catch ( SyntaticError syntaticError ) {
            result = treatSyntaticError(syntaticError);		
        } catch ( SemanticError e ) {
            result = "Erro semantico";
        }
    }

    private String treatLexicalError(LexicalError lexicalError) {
        var line = getLineFromPosition(lexicalError.getPosition());
        return "Erro na linha " + line + " - " + lexicalError.getMessage();
    }

    private String treatSyntaticError(SyntaticError syntaticError) {
        var line = getLineFromPosition(syntaticError.getPosition());
        var lexeme = syntaticError.getLexeme();
        lexeme = getLexemeOrEOF(syntaticError.getPosition(), lexeme);
        return "Erro na linha " + line + " - " + "encontrado " + lexeme + " " + syntaticError.getMessage();
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
