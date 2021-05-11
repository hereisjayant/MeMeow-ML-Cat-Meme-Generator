# Milestone 4 Project Report

## Current State of the project:

### Integration of Image Model:
Given the fact that the output text strings were all from the “happy” category, we debugged our code. We replaced “==” with “.equal()” operation on comparing strings, so that it is not comparing the hashcode of two strings which result in always falling into the default case.

After we fixed the bug, we found that our model tends to have a strong bias towards “angry”, which is not consistent with that on the notebook. We did some fine-tuning on the model, and managed to classify happy/sad images correctly, but the sleepy image is very likely to fall into the happy category and angry falls into sad. We thought this might be because the accuracy of our model is not very ideal so that it is not able to distinguish between these categories. Hence we narrowed down the number of categories to two (kept happy and sad). This results in the app to classify most images to sad. We then checked our input/output tensor in our app and compared it with those in the notebook. To fix the problem, we transformed the input tensor on both ends to make them consistent.

After many attempts to improve the accuracy of our app, we found out that the output memes would be more reasonable and interesting if we have two categories (happy and sad). We also recollected 300 hd real cat images for each class and raised the accuracy to 87%.

### App Fine-tuning:
- **Back Button**:
The problem of the back button in Image Upload activity was fixed. The user now could upload another image when going back to Image Upload activity directly from “Make Meme From Image”, instead of having to go back to the main activity and enter the Image Upload page from there. All we needed to do was destroy existing drawing caches.
- **Save Meme Button**:
A “save meme” button is added to both Make From Text and Make From Image activities to help the user easily save the memes.
- **Meme Layout**:
We changed the layout of the memes to better fit the width of the device, and enabled us to surpass the 45-character caption limit.
- **UI Appearance**:
We made some cool changes to the user interface, like changing the color of our logo,  and it looks better now :)


## Work Distribution:
*Christy*: Worked on debugging for the integration of our image classification model in collaboration with Fariha, Jayant, and Savitoj. Worked on fixing bugs in the memeFromImage file.

*Fariha*: Worked on debugging the integration of our image classification model in collaboration with Christy, Jayant, and Savitoj. Improved UI and helped implement added features for our app.

*Jayant*: Worked on fixing the integration of the image model with Savitoj thereby making the model work perfectly on all the images. Worked on training the image classification model with Christy, Savitoj and Fariha. Improved the App logo. Fixed the layout of the memes to better fit the width of the device, and enabled us to surpass the 45-character caption limit. Worked on making the back button work, UI stuff and general testing and debugging of the app.

*Savitoj*: Worked on fixing the integration of the image model with Jayant. Worked on making the back button work, some UI stuff and general testing and debugging of the app.
