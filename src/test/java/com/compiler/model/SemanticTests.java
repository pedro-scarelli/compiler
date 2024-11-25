package com.compiler.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SemanticTest {

    private Semantic semantic;

    @BeforeEach
    void setUp() {
        semantic = new Semantic();
        semantic.setLength(0);
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

            .class public _UNICA{
            .method static public void _principal(){
            .entrypoint
            """;

        assertEquals(expectedHeader, semantic.getObjectCode());
    }

    @Test
    void testAction101() {
        try {
            semantic.executeAction(101, new Token(16, "end", 38));
        } catch (SemanticError e) {
            e.printStackTrace();
        }

        var expected = """
            M1:ret
                }
            }
            """;

        assertEquals(expected, semantic.getObjectCode());
    }

    @Test
    void testAction128() {
        try {
            semantic.executeAction(128, new Token(4, "1", 38));
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
            semantic.executeAction(129, new Token(5, "1.0", 38));
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
            semantic.executeAction(130, new Token(6, "\"teste string\"", 38));
        } catch (SemanticError e) { e.printStackTrace(); }

        var expected = """
            ldstr "teste string"
            """;

        assertEquals(expected, semantic.getObjectCode());
        assertEquals("string", semantic.popTypeStack());
    }

    @Test
    void testAction118() {
        try {
            semantic.executeAction(118, new Token(9, "true", 38));
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
            semantic.executeAction(119, new Token(10, "false", 38));
        } catch (SemanticError e) { e.printStackTrace(); }

        var expected = """
            ldc.i4 0
            """;

        assertEquals(expected, semantic.getObjectCode());
        assertEquals("bool", semantic.popTypeStack());
    }

    @Test
    void testAction131() {
        try {
            semantic.executeAction(131, new Token(4, "7", 38));
        } catch (SemanticError e) { e.printStackTrace(); }

        var expected = """
            ldc.r8 -1.0
            mul
            """;

        assertEquals(expected, semantic.getObjectCode());
    }
    
    @Test
    void testAction123() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            Semantic.method123();
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
            Semantic.method124();
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
            Semantic.method125();
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
            Semantic.method126();
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = """
            div
            """;
        
        assertEquals(expected, semantic.getObjectCode());
        assertEquals("int64", semantic.popTypeStack());
    }

    @Test
    void testArimethicCombination() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            Semantic.pop2TypesAndPushCombination("+");
            assertEquals("int64", semantic.popTypeStack());

            semantic.pushTypeStack("float64");
            semantic.pushTypeStack("float64");
            Semantic.pop2TypesAndPushCombination("-");
            assertEquals("float64", semantic.popTypeStack());

            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("float64");
            Semantic.pop2TypesAndPushCombination("*");
            assertEquals("float64", semantic.popTypeStack());

            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("float64");
            Semantic.pop2TypesAndPushCombination("/");
            assertEquals("float64", semantic.popTypeStack());

            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("string");
            try {
                Semantic.pop2TypesAndPushCombination("+");
                fail("Expected SemanticError due to incompatible types");
            } catch (SemanticError e) {
                assertTrue(e.getMessage().contains("Types not compatible"));
            }
            
        } catch (SemanticError e) { e.printStackTrace(); }
    }
    
    @Test
    void testAction121() {
        try {
            semantic.executeAction(121, new Token(23, "==", 38));
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
            
            Semantic.method122();
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
            
            Semantic.method122();
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
            
            Semantic.method122();
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
            
            Semantic.method122();
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
            
            Semantic.method122();
            fail("Expected SemanticError due to invalid relational operator");
        } catch (SemanticError e) {
            assertTrue(e.getMessage().contains("Unrecognized operator"));
        }
    }

    @Test
    void testRelationalCombination() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            semantic.setRelationalOperator(">");
            Semantic.method122();
            assertEquals("bool", semantic.popTypeStack());

            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("int64");
            semantic.setRelationalOperator("<");
            Semantic.method122();
            assertEquals("bool", semantic.popTypeStack());

            semantic.pushTypeStack("float64");
            semantic.pushTypeStack("float64");
            semantic.setRelationalOperator(">");
            Semantic.method122();
            assertEquals("bool", semantic.popTypeStack());

            semantic.pushTypeStack("string");
            semantic.pushTypeStack("string");
            semantic.setRelationalOperator("==");
            Semantic.method122();
            assertEquals("bool", semantic.popTypeStack());
            
        } catch (SemanticError e) { e.printStackTrace(); }
    }

    @Test
    void testRelationalCombinationIntFloatFail() {
        try {
            semantic.pushTypeStack("int64");
            semantic.pushTypeStack("float64");
            semantic.setRelationalOperator("&&");
            Semantic.method122();
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
            Semantic.method122();
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
            Semantic.method122();
            fail("Expected SemanticError due to invalid relational operator");
        } catch (SemanticError e) {
            assertTrue(e.getMessage().contains("Types not compatible"));
        }
    }

    @Test
    void testAction120() {
        try {
            semantic.executeAction(120, new Token(10, "false", 38));
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = """
            ldc.i4 1
            xor
            """;
        
        assertEquals(expected, semantic.getObjectCode());
    }
    
    @Test
    public void testMethod116() throws SemanticError {
        semantic.pushTypeStack("bool");
        semantic.pushTypeStack("bool");
        Semantic.method116();

        assertEquals("and\n", semantic.getObjectCode());
    }

    @Test
    public void testMethod117() throws SemanticError {
        semantic.pushTypeStack("bool");
        semantic.pushTypeStack("bool");
        Semantic.method117();

        assertEquals("or\n", semantic.getObjectCode());
    }
            
    @Test
    public void testGetAndOrCombination() throws SemanticError {
        Semantic.getAndOrCombination("bool", "bool");
        assertEquals("bool", semantic.popTypeStack());

        semantic.pushTypeStack("int64");
        semantic.pushTypeStack("bool");

        try {
            Semantic.method117();
            fail("Expected SemanticError due to incompatible types");
        } catch (SemanticError e) {
            assertTrue(e.getMessage().contains("Types not compatible"));
        }
    }

    @Test
    void testAction127IdNotDeclared() {
        Token token = new Token(5, "f_var3", 38);

        Exception exception = assertThrows(SemanticError.class, () -> {
            semantic.executeAction(127, token);
        });

        assertEquals("f_var3 não declarado", exception.getMessage());
    }

    @Test
    void testAction127PushCorrectTypeToStack() throws SemanticError {
        semantic.addSymbolsTable("i_var1");
        Token token = new Token(4, "i_var1", 38);
        semantic.executeAction(127, token);

        assertEquals("int64", semantic.popTypeStack());
    }

    @Test
    void testAction127GenerateInt64Code() throws SemanticError {
        semantic.addSymbolsTable("i_var1");
        Token token = new Token(4, "i_var1", 38);
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
        Token token = new Token(5, "f_var2", 38);
        semantic.executeAction(127, token);


        var expected = """
            ldloc f_var2
            """;
        
        assertEquals(expected, semantic.getObjectCode());
        assertFalse(semantic.getObjectCode().contains("conv.r8"));
    }
}

