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
        assertEquals(4, semantic.popTypeStack());
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
        assertEquals(5, semantic.popTypeStack());
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
        assertEquals(6, semantic.popTypeStack());
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
        assertEquals(9, semantic.popTypeStack());
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
        assertEquals(10, semantic.popTypeStack());
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
            semantic.pushTypeStack(4);
            semantic.pushTypeStack(4);
            Semantic.method123();
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = """
            add
            """;
        
        assertEquals(expected, semantic.getObjectCode());
        assertEquals(4, semantic.popTypeStack());
    }
    
    @Test
    void testAction124() {
        try {
            semantic.pushTypeStack(4);
            semantic.pushTypeStack(4);
            Semantic.method124();
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = """
            sub
            """;
        
        assertEquals(expected, semantic.getObjectCode());
        assertEquals(4, semantic.popTypeStack());
    }
    
    @Test
    void testAction125() {
        try {
            semantic.pushTypeStack(4);
            semantic.pushTypeStack(4);
            Semantic.method125();
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = """
            mul
            """;
        
        assertEquals(expected, semantic.getObjectCode());
        assertEquals(4, semantic.popTypeStack());
    }
    
    @Test
    void testAction126() {
        try {
            semantic.pushTypeStack(4);
            semantic.pushTypeStack(4);
            Semantic.method126();
        } catch (SemanticError e) { e.printStackTrace(); }
    
        var expected = """
            div
            """;
        
        assertEquals(expected, semantic.getObjectCode());
        assertEquals(4, semantic.popTypeStack());
    }

    @Test
    void testArimethicCombination() {
        try {
            semantic.pushTypeStack(4);
            semantic.pushTypeStack(4);
            Semantic.pop2TypesAndPushCombination("+");
            assertEquals(4, semantic.popTypeStack());

            semantic.pushTypeStack(5);
            semantic.pushTypeStack(5);
            Semantic.pop2TypesAndPushCombination("-");
            assertEquals(5, semantic.popTypeStack());

            semantic.pushTypeStack(4);
            semantic.pushTypeStack(5);
            Semantic.pop2TypesAndPushCombination("*");
            assertEquals(5, semantic.popTypeStack());

            semantic.pushTypeStack(4);
            semantic.pushTypeStack(5);
            Semantic.pop2TypesAndPushCombination("/");
            assertEquals(5, semantic.popTypeStack());

            semantic.pushTypeStack(4);
            semantic.pushTypeStack(6);
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

    // @Test
    // void testAction122EqualOperator() {
    //     try {
    //         semantic.pushTypeStack(4);
    //         semantic.pushTypeStack(4);
            
    //         semantic.setRelationalOperator("==");
            
    //         Semantic.method122();
    //     } catch (SemanticError e) { e.printStackTrace(); }

    //     var expectedCode = """
    //         ceq
    //         """;
        
    //     assertEquals(expectedCode, semantic.getObjectCode());
    //     assertEquals(4, semantic.popTypeStack());
    // }

    // @Test
    // void testAction122NotEqualOperator() {
    //     try {
    //         semantic.pushTypeStack(4);
    //         semantic.pushTypeStack(4);
            
    //         semantic.setRelationalOperator("!=");
            
    //         Semantic.method122();
    //     } catch (SemanticError e) { e.printStackTrace(); }

    //     var expectedCode = """
    //         ceq
    //         ldc.i4 1
    //         xor
    //         """;
        
    //     assertEquals(expectedCode, semantic.getObjectCode());
    //     assertEquals(4, semantic.popTypeStack());
    // }

    // @Test
    // void testAction122GreaterThanOperator() {
    //     try {
    //         semantic.pushTypeStack(4);
    //         semantic.pushTypeStack(4);
            
    //         semantic.setRelationalOperator(">");
            
    //         Semantic.method122();
    //     } catch (SemanticError e) {
    //         e.printStackTrace();
    //     }

    //     var expectedCode = """
    //         cgt
    //         """;
        
    //     assertEquals(expectedCode, semantic.getObjectCode());
    //     assertEquals(4, semantic.popTypeStack());
    // }

    // @Test
    // void testAction122LessThanOperator() {
    //     try {
    //         semantic.pushTypeStack(4);
    //         semantic.pushTypeStack(4);
            
    //         semantic.setRelationalOperator("<");
            
    //         Semantic.method122();
    //     } catch (SemanticError e) {
    //         e.printStackTrace();
    //     }

    //     var expectedCode = """
    //         clt
    //         """;
        
    //     assertEquals(expectedCode, semantic.getObjectCode());
    //     assertEquals(4, semantic.popTypeStack());
    // }

    // @Test
    // void testAction122InvalidRelationalOperator() {
    //     try {
    //         semantic.pushTypeStack(4);
    //         semantic.pushTypeStack(4);
            
    //         semantic.setRelationalOperator("&");
            
    //         Semantic.method122();
    //         fail("Expected SemanticError due to invalid relational operator");
    //     } catch (SemanticError e) {
    //         assertTrue(e.getMessage().contains("Relational operator incorrect"));
    //     }
    // }
    
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
        semantic.pushTypeStack(9);
        semantic.pushTypeStack(9);
        Semantic.method116();

        assertEquals("and\n", semantic.getObjectCode());

        semantic.pushTypeStack(4);
        semantic.pushTypeStack(9);

        try {
            Semantic.method116();
            fail("Expected SemanticError due to incompatible types");
        } catch (SemanticError e) {
            assertTrue(e.getMessage().contains("Types not compatible"));
        }
    }

    @Test
    public void testMethod117() throws SemanticError {
        semantic.pushTypeStack(9);
        semantic.pushTypeStack(9);
        Semantic.method117();

        assertEquals("or\n", semantic.getObjectCode());

        semantic.pushTypeStack(4);
        semantic.pushTypeStack(9);

        try {
            Semantic.method117();
            fail("Expected SemanticError due to incompatible types");
        } catch (SemanticError e) {
            assertTrue(e.getMessage().contains("Types not compatible"));
        }
    }
}

