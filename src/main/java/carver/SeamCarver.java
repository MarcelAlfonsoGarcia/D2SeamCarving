package carver;

import imageWrapper.ImageWrapper;
import imageWrapper.Pixel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SeamCarver {

    public static List<BufferedImage> carve(boolean bHorizontalSeam, int nCarves, ImageWrapper imageWrap) {
        final int maxImages = 50;
        int saveImageEveryN = 1;
        if (nCarves > maxImages) {
            saveImageEveryN = (int) Math.floor((double) nCarves / (double) maxImages);
        }

        List<BufferedImage> images = new ArrayList<BufferedImage>();

        // add the initial image so we can see where the image started
        images.add(imageWrap.currentContentsAsImage());

        for (int i = 0; i < nCarves; ++i) {
            // first calculate the energy of each pixel in the image
            calculateImageEnergy(imageWrap);

            // Next find a seam
            int[] seamToRemove = findSeam(bHorizontalSeam, imageWrap);

            // Now remove the seam we found from the image
            if (bHorizontalSeam)
                imageWrap.removeHorizontalSeam(seamToRemove);
            else
                imageWrap.removeVerticalSeam(seamToRemove);

            if (i % saveImageEveryN == 0 || i == (nCarves - 1))
                images.add(imageWrap.currentContentsAsImage());
        }

        return images;
    }

    // iterates over every pixel in the image and calculates each pixel's energy
    public static void calculateImageEnergy(ImageWrapper imageWrap) {
        for (int y = 0; y < imageWrap.getHeight(); ++y) {
            for (int x = 0; x < imageWrap.getWidth(); ++x) {
                double pixelEnergy = calculatePixelEnergy(imageWrap, x, y);
                imageWrap.setEnergy(x, y, pixelEnergy);
            }
        }
    }

    // calculates energy of a pixel at a given x and y
    public static double calculatePixelEnergy(ImageWrapper imageWrap, int x, int y) {
        int height = imageWrap.getHeight() - 1;
        int width = imageWrap.getWidth() - 1;

        if (y > height || y < 0 || x > width || x < 0)
            throw new IllegalArgumentException("coordinates provided out of bounds for image!");

        //get pixel returns m_data[y][x] so use Princeton coordinates

        Pixel aboveP;
        Pixel belowP;

        Pixel rightP;
        Pixel leftP;

        //handles edge cases where pixel is at bottom or top of image
        if (y == height) {
            aboveP = imageWrap.getPixel(x, y - 1);
            belowP = imageWrap.getPixel(x, 0);
        } else if (y == 0) {
            aboveP = imageWrap.getPixel(x, height);
            belowP = imageWrap.getPixel(x, y + 1);
        }
        //handles case where pixel is somewhere between top and bottom;
        else {
            aboveP = imageWrap.getPixel(x, y - 1); //for energy of pixel (1,1), (1,0)
            belowP = imageWrap.getPixel(x, y + 1);//for energy of pixel (1,1), (1,2)
        }

        //handle edge cases where pixel is at far right or far left side of image
        if (x == width) {
            rightP = imageWrap.getPixel(x - 1, y);
            leftP = imageWrap.getPixel(0, y);
        } else if (x == 0) {
            leftP = imageWrap.getPixel(width, y);
            rightP = imageWrap.getPixel(x + 1, y);
        }
        //handles case where pixel is somewhere between left most and right most side of image;
        else {
            leftP = imageWrap.getPixel(x - 1, y); //for energy of pixel (1,1), (0,1)
            rightP = imageWrap.getPixel(x + 1, y); //for energy of pixel (1,1), (2,1)
        }

        int rX = Math.abs(rightP.r() - leftP.r());
        int gX = Math.abs(rightP.g() - leftP.g());
        int bX = Math.abs(rightP.b() - leftP.b());

        int xtot = rX + gX + bX;

        int rY = Math.abs(belowP.r() - aboveP.r());
        int gY = Math.abs(belowP.g() - aboveP.g());
        int bY = Math.abs(belowP.b() - aboveP.b());

        int ytot = rY + gY + bY;

        Double xSq = Math.pow((double) xtot, 2);
        Double ySq = Math.pow((double) ytot, 2);

        Double energy = Math.sqrt(xSq + ySq);

        return energy;
    }

    // finds the "shortest path" from the top to bottom or left to right of the images
    // using the pixels' energy as the weight for each step of the path
    public static int[] findSeam(boolean bHorizontalSeam, final ImageWrapper imageWrap) {
        double[][] energyForPath = new double[imageWrap.getHeight()][imageWrap.getWidth()];

        int[][] dirData = new int[imageWrap.getHeight()][imageWrap.getWidth()];

        int left = -1;
        int cntr = 0;
        int right = 1;

        int up = -1;
        int down = 1;

        if (bHorizontalSeam == true) {
            for (int x = imageWrap.getWidth() - 1; x >= 0; x--) {

                for (int y = 0; y < imageWrap.getHeight(); y++) {
                    if (x == imageWrap.getWidth() - 1) {
                        energyForPath[y][x] = imageWrap.getEnergy(x, y);
                    } else {
                        Double energyCntr = energyForPath[y + cntr][x + 1];

                        //handles cases with no "up" path because we're at the top of the image
                        if (y == 0) {
                            Double energyDown = energyForPath[y + down][x + 1];

                            if (energyDown < energyCntr)
                                dirData[y][x] = down; //-1

                            else
                                dirData[y][x] = cntr; // 0
                        }

                        //handles cases with no "down" path because we're at the bottom of the image
                        else if (y == imageWrap.getHeight() - 1) {
                            Double energyUp = energyForPath[y + up][x + 1];

                            if (energyUp < energyCntr)
                                dirData[y][x] = up; //+1

                            else
                                dirData[y][x] = cntr; //0
                        }

                        //handles cases where we're somewhere between the top and bottom of image
                        else {
                            Double energyUp = energyForPath[y + up][x + 1];
                            Double energyDown = energyForPath[y + down][x + 1];

                            boolean downWins;

                            if (energyDown < energyUp)
                                downWins = true;
                            else
                                downWins = false;

                            if (downWins == true) {
                                if (energyDown < energyCntr)
                                    dirData[y][x] = down; //-1
                                else
                                    dirData[y][x] = cntr; // 0
                            } else {
                                if (energyUp < energyCntr)
                                    dirData[y][x] = up; //+1
                                else
                                    dirData[y][x] = cntr; // 0
                            }

                        }
                        int direction = dirData[y][x];
                        energyForPath[y][x] = imageWrap.getEnergy(x, y) + energyForPath[y + direction][x + 1];
                    }

                }
            }
        } else {
            //creating minEnergyForPath array and directionData array
            for (int y = imageWrap.getHeight() - 1; y >= 0; y--) {

                for (int x = 0; x < imageWrap.getWidth(); x++) {
                    //initialize bottom row of minEnergyForPath with energy values;
                    if (y == imageWrap.getHeight() - 1) {
                        energyForPath[y][x] = imageWrap.getEnergy(x, y);
                    } else {
                        Double energyCntr = energyForPath[y + 1][x + cntr];

                        //handles case with no left path because we're at the left most side of image
                        if (x == 0) {
                            Double energyRight = energyForPath[y + 1][x + right];

                            if (energyRight < energyCntr)
                                dirData[y][x] = right;
                            else
                                dirData[y][x] = cntr;

                        }

                        //handles case with no right path because we're at the right most side of image
                        else if (x == imageWrap.getWidth() - 1) {
                            Double energyLeft = energyForPath[y + 1][x + left];

                            if (energyLeft < energyCntr)
                                dirData[y][x] = left;
                            else
                                dirData[y][x] = cntr;
                        }

                        //handles case with both left and right, somewhere in middle of image
                        else {
                            Double energyLeft = energyForPath[y + 1][x + left];
                            Double energyRight = energyForPath[y + 1][x + right];

                            boolean rightWins;

                            if (energyRight < energyLeft)
                                rightWins = true;
                            else
                                rightWins = false;


                            if (rightWins == true) {
                                if (energyRight < energyCntr)
                                    dirData[y][x] = right;
                                else
                                    dirData[y][x] = cntr;
                            } else {
                                if (energyLeft < energyCntr)
                                    dirData[y][x] = left;
                                else
                                    dirData[y][x] = cntr;
                            }
                        }

                        int direction = dirData[y][x];
                        energyForPath[y][x] = imageWrap.getEnergy(x, y) + energyForPath[y + 1][x + direction];
                    }
                }
            }
        }
        int[] seam = fillSeam(bHorizontalSeam, energyForPath, dirData, imageWrap);
        return seam;
    }

    private static int[] fillSeam(boolean bHorizontalSeam, double[][] energyPath, int[][] directions, final ImageWrapper imageWrap) {
        int[] seam;

        if (bHorizontalSeam == true) {
            seam = new int[imageWrap.getWidth()];

            double startPixelPathEnergy = energyPath[0][0];
            int startPixelIndex = 0;

            for (int y = 1; y < imageWrap.getHeight(); y++) {
                if (energyPath[y][0] < startPixelPathEnergy) {
                    startPixelPathEnergy = energyPath[y][0];
                    startPixelIndex = y;
                }
            }
            seam[0] = startPixelIndex;

            for (int i = 1; i < imageWrap.getWidth(); i++)
                seam[i] = seam[i - 1] + directions[i - 1][seam[i - 1]];

            return seam;

        }

        seam = new int[imageWrap.getHeight()];

        //find start pixel at top;
        double startPixelPathEnergy = energyPath[0][0];
        int startPixelIndex = 0;

        for (int x = 1; x < imageWrap.getWidth(); x++) {
            if (energyPath[0][x] < startPixelPathEnergy) {
                startPixelPathEnergy = energyPath[0][x];
                startPixelIndex = x;
            }

        }

        seam[0] = startPixelIndex;

        for (int i = 1; i < imageWrap.getHeight(); i++)
            seam[i] = seam[i - 1] + directions[i - 1][seam[i - 1]];

        return seam;
    }
}


