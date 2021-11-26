//Timmethy Tran, CS350 Term Project

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class Main {
    //Used in my first iteration of tested, a manually created array using an external data file
    public static int [] initializeArray(String filename) throws FileNotFoundException{
        Scanner input = new Scanner(new File(filename));
        int i = 0;

        int MAX = input.nextInt();
        int [] array = new int[MAX];

        while (input.hasNextInt()){
            array[i] = input.nextInt();
            ++i;
        }

        return array;
    }

    //Function responsible for iteratively searching using interpolation search.
    public static int interpolationSearch(int searchFor, int [] array) {
        int start, end, i = 0;
        start = 0;
        end = array.length - 1;
        int count = 0;

        //Check if initial value is the wanted number
        if (searchFor == array[start])
            return i;

        //Iterative loop for searching along with process counter
        while (searchFor >= array[start] && searchFor <= array[end] && start != end) {
            ++count;
            i = start + (searchFor - array[start])*(end - start)/(array[end] - array[start]);

            //When equation created a negative of the value
            if (i < 0)
                i = i * -1;

            //If value is higher than current position value, increase the start, otherwise decrease the end
            if (searchFor > array[i]) {
                start = i + 1;
            }
            else {
                end = i - 1;
            }

            //If matched, exit loop
            if (searchFor == array[i]) {
                System.out.println("Computations: " + count);
                return i + 1;
            }
        }

        //If no match found, exit with -1
        System.out.println("Computations: " + count);
        return -1;
    }

    //Function responsible for binary searching iteratively.
    public static int binarySearch(int searchFor, int [] array) {
        int start, end, i = 0;
        int count = 0;
        end = array.length - 1;
        start = 0;

        //Iterative loop to search along with process counter
        while (start <= end) {
            ++count;
            i = start + (end - start)/2;

            //If value is already present, exit with position
            if (array[i] == searchFor){
                System.out.println("Computations: " + count);
                return i + 1;
            }

            //If wanted value is higher than current position value, increase the min, otherwise decrease the max
            if (searchFor > array[i]) {
                start = i + 1;
            }
            else {
                end = i - 1;
            }
        }
        //If value not found, return with -1
        System.out.println("Computations: " + count);
        return -1;
    }

    //Function responsible for sequential search through iteration
    public static int sequentialSearch(int searchFor, int [] array) {
        int i = 0;
        int count = 0;

        //Iterative loop with process counter
        while (array[i] != searchFor){
            ++i;
            ++count;

            //If value is larger than current position value, return with -1
            if (array[i] > searchFor && i < array.length) {
                System.out.println("Computations: " + count);
                return -1;
            }

        }

        //If loop exists without return, value has been found, return value
        System.out.println("Computations: " + count);
        return i + 1;
    }

    public static void main(String args[]) throws FileNotFoundException {
        //Used to creating first test
        String filename = "numbersForArray.txt";
        int[] array = initializeArray(filename);
        int i = 0;

        //Used for creating second test using random numbers and evenly spaced numbers
        int k;
        int[] array2 = new int[50000];
        Random rand = new Random();
        for (k = 0; k < array2.length; ++k) {
            //array2[k] = k * 2;
            array2[k] = rand.nextInt(50000);
        }
        java.util.Arrays.sort(array2);


        //Loop to test all algorithms. Value k represents the wanted value in the searching algorithms. All returned
        //values have an extra +1 added to them to represent location of the wanted number in a 1 based list.
        for (k = 0; k < 50000; ++k) {
            System.out.println(k);
            i = sequentialSearch(k, array2);
            System.out.println(i);

            i = binarySearch(k, array2);
            System.out.println(i);

            i = interpolationSearch(k, array2);
            System.out.println(i);
        }
    }
}
