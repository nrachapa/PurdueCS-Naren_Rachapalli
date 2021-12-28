package HW10;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * A simple Player class which may cause race conditions in multi-thread program.
 * 
 */
public class Player {
	private int x;	//x position of the player
	private int y;	//y position of the player
	private int hp;		//health point of the player
	private AtomicInteger a = new AtomicInteger(y);
	
	public Player(int x, int y, int hp){
		this.x = x;
		this.y = y;
		this.a = new AtomicInteger(y);
		this.hp = hp;
	}
	
	public static Player obj = new Player(0,0,5000);

	public void printPlayer(){
		System.out.printf("x position:\t%d\ny position:\t%d\nhealth point:\t%d\n", x, a.get(), hp);
	}
	
	public synchronized void moveLeft(){
		x --;
	}
	public synchronized void moveRight(){
		x ++;
	}
	
	public void moveUp(){
		a.decrementAndGet();
		y = a.get();
	}
	public void moveDown(){
		a.incrementAndGet();
		y = a.get();
	}
	
	public void loseHealth() {
		synchronized(obj) {
			hp --;
		}
	}
	public void gainHealth() {
		synchronized(obj) {
			hp ++;
		}
	}
	
}
