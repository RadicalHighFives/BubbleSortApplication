package com.example.cpsc544;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest extends MainActivity{
    @Test
    public void addition_isCorrect() {

        MainActivity mainActivity = new MainActivity();
        //Create test cases here
        int[] test_one = {4,3,2}; // 2,3,4
        int[] test_actual = {2,3,4}; // 2,3,4

        int[] test_two = {3,3,2}; // 2,3,3
        int[] two_actual = {2,3,3};

        int[] test_three = {5,6,7}; // 5,6,7
        int[] three_actual = {5,6,7};
        assertArrayEquals(three_actual, mainActivity.BubbleSortTest(test_three));

        int[] test_four = {9,1,7,2,5}; // 5,6,7
        int[] four_actual = {1,2,5,7,9};


//        int [] t1 = mainActivity.BubbleSortTest(test_one);
//        for (int i: t1){
//            System.out.println(i);
//        }

//        System.out.println(mainActivity.BubbleSortTest(test_one));
//        assertArrayEquals(test_actual, mainActivity.BubbleSortTest(test_one));
//
//        System.out.println(mainActivity.BubbleSortTest(test_two));
//        assertArrayEquals(two_actual, mainActivity.BubbleSortTest(test_two));
//
//        System.out.println(mainActivity.BubbleSortTest(test_one));
        assertArrayEquals(three_actual, mainActivity.BubbleSortTest(test_three));
        int [] t4 = mainActivity.BubbleSortTest(test_four);
        for (int i: t4){
            System.out.print(i);
        }
        //System.out.println(mainActivity.BubbleSortTest(test_four));
        assertArrayEquals(four_actual, mainActivity.BubbleSortTest(test_four));

        System.out.println("Success you made it this far");




    }
}