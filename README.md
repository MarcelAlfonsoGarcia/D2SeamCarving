# SeamCarving

## Members

Marcel Alfonso Garcia, Joe Pokorny, Caleb Papay, Alex Whitehair

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

