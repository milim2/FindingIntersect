///////////////////////////////////////////////////////////////////////////////
// Line.java
// =========
// Algorithm to create 2 lines, then find the interseted point 
// between line1 and line2 using the algorithm of parametic form and standard form
//
// AUTHOR: Milim Lee (991274533)
// CREATED: 2018-01-28
// UPDATED: 2018-02-01
// UPDATED: 2018-02-04
// UPDATED: 2018-02-12
///////////////////////////////////////////////////////////////////////////////
package ml;

public class Line {

    // Define 2 members variables as Vector 2
    private Vector2 point;//= new Vector2() by P.F ;any point on the line
    private Vector2 direction;// p1-p0 (direction vector)+

    // Define 4 constructors - main purpose : initialize
    public Line() {
        point = new Vector2(0, 0); // passing default value of Vector2 
        direction = new Vector2(0, 0); // p1-p0
    }

    public Line(Vector2 point, Vector2 direction) {
        this.point = new Vector2(point.x, point.y);
        this.direction = new Vector2(direction.x, direction.y);  // *****
    }

    // to get direction, point -- in TestLine class, this will be used
    public Line(float x1, float y1, float x2, float y2) {
        point = new Vector2(x1, y1);
        direction = new Vector2(x2 - x1, y2 - y1);
    }

    public Line(float slope, float intercept) {
        point = new Vector2(0, intercept);
        direction = new Vector2(1, slope); // Put 1 as base value of x axis 
    }

    // Define three set methods
    public void set(Vector2 point, Vector2 direction) {
//        this.point = point;
//        this.direction = direction;  // ****
        
        this.point.set(point);
        this.direction.set(direction);
    }

    // To set slope and intercept
    public void set(float slope, float intercept) {
//        slope = direction.y / direction.x;
//        intercept = point.y - slope * point.x;      // ***** 
        
        this.point.set(0, intercept);
        this.direction.set(1, slope);
    }

    public void set(float x1, float y1, float x2, float y2) {
//        x1 = x1;
//        y1 = y1;
//        x2 = x2;
//        y2 = y2;     // ******* 
        
        this.point.set(x1, y1);
        this.direction.set(x2-x1, y2-y1);
    }

    // Define getPoint() and setPoint methods
    public void setPoint(Vector2 point) {
        // point = point;// able to omit //******
        this.point.set(point);
    }

    public Vector2 getPoint() {
        return point;
    }

    // Define getDirection() and setDirection methods
    public void setDirection(Vector2 direction) {
//        direction = direction; // ****** 

          this.direction.set(direction);  
    }

    public Vector2 getDirection() {
        return direction;
    }

    @Override // Annotation
    public String toString() {
        String s = " Line\n ====\nPoint : (" + point.x + " , " + point.y + ")\n"
                + "Direction : (" + direction.x + " , " + direction.y + ")\n";
        return s;
    }

    // Define intersect() method to find the intersection point from 2 line segments
    // If there is no intersection, return a point with Float.NaN
    // find intersection
    //    line1 : ax + by  = c(this)
    //    line2 : dx + ef  = g(other)
    //    intersect point:  x = (ce - bf)/(ae - bd)
    //                      y = (af -cd)/(ae - bd)
    public Vector2 intersect(Line line) {
        // find efficients (a,b,c) of line 1(this), line 2 is line
        float a = direction.y;
        float b = -direction.x;
        float c = direction.y * point.x - direction.x * point.y;

        float d = line.getDirection().y; // getY() : private, public
        float e = -line.getDirection().x;
        float f = line.getDirection().y * line.getPoint().x - line.getDirection().x * line.getPoint().y;

        //  find determinant : ae - bd -- determine about intersect
        // ae-bd = 0, no answer (det)
        float determinant = a * e - b * d;

        // find the intersect point (x,y) if det !=0        
        // if det=0, return a Vector2 with Float.NaN
        Vector2 intersectPoint = new Vector2(Float.NaN, Float.NaN); // default point with NaN
        // Default x, y value
        float x = Float.NaN;
        float y = Float.NaN;
        if (determinant != 0) {
//            intersectPoint.y = (a * f) - (c * d) / determinant;
//            intersectPoint.x = (c * e) - (b * f) / determinant;     // ****** 
            
            intersectPoint.x = (c * e - b * f) / determinant;
            intersectPoint.y = (a * f - c * d) / determinant;
        }
        // return the intersected point
        return intersectPoint;
    }

    // Define intersected() method to determine if 2 lines are intersected or not
    // extra process for check
    public boolean isIntersected(Line line) {
        float a = direction.y;
        float b = -direction.x;

        float d = line.getDirection().y; // private, public -- getY()
        float e = -line.getDirection().x;

        return ((a * e - b * d) != 0);

//        if ((a * e - b * d) == 0) {
//            return false;
//        }
//        else
//            return true;    
    }
}
