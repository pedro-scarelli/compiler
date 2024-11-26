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
    private static Stack<String> typeStack = new Stack<>();
    private static Stack<String> labelStack = new Stack<>();
    private static List<String> idList = new ArrayList<>();
    private static Set<String> symbolTable = new HashSet<>();

    private static int labelCounter = 0;

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
        var methodAction = ACTIONS.get(action);
        methodAction.run();
    }

    public String getObjectCode() {
        return objectCode.toString();
    }
    
    public void clearState() {
        labelCounter = 0;
        symbolTable.clear();
        idList.clear();
        labelStack.clear();
        objectCode.setLength(0);
    }

    public static void method100() {
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
    
    public static void method101() {
        var endOfProgram = """
            ret
                }
            }
            """;
        objectCode.append(endOfProgram);
    }
    
    public static void method102() throws SemanticError {
        for (int i = 0; i < idList.size(); i++) {
            var id = idList.get(i);
            if (symbolTable.contains(id)) throw new SemanticError(currentToken.getLexeme() + " já declarado");

            symbolTable.add(id);
            var varType = getVariableTypeByName(id);
            objectCode.append(String.format(".locals (%s %s)\n", varType, id));
        }
        idList.clear();
    }

    public static String getVariableTypeByName(String id) throws SemanticError {
        var variableInitialLetter = getVarInitialLetter(id);
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

    public static String getVarInitialLetter(String id) {
        var idParts = id.split("_");
        return idParts[0];
    }
    
    public static void method103() throws SemanticError {
        var type = typeStack.pop();
        if (type == "int64") objectCode.append("conv.i8\n");
        for (int i = 0; i < idList.size() - 1; i++) {
            objectCode.append("dup\n");
        }

        for (int i = 0; i < idList.size(); i++) {
            var id = idList.get(i);
            if (!symbolTable.contains(id)) throw new SemanticError(currentToken.getLexeme() + " não declarado");

            objectCode.append(String.format("stloc %s\n", id));
        }
        idList.clear();
    }
    
    public static void method104() {
        idList.add(currentToken.getLexeme());
    }
    
    public static void method105() throws SemanticError {
        var id = currentToken.getLexeme();
        if (!symbolTable.contains(id)) throw new SemanticError(currentToken.getLexeme() + " não declarado");
    
        objectCode.append("call string [mscorlib] System.Console::ReadLine()\n");

        var varType = getVariableTypeByName(id);
        var varClass = "";
        switch(varType) {
            case "string":
                objectCode.append(String.format("stloc %s\n", currentToken.getLexeme()));
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
    
    public static void method106() {
        objectCode.append(String.format("ldstr \"%s\"\n", currentToken.getLexeme()));
        objectCode.append("call void [mscorlib]System.Console::Write(string)\n");
    }
    
    public static void method107() {
        objectCode.append(String.format("ldstr %s\ncall void [mscorlib]System.Console::Write(string)\n", "\"\\n\""));
    }
    
    public static void method108() {
        var tokenType = typeStack.pop();

        if (tokenType == "int64") objectCode.append("conv.i8\n");
        else if (tokenType == "bool") tokenType += "ean";

        objectCode.append(String.format("call void [mscorlib]System.Console::Write(%s)\n", tokenType));
    }
    
    public static void method109() {
        var firstLabel = createLabel();
        labelStack.push(firstLabel);
        var secondLabel = createLabel();
        objectCode.append(String.format("brfalse %s\n", secondLabel));
        labelStack.push(secondLabel);
    }

    private static String createLabel() {
        return "M" + labelCounter++;
    }
    
    public static void method110() {
        var secondLabel = labelStack.pop();
        var firstLabel = labelStack.pop();
        objectCode.append(String.format("br %s\n", firstLabel));
        labelStack.push(firstLabel);
        objectCode.append(String.format("%s:\n", secondLabel));
    }
    
    public static void method111() {
        var label = labelStack.pop();
        objectCode.append(String.format("%s:\n", label));
    }
    
    public static void method112() {
        var label = createLabel();
        objectCode.append(String.format("brfalse %s\n", label));
        labelStack.push(label);
    }
    
    public static void method113() {
        var label = createLabel();
        objectCode.append(String.format("%s:\n", label));
        labelStack.push(label);
    }
    
    public static void method114() {
        var label = labelStack.pop();
        objectCode.append(String.format("brtrue %s\n", label));
    }
    
    public static void method115() {
        var label = labelStack.pop();
        objectCode.append(String.format("brfalse %s\n", label));
    }
    
    public static void method116() throws SemanticError {
        pop2TypesAndPushCombination("&&");
        objectCode.append("and\n");
    }
    
    public static void method117() throws SemanticError {
        pop2TypesAndPushCombination("||");
        objectCode.append("or\n");
    }
    
    public static void method118() throws SemanticError {
        typeStack.push("bool");
        objectCode.append("ldc.i4 1\n");
    }
    
    public static void method119() throws SemanticError {
        typeStack.push("bool");
        objectCode.append("ldc.i4 0\n");
    }
    
    public static void method120() throws SemanticError {
        objectCode.append("ldc.i4 1\nxor\n");
    }
    
    public static void method121() {
        relationalOperator = currentToken.getLexeme();
    }
    
    public static void method122() throws SemanticError {
        pop2TypesAndPushCombination(relationalOperator);
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
    
    public static void method123() throws SemanticError {
        pop2TypesAndPushCombination("+");
        var addCode = "add\n";
        objectCode.append(addCode);
    }
    
    public static void method124() throws SemanticError {
        pop2TypesAndPushCombination("-");
        var subCode = "sub\n";
        objectCode.append(subCode);
    }
    
    public static void method125() throws SemanticError {
        pop2TypesAndPushCombination("*");
        var mulCode = "mul\n";
        objectCode.append(mulCode);
    }
    
    public static void method126() throws SemanticError {
        pop2TypesAndPushCombination("/");
        var divCode = "div\n";
        objectCode.append(divCode);
    }

    public static void pop2TypesAndPushCombination(String operator) throws SemanticError {
        var firstValueType = typeStack.pop();
        var secondValueType = typeStack.pop();
        pushVarsCombinationType(firstValueType, secondValueType, operator);
    }

    public static void pushVarsCombinationType(String firstValueType, String secondValueType, String operator) throws SemanticError {
        switch (operator) {
            case "+":
            case "-":
            case "*":
            case "/": getArimethicCombination(firstValueType, secondValueType); break;
            case "&&": 
            case "||": getAndOrCombination(firstValueType, secondValueType); break;
            case "==": 
            case "!=": 
            case "<": 
            case ">": getRelationalCombination(firstValueType, secondValueType); break;
            default: throw new SemanticError("Unrecognized operator");
        }
    }

    public static void getAndOrCombination(String firstValueType, String secondValueType) throws SemanticError {
        if(!firstValueType.equals("bool") || !secondValueType.equals("bool"))
            throw new SemanticError("Types not compatible");
        pushBooleanTypeToStack();
    }

    public static void getRelationalCombination(String firstValueType, String secondValueType) throws SemanticError {
        if(isTypesValidForRelationalOperation(firstValueType, secondValueType))
            throw new SemanticError("Types not compatible");
        pushBooleanTypeToStack();
    }

    public static boolean isTypesValidForRelationalOperation(String firstValueType, String secondValueType) {
        return (firstValueType != secondValueType) || 
        (!firstValueType.equals("int64") && !firstValueType.equals("float64") && !firstValueType.equals("string")) || 
        (!secondValueType.equals("int64") && !secondValueType.equals("float64") && !secondValueType.equals("string"));
    }

    public static void pushBooleanTypeToStack() {
        typeStack.push("bool");
    }
    
    public static void getArimethicCombination(String firstValueType, String secondValueType) throws SemanticError {
        if (firstValueType == secondValueType) {
            typeStack.push(firstValueType);
            return;
        } else if (firstValueType.equals("int64") && secondValueType.equals("float64") || 
                    firstValueType.equals("float64") && secondValueType.equals("int64")) {
            typeStack.push("float64");
            return;
        }

        throw new SemanticError("Types not compatible");
    }

    public static void method127() throws SemanticError {
        var lexeme = currentToken.getLexeme();
        if (!symbolTable.contains(lexeme)) throw new SemanticError(currentToken.getLexeme() + " não declarado");

        var varType = getVariableTypeByName(lexeme);
        typeStack.push(varType);

        objectCode.append(String.format("ldloc %s\n", currentToken.getLexeme()));
        if (varType.equals("int64")) objectCode.append("conv.r8\n");
    }
    
    public static void method128() {
        typeStack.push("int64");
        objectCode.append(String.format("ldc.i8 %s\nconv.r8\n", currentToken.getLexeme()));
    }
    
    public static void method129() {
        typeStack.push("float64");
        var doubleValue = currentToken.getLexeme().replace(",", ".");
        objectCode.append(String.format("ldc.r8 %s\n", doubleValue));
    }
    
    public static void method130() {
        typeStack.push("string");
        objectCode.append(String.format("ldstr %s\n", currentToken.getLexeme()));
    }
    
    public static void method131() {
        objectCode.append(String.format("ldc.r8 -1.0\nmul\n", currentToken.getLexeme()));
    }

    //testing methods
    public void setLength(int length) {
        objectCode.setLength(length);
    }

    public String popTypeStack() {
        return typeStack.pop();
    }

    public void pushTypeStack(String type) {
        typeStack.push(type);
    }

    public void addSymbolsTable(String symbol) {
        symbolTable.add(symbol);
    }

    public boolean symbolsTableContains(String id) {
        return symbolTable.contains(id);
    }

    public String getRelationalOperator() {
        return relationalOperator;
    }

    public void setRelationalOperator(String operator) {
        relationalOperator = operator;
    }

    public boolean idListContains(String id) {
        return idList.contains(id);
    }

    public void addIdToList(String id) {
        idList.add(id);
    }

    public boolean isIdListEmpty() {
        return idList.isEmpty();
    }

    public int getLabelStackSize() {
        return labelStack.size();
    }

    public String popLabelFromStack() {
        return labelStack.pop();
    }

    public void pushLabelToStack(String label) {
        labelStack.push(label);
    }

    public boolean isLabelStackEmpty() {
        return labelStack.isEmpty();
    }
}
