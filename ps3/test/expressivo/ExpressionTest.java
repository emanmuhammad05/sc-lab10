package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy:
    // 1. Test the toString(), equals(), and hashCode() methods for:
    //    - Number: positive, zero, and floating-point comparisons
    //    - Variable: case sensitivity and different lengths
    //    - Sum: simple and nested sums, associativity
    //    - Product: simple and nested products, associativity
    // 2. Test interaction between Sum and Product (e.g., Sum(Product(...))).
    // 3. Ensure structural equality is respected.

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // Tests for Number
    @Test
    public void testNumber() {
        Expression e1 = new Number(3);
        Expression e2 = new Number(3.0);
        Expression e3 = new Number(4);

        assertEquals("3.0", e1.toString()); // Check string representation
        assertEquals(e1, e2);               // Check equality
        assertNotEquals(e1, e3);            // Different numbers should not be equal
        assertEquals(e1.hashCode(), e2.hashCode()); // Consistent hashCode for equal objects
    }

    // Tests for Variable
    @Test
    public void testVariable() {
        Expression v1 = new Variable("x");
        Expression v2 = new Variable("x");
        Expression v3 = new Variable("y");
        Expression v4 = new Variable("X"); // Case-sensitive

        assertEquals("x", v1.toString()); // Check string representation
        assertEquals(v1, v2);             // Variables with the same name should be equal
        assertNotEquals(v1, v3);          // Different variables should not be equal
        assertNotEquals(v1, v4);          // Case-sensitive: "x" != "X"
    }

    // Tests for Sum
    @Test
    public void testSum() {
        Expression sum1 = new Sum(new Number(1), new Variable("x"));
        Expression sum2 = new Sum(new Number(1), new Variable("x"));
        Expression sum3 = new Sum(new Variable("x"), new Number(1));

        assertEquals("(1.0 + x)", sum1.toString()); // Check string representation
        assertEquals(sum1, sum2);                   // Structural equality
        assertNotEquals(sum1, sum3);                // Order matters: 1 + x != x + 1
    }

    // Tests for Product
    @Test
    public void testProduct() {
        Expression prod1 = new Product(new Number(2), new Variable("y"));
        Expression prod2 = new Product(new Number(2), new Variable("y"));
        Expression prod3 = new Product(new Variable("y"), new Number(2));

        assertEquals("(2.0 * y)", prod1.toString()); // Check string representation
        assertEquals(prod1, prod2);                  // Structural equality
        assertNotEquals(prod1, prod3);               // Order matters: 2 * y != y * 2
    }

    // Tests for nested expressions
    @Test
    public void testNestedExpressions() {
        Expression expr1 = new Sum(new Product(new Number(2), new Variable("x")), new Number(3));
        Expression expr2 = new Sum(new Product(new Number(2), new Variable("x")), new Number(3));
        Expression expr3 = new Product(new Sum(new Number(2), new Variable("x")), new Number(3));

        assertEquals("((2.0 * x) + 3.0)", expr1.toString()); // Nested expression
        assertEquals(expr1, expr2);                          // Structural equality
        assertNotEquals(expr1, expr3);                       // Different structure
    }

    // Test hashCode consistency
    @Test
    public void testHashCodeConsistency() {
        Expression e1 = new Sum(new Number(1), new Variable("x"));
        Expression e2 = new Sum(new Number(1), new Variable("x"));

        assertEquals(e1.hashCode(), e2.hashCode()); // Equal expressions should have equal hash codes
    }
}
