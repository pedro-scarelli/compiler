package com.compiler.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SemanticTest {

    private Semantic semantic;

    @BeforeEach
    void setUp() {
        semantic = new Semantic();
        semantic.clearState();
    }

    @Test
    void testAction100() {
        try {
            semantic.executeAction(100, null);
        } catch (SemanticError e) {
            e.printStackTrace();
        }

        var expectedHeader = """
            .assembly extern mscorlib {}
            .assembly _codigo_objeto{}
            .module _codigo_objeto.exe
            .class public UNICA{
            .method static public void _principal() {
            .entrypoint
            """;

        assertEquals(expectedHeader, semantic.getObjectCode());
    }

    @Test
    void testAction101() {
        try {
            semantic.executeAction(101, new Token(16, "end", 0));
        } catch (SemanticError e) {
            e.printStackTrace();
        }

        var expected = """
            ret
            }
            }
            """;

        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testMethod102_validIdentifiers() {
        semantic.addSymbolsTable("i_var");

        semantic.addIdToList("i_area");
        semantic.addIdToList("f_perimeter");
        semantic.addIdToList("s_teste");
        semantic.addIdToList("b_oolean");

        var token = new Token(3, "i_area", 0);

        assertDoesNotThrow(() -> semantic.executeAction(102, token));

        assertTrue(semantic.symbolsTableContains("i_area"));
        assertTrue(semantic.symbolsTableContains("f_perimeter"));
        assertTrue(semantic.symbolsTableContains("s_teste"));
        assertTrue(semantic.symbolsTableContains("b_oolean"));

        var expected = """
            .locals(int64 i_area)
            .locals(float64 f_perimeter)
            .locals(string s_teste)
            .locals(bool b_oolean)
            """;

        assertEquals(expected, semantic.getObjectCode());
        assertTrue(semantic.isIdListEmpty());
    }

    @Test
    void testMethod102_duplicateIdentifier() {
        semantic.addSymbolsTable("i_existingvar");
        semantic.addIdToList("i_existingvar");

        var token = new Token(3, "i_existingvar", 0);

        var exception = assertThrows(SemanticError.class, () -> semantic.executeAction(102, token));
        assertEquals("i_existingvar já declarado", exception.getMessage());
    }
    
    @Test
    void testMethod103_validAssignment() {
        semantic.addSymbolsTable("i_var");
        semantic.addSymbolsTable("f_area");
        semantic.addSymbolsTable("b_active");

        semantic.addIdToList("i_var");
        semantic.addIdToList("f_area");
        semantic.addIdToList("b_active");

        semantic.pushTypeStack("int64");

        var token = new Token(3, "i_var", 0);

        assertDoesNotThrow(() -> semantic.executeAction(103, token));
        var expectedObjectCode = """
            conv.i8
            dup
            dup
            stloc i_var
            stloc f_area
            stloc b_active
            """;
        assertEquals(expectedObjectCode, semantic.getObjectCode());

        assertTrue(semantic.isIdListEmpty());
    }

    @Test
    void testMethod103_identifierNotDeclared() {
        semantic.addSymbolsTable("i_var");
        semantic.addSymbolsTable("f_area");

        semantic.addIdToList("i_var");
        semantic.addIdToList("f_area");
        semantic.addIdToList("b_active");

        semantic.pushTypeStack("int64");

        var token = new Token(3, "b_active", 0);
        var exception = assertThrows(SemanticError.class, () -> semantic.executeAction(103, token));

        assertEquals("b_active não declarado", exception.getMessage());

        assertFalse(semantic.isIdListEmpty());
    }

    @Test
    void testAction104() throws SemanticError {
        semantic.executeAction(104, new Token(3, "s_teste", 0));
        
        assertTrue(semantic.idListContains("s_teste"));
    }

    @Test
    void testAction105_IdNotDeclared() throws SemanticError {
        var token = new Token(3, "s_teste", 0);
        var exception = assertThrows(SemanticError.class, () -> semantic.executeAction(105, token));

        assertEquals("s_teste não declarado", exception.getMessage());
    }

    @Test
    void testAction105String() throws SemanticError {
        semantic.addSymbolsTable("s_text");
        semantic.executeAction(105, new Token(3, "s_text", 0));
        var expected = """
            call string [mscorlib]System.Console::ReadLine()
            stloc s_text
            """;

        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testAction105Int() throws SemanticError {
        semantic.addSymbolsTable("i_size");
        semantic.executeAction(105, new Token(3, "i_size", 0));
        var expected = """
            call string [mscorlib]System.Console::ReadLine()
            call int64 [mscorlib]System.Int64::Parse(string)
            stloc i_size
            """;

        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testAction105Float() throws SemanticError {
        semantic.addSymbolsTable("f_area");
        semantic.executeAction(105, new Token(3, "f_area", 0));
        var expected = """
            call string [mscorlib]System.Console::ReadLine()
            call float64 [mscorlib]System.Double::Parse(string)
            stloc f_area
            """;

        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testAction105Boolean() throws SemanticError {
        semantic.addSymbolsTable("b_active");
        semantic.executeAction(105, new Token(3, "b_active", 0));
        var expected = """
            call string [mscorlib]System.Console::ReadLine()
            call bool [mscorlib]System.Boolean::Parse(string)
            stloc b_active
            """;

        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testAction106() throws SemanticError {
        semantic.executeAction(106, new Token(6, "\"test\"", 0));
        var expected = """
            ldstr "test"
            call void [mscorlib]System.Console::Write(string)
            """;
        
        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testAction107() throws SemanticError {
        semantic.pushTypeStack("string");
        semantic.executeAction(108, new Token(6,"test", 0));
        semantic.executeAction(107, new Token(6,"test", 0));

        var expected = """
            call void [mscorlib]System.Console::WriteLine(string)
            """;
        
        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testAction108Int64() throws SemanticError {
        semantic.pushTypeStack("int64");
        semantic.executeAction(108, new Token(4, "1", 0));

        var expected = """
            conv.i8
            call void [mscorlib]System.Console::Write(int64)
            """;
        
        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testAction108Boolean() throws SemanticError {
        semantic.pushTypeStack("bool");
        semantic.executeAction(108, new Token(9, "true", 0));

        var expected = """
            call void [mscorlib]System.Console::Write(boolean)
            """;
        
        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testAction108Float64() throws SemanticError {
        semantic.pushTypeStack("float64");
        semantic.executeAction(108, new Token(5, "1.0", 0));

        var expected = """
            call void [mscorlib]System.Console::Write(float64)
            """;
        
        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testAction108String() throws SemanticError {
        semantic.pushTypeStack("string");
        semantic.executeAction(108, new Token(6, "test", 0));

        var expected = """
            call void [mscorlib]System.Console::Write(string)
            """;
        
        assertEquals(expected, semantic.getObjectCode());
    }
    
    @Test
    void testMethod109() throws SemanticError {
        semantic.executeAction(109, new Token(5, "1.0", 0));

        assertEquals(2, semantic.getLabelStackSize());
        assertEquals("M1", semantic.popLabelFromStack());
        assertEquals("M0", semantic.popLabelFromStack());

        assertEquals("brfalse M1\n", semantic.getObjectCode());
    }

    @Test
    void testMethod110() throws SemanticError {
        semantic.pushLabelToStack("M0");
        semantic.pushLabelToStack("M1");

        semantic.executeAction(110, new Token(5, "1.0", 0));

        assertEquals(1, semantic.getLabelStackSize());
        assertEquals("M0", semantic.popLabelFromStack());

        String expectedCode = "br M0\nM1:\n";
        assertEquals(expectedCode, semantic.getObjectCode());
    }

    @Test
    void testMethod111() throws SemanticError {
        semantic.pushLabelToStack("M0");

        semantic.executeAction(111, new Token(5, "1.0", 0));

        assertTrue(semantic.isLabelStackEmpty());

        assertEquals("M0:\n", semantic.getObjectCode());
    }

    @Test
    void testMethod112() throws SemanticError {
        semantic.executeAction(112, new Token(5, "1.0", 0));
        assertEquals(1, semantic.getLabelStackSize());
        assertEquals("M0", semantic.popLabelFromStack());

        assertEquals("brfalse M0\n", semantic.getObjectCode());
    }

    @Test
    void testMethod113() throws SemanticError {
        semantic.executeAction(113, new Token(5, "1.0", 0));

        assertEquals(1, semantic.getLabelStackSize());
        assertEquals("M0", semantic.popLabelFromStack());

        assertEquals("M0:\n", semantic.getObjectCode());
    }

    @Test
    void testMethod114() throws SemanticError {
        semantic.pushLabelToStack("M0");

        semantic.executeAction(114, new Token(5, "1.0", 0));

        assertTrue(semantic.isLabelStackEmpty());

        assertEquals("brtrue M0\n", semantic.getObjectCode());
    }

    @Test
    void testMethod115() throws SemanticError {
        semantic.pushLabelToStack("M0");

        semantic.executeAction(115, new Token(5, "1.0", 0));

        assertTrue(semantic.isLabelStackEmpty());

        assertEquals("brfalse M0\n", semantic.getObjectCode());
    }
    
    @Test
    public void testMethod116() throws SemanticError {
        semantic.pushTypeStack("bool");
        semantic.pushTypeStack("bool");
        semantic.executeAction(116, new Token(9, "true", 0));

        assertEquals("and\n", semantic.getObjectCode());
    }

    @Test
    public void testMethod117() throws SemanticError {
        semantic.pushTypeStack("bool");
        semantic.pushTypeStack("bool");
        semantic.executeAction(117, new Token(9, "true", 0));

        assertEquals("or\n", semantic.getObjectCode());
    }

    @Test
    void testAction118() {
        try {
            semantic.executeAction(118, new Token(9, "true", 0));
        } catch (SemanticError e) { e.printStackTrace(); }

        var expected = """
            ldc.i4 1
            """;

        assertEquals(expected, semantic.getObjectCode());
        assertEquals("bool", semantic.popTypeStack());
    }

    @Test
    void testAction119() {
        try {
            semantic.executeAction(119, new Token(10, "false", 0));
        } catch (SemanticError e) { e.printStackTrace(); }

        var expected = """
            ldc.i4 0
            """;

        assertEquals(expected, semantic.getObjectCode());
        assertEquals("bool", semantic.popTypeStack());
    }

    @Test
    void testAction120() {
        try {
            semantic.executeAction(120, new Token(10, "false", 0));
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = """
            ldc.i4 1
            xor
            """;
        
        assertEquals(expected, semantic.getObjectCode());
    }
    
    @Test
    void testAction121() {
        try {
            semantic.executeAction(121, new Token(23, "==", 0));
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = "==";
        
        assertEquals(expected, semantic.getRelationalOperator());
    }

    @Test
    void testAction122EqualOperator() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            
            semantic.setRelationalOperator("==");
            
            semantic.executeAction(122, new Token(4, "1", 0));
        } catch (SemanticError e) { e.printStackTrace(); }

        var expectedCode = """
            ceq
            """;
        
        assertEquals(expectedCode, semantic.getObjectCode());
    }

    @Test
    void testAction122NotEqualOperator() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            
            semantic.setRelationalOperator("!=");
            
            semantic.executeAction(122, new Token(4, "1", 0));
        } catch (SemanticError e) { e.printStackTrace(); }

        var expectedCode = """
            ceq
            ldc.i4 1
            xor
            """;
        
        assertEquals(expectedCode, semantic.getObjectCode());
    }

    @Test
    void testAction122GreaterThanOperator() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            
            semantic.setRelationalOperator(">");
            
            semantic.executeAction(122, new Token(4, "1", 0));
        } catch (SemanticError e) {
            e.printStackTrace();
        }

        var expectedCode = """
            cgt
            """;
        
        assertEquals(expectedCode, semantic.getObjectCode());
    }

    @Test
    void testAction122LessThanOperator() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            
            semantic.setRelationalOperator("<");
            
            semantic.executeAction(122, new Token(4, "1", 0));
        } catch (SemanticError e) {
            e.printStackTrace();
        }

        var expectedCode = """
            clt
            """;
        
        assertEquals(expectedCode, semantic.getObjectCode());
    }

    @Test
    void testAction122InvalidRelationalOperator() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            
            semantic.setRelationalOperator("&");
            
            semantic.executeAction(122, new Token(4, "1", 0));
            fail("Expected SemanticError due to invalid relational operator");
        } catch (SemanticError e) {
            assertTrue(e.getMessage().contains("Unrecognized operator"));
        }
    }
    
    @Test
    void testAction123() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            semantic.executeAction(123, new Token(4, "1", 0));
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = """
            add
            """;
        
        assertEquals(expected, semantic.getObjectCode());
        assertEquals("int64", semantic.popTypeStack());
    }
    
    @Test
    void testAction124() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            semantic.executeAction(124, new Token(4, "1", 0));
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = """
            sub
            """;
        
        assertEquals(expected, semantic.getObjectCode());
        assertEquals("int64", semantic.popTypeStack());
    }
    
    @Test
    void testAction125() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            semantic.executeAction(125, new Token(4, "1", 0));
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = """
            mul
            """;
        
        assertEquals(expected, semantic.getObjectCode());
        assertEquals("int64", semantic.popTypeStack());
    }
    
    @Test
    void testAction126() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            semantic.executeAction(126, new Token(4, "1", 0));
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = """
            div
            """;
        
        assertEquals(expected, semantic.getObjectCode());
        assertEquals("int64", semantic.popTypeStack());
    }

    @Test
    void testAction127IdNotDeclared() {
        Token token = new Token(3, "f_var3", 38);

        Exception exception = assertThrows(SemanticError.class, () -> {
            semantic.executeAction(127, token);
        });

        assertEquals("f_var3 não declarado", exception.getMessage());
    }

    @Test
    void testAction127PushCorrectTypeToStack() throws SemanticError {
        semantic.addSymbolsTable("i_var1");
        Token token = new Token(3, "i_var1", 38);
        semantic.executeAction(127, token);

        assertEquals("int64", semantic.popTypeStack());
    }

    @Test
    void testAction127GenerateInt64Code() throws SemanticError {
        semantic.addSymbolsTable("i_var1");
        Token token = new Token(3, "i_var1", 38);
        semantic.executeAction(127, token);

        var expected = """
            ldloc i_var1
            conv.r8
            """;
        
        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testAction127GenerateFloat64Code() throws SemanticError {
        semantic.addSymbolsTable("f_var2");
        var token = new Token(3, "f_var2", 38);
        semantic.executeAction(127, token);


        var expected = """
            ldloc f_var2
            """;
        
        assertEquals(expected, semantic.getObjectCode());
        assertFalse(semantic.getObjectCode().contains("conv.r8"));
    }

    @Test
    void testAction128() {
        try {
            semantic.executeAction(128, new Token(4, "1", 0));
        } catch (SemanticError e) { e.printStackTrace(); }

        var expected = """
            ldc.i8 1
            conv.r8
            """;

        assertEquals(expected, semantic.getObjectCode());
        assertEquals("int64", semantic.popTypeStack());
    }

    @Test
    void testAction129() {
        try {
            semantic.executeAction(129, new Token(5, "1.0", 0));
        } catch (SemanticError e) { e.printStackTrace(); }

        var expected = """
            ldc.r8 1.0
            """;

        assertEquals(expected, semantic.getObjectCode());
        assertEquals("float64", semantic.popTypeStack());
    }

    @Test
    void testAction130() {
        try {
            semantic.executeAction(130, new Token(6, "\"test string\"", 0));
        } catch (SemanticError e) { e.printStackTrace(); }

        var expected = """
            ldstr "test string"
            """;

        assertEquals(expected, semantic.getObjectCode());
        assertEquals("string", semantic.popTypeStack());
    }

    @Test
    void testAction131() {
        try {
            semantic.executeAction(131, new Token(4, "1", 0));
        } catch (SemanticError e) { e.printStackTrace(); }

        var expected = """
            ldc.r8 -1.0
            mul
            """;

        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testArimethicCombination() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            Semantic.pop2TypesAndPushCombinationTest("+");
            assertEquals("int64", semantic.popTypeStack());

            semantic.pushTypeStack("float64");
            semantic.pushTypeStack("float64");
            Semantic.pop2TypesAndPushCombinationTest("-");
            assertEquals("float64", semantic.popTypeStack());

            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("float64");
            Semantic.pop2TypesAndPushCombinationTest("*");
            assertEquals("float64", semantic.popTypeStack());

            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("float64");
            Semantic.pop2TypesAndPushCombinationTest("/");
            assertEquals("float64", semantic.popTypeStack());

            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("string");
            try {
                semantic.setToken(new Token(4, "1", 0));
                Semantic.pop2TypesAndPushCombinationTest("+");
                fail("Expected SemanticError due to incompatible types");
            } catch (SemanticError e) {
                assertTrue(e.getMessage().contains("Types not compatible"));
            }
            
        } catch (SemanticError e) { e.printStackTrace(); }
    }

    @Test
    void testRelationalCombination() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            semantic.setRelationalOperator(">");
            
            semantic.executeAction(122, new Token(4, "1", 0));
            assertEquals("bool", semantic.popTypeStack());

            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            semantic.setRelationalOperator("<");
            
            semantic.executeAction(122, new Token(4, "1", 0));
            assertEquals("bool", semantic.popTypeStack());

            semantic.pushTypeStack("float64");
            semantic.pushTypeStack("float64");
            semantic.setRelationalOperator(">");
            
            semantic.executeAction(122, new Token(4, "1", 0));
            assertEquals("bool", semantic.popTypeStack());

            semantic.pushTypeStack("string");
            semantic.pushTypeStack("string");
            semantic.setRelationalOperator("==");
            
            semantic.executeAction(122, new Token(4, "1", 0));
            assertEquals("bool", semantic.popTypeStack());
            
        } catch (SemanticError e) { e.printStackTrace(); }
    }

    @Test
    void testRelationalCombinationIntFloatFail() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("float64");
            semantic.setRelationalOperator("&&");
            semantic.executeAction(122, new Token(5, "1.0", 0));
            fail("Expected SemanticError due to invalid relational operator");
        } catch (SemanticError e) {
            assertTrue(e.getMessage().contains("Types not compatible"));
        }
    }
    
    @Test
    void testRelationalCombinationIntStringFail() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("string");
            semantic.setRelationalOperator("&&");
            semantic.executeAction(122, new Token(6, "test", 0));
            fail("Expected SemanticError due to invalid relational operator");
        } catch (SemanticError e) {
            assertTrue(e.getMessage().contains("Types not compatible"));
        }
    }
    
    @Test
    void testRelationalCombinationFloatStringFail() {
        try {
            semantic.pushTypeStack("string");
            semantic.pushTypeStack("float64");
            semantic.setRelationalOperator("&&");
            semantic.executeAction(122, new Token(5, "1.0", 0));
            fail("Expected SemanticError due to invalid relational operator");
        } catch (SemanticError e) {
            assertTrue(e.getMessage().contains("Types not compatible"));
        }
    }
            
    @Test
    public void testGetAndOrCombination() throws SemanticError {
        Semantic.getAndOrCombinationTest("bool", "bool");
        assertEquals("bool", semantic.popTypeStack());

        semantic.pushTypeStack("int64");
        semantic.pushTypeStack("bool");

        try {
            semantic.executeAction(117, new Token(9, "true", 0));
            fail("Expected SemanticError due to incompatible types");
        } catch (SemanticError e) {
            assertTrue(e.getMessage().contains("Types not compatible"));
        }
    }
}

