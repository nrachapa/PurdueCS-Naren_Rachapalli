/**
 * CS 251: Data Structures and Algorithms
 * Project 2: Part 4
 *
 * TODO: Complete OrderSystemModel.
 *
 * @author Chirayu Garg, TODO: add your name here
 * @username garg104, TODO: add your Purdue username here
 * @sources TODO: list your sources here
 */

import java.security.NoSuchAlgorithmException;

public class OrderSystemModel {
    private BetterCustomerOrderQueue orderList;
    private int capacityThreshold;
    private int ordersDelayed;
    private int ordersOnTime;
    private int ordersCanceled;
    private int time;
    private int totalDelayTime;

    public int getOrdersDelayed() {
        return ordersDelayed;
    }

    public int getOrdersOnTime() {
        return ordersOnTime;
    }

    public int getOrdersCanceled() {
        return ordersCanceled;
    }

    public int getTotalDelayTime() {
        return totalDelayTime;
    }

    public BetterCustomerOrderQueue getOrderList() {
        return orderList;
    }

    /**
     * Constructor of the class.
     *
     * Initialize a new OrderSystemModel and OrderSystemModel
     *
     */
    public OrderSystemModel(int capacityThreshold) {
        this.capacityThreshold = capacityThreshold;
        this.orderList = new BetterCustomerOrderQueue(this.capacityThreshold);
        this.ordersDelayed = 0;
        this.ordersOnTime = 0;
        this.ordersCanceled = 0;
        this.time = 0;
        this.totalDelayTime = 0;
    }

    /**
     *
     * TODO: Process a new CustomerOrder with a given name.
     *
     */
    public String process(String name, int orderTime, int deliveryTime) throws NoSuchAlgorithmException {
    	CustomerOrder new_order = new CustomerOrder(name, orderTime, deliveryTime);
    	if (this.orderList.size() == this.orderList.getOrderList().length) {
    		if (new_order.compareTo(this.orderList.getMax()) == 1) {
    			return null;
    		} else {
    			String completed = completeNextOrder();
    			this.orderList.insert(new_order);
    			return completed;
    		}
    	} else {
    		this.orderList.insert(new_order);
    		return new_order.getName();
    	}    	
    }

    /**
     *
     * TODO: Complete the highest priority order
     *
     */
    public String completeNextOrder() throws NoSuchAlgorithmException {
    	CustomerOrder order = this.orderList.getMax();
    	if (order == null) return null;
    	if (order.getOrderDeliveryTime() <= this.time) {
    		this.ordersOnTime++;
    	} else {
    		this.ordersDelayed++;
    		this.totalDelayTime += (order.getOrderDeliveryTime() - this.time);
    	}
    	this.orderList.delMax();
    	this.time++;
    	return order.getName();
    }

   /**
     *
     * TODO: Update the delivery time of the order for the given name
     *
     */
    public String updateOrderTime(String name, int newDeliveryTime) throws NoSuchAlgorithmException {
    	if(this.orderList.get(name) == null) return null;
    	if (newDeliveryTime < this.orderList.getMax().getOrderDeliveryTime()) {
    		if (newDeliveryTime < this.time) { 
    			this.cancelOrder(name); 
    		} else {
    			CustomerOrder removed_order = this.orderList.remove(name);
    			if (removed_order.getOrderDeliveryTime() <= this.time) {
    	    		this.ordersOnTime++;
    	    	} else {
    	    		this.ordersDelayed++;
    	    		this.totalDelayTime += (removed_order.getOrderDeliveryTime() - this.time);
    	    	}
    		}
    		return name;
    	}
    	this.orderList.update(name, newDeliveryTime);
    	return null;
    }

    /**
     *
     * TODO: Cancel the order for the given name
     *
     */
    public CustomerOrder cancelOrder(String name) throws NoSuchAlgorithmException {
        if (this.orderList.get(name) == null) return null;
    	CustomerOrder canceled_order = this.orderList.remove(name);
    	this.ordersCanceled++;
    	return canceled_order;
    }

}

