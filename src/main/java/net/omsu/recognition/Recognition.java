package net.omsu.recognition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.omsu.recognition.Numbers.COLUMNS;
import static net.omsu.recognition.Numbers.LINES;

/**
 *
 */
public class Recognition {

    private final int noisyPersent;
    private final int sizeOfDimention;
    private int[][] number;

    public Recognition(final int noisyPersent, final  int[][] number, final int sizeOfDimention) {
        this.noisyPersent = noisyPersent;
        this.sizeOfDimention = sizeOfDimention;

        this.number = new int[LINES][COLUMNS];
        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                this.number[i][j] = number[i][j];
            }
        }
    }

    public void makeNoisy() {

        Random rand = new Random();
        rand.setSeed(100);
        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((Math.abs(rand.nextInt()) % 100) < noisyPersent) {
                    number[i][j] = (number[i][j] + 1) % 2;
                }
            }
        }
    }

    public void recognize() {
        final List<int[][]> numbers = new ArrayList<int[][]>();
        numbers.add(Numbers.ONE);
        numbers.add(Numbers.TWO);
        numbers.add(Numbers.THREE);

        double distance = 0;
        double min = 10000;
        int resultNumber = -1;
        for (int n = 0; n < numbers.size(); n++) {
            int[][] value = numbers.get(n);

            for (int i = 0; i < LINES; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    distance += Math.pow(Math.abs(number[i][j] - value[i][j]), sizeOfDimention);
                }
            }
            distance = Math.pow(distance, 1.0 / sizeOfDimention);

            if (distance < min) {
                min = (int)distance;
                resultNumber = n;
            }
            distance = 0;
        }

        number = numbers.get(resultNumber);
    }

    public void print() {
        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(number[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        Recognition recognition = new Recognition(50, Numbers.TWO, 1);
        recognition.print();
        recognition.makeNoisy();
        recognition.print();
        recognition.recognize();
        recognition.print();
    }

}
