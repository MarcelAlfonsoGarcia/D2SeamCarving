package util;

import domain.Trio;

public class EnergyCalculator {

    // summed up energies array
    public static void sumArray(Trio[][] image, int rows, int cols) {

        // create array of energies
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                image[i][j].setEnergy(energy(image, i, j));
            }
        }

        // sum up energies
        for (int j = 1; j < rows; j++) {
            for (int i = 0; i < cols; i++) {

                // if you're at the leftmost column, you only consider the values directly above
                // and to the top right
                if (i == 0) {

                    // find the minimum energy and set the initial position of the smallest value
                    // to the same column
                    int minEnergy = Math.min(image[j - 1][i].getEnergy(), image[j - 1][i + 1].getEnergy());
                    int pos = i;

                    // find the exact column of the minimum value
                    if (minEnergy == image[j - 1][i].getEnergy()) {
                        pos = i;
                    } else {
                        pos = i + 1;
                    }

                    // create the association between the energy of the pixel and the position of
                    // the lowest
                    // energy pixel that leads to it
                    // System.out.println(i + " cols and  " + j + " rows");
                    image[j][i].setEnergy(image[j][i].getEnergy() + minEnergy);
                    image[j][i].setNext(pos);

                    // if you're at the rightmost column, you only consider the values directly
                    // above and to the top left
                } else if (i == cols - 1) {

                    // find the minimum energy and set the initial position of the smallest value
                    // to the same column
                    int minEnergy = Math.min(image[j - 1][i].getEnergy(), image[j - 1][i - 1].getEnergy());
                    int pos = i;

                    // find the exact column of the minimum value
                    if (minEnergy == image[j - 1][i].getEnergy()) {
                        pos = i;
                    } else {
                        pos = i - 1;
                    }

                    // create the association between the energy of the pixel and the position of
                    // the lowest
                    // energy pixel that leads to it
                    image[j][i].setEnergy(image[j][i].getEnergy() + minEnergy);
                    image[j][i].setNext(pos);

                } else {

                    // find the minimum energy and set the initial position of the smallest value
                    // to the same column
                    int minEnergy = Math.min(Math.min(image[j - 1][i].getEnergy(), image[j - 1][i - 1].getEnergy()),
                            image[j - 1][i + 1].getEnergy());
                    int pos = i;

                    // find the exact column of the minimum value
                    if (minEnergy == image[j - 1][i].getEnergy()) {
                        pos = i;
                    } else if (minEnergy == image[j - 1][i - 1].getEnergy()) {
                        pos = i - 1;
                    } else {
                        pos = i + 1;
                    }

                    // create the association between the energy of the pixel and the position of
                    // the lowest
                    // energy pixel that leads to it
                    image[j][i].setEnergy(image[j][i].getEnergy() + minEnergy);
                    image[j][i].setNext(pos);
                }
            }
        }
    }

    // find the energy of a pixel
    private static int energy(Trio[][] arr, int row, int col) {

        int[] yRgb = calculateRGBY(arr, row, col);
        int[] xRgb = calculateRGBX(arr, row, col);

        // values of RGB across x axis
        int redX = xRgb[0];
        int blueX = xRgb[1];
        int greenX = xRgb[2];

        // values of RGB across y axis
        int redY = yRgb[0];
        int blueY = yRgb[1];
        int greenY = yRgb[2];

        // find the change in x
        int changeX = (int) (Math.pow(redX, 2) + Math.pow(greenX, 2) + Math.pow(blueX, 2));

        // find the change in y
        int changeY = (int) (Math.pow(redY, 2) + Math.pow(greenY, 2) + Math.pow(blueY, 2));

        // returns energy of input
        return changeX + changeY;
    }

    private static int[] calculateRGBY(Trio[][] arr, int row, int col) {
        int[] yRgb = new int[3];

        if (row == arr.length - 1) {
            // if we are in last row, the bottom pixel is on same column, top row
            yRgb[0] = Math.abs(arr[row - 1][col].getColor().getRed());
            yRgb[1] = Math.abs(arr[row - 1][col].getColor().getGreen());
            yRgb[2] = Math.abs(arr[row - 1][col].getColor().getBlue());

        } else if (row == 0) {
            // if we are in first row, the top pixel is on same column, bottom row
            yRgb[0] = Math.abs(arr[row + 1][col].getColor().getRed());
            yRgb[1] = Math.abs(arr[row + 1][col].getColor().getGreen());
            yRgb[2] = Math.abs(arr[row + 1][col].getColor().getBlue());
        } else {
            yRgb[0] = Math.abs(arr[row - 1][col].getColor().getRed() - arr[row + 1][col].getColor().getRed());
            yRgb[1] = Math.abs(arr[row - 1][col].getColor().getGreen() - arr[row + 1][col].getColor().getGreen());
            yRgb[2] = Math.abs(arr[row - 1][col].getColor().getBlue() - arr[row + 1][col].getColor().getBlue());
        }

        return yRgb;
    }

    private static int[] calculateRGBX(Trio[][] arr, int row, int col) {
        int[] xRgb = new int[3];

        if (col == 0) {
            // if the pixel is in the first column, the left pixel is in the same row, last
            // column
            xRgb[0] = Math.abs(arr[row][col + 1].getColor().getRed());
            xRgb[1] = Math.abs(arr[row][col + 1].getColor().getGreen());
            xRgb[2] = Math.abs(arr[row][col + 1].getColor().getBlue());
        } else if (col == arr[row].length - 1) {
            // if the pixel is in the last column, the right pixel is in the same row, first
            // column
            xRgb[0] = Math.abs(arr[row][col - 1].getColor().getRed());
            xRgb[1] = Math.abs(arr[row][col - 1].getColor().getGreen());
            xRgb[2] = Math.abs(arr[row][col - 1].getColor().getBlue());
        } else {
            xRgb[0] = Math.abs(arr[row][col - 1].getColor().getRed() - arr[row][col + 1].getColor().getRed());
            xRgb[1] = Math.abs(arr[row][col - 1].getColor().getGreen() - arr[row][col + 1].getColor().getGreen());
            xRgb[2] = Math.abs(arr[row][col - 1].getColor().getBlue() - arr[row][col + 1].getColor().getBlue());
        }

        return xRgb;
    }

    // get column of the minimum value of array
    public static int getMinCol(Trio[] array) {
        int theMin = array[0].getEnergy();
        int index = 0;
        for (int i = 0; i < array.length; i++) {

            if (array[i] != null) {
                if (array[i].getEnergy() < theMin) {
                    theMin = array[i].getEnergy();
                    index = i;
                }
            }
        }
        return index;
    }
}
