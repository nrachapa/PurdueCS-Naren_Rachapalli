

/**
 * CS 251: Data Structures and Algorithms
 * Project 1: Part 1
 *
 * TODO: Complete CyclicDoubleQueue.
 *
 * @author , Naren Rachapalli
 * @username , nrachapa
 * @sources TODO: N/A
 *
 *
 *
 */

@SuppressWarnings("unchecked")
public class CyclicDoubleQueue<Item> {

    // initial capacity of the queue
    private int initialCapacity = 7;

    // queue (array of items)
    private Item[] queue;

    // front of the queue
    private int front;

    // back of the queue
    private int back;

    // keeps track of the size;
    private int size;

    // increase factor for resizing the queue
    private int increaseFactor = 2;

    // decrease factor for resizing the queue
    private double decreaseFactor = 0.50;


    /**
     * Constructor of the class.
     * TODO: complete the default Constructor of the class
     *
     * initial capacity = 7;
     *
     */
    @SuppressWarnings("unchecked")
    public CyclicDoubleQueue() {
        this.queue = (Item[]) new Object[this.initialCapacity];
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }


    /**
     * Constructor of the class.
     * TODO: complete the default Constructor of the class
     *
     * initial capacity = 7;
     *
     */
    @SuppressWarnings("unchecked")
    public CyclicDoubleQueue(int initialCapacity, int increaseFactor, double decreaseFactor) {
        this.initialCapacity = initialCapacity;
        this.increaseFactor = increaseFactor;
        this.decreaseFactor = decreaseFactor;
        this.queue = (Item[]) new Object[this.initialCapacity];
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }


    public int getFront() {
        return this.front;
    }

    public int getBack() {
        return this.back;
    }

    public int getSize() {
        return this.size;
    }

    public int getQueueLength() {
        return this.queue.length;
    }


    /**
     *
     * TODO: Enqueue the passed item to the front of the queue.
     *
     */
    public void enqueueFront(Item item) {
    	
    	if (this.isEmpty() &&  this.getFront() == this.getBack()) {
    		this.queue[this.front] = item;
    		this.back++;
    		if (this.back >= this.getQueueLength()) back = 0;
    		this.size++;
    		return;
    	}
    	
    	if (this.size == this.getQueueLength()) {
    		Item[] newQueue = (Item[]) new Object[(int) (this.increaseFactor * this.getQueueLength())];
    		int idx = 0;
    		newQueue[idx++] = item;
    		if (this.getFront() < this.getBack()) {
    			for (int i = this.getFront(); i < this.getBack(); i++) newQueue[idx++] = this.queue[i];
    		} else {
    			for (int i = this.getFront(); i < this.getQueueLength(); i++) newQueue[idx++] = this.queue[i];
    			for (int i = 0; i < this.getBack(); i++) newQueue[idx++] = this.queue[i];
    		}
    		this.front = 0;
    		this.back = idx;
    		this.size++;
    		this.queue = newQueue;
    		if (this.getBack() >= this.getQueueLength()) this.back = 0;
    		return;
    	}
    	
    	this.front = this.front - 1;
    	if (this.getFront() < 0) this.front = this.getQueueLength() - 1;
    	this.queue[this.getFront()] = item;
    	size++;
    	return;
    	
//       

    }


    /**
     *
     * TODO: dequeue the element at the front of the queue
     *
     */
    public Item dequeueFront() throws Exception {
    	if (isEmpty()) throw new Exception();
    	
    	Item removed_item = this.peekFront();
    	if (((double) this.getSize() / this.getQueueLength()) <= 0.25 && this.getQueueLength() > this.initialCapacity) {
    		
    		int newLength = (int) (this.getQueueLength() * this.decreaseFactor);
            if (newLength < this.initialCapacity) newLength = this.initialCapacity;
            Item[] newQueue = (Item[]) new Object[newLength];
    		
            int idx = 0;
    		if (this.getFront() + 1 < this.getBack()) {
    			for (int i = this.getFront() + 1; i < this.getBack(); i++) newQueue[idx++] = this.queue[i];
    		} else {
    			for (int i = this.getFront() + 1; i < this.getQueueLength(); i++) newQueue[idx++] = this.queue[i];
    			for (int i = 0; i < this.getBack(); i++) newQueue[idx++] = this.queue[i];
    		}
    		this.queue = newQueue;
    		this.front = 0;
    		this.back = idx; 
    		if (this.getBack() >= this.getQueueLength()) this.back = 0;
    		size--;
    		return removed_item;
    	}
    	this.queue[this.getFront()] = null;
    	this.front++;
    	if (this.getFront() == this.getQueueLength()) this.front = 0;
    	size--;
    	return removed_item;
    }


