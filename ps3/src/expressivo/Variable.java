package expressivo;

/**
 * Immutable data type representing a variable (case-sensitive string of letters).
 * Abstraction Function (AF):
 *   AF(name) = a variable with the name 'name'
 * 
 * Representation Invariant (RI):
 *   name is non-empty and contains only letters.
 * 
 * Safety from rep exposure:
 *   All fields are private, final, and immutable.
 */
public class Variable implements Expression {
    private final String name;

    // Abstraction function and rep invariant check
    private void checkRep() {
        assert name.matches("[a-zA-Z]+") : "Variable name must contain only letters";
        assert !name.isEmpty() : "Variable name must not be empty";
    }

    public Variable(String name) {
        this.name = name;
        checkRep();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Variable)) return false;
        Variable that = (Variable) thatObject;
        return this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    public Expression differentiate(String variable) {
        if (this.name.equals(variable)) {
            return new Number(1); // Derivative of x with respect to x is 1
        } else {
            return new Number(0); // Derivative of a variable with respect to a different variable is 0
        }
    }
}
