/**
 * Created by Tim on 2/13/2017.
 */

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String [] args){

        Scanner in = new Scanner(System.in);
        int toBinary;
        int[] binary;
        int MAX = 10;
        binary = new int[MAX];
        int count = 0;

        System.out.println("Enter a number to change to binary: ");
        toBinary = in.nextInt();

        do{
            if (toBinary%2 == 1) {
                binary[count] = 1;
                ++count;
            }
            else {
                binary[count] = 0;
                ++count;
            }
            toBinary = toBinary/2;
        } while (toBinary != 0);

        int i = MAX - 1;
        while (binary[i] == 0) {
            --i;
        }

        for (; i > 0; --i) {
            System.out.print(binary[i]);
        }
    }
}
