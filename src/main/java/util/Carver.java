package util;

import domain.Trio;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static util.EnergyCalculator.getMinCol;
import static util.EnergyCalculator.sumArray;

public class Carver {

    public static void doVerticalCarve(String filename) {
        File file = new File(filename);

        try {
            BufferedImage imageSource = ImageIO.read(file);
            int rows = imageSource.getHeight();
            int cols = imageSource.getWidth();

            System.out.printf("%d by %d pixels\n", rows, cols);

            /* Read into an array of rgb values */
            Trio[][] image = new Trio[rows][cols];
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    int color = imageSource.getRGB(i, j);
                    int red = (color >> 16) & 0xff;
                    int green = (color >> 8) & 0xff;
                    int blue = (color) & 0xff;
                    image[j][i] = new Trio(new Color(red, green, blue), 0, 0);
                }
            }

            // create the array of sum of energies and carve it
            sumArray(image, rows, cols);
            for (int i = 0; i < 500; i++) {
                carve(image, rows - 1, getMinCol(image[rows - 1]));

                // recalculate energies and seams
                sumArray(image, rows, cols - i - 1);
            }

            System.out.println(rows);
            /* Save as new image where g values set to 0 */
            BufferedImage imageNew = new BufferedImage(image[rows - 1].length, rows, BufferedImage.TYPE_INT_RGB);
            File fileNew = new File("./treeCarved500.jpg");
            for (int i = 0; i < image[0].length; i++) {
                for (int j = 0; j < rows; j++) {
                    int r = image[j][i].getColor().getRed();
                    int g = image[j][i].getColor().getGreen();
                    int b = image[j][i].getColor().getBlue();
                    int col = (r << 16) | (g << 8) | b;
                    imageNew.setRGB(i, j, col);
                }
            }
            System.out.println(imageNew.getHeight());
            ImageIO.write(imageNew, "JPEG", fileNew);
        } catch (IOException e) {
            System.out.println("Errored Out big time");
        }
    }

    public static void doHorizontalCarve(String filename) {
        File file = new File(filename);

        try {
            BufferedImage imageSource = ImageIO.read(file);
            int rows = imageSource.getHeight();
            int cols = imageSource.getWidth();

            System.out.printf("%d by %d pixels\n", rows, cols);

            /* Read into an array of rgb values */
            Trio[][] image = new Trio[rows][cols];
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    int color = imageSource.getRGB(i, j);
                    int red = (color >> 16) & 0xff;
                    int green = (color >> 8) & 0xff;
                    int blue = (color) & 0xff;
                    image[j][i] = new Trio(new Color(red, green, blue), 0, 0);
                }
            }

            // create the array of sum of energies and carve it
            sumArray(image, rows, cols);
            for (int i = 0; i < 500; i++) {
                carve(image, rows - 1, getMinCol(image[rows - 1]));

                // recalculate energies and seams
                sumArray(image, rows, cols - i - 1);
            }

            System.out.println(rows);
            /* Save as new image where g values set to 0 */
            BufferedImage imageNew = new BufferedImage(image[rows - 1].length, rows, BufferedImage.TYPE_INT_RGB);
            File fileNew = new File("./treeCarved500.jpg");
            for (int i = 0; i < image[0].length; i++) {
                for (int j = 0; j < rows; j++) {
                    int r = image[j][i].getColor().getRed();
                    int g = image[j][i].getColor().getGreen();
                    int b = image[j][i].getColor().getBlue();
                    int col = (r << 16) | (g << 8) | b;
                    imageNew.setRGB(i, j, col);
                }
            }
            System.out.println(imageNew.getHeight());
            ImageIO.write(imageNew, "JPEG", fileNew);
        } catch (IOException e) {
            System.out.println("Errored Out big time");
        }
    }

    // column and row of lowest energy pixel
    public static void carve(Trio[][] imageArray, int row, int col) {

        // if you're at the top row stop the recursion
        if (row == 0) {

            // create the new row and add all the elements of the previous row,
            // except for the element at the column to be carved
            Trio newArr[] = new Trio[imageArray[0].length - 1];

            for (int i = 0; i < imageArray[0].length - 1; i++) {

                if (i < col) {
                    newArr[i] = imageArray[row][i];
                } else {
                    newArr[i] = imageArray[row][i + 1];
                }
            }

            imageArray[0] = newArr;

        } else {

            // recurse before you remove the element
            carve(imageArray, row - 1, imageArray[row][col].getNext());

            // create the new row and add all the elements of the previous row,
            // except for the element at the column to be carved
            Trio newArr[] = new Trio[imageArray[row].length - 1];

            for (int i = 0; i < imageArray[row].length - 1; i++) {

                if (i < col) {
                    newArr[i] = imageArray[row][i];
                } else {
                    newArr[i] = imageArray[row][i + 1];
                }
            }

            imageArray[row] = newArr;
        }
    }
}
