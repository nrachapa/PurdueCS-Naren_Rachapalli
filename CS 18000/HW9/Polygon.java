package HW9;
/**
 * Homework 8 - Polygon
 * 
 * Polygon interface that inherits Shape interface
 * @author Naren Rachapalli, lab sec 008 
 * @version October 20, 2020
 */
public interface Polygon extends Shape {
    double getLongestDiagonal();
    double getLongestSide();
    int getNumberOfDiagonals();
    int getNumberOfSides();
    boolean isRegularPolygon();
}
    
