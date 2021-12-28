package HW9;
/**
 * Homework 8 - Rectangle
 * 
 * Rectangle class that implements Polygon methods
 * @author Naren Rachapalli, lab sec 008 
 * @version October 20, 2020
 */
public class Rectangle implements Polygon {
    private double breadth; 
    private double length; 
    
    public Rectangle(double length, double breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    public double calculateArea() {
        double area = this.length * this.breadth;
        return area;
    }
    
    public double calculatePerimeter() {
        double perimeter = 2 * (this.breadth + this.length);
        return perimeter;
    }
    
    public double getBreadth() {
        return this.breadth; 
    }
    
    public double getLength() {
        return this.length;
    }
    
    public double getLongestDiagonal() {
        double diagonal = Math.sqrt((Math.pow(this.breadth, 2) + Math.pow(this.length, 2)));
        return diagonal;
    }
    
    public double getLongestSide() {
        if (this.length > this.breadth) {
            return this.length;
        } else {
            return this.breadth;
        }
    }
    
    public String getName() {
        return "Rectangle";
    }
    
    public int getNumberOfDiagonals() {
        return 2;
    }
    
    public int getNumberOfSides() {
        return 4;
    }
    
    public boolean isRegularPolygon() {
        if (this.length == this.breadth) {
            return true;
        }
        return false;
    }
}

