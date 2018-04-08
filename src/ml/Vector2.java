///////////////////////////////////////////////////////////////////////////////
// Vector2.java
// =============
// althorithm to create methods to get Vector
//
// AUTHOR: Milim Lee (991274533)
// CREATED: 2018-01-28
// UPDATED: 2018-02-01
// UPDATED: 2018-02-04
///////////////////////////////////////////////////////////////////////////////
package ml;

public class Vector2 {

    // member variables
    public float x, y;

    // Construct and initialize
    public Vector2() {
        set(0, 0);
    }

    // Construct and initialize 
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Set the location -- encapsulation
    public void set(float x, float y) {
        // set(x, y);
        this.x = x;
        this.y = y;
    }

    // constructor and initial value v as a vector - overloading
    public void set(Vector2 v) {
        set(v.x, v.y);
    }

    // Override toString(): Annotation
    @Override
    public String toString() {
        return "Vector2 (" + x + " , " + y + ")";
    }

    // Return the X coordinate
    public float getX() {
        return x;
    }

    // Return the Y coordinate
    public float getY() {
        return y;
    }

    @Override  // To do deep copy
    public Vector2 clone() {
        return new Vector2(this.x, this.y);
    }

    // Vector addition v1 <= this + v2
    public Vector2 add(Vector2 v) {
        x += v.x;
        y += v.y;
        return this;
    }

    // Vector subtract v1 <= this = v2
    public Vector2 subtract(Vector2 v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    // Vector scala multiplication
    public Vector2 scale(float scala) {
        x *= scala;
        y *= scala;
        return this;
    }

    // Vector dot product of two Vectors
    public float dot(Vector2 v) {
        return x * v.x + y * v.y;
    }

    // Vector length
    public float getLength() {
        return (float) Math.sqrt(x * x + y * y);
    }

    // Vector normalization of a unit length of 1
    public Vector2 normalize() {
//        x = x / getLength();
//        y = y / getLength();
//        return this;
        float d = getLength();
        x /= d;  // x = x / d
        y /= d;
        return this;
    }
}
