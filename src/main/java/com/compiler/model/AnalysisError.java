package com.compiler.model;

public class AnalysisError extends Exception
{
    private int position;
    private String lexeme;

    public AnalysisError(String msg, int position, String lexeme)
    {
        super(msg);
        this.position = position;
        this.lexeme = lexeme;
    }

    public AnalysisError(String msg, int position)
    {
        super(msg);
        this.position = position;
    }

    public AnalysisError(String msg)
    {
        super(msg);
        this.position = -1;
    }

    public int getPosition()
    {
        return position;
    }
    
    public String getLexeme(){
        return this.lexeme;
    }

    public String toString()
    {
        return super.toString() + ", @ "+position;
    }
}
