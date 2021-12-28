
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class CyclicDoubleQueueTest {

    private static Random gen;
    private double totalScore = 0.0;


    /**
     *
     */
    private static void init_random() {
        gen = new Random(System.currentTimeMillis());
    }



    @Test
    public void test1() {
        // test basic empty queue operations
        double score = 0.0;
        CyclicDoubleQueue queue = new CyclicDoubleQueue<>();

        // check empty
        if (queue.isEmpty()) {
            score += 0.25;
        }

        // check back
        if (queue.getBack() == 0) {
            score += 0.25;
        }

        // check front
        if (queue.getFront() == 0) {
            score += 0.25;
        }

        // check size
        if (queue.getSize() == 0) {
            score += 0.25;
        }

        // check empty dequeueFront
        try {
            queue.dequeueFront();
        } catch (Exception e) {
            score += 0.25;
        }

        // check empty dequeueBack
        try {
            queue.dequeueBack();
        } catch (Exception e) {
            score += 0.25;
        }

        // check empty peekBack
        try {
            queue.peekBack();
        } catch (Exception e) {
            score += 0.25;
        }

        // check empty peekBack
        try {
            queue.peekFront();
        } catch (Exception e) {
            score += 0.25;
        }

        totalScore += score;


        assertEquals("test1 failed\n", 2.0, score, 0.001);
    }


    @Test(timeout = 1000)
    public void test2() {
        init_random();
        double scorePeek = 0.0;
        double scoreFront = 0.0;
        double scoreBack = 0.0;
        ArrayList<Character> list = new ArrayList<>();
        CyclicDoubleQueue<Character> queue = new CyclicDoubleQueue<>();

        // check peek back and front
        for (int i = 0; i < gen.nextInt(100000) + 100000; i++) {
            char toInsert = (char) (gen.nextInt(75) + 48);
            list.add(toInsert);
            queue.enqueueFront(toInsert);
        }
        try {
            assertEquals("Check PeekFront\n", list.remove(list.size() - 1), queue.peekFront());
            assertEquals("PeekBack is wrong\n", list.remove(0), queue.peekBack());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        scorePeek += 1.0;


        //enqueueFront random items
        list = new ArrayList<>();
        queue = new CyclicDoubleQueue<>();
        for (int i = 0; i < gen.nextInt(100000) + 100000; i++) {
            char toInsert = (char) (gen.nextInt(75) + 48);
            list.add(toInsert);
            queue.enqueueFront(toInsert);
        }
        if (!queue.isEmpty()) {
        	
            scoreFront += 0.25;
        } else {
            System.out.println("Check isEmpty");
        }

        if (queue.getSize() == list.size()) {
            scoreFront += 0.25;
        } else {
            System.out.println("Queue Size is incorrect");
        }


        // dequeueFront all the elements
        int temp = queue.getSize();
        for (int i = 0; i < temp; i++) {
            try {
                assertEquals("Check enqueueFront and dequeueFront\n", list.remove(list.size() - 1), queue.dequeueFront());
                if (queue.getBack() == queue.getQueueLength()) {
                	System.out.printf("front: %d back: %d size: %d length: %d\n", queue.getFront(), queue.getBack(), queue.getSize(), queue.getQueueLength());
                }
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }
        scoreFront += 1;


        if (queue.isEmpty()) {
            scoreFront += 0.25;
        } else {
            System.out.println("Check isEmpty after a series of dequeueFront; your size " + queue.getSize());
        }

        if (queue.getQueueLength() == 7) {
            scoreFront += 0.25;
        } else {
            System.out.println("Check you resize\n" + "your queue length = " + queue.getQueueLength() + ";\nshould be 7");
        }


        //enqueueBack random items
        list = new ArrayList<>();
        for (int i = 0; i < gen.nextInt(100000) + 100000; i++) {
            char toInsert = (char) (gen.nextInt(75) + 48);
            list.add(toInsert);
            if (queue.getBack() == queue.getQueueLength()) {
            	System.out.printf("front: %d back: %d size: %d length: %d\n", queue.getFront(), queue.getBack(), queue.getSize(), queue.getQueueLength(), toInsert);
            }
            queue.enqueueBack(toInsert);
        }
        if (!queue.isEmpty()) {
            scoreBack += 0.25;
        } else {
            System.out.println("Check is Empty");
        }

        if (queue.getSize() == list.size()) {
            scoreBack += 0.25;
        } else {
            System.out.println("Sizes do not match");
        }


        // dequeueBack all the elements
        temp = list.size();
        for (int i = 0; i < temp; i++) {
            try {
                assertEquals("Check enqueueBack and dequeueBack\n", list.remove(list.size() - 1), queue.dequeueBack());
            } catch (Exception e) {
            	fail(e.getMessage());
            }
        }
        scoreBack += 1;

        if (queue.isEmpty() && list.size() == 0) {
            scoreBack += 0.25;
        } else {
            System.out.println("Check isEmpty after a series of dequeueBack");
        }

        if (queue.getQueueLength() == 7) {
            scoreBack += 0.25;
        } else {
            System.out.println("Check you resize:\n" + "\tyour queue length = " + queue.getQueueLength() + ";\n\tshould be 7");
        }


        totalScore += (scoreBack + scoreFront + scorePeek);
        assertEquals("test2 failed\n", 5.0, scoreBack + scoreFront + scorePeek, 0.001);
    }


    @Test
    public void test3() {
        // random enqueues and dequeues
        init_random();
        for (int i = 0; i < gen.nextInt(1000000) + 500; i++) {
            ArrayList<Character> list = new ArrayList<>();
            CyclicDoubleQueue<Character> queue = new CyclicDoubleQueue<>();
            testRandomized(list, queue);
            int size = queue.getSize();
            for (int j = 0; j < size; j++) {
                // compare the list and the queue
                try {
                    assertEquals("Queue is incorrect at index: " + j, list.get(j), queue.dequeueFront());
                } catch (Exception e) {
                    fail(e.getMessage());
                }

            }

        }

    }


    @Test(timeout = 1000)
    public void test4() {
        init_random();
        double scorePeek = 0.0;
        double scoreFront = 0.0;
        double scoreBack = 0.0;
        ArrayList<Character> list = new ArrayList<>();
        CyclicDoubleQueue<Character> queue = new CyclicDoubleQueue<>();

        // check peek back and front
        for (int i = 0; i < gen.nextInt(100000) + 100000; i++) {
            char toInsert = (char) (gen.nextInt(75) + 48);
            list.add(toInsert);
            queue.enqueueFront(toInsert);
        }
        try {
            assertEquals("Check PeekFront\n", list.remove(list.size() - 1), queue.peekFront());
            assertEquals("PeekBack is wrong\n", list.remove(0), queue.peekBack());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        scorePeek += 1.0;


        //enqueueBack random items
        list = new ArrayList<>();
        queue = new CyclicDoubleQueue<>();
        for (int i = 0; i < gen.nextInt(100000) + 10000; i++) {
            char toInsert = (char) (gen.nextInt(75) + 48);
            list.add(toInsert);
            queue.enqueueBack(toInsert);
        }
        if (!queue.isEmpty()) {
            scoreFront += 0.25;
        } else {
            System.out.println("Check isEmpty");
        }

        if (queue.getSize() == list.size()) {
            scoreFront += 0.25;
        } else {
            System.out.println("Queue Size is incorrect");
        }


        // dequeueFront all the elements
        int temp = queue.getSize();
        for (int i = 0; i < temp; i++) {
            try {
                assertEquals("Check enqueueBack and dequeueFront\n", list.remove(0), queue.dequeueFront());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }
        scoreFront += 1;


        if (queue.isEmpty()) {
            scoreFront += 0.25;
        } else {
            System.out.println("Check isEmpty after a series of dequeueFront; your size " + queue.getSize());
        }

        if (queue.getQueueLength() == 7) {
            scoreFront += 0.25;
        } else {
            System.out.println("Check you resize\n" + "your queue length = " + queue.getQueueLength() + ";\nshould be 7");
        }


        //enqueueFront random items
        list = new ArrayList<>();
        for (int i = 0; i < gen.nextInt(100000) + 10000; i++) {
            char toInsert = (char) (gen.nextInt(75) + 48);
            list.add(0, toInsert);
            queue.enqueueFront(toInsert);
        }
        if (!queue.isEmpty()) {
            scoreBack += 0.25;
        } else {
            System.out.println("Check isEmpty");
        }

        if (queue.getSize() == list.size()) {
            scoreBack += 0.25;
        } else {
            System.out.println("Sizes do not match");
        }


        // dequeueBack all the elements
        temp = list.size();
        for (int i = 0; i < temp; i++) {
            try {
                assertEquals("Check enqueueBack and dequeueBack\n", list.remove(list.size() - 1), queue.dequeueBack());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }
        scoreBack += 1;

        if (queue.isEmpty() && list.size() == 0) {
            scoreBack += 0.25;
        } else {
            System.out.println("Check isEmpty after a series of dequeueBack");
        }

        if (queue.getQueueLength() == 7) {
            scoreBack += 0.25;
        } else {
            System.out.println("Check you resize:\n" + "\tyour queue length = " + queue.getQueueLength() + ";\n\tshould be 7");
        }


        totalScore += (scoreBack + scoreFront + scorePeek);
        assertEquals("test2 failed\n", 5.0, scoreBack + scoreFront + scorePeek, 0.001);
    }


    @Test
    public void test5() {
        // random enqueues and dequeues
        init_random();
        for (int i = 0; i < gen.nextInt(1000000) + 500; i++) {
            ArrayList<Character> list = new ArrayList<>();
            CyclicDoubleQueue<Character> queue = new CyclicDoubleQueue<>(10, 4, 0.5);
            testRandomized(list, queue);
            int size = queue.getSize();
            for (int j = 0; j < size; j++) {
                // compare the list and the queue
                try {
                    assertEquals("Queue is incorrect at index: " + j, list.get(j), queue.dequeueFront());
                } catch (Exception e) {
                    fail(e.getMessage());
                }

            }

        }

    }


    private void testRandomized(ArrayList<Character> list, CyclicDoubleQueue<Character> queue) {
        for (int j = 0; j < gen.nextInt(10000) + 4800; j++) {
            char toInsert = (char) (gen.nextInt(75) + 48);
            switch (gen.nextInt(4)) {
                case 0:
                    // enqueueFront
                    assertEquals("Queue Size is not correct\n", list.size(), queue.getSize());
                    list.add(0, toInsert);
                    queue.enqueueFront(toInsert);
                    try {
                        assertEquals("Error in enqueueFront/peekFront", list.get(0), queue.peekFront());
                    } catch (Exception e) {
                        fail(e.getMessage());
                    }
                    break;
                case 1:
                    // dequeueFront
                    assertEquals("Queue Size is not correct\n", list.size(), queue.getSize());
                    if (queue.getSize() > 0) {
                        try {
                            assertEquals("Error in dequeueFront/peekFront", list.get(0), queue.peekFront());
                            list.remove(0);
                            queue.dequeueFront();
                        } catch (Exception e) {
                            fail(e.getMessage());
                        }
                    }
                    break;
                case 2:
                    // enqueueBack
                    assertEquals("Queue Size is not correct\n", list.size(), queue.getSize());
                    list.add(toInsert);
                    queue.enqueueBack(toInsert);
                    try {
                        assertEquals("Error in enqueueBack/PeekBack", list.get(list.size() - 1), queue.peekBack());
                    } catch (Exception e) {
                        fail(e.getMessage());
                    }
                    break;
                case 3:
                    // dequeueBack
                    assertEquals("Queue Size is not correct\n", list.size(), queue.getSize());
                    if (queue.getSize() > 0) {
                        try {
                            assertEquals("Error in dequeueBack/peekBack", list.get(list.size() - 1), queue.peekBack());
                            list.remove(list.size() - 1);
                            queue.dequeueBack();
                        } catch (Exception e) {
                            fail(e.getMessage());
                        }
                    }
                    break;
            }
            assertEquals("Queue Size is not correct\n", list.size(), queue.getSize());
        }
    }


}