import java.io.*;
import java.util.Scanner;

/**
 * Created by Tim on 3/2/2016.
 */
public class Main {

    public static void main(String [] args) throws FileNotFoundException, ArrayIndexOutOfBoundsException {

        try {
            Scanner input = new Scanner(new File("Data25.txt"));
            int size = input.nextInt();
            int start = (input.nextInt() - 1);

            int table[][] = new int[size][size];
            int visited[] = new int[size];
            int shortestDistance[] = new int[size];
            int previous[] = new int[size];
            int min = 99999;
            int next = 0;

            for (int i = 0; i < size; ++i) {
                visited[i] = 0;
                previous[i] = 0;

                for (int j = 0; j < size; ++j) {
                    table[i][j] = input.nextInt();
                }
            }

            shortestDistance = table[start];
            shortestDistance[start] = 0;
            visited[start] = 1;

            for (int i = 0; i < size; ++i) {

                min = 99999;

                for (int j = 0; j < size; ++j) {
                    if (min > shortestDistance[j] && visited[j] != 1) {
                        min = shortestDistance[j];
                        next = j;
                    }
                }

                visited[next] = 1;

                for (int j = 0; j < size; ++j) {
                    if (shortestDistance[j] > (min + table[next][j])) {
                        shortestDistance[j] = (min + table[next][j]);
                        previous[j] = next;
                    }
                }
            }

            int j = 0;
            System.out.println("Shortest distance to every node starting from: " + (start + 1) + "\n");
            for (int i = 0; i < size; ++i) {

                if (shortestDistance[i] == 0)
                    System.out.println("Vertex-" + (start + 1) + ": Self");

                else {
                    System.out.print("Vertex-" + (i + 1) + ": " + "Distance " + shortestDistance[i]);

                    if (previous[i] == 0)
                        System.out.print("      Previous: " + (start + 1));

                    else
                        System.out.print("      Previous: " + (previous[i] + 1));

                    System.out.println();
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) { System.err.println("Data too large."); }
          catch (FileNotFoundException r) { System.out.println("Did not find file."); }
    }


}
