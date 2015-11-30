package net.omsu.recognition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.omsu.recognition.Numbers.COLUMNS;
import static net.omsu.recognition.Numbers.FIVE;
import static net.omsu.recognition.Numbers.FOUR;
import static net.omsu.recognition.Numbers.LINES;
import static net.omsu.recognition.Numbers.ONE;
import static net.omsu.recognition.Numbers.SIX;
import static net.omsu.recognition.Numbers.THREE;
import static net.omsu.recognition.Numbers.TWO;

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
        int numbers = (int) Math.floor((45 * noisyPersent) / 100);
        Random rand = new Random();

        for (int i = 0; i < numbers; i++) {
            int index = Math.abs(rand.nextInt() % 45);
            int line = (int) Math.floor(index / 5);
            int column = index % 5;
            number[line][column] = (number[line][column] + 1) % 2;
        }
    }

    public void recognize() {
        final List<int[][]> numbers = new ArrayList<int[][]>();
        numbers.add(ONE);
        numbers.add(TWO);
        numbers.add(THREE);
        numbers.add(FOUR);
        numbers.add(FIVE);
        numbers.add(SIX);

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
        Recognition recognition = new Recognition(45, FIVE, 1);
        recognition.print();
        recognition.makeNoisy();
        recognition.print();
        recognition.recognize();
        recognition.print();
    }

}
