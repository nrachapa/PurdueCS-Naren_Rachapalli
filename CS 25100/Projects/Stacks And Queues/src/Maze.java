
import static org.junit.Assert.assertEquals;

/**
 * CS 251: Data Structures and Algorithms
 * Project 1: Part 12
 *
 * TODO: Complete Maze.
 *
 * @author , Naren Rachapalli
 * @username , nrachapa 
 * @sources N/A
 *
 *
 *
 */
public class Maze {

    private final char SPACE = '.';
    private final char WALL = '#';
    private final char START = '$';
    private final char END = '%';

    /**
     * Finds the path using CyclicDoubleQueue as a traditional stack
     * Returns the path and number of spaces checked as {@code String}.
     *
     * @param  map a {@code char[][]} provide.
     * @return the path and number of spaces checked as {@code String}
     */
    public String solveWithStack(char[][] map) throws Exception {
    	int[] start_pos = new int[2];
    	int[] end_pos = new int[2];
    	for (int i = 0; i < map.length; i++) {
    		for (int j = 0; j < map[i].length; j++) {
    			if (map[i][j] == START) {
    				start_pos[0] = i;
    				start_pos[1] = j;
    				continue;
    			}
    			if (map[i][j] == END) {
    				end_pos[0] = i;
    				end_pos[1] = j;
    				continue;
    			}
    		}
    	}
    	
    	
    	return "";
    }

