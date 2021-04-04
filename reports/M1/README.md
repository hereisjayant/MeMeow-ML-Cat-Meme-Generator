# Milestone M1 report

## Current State of the project

### App Development
The basic skeleton of the app is ready, with coupled activities. For now, it’s just basic functionality. We have not gone overboard with aesthetics since we’re still at an early stage in the project. We have a Main Activity that links us to 2 other activities, one for selecting the image source and the other to enter text for the meme caption. The Activity for selecting the image has an ‘EditText’ element with an where the user can enter the meme caption, and it has an image element that displays a sample meme to test the layout. A clearer layout could be designed using a Bottom Navigation Activity, which would serve the purpose of navigating the user through different input methods to select the source for building the meme. More interactive features and aesthetics will be added to the app before Milestone 3, if all goes as planned.

### Image Classification
The dataset and model for the image classification task is ready. The accuracy for classifying the emotion of a cat picture is around 65%. We have created a transfer learning class that takes all adjustable parameters as its inputs so that it is easier and more efficient for us to test different combinations of learning rate, schedulers, and optimizers, etc. Currently, we are using a similar procedure to that of Assignment 2. The key difference is that we are using four labels (since we have four classes for our classification model): angry (0), happy (1), sad (2), sleepy (3). Aside from that, we are planning on using Cross Entropy Loss and the SGD optimizer, though we may experiment with other optimizers to attain a higher accuracy and lower loss. We are also planning to use some image transformations (for example RandomGrayscale, RandomHorizontalFlip) on our current dataset to obtain more data if the overall accuracy is not ideal.

## Feature changes to the proposal
We plan on using Android Studio IDE for the development of our app instead of Kivy, because it allows for easier integration and better performance using dedicated tools for mobile development like PyTorch Mobile. The IDE also provides us with tools for Debugging the App in real time which is a huge plus.
We would also like to change our dino to Velociraptor as it's cooler. :)

## Current Challenges and Bottlenecks
One technical challenge we have is that the parameters for the image classification model yield a satisfactory accuracy but have yet to be optimized. For the next milestone, we plan on experimenting with different values for the optimizer, scheduler, and learning rate to improve accuracy to be around 80%-90%. This will require us to modify our existing values for the parameters mentioned above and try different combinations to understand what works best with the dataset and classes we currently have.
In addition, while we have an action plan for both the sentimental analysis and image classification aspects of the project, we still need to decide on how to best integrate both parts in a cohesive manner. This is more of a design decision to be made rather than a technical challenge but is critical to the success of our project as it defines the outcomes of the machine learning model. While we have made changes to the proposal that anticipate this decision, it is worth explicitly identifying it as a potential challenge in the near future.

## Work Distribution
- Christy: Worked on building the image classification model with Fariha.
- Fariha: Worked on cleaning up two sub-datasets and collaborated with Christy on the Python script for the image classification model.
- Jayant: Worked on designing and implementing the layout of the app in collaboration with Savitoj, researched new layout options for the app.
- Savitoj: Generated and cleaned up two cat image datasets. Worked on designing the basic structure of the app in collaboration with Jayant.
