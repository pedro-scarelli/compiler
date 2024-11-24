package com.compiler.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Semantic implements Constants
{

    private static Stack<Integer> typeStack = new Stack<>();
    private static final Map<Integer, ActionWithException> ACTIONS = new HashMap<>();
    private static StringBuilder objectCode = new StringBuilder();

    private static Token currentToken;
        
    static {
        ACTIONS.put(100, () -> method100());
        ACTIONS.put(101, () -> method101());
        ACTIONS.put(108, () -> method108());
        ACTIONS.put(123, () -> method123());
        // ACTIONS.put(124, () -> method124());
        // ACTIONS.put(125, () -> method125());
        // ACTIONS.put(126, () -> method126());
        ACTIONS.put(128, () -> method128());
        ACTIONS.put(129, () -> method129());
    }

    public void executeAction(int action, Token token)	throws SemanticError
    {
        currentToken = token;
        System.out.println("Action #"+action+", Token: "+token);
        var methodAction = ACTIONS.get(action);
        if (methodAction != null)
            methodAction.run();
        else
            objectCode.append("-" + action + "-\n");
    }

    public String getObjectCode() {
        return objectCode.toString();
    }

    private static void method100() {
        var headers = """
            .assembly extern mscorlib {}
            .assembly _codigo_objeto{}
            .module _codigo_objeto.exe

            .class public _UNICA{
            .method static public void _principal(){
            .entrypoint
            """;
        objectCode.append(headers);
    }
    
    private static void method101() {
        var endOfProgram = """
            M1:ret
                }
            }
            """;
        objectCode.append(endOfProgram);
    }
    
    private static void method108() {
        var tokenTypeId = typeStack.pop();
        var tokenTypeName = "";
        switch (tokenTypeId) {
            case 4:
            objectCode.append("conv.i8\n");
                tokenTypeName = "int64";
            break;
            case 5:
                tokenTypeName = "float64";
            break;
            case 6:
                tokenTypeName = "string";
            break;
            case 9:
            case 10:
                tokenTypeName = "boolean";
            break;
        }
        objectCode.append(String.format("call void [mscorlib]System.Console::Write(%s)\n", tokenTypeName));
    }
    
    private static void method123() throws SemanticError {
        pop2TypesAndPushCombination();
        var addCode = "add\n";
        objectCode.append(addCode);
    }
    
    // private static void method124() throws SemanticError {
    //     pop2TypesAndPushCombination();
    //     var subCode = "sub\n";
    //     objectCode.append(subCode);
    // }
    
    // private static void method125() throws SemanticError {
    //     pop2TypesAndPushCombination();
    //     var mulCode = "mul\n";
    //     objectCode.append(mulCode);
    // }
    
    // private static void method126() throws SemanticError {
    //     pop2TypesAndPushCombination();
    //     var divCode = "div\n";
    //     objectCode.append(divCode);
    // }

    private static void pop2TypesAndPushCombination() throws SemanticError {
        var firstValueType = typeStack.pop();
        var secondValueType = typeStack.pop();
        pushVarsCombinationType(firstValueType, secondValueType);
    }

    private static void pushVarsCombinationType(int firstValueType, int secondValueType) throws SemanticError {
        if (firstValueType == secondValueType) {
            typeStack.push(firstValueType);
            return;
        } else if (firstValueType == 4 && secondValueType == 5 || firstValueType == 5 && secondValueType == 4) {
            typeStack.push(5);
            return;
        }

        throw new SemanticError("Types not compatible");
    }
    
    private static void method128() {
        typeStack.push(4);
        objectCode.append(String.format("ldc.i8 %s\nconv.r8\n", currentToken.getLexeme()));
    }
    
    private static void method129() {
        typeStack.push(5);
        var doubleValue = currentToken.getLexeme().replace(",", ".");
        objectCode.append(String.format("ldc.r8 %s\n", doubleValue));
    }
}
