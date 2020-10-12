# Cert It!

Cert It! is a web based and android based app that aims to provide and generate certificates over a range of many templates that can be chosen by the user. The user can enter his or her details
through a .csv or .xlsx file (containing data in a predefined format having multiple users) or they can list out their own requirements to generate a single certificate.


## Problem Statement

There are numerous companies and organizations out there that are providing certificates to their participants / winners. Sometimes , even educational organizations have to provide a load of 
generated certificates to their people. This process gets pretty hectic since its a very repetitive task. 

## Solution

Cert It! aims to solve this problem. We are providing an all round elucidation into this issue by providing an idea that automates these tasks & at the same time keep it user friendly. Through this application
we want to provide our users with 
 - Sample templates of our own on which they can choose and select the best possible fit for their organization and participants.
 - Allow the user to upload their own template and generate certificates.
 - Allow the user to upload a snapshot of the handwritten data in a specified format through which our app will recognize the necessary details and map it out to generate a certificate.
 
 ## Tech Stack Used
 
 - Firebase
 - Firestore ML Kit
 - Firebase Storage
 - Android
 - AR – Unity & Vuforia
 - Flask
 - React.Js
 - Python
 - Java

 ## Sample Templates
 
 We have created sample templates for our application a few of them are here - 
 
 <img src="https://github.com/newb-dev-1008/Certificate-Generator/blob/master/final_images/Certificate_Template1.png" width="750" height="400">
 <img src="https://github.com/newb-dev-1008/Certificate-Generator/blob/master/final_images/Certificate_Template2.png" width="750" height="400">


 ## Web App
 
 React.js alongside Flask to create it. The backend consists of all our generating and serving APIs. It was deployed using Heroku with Amazon S3 storage bucket to store the certificates. One standout feature about our project is the fact that the user has also been given an option to create a template in our web app. It has a wide number of varieties like . After that , he can choose a .csv file to upload (in a certain format) wherein after preprocessing , he will receive a .zip file containing all the png images of the certificate.
 
 ### Sample Images of Our Web App
 <img src="https://github.com/newb-dev-1008/Certificate-Generator/blob/master/final_images/WebApp8.png" width="750" height="400">
 <img src="https://github.com/newb-dev-1008/Certificate-Generator/blob/master/final_images/WebApp5.png" width="750" height="400">
 
   
## Android App
Here the user has the option to upload his data in 3 different ways.
Firstly, he can enter details for one single certificate , then he can enter a .csv file containing multiple entries.

Lastly he is allowed to take a photo of the spreadsheet in the preferred format , and using CameraX API after which, with the help of Firebase's ML Kit TextRecognizer, the text data will be read, recognized, parsed and processed; post which a certificate will be mapped and given.

 
 ### Sample Images of Our Android App
 
 <img src="https://github.com/newb-dev-1008/Certificate-Generator/blob/master/final_images/AppHomescreen.jpeg" width="250" height="400">
 <img src="https://github.com/newb-dev-1008/Certificate-Generator/blob/master/final_images/AppDetails1.jpeg" width="250" height="400">
 
 ## Additional Features
 
 ## Voice Assistant Bot
 
 We have also devised another tool for generating certificates. The Google assistant bot was built using Google's dialogflow. The handling of data and API requests are done using webhooks. the user gives all the necessary details in one sentence. And then accordingly , our program maps the necessary keywords and generates a certificate.

 <img src="https://github.com/newb-dev-1008/Certificate-Generator/blob/master/final_images/VoiceAssistant2.jpeg" width="250" height="400">

 ## AR Certificate
 
 This aspect of our project on certificate generation has been the creation of an AR model where the user can see his or her certificate (once generated) The AR certificate is made using Unity and Vuforia. The image(certificate) is loaded on the cover of a book using runtime image targets.


 <img src="https://github.com/newb-dev-1008/Certificate-Generator/blob/master/final_images/AR1.jpeg" width="250" height="400">

