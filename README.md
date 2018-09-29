# FindingIntersect ![CI status](https://img.shields.io/badge/build-passing-brightgreen.svg)

> Finding Intersect inplements Vector 2D by JavaFX

> To interact with user input from mouse and keyboard (arrow keys), and to visualize 2 lines corresponding to user input and the intersection point of 2 lines

### Requirements
* JDK v8 and up
* Netbeans
* Scene Builder

### About Line Class
```
> Line class can be defined with 2 member variables
public class Line
{
private Vector2 point; // a point on the line 
private Vector2 direction; // direction vector
...
}

> Constructors
1. From 2 points: (x1, y1), (x2, y2) 
                  point = (𝑥1, 𝑦1) 
                  direction = (𝑥2−𝑥1, 𝑦2−𝑦1)
2. From slope and y-intercept: point = (0, intercept) 
                               direction = (1, slope)
                               
> Intersection Point
If the denominator of x and y is 0, then there is no solution (no intersect) because of division by zero. The denominator (𝑎𝑒−𝑏𝑑) is called determinant.
if(ae – bd != 0) return true; 
else             return false;

### Visuals
```
![pic11](./img/pic11.png)