    /**
     * Finds the path using CyclicDoubleQueue as a traditional queue.
     * Returns the path and number of spaces checked as {@code String}.
     *
     * @param  map a {@code char[][]} provide.
     * @return the path and number of spaces checked as {@code String}
     */
    public String solveWithQueue(char[][] map) throws Exception {
    	int[] start_pos = new int[2];
    	int[] end_pos = new int[2];
    	int[] current_pos = new int[2];
    	for (int i = 0; i < map.length; i++) {
    		for (int j = 0; j < map[i].length; j++) {
    			if (map[i][j] == START) {
    				current_pos[0] = start_pos[0] = i;
    				current_pos[1] = start_pos[1] = j;
    				continue;
    			}
    			if (map[i][j] == END) {
    				end_pos[0] = i;
    				end_pos[1] = j;
    				continue;
    			}
    		}
    	}
    	
    	CyclicDoubleQueue<int[]> buffer = new CyclicDoubleQueue<>();
    	CyclicDoubleQueue<int[]> queue = new CyclicDoubleQueue<>();
    	buffer.enqueueFront(current_pos);
		queue.enqueueFront(current_pos);
		System.out.printf("Start: (%d, %d)\n", start_pos[0], start_pos[1]);
		System.out.printf("End: (%d, %d)", end_pos[0], end_pos[1]);
		int count = 0;
		
    	while (!(current_pos[0] == end_pos[0] && current_pos[1] == end_pos[1])) {
    		int[] prev = {current_pos[0], current_pos[1]};
    		current_pos = buffer.dequeueFront();
    		
    		System.out.printf("\n----------------\nNew Start: (%d, %d)\n", current_pos[0], current_pos[1]);
    		//Down
    		if (((current_pos[0] + 1) >= 0 && (current_pos[0] + 1) < map.length) && 
    			(map[current_pos[0] + 1][current_pos[1]] == SPACE || 
    			map[current_pos[0] + 1][current_pos[1]] == END)) {
    			int[] square = {current_pos[0] + 1, current_pos[1]};
    			boolean exists = false;
    			if (square[0] == prev[0] && square[1] == prev[1]) exists = true;
    			if (!exists) {
    				count++;
    				buffer.enqueueBack(square);
    				queue.enqueueBack(square);
    				System.out.printf("Buffer Added: (%d, %d)\n", buffer.peekBack()[0], buffer.peekBack()[1]);
    				System.out.printf("Queue Added: (%d, %d)\n", queue.peekBack()[0], queue.peekBack()[1]);
    				System.out.printf("Count: %d\n", count);
    			}
    			
    		}
    		
    		//Right
    		if (((current_pos[1] + 1) >= 0 && (current_pos[1] + 1) < map[current_pos[0]].length) && 
    			(map[current_pos[0]][current_pos[1] + 1] == SPACE || 
    			map[current_pos[0]][current_pos[1] + 1] == END)) {
    			int[] square = {current_pos[0], current_pos[1] + 1};
    			boolean exists = false;
    			if (square[0] == prev[0] && square[1] == prev[1]) exists = true;
    			if (!exists) {
    				count++;
    				buffer.enqueueBack(square);
    				queue.enqueueBack(square);
    				System.out.printf("Buffer Added: (%d, %d)\n", buffer.peekBack()[0], buffer.peekBack()[1]);
    				System.out.printf("Queue Added: (%d, %d)\n", queue.peekBack()[0], queue.peekBack()[1]);
    				System.out.printf("Count: %d\n", count);
    			}
    		}
    		
    		//Up
    		if (((current_pos[0] - 1) >= 0 && (current_pos[0] - 1) < map.length) && 
    			(map[current_pos[0] - 1][current_pos[1]] == SPACE || 
    			map[current_pos[0] - 1][current_pos[1]] == END)) {
    			int[] square = {current_pos[0] - 1, current_pos[1]};
    			boolean exists = false;
    			if (square[0] == prev[0] && square[1] == prev[1]) exists = true;
    			if (!exists) {
    				count++;
    				buffer.enqueueBack(square);
    				queue.enqueueBack(square);
    				System.out.printf("Buffer Added: (%d, %d)\n", buffer.peekBack()[0], buffer.peekBack()[1]);
    				System.out.printf("Queue Added: (%d, %d)\n", queue.peekBack()[0], queue.peekBack()[1]);
    				System.out.printf("Count: %d\n", count);
    			}
    		}
    		
    		//Left
    		if (((current_pos[1] - 1) >= 0 && (current_pos[1] - 1) < map[current_pos[0]].length) && 
    			(map[current_pos[0]][current_pos[1] - 1] == SPACE || 
    			map[current_pos[0]][current_pos[1] - 1] == END)) {
    			int[] square = {current_pos[0], current_pos[1] - 1};
    			boolean exists = false;
    			if (square[0] == prev[0] && square[1] == prev[1]) exists = true;
    			if (!exists) {
    				count++;
    				buffer.enqueueBack(square);
    				queue.enqueueBack(square);
    				System.out.printf("Buffer Added: (%d, %d)\n", buffer.peekBack()[0], buffer.peekBack()[1]);
    				System.out.printf("Queue Added: (%d, %d)\n", queue.peekBack()[0], queue.peekBack()[1]);
    				System.out.printf("Count: %d\n", count);
    			}
    		}
    	}
    	
    	String output = "";
    	for (int[] coord = queue.dequeueBack(); !queue.isEmpty(); coord = queue.dequeueBack()) {
    		System.out.printf("Coord: (%d, %d)\n", coord[0], coord[1]);
    		boolean adjacent = false;
    		
    		//Check Left
    		if ((coord[0]) == queue.peekBack()[0] && (coord[1] - 1) == queue.peekBack()[1]) {
    			adjacent = true;
    		}
    		//Check Up
    		else if ((coord[0] - 1) == queue.peekBack()[0] && (coord[1]) == queue.peekBack()[1]) {
    			adjacent = true;
    		}
    		//Check Right
    		else if ((coord[0]) == queue.peekBack()[0] && (coord[1] + 1) == queue.peekBack()[1]) {
    			adjacent = true;
    		}
    		//Check Down
    		else if ((coord[0] + 1) == queue.peekBack()[0] && (coord[1]) == queue.peekBack()[1]) {
    			adjacent = true;
    		}
    		
    		else {
    			queue.dequeueBack();
    		}
    		
    		if (adjacent) output = String.format("(%d,%d)", coord[0], coord[1]).concat(output);
    		if(queue.isEmpty()) break;
    	}
    	output = String.format("(%d,%d)", start_pos[0], start_pos[1]).concat(output);
    	output = output.concat(String.format(" %d", count));
    	System.out.printf("\noutput: %s\n", output);

    	
    	
        return output;
    }
    
    
    public static char[][] toMap(String str) {
        String[] lines = str.split("\n");
        char[][] map = new char[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            map[i] = lines[i].toCharArray();
        }

        return map;
    }
    
    public static void main(String[] args) throws Exception {
    	String queueFail = "queue test fail.";
    	char[][] map = toMap(
                "$#####\n" +
                        ".....#\n" +
                        "####%#\n" +
                        "######");
        String result = "(0,0)(1,0)(1,1)(1,2)(1,3)(1,4)(2,4) 6";
        Maze maze = new Maze();
        assertEquals(queueFail, result, maze.solveWithQueue(map));

    }


}
