package HW9;
/**
 * Homework 8 - Square
 * 
 * Square class that inherits Rectangle class that implements Polygon
 * @author Naren Rachapalli, lab sec 008 
 * @version October 20, 2020
 */
public class Square extends Rectangle {
    public Square (double side) {
        super(side, side);
    }
    
    @Override
    public String getName() {
        return "Square";
    }
}