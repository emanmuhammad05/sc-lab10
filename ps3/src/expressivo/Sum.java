package expressivo;

/**
 * Immutable data type representing the sum of two expressions.
 * Abstraction Function (AF):
 *   AF(left, right) = left + right
 * 
 * Representation Invariant (RI):
 *   left and right are non-null.
 * 
 * Safety from rep exposure:
 *   All fields are private, final, and immutable.
 */
public class Sum implements Expression {
    private final Expression left;
    private final Expression right;

    private void checkRep() {
        assert left != null : "Left expression cannot be null";
        assert right != null : "Right expression cannot be null";
    }

    public Sum(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        checkRep();
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Sum)) return false;
        Sum that = (Sum) thatObject;
        return this.left.equals(that.left) && this.right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return 31 * left.hashCode() + right.hashCode();
    }
    
    @Override
    public Expression differentiate(String variable) {
        Expression leftDiff = left.differentiate(variable); // Differentiate left operand
        Expression rightDiff = right.differentiate(variable); // Differentiate right operand
        return new Sum(leftDiff, rightDiff); // Derivative of a sum is the sum of the derivatives
    }
}
