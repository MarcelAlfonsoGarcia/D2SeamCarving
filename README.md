# SeamCarving

## Members

Marcel Alfonso Garcia, Joe Pokorny, Caleb Papay, Alex Whitehair

## Design Document

### What will you teach?

#### Learning Objectives:

* What is Seam Carving
* What are the applications/advantages
* What is energy
* How we find a seam

#### How:

Joe will put together a video

#### Outline of content:

* What is/Applications of Seam Carving
* How we interpreted an image as meaningful data
* Energy calculation
* Identifying the seam

#### How we will tie to our code:

We want to avoid putting blocks of code in, so we will mostly discuss concepts through graphics and images. However, we
might insert small snippets of pseudocode if adequate

#### Activity:

Currently not planning to include an activity

### What are you coding?

#### Goal

* We want to be able to visualize the process of seam carving through a simple GUI
* We intend to allow users to carve both vertical and horizontal seams
* We intend to allow users to choose how many seams to carve
* We intend to allow users to step through the process of carving a seam
* We intend to allow the users to animate the seam carving user

#### Design Big Picture

* A user interface
    * Will be implemented through a Java Swing component consisting of multiple panels each containing vital seam
      carving functionalities.
        * The Settings component will contain a button to select an image, a textbox to input how many carves to
          perform, and a selector for whether the carves will be horizontal or vertical
        * The Action component will contain buttons that can step through the images pertaining to the process of seam
          carving (i.e. the original image, the image with the seam highlighted, and the image without the seam, etc…)
        * The Image component will simply display whatever image we are currently viewing. It will update the display
          according to the presses of the buttons from the Action component
* A custom image data interface abstracting the representation and access of per-pixel image data
    * Facilitates storing and accessing of per-pixel data of image
    * Supports seam removal
    * Can modified from seams
* An image loading/saving class to handle creating our image class from the file system and writing our file class to
  the file system
* A seam carving component and accompanying tests
    * Implements energy calculations
    * Seam identification
    * Create “energy” image to visualize pixel energy
    * Create colored seam images to visualize seams that will be removed

#### What are we borrowing

We will be building all the code ourselves but will rely on the algorithm provided in the original problem statement for
guidance.

#### How we will do our testing

We will be performing automated testing which is facilitated by having the functionality separated from the visual
components. We will be using JUnit to test out each separate step of our process to make sure that all the
functionalities work well independently as well as together.

### Planning

#### Who’s doing what

* Marcel will take ownership of the GUI to make sure that everything is displayed correctly. He will coordinate with the
  rest to standardize the inputs and outputs. GUI work should be done by Feb 27. Marcel will also be available to help
  out with any logic and/or testing for the code.
* Caleb will implement energy calculations and seam identification, implementing tests (collaborate with Joe).
* Joe will create the presentation video which will be finished 2 days after the work has been completed as well as
  assist Caleb on his portion in any way he can.
* Alex will build the image data interface/class as well as code to load and save images to/from the file system by Mar
  3rd

## Test Plan

### Test Descriptions

* **calculateImageEnergy**
    * This will test the method that sets pixel Energy for all the pixels in an image
    * Ensures that every pixel is assigned the proper pixel energy for the whole image
    * Input: A simple 3x3 image with black pixels at determined locations
    * Output: values for energy of all pixels in image calculated by hand
* **calculatePixelEnergy**
    * This will test the method that calculates the pixel energy for each pixel in an image.
    * Ensures that for a given pixel, the correct energy value is calculated
    * Input: A simple 3x3 image with black pixels at determined locations
    * Output: values for the energy of a given pixel determined by hand
* **findSeam**
    * This will test the method that finds the optimal path of energy (seam) through the image
    * Ensures that the seam found is the path (horizontal or vertical) through the image that has the least total energy
    * Input: A simple 3x3 image with known pixel energy for all 9 pixels
    * Output: the vertical or horizontal series of pixels from top to bottom or left to right respectively (determined
      on paper) that has the lowest total energy
* **ImageWrapper.removeHorizontalSeam** and **removeVerticalSeam**
    * This test will verify the removeHorizontal/Vertical seam methods remove only the correct pixels, specified by a
      seam from an image
    * Input: a simple 3x3 image with known contents, various seams to remove
    * Output: a resulting image with the pixels removed specified by the seam

### User Actions:

* We expect the user to select a file, a number of seams to carve, and a seam direction before clicking _Submit_ in
  order to perform the carves.
    * If they don’t select a file, an exception will appear in the GUI notifying them
    * The program is set to not allow the user to select files not in the Image formats (png, jpeg, etc…)
    * If they don’t set a number of carves, an exception will appear in the GUI notifying them
    * One of the carve directions will be selected on startup and because they are radio buttons they will never be both
      null, but we will still have an error appear just in case (can’t trust users too much)
* They should then be able to click on _Next_ the next seam to be removed highlighted in red, and click again to see the
  image with the seam removed. This will be allowed until all steps are seen and the image is fully carved.
    * If no image has been submitted, an error will appear
    * Once the user reaches the end of the image carousel, nothing will happen
* By clicking _Prev_ they should be able to go backwards in the process.
    * If no image has been submitted, an error will appear
    * Once the user reaches the beginning of the image carousel, nothing will happen
* By clicking _Last_ they should be able to see the last image, i.e. the image after it has been carved
    * If no image has been submitted, an error will appear
* By clicking _Save_ they should be able to save the image they are currently viewing
    * If no image is being shown an error will appear
* Lastly, by clicking _Animate_ they will see the seams be removed at a 50ps rate as if they were clicking on _Next_.
  Once it reaches the end of the carousel, it will start again from the beginning. The user can stop the animation at
  any time by clicking on _Stop Animate_ which will appear only after the _Animate_ button is clicked and disappears
  once it has been clicked.
    * If no image has been submitted, an error will appear
    * We will have the animate automatically stop after a certain period of time in order to prevent having the infinite
      while loop run forever.
