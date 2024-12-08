/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;


import org.junit.Test;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {
	
	private final Expression zero = new Number(0);
    private final Expression one = new Number(1);
    private final Expression x = new Variable("x");
    private final Expression y = new Variable("y");
    
    private final Expression exp1 = new Sum(one, x);
    private final Expression exp2 = new Product(x, one);
    private final Expression exp3 = new Product(exp1, exp2);
    private final Expression exp4 = new Product(x, y);

	 @Test
	    public void testDifferentiateNumber() {
	        assertEquals("expected differentiated expression", one.differentiate("x"), zero);
	    }

	    @Test
	    public void testDifferentiateVariable() {
	        assertEquals("expected differentiated expression", x.differentiate("x"), one);
	    }

	    @Test
	    public void testDifferentiatePlus() {
	        Expression exp = new Sum(zero, one);
	        assertEquals("expected differentiated expression", exp1.differentiate("x"), exp);
	    }

	    @Test
	    public void testDifferentiateMultiply() {
	        Expression exp = new Sum(new Product(one, one),
	            new Product(x, zero));
	        assertEquals("expected differentiated expression", exp2.differentiate("x"), exp);
	    }

	    @Test
	    public void testDifferentiateSingleSameVariable() {
	        Expression left = new Product(new Sum(zero, one),
	            new Product(x, one));
	        Expression right = new Product(new Sum(one, x),
	            new Sum(new Product(one, one), new Product(x, zero)));
	        Expression exp = new Sum(left, right);
	        assertEquals("expected differentiated expression", exp3.differentiate("x"), exp);
	    }

	    @Test
	    public void testDifferentiateSingleDifferentVariable() {
	        Expression left = new Product(new Sum(zero, zero),
	            new Product(x, one));
	        Expression right = new Product(new Sum(one, x),
	            new Sum(new Product(zero, one), new Product(x, zero)));
	        Expression exp = new Sum(left, right);
	        assertEquals("expected differentiated expression", exp3.differentiate("y"), exp);
	    }

	    @Test
	    public void testDifferentiateMultipleVariables() {
	        Expression exp = new Sum(new Product(one, y),
	            new Product(x, zero));
	        assertEquals("expected differentiated expression", exp4.differentiate("x"), exp);
	    }

    
}
