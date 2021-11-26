/**
 * Created by Tim on 2/13/2017.
 */

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void BRGC(int n, int [] array, int MAX) {
        if (n == 0) {
            for (int j = MAX - 1; j >= n; --j) {
                System.out.print(array[j]);
            }
            System.out.println();
        }
        else {
            array[n - 1] = 0;
            BRGC(n - 1, array, MAX);
            array[n - 1] = 1;
            BRGCopposite(n - 1, array, MAX);
        }
    }

    public static void BRGCopposite(int n, int[] array, int MAX) {
        if (n == 0) {
            for (int j = MAX - 1; j >= n; --j) {
                System.out.print(array[j]);
            }
            System.out.println();
        }
        else {
            array[n - 1] = 0;
            BRGC(n - 1, array, MAX);
            array[n - 1] = 1;
            BRGCopposite(n - 1, array, MAX);

        }
    }

    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        int n;
        int [] array;

        System.out.print("Enter number of binary digits: ");
        n = in.nextInt();
        array = new int[n];

        BRGC(n, array, n);
    }
}
