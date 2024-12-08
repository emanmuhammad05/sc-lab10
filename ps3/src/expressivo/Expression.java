/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.Map;


import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;
import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionParser;
import expressivo.parser.ExpressionParser.RootContext;
import expressivo.parser.ExpressionParser.SumContext;
import expressivo.parser.ExpressionParser.PrimitiveContext;

/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    // Datatype definition
    //   TODO
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
	public static Expression parse(String input) {
	    // Convert the input string into a CharStream
		CharStream stream = new ANTLRInputStream(input);
	    
	    // Create the lexer
	    ExpressionLexer lexer = new ExpressionLexer(stream);
	    lexer.removeErrorListeners();  // Remove default error listeners
	    lexer.addErrorListener(new DiagnosticErrorListener());  // Add custom error listener to throw exceptions
	    
	    // Tokenize the input
	    CommonTokenStream tokens = new CommonTokenStream(lexer);
	    
	    // Create the parser
	    ExpressionParser parser = new ExpressionParser(tokens);
	    parser.removeErrorListeners();  // Remove default error listeners
	    parser.addErrorListener(new DiagnosticErrorListener());  // Add custom error listener
	    
	    // Parse the input to get the parse tree for the root expression
	    RootContext rootContext = parser.root();  // Parse starting from the root rule
	    
	    // Build the expression from the parse tree
	    return buildExpressionFromParseTree(rootContext);  // You need to implement this method
	}
	
	public static Expression buildExpressionFromParseTree(ParseTree tree) {
	    // Handle base case: leaf nodes (Number or Variable)
	    if (tree instanceof ExpressionParser.PrimitiveContext) {
	        ExpressionParser.PrimitiveContext primitiveContext = (ExpressionParser.PrimitiveContext) tree;
	        
	        // Check if it's a NUMBER (literal number)
	        if (primitiveContext.NUMBER() != null) {
	            String numberStr = primitiveContext.NUMBER().getText();
	            return new Number(Double.parseDouble(numberStr));
	        }
	        
	        // Otherwise, it should be a sum inside parentheses
	        if (primitiveContext.sum() != null) {
	            return buildExpressionFromParseTree(primitiveContext.sum());
	        }
	    }
	    
	    // Handle internal nodes: Sum
	    if (tree instanceof ExpressionParser.SumContext) {
	        ExpressionParser.SumContext sumContext = (ExpressionParser.SumContext) tree;
	        
	        // Build the leftmost expression
	        Expression left = buildExpressionFromParseTree(sumContext.primitive(0));
	        
	        // Iterate through all the other terms in the sum
	        for (int i = 1; i < sumContext.primitive().size(); i++) {
	            Expression right = buildExpressionFromParseTree(sumContext.primitive(i));
	            left = new Sum(left, right); // Add the next primitive to the sum
	        }
	        
	        return left;
	    }
	    
	    // Handle unexpected cases
	    throw new IllegalArgumentException("Unsupported parse tree structure: " + tree.getClass().getName());
	}



	/**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    // TODO more instance methods
    public Expression differentiate(String variable);

    /**
     * Simplify an expression.
     * @param environment maps variables to values. Variables are required to be case-sensitive nonempty 
     *         strings of letters. The set of variables in environment is allowed to be different than the 
     *         set of variables actually found in expression. Values must be nonnegative numbers.
     * @return an expression equal to the input, but after substituting every variable v that appears in both
     *         the expression and the environment with its value, environment.get(v). If there are no
     *         variables left in this expression after substitution, it's evaluated to a single number.
     */
   // public Expression simplify(Map<String, Double> environment);
    
}
