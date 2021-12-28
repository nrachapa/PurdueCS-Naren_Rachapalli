
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MazeTest {

    private String queueFail = "queue test fail.";
    private String stackFail = "stack test fail.";

    @Test(timeout = 1000)
    public void testStack1() throws Exception {
        char[][] map = toMap(
                "$#####\n" +
                        ".....#\n" +
                        "####%#\n" +
                        "######");
        String result = "(0,0)(1,0)(1,1)(1,2)(1,3)(1,4)(2,4) 6";
        Maze maze = new Maze();
        assertEquals(queueFail, result, maze.solveWithQueue(map));
    }


    @Test(timeout = 1000)
    public void testQueue1() throws Exception {
        char[][] map = toMap(
                "$#####\n" +
                        ".....#\n" +
                        "####%#\n" +
                        "######");
        String result = "(0,0)(1,0)(1,1)(1,2)(1,3)(1,4)(2,4) 6";
        Maze maze = new Maze();
        assertEquals(queueFail, result, maze.solveWithQueue(map));
    }


    @Test(timeout = 1000)
    public void testStack2() throws Exception {
        char[][] map = toMap(
                        "$#########\n" +
                        "..#.....##\n" +
                        "#.######.#\n" +
                        "#.......##\n" +
                        "##.#.#%###\n" +
                        "##########");
        String result1 = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(4,6) 10";

        Maze maze = new Maze();
        assertEquals(stackFail, result1, maze.solveWithStack(map));

    }


    @Test(timeout = 1000)
    public void testQueue2() throws Exception {
        char[][] map = toMap(
                "$#########\n" +
                        "..#.....##\n" +
                        "#.######.#\n" +
                        "#.......##\n" +
                        "##.#.#%###\n" +
                        "##########");
        String result = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(4,6) 12";

        Maze maze = new Maze();
        assertEquals(queueFail, result, maze.solveWithQueue(map));

    }


 //    public void testStack23() throws Exception {
//        // EXAMPLE 2 IN THE HANDOUT
//        // THIS TEST CASE FAILS
//        // DO NOT UNCOMMENT
//        char[][] map = toMap(
//                        "$#########\n" +
//                        "........##\n" +
//                        "#####..#.#\n" +
//                        "#.......##\n" +
//                        "##.#.#%###\n" +
//                        "##########");
//
//        Maze maze = new Maze();
//        assertEquals(stackFail, maze.solveWithQueue(map), maze.solveWithStack(map));
//    }




    @Test(timeout = 1000)
    public void testStack3() throws Exception {
        char[][] map = toMap(
                "$#########\n" +
                        "........##\n" +
                        "#.######.#\n" +
                        "#......%##\n" +
                        "##.#.#####\n" +
                        "##########");
        String result1 = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(3,7) 16";
        Maze maze = new Maze();
        assertEquals(stackFail, result1, maze.solveWithStack(map));

    }


    @Test(timeout = 1000)
    public void testQueue3() throws Exception {
        char[][] map = toMap(
                "$#########\n" +
                        "........##\n" +
                        "#.######.#\n" +
                        "#......%##\n" +
                        "##.#.#####\n" +
                        "##########");
        String result = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(3,7) 18";
        Maze maze = new Maze();
        assertEquals(queueFail, result, maze.solveWithQueue(map));

    }


    @Test(timeout = 1000)
    public void testStack4() throws Exception {
        char[][] map1 = toMap(
                "$#########\n" +
                        "........##\n" +
                        "#.######.#\n" +
                        "#.....#%##\n" +
                        "##.#.#####\n" +
                        "##########");
        //String result = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(3,7) 18";
        //String result1 = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(3,7) 16";
        Maze maze = new Maze();
        assertEquals(stackFail, "no way", maze.solveWithStack(map1));

    }


    @Test(timeout = 1000)
    public void testQueue4() throws Exception {
        char[][] map = toMap(
                "$#########\n" +
                        "........##\n" +
                        "#.######.#\n" +
                        "#.....#%##\n" +
                        "##.#.#####\n" +
                        "##########");
        //String result = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(3,7) 18";
        //String result1 = "(0,0)(1,0)(1,1)(2,1)(3,1)(3,2)(3,3)(3,4)(3,5)(3,6)(3,7) 16";
        Maze maze = new Maze();
        assertEquals(queueFail, "no way", maze.solveWithQueue(map));

    }


    @Test(timeout = 1000)
    public void testStack5() throws Exception {
        char[][] map = toMap(
                "$.#############################\n" +
                        "..................#..#........#\n" +
                        "#######..####..####..#..####..#\n" +
                        "#........#..#...........#.....#\n" +
                        "#..#######..####..####..#..#..#\n" +
                        "#..#.....#...........#..#..#..#\n" +
                        "#..#..#..####..#############..#\n" +
                        "#..#..#..#.....#..............#\n" +
                        "#..####..#..##########..#######\n" +
                        "#.....#...........#..#........#\n" +
                        "#############..####..####..####\n" +
                        "#........#..............#..#..#\n" +
                        "#######..#..####..####..#..#..#\n" +
                        "#...........#........#..#.....#\n" +
                        "####..####..#############..####\n" +
                        "#.....#.....#.....#.....#..#..#\n" +
                        "####..#######..#######..#..#..#\n" +
                        "#..............#..............#\n" +
                        "#..####..##########..#..#######\n" +
                        "#..#........#........#.........\n" +
                        "############################.%.");

        String result1 = "(0,0)(0,1)(1,1)(1,2)(1,3)(1,4)(1,5)(1,6)(1,7)(1,8)(1,9)(1,10)(1,11)(1,12)(1,13)(1,14)(2,14)(2,13)(3,13)(3,14)(3,15)(3,16)(3,17)(3,18)(3,19)(2,19)(1,19)(1,20)(2,20)(3,20)(3,21)(3,22)(2,22)(1,22)(1,23)(1,24)(1,25)(1,26)(1,27)(1,28)(1,29)(2,29)(2,28)(3,28)(3,29)(4,29)(4,28)(5,28)(5,29)(6,29)(6,28)(7,28)(7,27)(7,26)(7,25)(7,24)(7,23)(7,22)(8,22)(8,23)(9,23)(9,24)(9,25)(9,26)(10,26)(10,25)(11,25)(11,26)(12,26)(12,25)(13,25)(13,26)(14,26)(14,25)(15,25)(15,26)(16,26)(16,25)(17,25)(17,24)(17,23)(17,22)(18,22)(18,23)(19,23)(19,24)(19,25)(19,26)(19,27)(19,28)(19,29)(20,29) 143";
        Maze maze = new Maze();
        assertEquals(stackFail, result1, maze.solveWithStack(map));
    }


    @Test(timeout = 1000)
    public void testQueue5() throws Exception {
        char[][] map = toMap(
                "$.#############################\n" +
                        "..................#..#........#\n" +
                        "#######..####..####..#..####..#\n" +
                        "#........#..#...........#.....#\n" +
                        "#..#######..####..####..#..#..#\n" +
                        "#..#.....#...........#..#..#..#\n" +
                        "#..#..#..####..#############..#\n" +
                        "#..#..#..#.....#..............#\n" +
                        "#..####..#..##########..#######\n" +
                        "#.....#...........#..#........#\n" +
                        "#############..####..####..####\n" +
                        "#........#..............#..#..#\n" +
                        "#######..#..####..####..#..#..#\n" +
                        "#...........#........#..#.....#\n" +
                        "####..####..#############..####\n" +
                        "#.....#.....#.....#.....#..#..#\n" +
                        "####..#######..#######..#..#..#\n" +
                        "#..............#..............#\n" +
                        "#..####..##########..#..#######\n" +
                        "#..#........#........#.........\n" +
                        "############################.%.");

        String result = "(0,0)(0,1)(1,1)(1,2)(1,3)(1,4)(1,5)(1,6)(1,7)(1,8)(1,9)(1,10)(1,11)(1,12)(1,13)(1,14)(2,14)(3,14)(3,15)(3,16)(3,17)(3,18)(3,19)(3,20)(3,21)(3,22)(2,22)(1,22)(1,23)(1,24)(1,25)(1,26)(1,27)(1,28)(2,28)(3,28)(4,28)(5,28)(6,28)(7,28)(7,27)(7,26)(7,25)(7,24)(7,23)(8,23)(9,23)(9,24)(9,25)(10,25)(11,25)(12,25)(13,25)(14,25)(15,25)(16,25)(17,25)(17,24)(17,23)(18,23)(19,23)(19,24)(19,25)(19,26)(19,27)(19,28)(19,29)(20,29) 338";
        Maze maze = new Maze();
        assertEquals(queueFail, result, maze.solveWithQueue(map));
    }


    private char[][] toMap(String str) {
        String[] lines = str.split("\n");
        char[][] map = new char[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            map[i] = lines[i].toCharArray();
        }

        return map;
    }


}