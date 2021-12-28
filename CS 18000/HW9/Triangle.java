package HW9;
/**
 * Homework 8 - Triangle
 * 
 * Triangle class that implements Polygon methods
 * @author Naren Rachapalli, lab sec 008 
 * @version October 20, 2020
 */
public class Triangle implements Polygon {
    private double[] sides = new double[3];
    
    public Triangle (double side) {
        this.sides[0] = side;
        this.sides[1] = side;
        this.sides[2] = side;
    }
    
    public Triangle (double side1, double side2, double side3) {
        this.sides[0] = side1;
        this.sides[1] = side2;
        this.sides[2] = side3;
    }
    
    public double calculateArea() {
        double s = (this.sides[0] + this.sides[1] + this.sides[2]) / 2;
        double squaredArea = s * (s - this.sides[0]) * (s - this.sides[1]) * (s - this.sides[2]);
        double area = Math.sqrt(squaredArea);
        return area;
    }
    
    public double calculatePerimeter() {
        double p = this.sides[0] + this.sides[1] + this.sides[2];
        return p;
    }
    
    public double getLongestDiagonal() {
        return 0;
    }
    
    public double getLongestSide() {
        if (this.sides[0] > this.sides[1] && this.sides[0] > this.sides[2]) {
            return this.sides[0];
        } else if (this.sides[1] > this.sides[0] && this.sides[1] > this.sides[2]) {
            return this.sides[1];
        } else {
            return this.sides[2];
        }
    }
    
    public String getName() {
        return "Triangle";
    }
    
    public int getNumberOfDiagonals() {
        return 0;
    }
    
    public int getNumberOfSides() {
        return 3; 
    }
    
    public double[] getSides() {
        return this.sides;
    }
    
    public boolean isRegularPolygon() {
        if (this.sides[0] == this.sides[1] && this.sides[1] == this.sides[2]) {
            return true;
        }
        return false;
    }
}

