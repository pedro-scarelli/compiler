package com.compiler.model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Semantic implements Constants
{

    private static Token currentToken;

    private static String relationalOperator = "";
    private static StringBuilder objectCode = new StringBuilder();
    private static Stack<Integer> typeStack = new Stack<>();
    private static Stack<String> labelStack = new Stack<>();
    private static List<String> idList = new ArrayList<>();
    private static Set<String> symbolTable = new HashSet<>();


    private static final Map<Integer, ActionWithException> ACTIONS = new HashMap<>();
        
    static {
        ACTIONS.put(100, () -> method100());
        ACTIONS.put(101, () -> method101());
        ACTIONS.put(102, () -> method102());
        ACTIONS.put(103, () -> method103());
        ACTIONS.put(104, () -> method104());
        ACTIONS.put(105, () -> method105());
        ACTIONS.put(106, () -> method106());
        ACTIONS.put(107, () -> method107());
        ACTIONS.put(108, () -> method108());
        ACTIONS.put(109, () -> method109());
        ACTIONS.put(110, () -> method110());
        ACTIONS.put(111, () -> method111());
        ACTIONS.put(112, () -> method112());
        ACTIONS.put(113, () -> method113());
        ACTIONS.put(114, () -> method114());
        ACTIONS.put(115, () -> method115());
        ACTIONS.put(116, () -> method116());
        ACTIONS.put(117, () -> method117());
        ACTIONS.put(118, () -> method118());
        ACTIONS.put(119, () -> method119());
        ACTIONS.put(120, () -> method120());
        ACTIONS.put(121, () -> method121());
        ACTIONS.put(122, () -> method122());
        ACTIONS.put(123, () -> method123());
        ACTIONS.put(124, () -> method124());
        ACTIONS.put(125, () -> method125());
        ACTIONS.put(126, () -> method126());
        ACTIONS.put(127, () -> method127());
        ACTIONS.put(128, () -> method128());
        ACTIONS.put(129, () -> method129());
        ACTIONS.put(130, () -> method130());
        ACTIONS.put(131, () -> method131());
    }

    public void executeAction(int action, Token token) throws SemanticError
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
    
    private static void method102() throws SemanticError {
        for (int i = 0; i < idList.size(); i++) {
            var id = idList.get(i);
            if (symbolTable.contains(id)) throw new SemanticError(currentToken.getLexeme() + " já declarado");

            symbolTable.add(id);
            var varType = getVariableTypeByInitial(id);
            objectCode.append(String.format(".locals (%s %s)\n", varType, id));
        }
        idList.clear();
    }

    

    private static String getVariableTypeByInitial(String id) throws SemanticError {
        var idParts = id.split("_");
        var variableInitialLetter = idParts[0];
        switch(variableInitialLetter) {
            case "i":
                return "int64";
            case "f":
                return "float64";
            case "s":
                return "string";
            case "b":
                return "bool";
            default:
                throw new SemanticError("Variable identifier incorrect");
        }
    }
    
    private static void method103() throws SemanticError {
        var type = typeStack.pop();
        if (type == 4) objectCode.append("conv.i8\n");
        for (int i = 0; i < idList.size() - 1; i++) {
            objectCode.append("dup\n");
            var id = idList.get(i);
            if (!symbolTable.contains(id)) throw new SemanticError(currentToken.getLexeme() + " não declarado");

            objectCode.append(String.format("stloc %s\n", id));
        }
        idList.clear();
    }
    
    private static void method104() {
        idList.add(currentToken.getLexeme());
    }
    
    private static void method105() throws SemanticError {
        var id = currentToken.getLexeme();
        if (!symbolTable.contains(id)) throw new SemanticError(currentToken.getLexeme() + " não declarado");
    
        objectCode.append("call string [mscorlib] System.Console::ReadLine()\n");

        var varType = getVariableTypeByInitial(id);
        var varClass = "";
        switch(varType) {
            case "string":
            return;
            case "int64":
                varClass = "Int64";
            break;
            case "float64":
                varClass = "Double";
            break;
            case "bool":
                varClass = "Boolean";
            break;
        }
        objectCode.append(String.format("call %s [mscorlib] System.%s::Parse(string)\n", varType, varClass));
        objectCode.append(String.format("stloc %s\n", currentToken.getLexeme()));
    }
    
    private static void method106() {
        objectCode.append(String.format("ldstr %s\n", currentToken.getLexeme()));
        objectCode.append("call void [mscorlib]System.Console::Write(string)\n");
    }
    
    private static void method107() {
        objectCode.append(String.format("ldstr %s\ncall void [mscorlib]System.Console::Write(string)\n", "\"\\n\""));
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
    
    private static void method109() {
        var firstLabel = "M" + labelStack.size();
        labelStack.push(firstLabel);
        var secondLabel = "M" + labelStack.size();
        objectCode.append(String.format("brfalse %s\n", firstLabel));
        labelStack.push(secondLabel);
    }
    
    private static void method110() {
        var secondLabel = labelStack.pop();
        var firstLabel = labelStack.pop();
        objectCode.append(String.format("br %s\n", firstLabel));
        labelStack.push(firstLabel);
        objectCode.append(String.format("%s:\n", secondLabel));
    }
    
    private static void method111() {
        var label = labelStack.pop();
        objectCode.append(String.format("%s:\n", label));
    }
    
    private static void method112() {
        var label = "M" + labelStack.size();
        objectCode.append(String.format("brfalse %s\n", label));
        labelStack.push(label);
    }
    
    private static void method113() {
        var label = "M" + labelStack.size();
        objectCode.append(String.format("%s:\n", label));
        labelStack.push(label);
    }
    
    private static void method114() {
        var label = labelStack.pop();
        objectCode.append(String.format("brtrue %s\n", label));
    }
    
    private static void method115() {
        var label = labelStack.pop();
        objectCode.append(String.format("brfalse %s\n", label));
    }
    
    private static void method116() throws SemanticError {
        pop2TypesAndPushCombination();
        objectCode.append("and\n");
    }
    
    private static void method117() throws SemanticError {
        pop2TypesAndPushCombination();
        objectCode.append("or\n");
    }
    
    private static void method118() throws SemanticError {
        typeStack.push(9);
        objectCode.append("ldc.i4 1\n");
    }
    
    private static void method119() throws SemanticError {
        typeStack.push(10);
        objectCode.append("ldc.i4 0\n");
    }
    
    private static void method120() throws SemanticError {
        objectCode.append("ldc.i4 1\nxor\n");
    }
    
    private static void method121() {
        relationalOperator = currentToken.getLexeme();
    }
    
    private static void method122() throws SemanticError {
        pop2TypesAndPushCombination();
        switch(relationalOperator) {
            case "==":
                objectCode.append("ceq\n");
            break;
            case "!=":
                objectCode.append("ceq\n");
                objectCode.append("ldc.i4 1\nxor\n");
            break;
            case ">":
                objectCode.append("cgt\n");
            break;
            case "<":
                objectCode.append("clt\n");
            break;
            default:
                throw new SemanticError("Relational operator incorrect");
        }

    }
    
    private static void method123() throws SemanticError {
        pop2TypesAndPushCombination();
        var addCode = "add\n";
        objectCode.append(addCode);
    }
    
    private static void method124() throws SemanticError {
        pop2TypesAndPushCombination();
        var subCode = "sub\n";
        objectCode.append(subCode);
    }
    
    private static void method125() throws SemanticError {
        pop2TypesAndPushCombination();
        var mulCode = "mul\n";
        objectCode.append(mulCode);
    }
    
    private static void method126() throws SemanticError {
        pop2TypesAndPushCombination();
        var divCode = "div\n";
        objectCode.append(divCode);
    }

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
    
    private static void method127() throws SemanticError {
        if (!symbolTable.contains(currentToken.getLexeme())) throw new SemanticError(currentToken.getLexeme() + " não declarado");
        var varType = currentToken.getId();
        typeStack.push(varType);// ver com a professora
        objectCode.append(String.format("ldloc %s\n", currentToken.getLexeme()));
        if (varType == 4) objectCode.append("conv.r8\n");
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
    
    private static void method130() {
        typeStack.push(6);
        objectCode.append(String.format("ldstr %s\n", currentToken.getLexeme()));
    }
    
    private static void method131() {
        objectCode.append(String.format("ldc.r8 -1.0\nmul\n", currentToken.getLexeme()));
    }
}
