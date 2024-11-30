package expressivo;

/**
 * Immutable data type representing a nonnegative integer or floating-point number.
 * Abstraction Function (AF):
 *   AF(value) = a nonnegative numeric constant represented by 'value'.
 * 
 * Representation Invariant (RI):
 *   value >= 0
 * 
 * Safety from rep exposure:
 *   All fields are private, final, and immutable.
 */
public class Number implements Expression {
    private final double value;

    // Abstraction function and rep invariant check
    private void checkRep() {
        assert value >= 0 : "Number must be nonnegative";
    }

    public Number(double value) {
        this.value = value;
        checkRep();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Number)) return false;
        Number that = (Number) thatObject;
        return Double.compare(this.value, that.value) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