    /**
     *
     * TODO: Enqueue the passed item to the back of the queue.
     *
     */
    public void enqueueBack(Item item) {
    	if (this.isEmpty() && this.getFront() == this.getBack()) {
            this.queue[this.front] = item;
            this.back++;
            if (this.getBack() >= this.getQueueLength()) this.back = 0;
            this.size++;
            return;
        }
    	
        if (this.size == this.getQueueLength()) {
            Item[] newQueue = (Item[]) new Object[(int) (this.increaseFactor * this.getQueueLength())];
            int idx = 0;
            for (int i = this.front; i < this.getQueueLength(); i++) { 
                newQueue[idx++] = this.queue[i]; 
            }
            for (int i = 0; i < this.back; i++) {
                newQueue[idx++] = this.queue[i];
            }
            newQueue[idx++] = item;
            this.front = 0;
            this.back = idx;
            this.queue = newQueue;
            if (this.back >= this.getQueueLength()) this.back = 0;
            this.size++;
            return;
        }
        
        this.queue[this.getBack()] = item;
        this.back++;
        this.size++;
        if (this.getBack() >= this.getQueueLength()) {
        	this.back = 0;
        }
        return;
    }


    /**
     *
     * TODO: dequeue the element at the back of the queue
     *
     */
    public Item dequeueBack() throws Exception {
        if (isEmpty()) throw new Exception();
        
        if ((((double) this.size / this.queue.length)) <= 0.25 && this.queue.length > this.initialCapacity) {
            
        	int newLength = (int) (this.getQueueLength() * this.decreaseFactor);
            if (newLength < this.initialCapacity) newLength = this.initialCapacity;
            Item[] newQueue = (Item[]) new Object[newLength];
            
            int idx = 0;
            if (this.front >= this.back) {
                for (int i = this.getFront(); i < this.getQueueLength(); i++) newQueue[idx++] = this.queue[i]; 
                for (int i = 0; i < this.getBack(); i++) newQueue[idx++] = this.queue[i];
            } else {
            	for (int i = this.getFront(); i < this.getBack(); i++) newQueue[idx++] = this.queue[i];
            }
            
            this.queue = newQueue;
            Item removed_item = this.queue[idx - 1];
            newQueue[idx - 1] = null;  
            this.front = 0;
            this.back = idx - 1;
            if (this.back == this.getQueueLength()) this.back = 0;
            size--;
            
            return removed_item;
            
        }
        
        Item removed_item;
        if (this.getBack() - 1 < 0) {
        	this.back = this.getQueueLength() - 1;
        } else {
            this.back--;
        }
        
        removed_item = this.queue[this.getBack()];
        this.queue[this.getBack()] = null;
        
        size--;
        
        return removed_item;
    }


    /**
     *
     * TODO: peek the element at the front of the queue
     *
     */
    public Item peekFront() throws Exception {
        if (this.isEmpty() || this.queue[this.getFront()] == null) throw new Exception();
        return this.queue[this.getFront()];
    }


    /**
     *
     * TODO: peek the element at the back of the queue
     *
     */
    public Item peekBack() throws Exception {
        if (this.isEmpty()) throw new Exception();
        int lastElement = this.getBack() - 1;
        if (lastElement < 0) lastElement = this.getQueueLength() - 1;
        if (this.queue[lastElement] == null) throw new Exception();
        return this.queue[lastElement];
    }


    /**
     *
     * TODO: check if the queue is empty
     *
     */
    public boolean isEmpty() {
        return this.size == 0;
    }
        
}
