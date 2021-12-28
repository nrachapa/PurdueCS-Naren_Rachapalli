package HW9;
/**
 * Homework 8 - Circle
 * 
 * Circle class that implements Shape interface
 * @author Naren Rachapalli, lab sec 008 
 * @version October 20, 2020
 */
public class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    public double calculateArea() {
        double area = Math.PI * this.radius * this.radius;
        return area;
    }
    
    public double calculatePerimeter() {
        double perimeter = 2 * Math.PI * this.radius;
        return perimeter;
    }
    
    public String getName() {
        return "Circle";
    }
    
}


