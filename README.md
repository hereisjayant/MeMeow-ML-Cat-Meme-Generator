# Project-team-rubeosaurus:
## Original Proposal
### Abstract
Team Rubeosaurus spent a month bringing to you MeMeow, an ML-based Cat Meme Generator app for Android. The application allows you to convert the image of a cat into a meme. Our Image Classifying model recognizes the sentiments of the cat in order to bring you an interesting meme. But not just that, we also allow you to make a meme from text (works well for people without cats). Our Text Sentiment Analysis model classifies your text into one of four sentiments - happy, sad, angry, and scared - and creates a cat meme based on that. Either way, you get cat memes and are guaranteed peak entertainment!

### ML models

For the image classification task, we'll be using ResNet18 to follow a very similar
approach to the one in Assignment 2. For the text classification part, we'll use torch
embeddings and classify text based on sentiments. We may have to merge similar
sentiments together but that's a design choice for later.

### Data / Sources

For the image classification task, we'll be constructing a dataset using Selenium.
Selenium is an easy way to extract images from google. We may have to manually
remove some 'bad' data. We expect we'll need about 300 images for every category
and augment if required. For the text classification task, we'll be using this dataset on
Kaggle : [https://www.kaggle.com/praveengovi/emotions-dataset-for-nlp](https://www.kaggle.com/praveengovi/emotions-dataset-for-nlp)

For the captions we have 2 datasets [here](https://www.kaggle.com/dylanwenzlau/imgflip-meme-text-samples-for-top-24-memes) and [here](https://www.kaggle.com/abhishtagatya/imgflipscraped-memes-caption-dataset), which we need to filter to fit our
project and classify using our model.

### Non-ML Component

For the Non-ML component, we will be using Android Studio. Android Studio is a free
and open source IDE developed by Google for developing Android mobile apps and
other Android-based multitouch application software with a natural user interface. It
allows for easy ML model integration and better performance using dedicated tools for
mobile development like PyTorch Mobile. The IDE also provides us with tools for
Debugging the App in real time which is a huge plus.

### Related Work

A google search for "cat meme generator" or "meme generator" shows me a bunch of
image editing tools. So, maybe this is the first ML based meme generator?

### Work Distribution

Since each problem has its own challenges, we feel it would be better if two people
work on a task at a time and then move on to the next. We will distribute duties in a way
that allows everyone to work in pairs on the four components :
   1. Image classifier
   2. Text Classifier
   3. Generating meme output
   4. Creating the user interface.

### Milestones

- Milestone 1 : Getting decent results for the Image classifier + Creating a basic app using
Android Studio
- Milestone 2 : Getting decent results for the Text Classifier and generating meme output
from input
- Milestone 3 : Getting somewhat functional user interface
- Milestone 4 : The complete project

### Risks / Backup

● There are no restrictions for text inputs. This means our classifier may not work
very well with the input if it has words outside our vocabulary. Anyway, we'll get
some meme output that'll make us happy.

● If the Android App does not seem to integrate well with the PyTorch model, we
can create a web-app.
