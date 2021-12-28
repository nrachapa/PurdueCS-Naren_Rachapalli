package HW9;
/**
 * Homework 8 - Shape
 * 
 * Shape interface with methods that other shapes will implement
 * @author Naren Rachapalli, lab sec 008 
 * @version October 20, 2020
 */
public interface Shape {
	double calculateArea();
	double calculatePerimeter();
	default int compareArea(Shape shape) {
		if (this.calculateArea() > shape.calculateArea()) {
			return 1;
		} else if (this.calculateArea() == shape.calculateArea()) {
			return 0;
		} else {
			return -1;
		}
	}
	default int comparePerimeter(Shape shape) {
		if (this.calculatePerimeter() > shape.calculatePerimeter()) {
			return 1;
		} else if (this.calculatePerimeter() == shape.calculatePerimeter()) {
			return 0;
		} else {
			return -1;
		}
	}
	String getName();
}
