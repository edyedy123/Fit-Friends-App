# Group_24
## Group Members:
    ●   Cha Li
    ●   Eduardo Sorozabal
    ●   Riyad Almalki
    ●   Gerald Encabo
    
### Client information
For this project, We will be acting as our own client. 
##### Eduardo Sorozabal
I am a Software development student at Seneca @ york. I have programed in a variety of languages in my studies including C/C++, HTML/CSS/JavaScript, and Java. I enjoy Android app making the most because it uses Java, a language I am comfortable with, and it isn’t too hard to make a good app. I also have experience with photoshop.

##### Cha Li 
I am a Seneca student currently in the BSD program. I have learned a lot of different languages through my stay at seneca college that include C++, Java and more. Android is great platform which has a massive amount of users. I enjoy developing Android applications and sharing my experience with others.

##### Riyad Almalki
I'm a fourth year student at Seneca@York studying Software development. I have a two year diploma in Computer Science (Programming Technology), I also worked as an IT and Database Administrator for 6 years. I have worked with many programming languages during my career and in school including Visual Basic, C/C++, HTML/CSS/JavaScript/PHP and C#. Furthermore, I’m experienced in designing and managing databases using SQL Server and Oracle SQL. I have designed and developed Android applications using Android Studio.

##### Gerald Encabo
I am Gerald Encabo and currently studying at Seneca College @ York Campus as Software Developer. During my first and second year of studies, I have learned variety of programming languages such as HTML, CSS, JavaScript, Basic C, C++, Java, Bootstrap, ASP.net, JQuery, PHP and SQL. I love building websites and apps especially making Android applications because I enjoy coding using Java which is a language that is easy to learn and play around with!


## Project Overview

### Business Statement

#### Similar apps in the Google Play Store:

##### FitNotes:

FitNotes is an app that lets you log your workouts in a simple and easy to use way.

##### GymRun Fitness Notepad Diary:

GymRun Fitness lets you log your workouts with pictures of your workouts and other added features.

Fit Friends is similar to FitNotes and other apps like it in the functionality of logging workouts, but unlike these apps, it allows you to view your friend’s workouts in real time to improve your own workouts. The app market is currently missing an app that gives the user a simple way to access friend’s workouts and Fit Friends is the solution.

Fit Friends will allow the user to sign up for an account and log in. The user will be able to view all his workouts by date in the home screen. The user can the add workouts by selecting a workout and inputting the weight and reps for each set. When the user adds a workout it will be viewable by all his friends on Fit Friends. The user can add friends and view their workouts on the friends screen.

Fit Friends is ideal for any passionate gym goers who are constantly trying to improve and want feedback from their friends. No longer will you have to nag your friends into typing out their workouts on the group chat or over text, you will be able to quickly and effortlessly view their workouts yourself. This will help new gym goers learn new workouts from their friends without all the hassle of research or experienced gym goers to compete with their friends and try new workouts.

### Key System Requirements
In what follows, we present a list of important requirements that the system must have in order to solve the business problem:

#### General Requirements   
* It must be available 24/7 through Firebase.
* It must have offline capability.
* Should be accessible to most android devices.

#### Security Requirements
* Users must sign in before being able to access their information.
* Passwords and user information must be saved in a secure manner (Through Google Firebase).
* It should maintain user's sign in authentication, even if they switch to other views.
* User must register using valid email address.
* User must verify the email address.
* Password must be at least minimum length of 8 characters and maximum of 32 characters.
* Password must contain at least 1 lowercase and 1 uppercase letter.
#### Logical Requirements
* Only logged in users can view workouts.
* Only the original owner of the plan can make changes to the plan.
* Users should only be able to see workouts of friends that have accepted their friend request.
* Workouts must be able to be added, deleted and edited.
* Friend's workouts can be copied, modified and added to your own workout plan.
#### Communication Requirements
* Error handling should be handled through toasts with meaningful error messages whenever invalid data is entered. If there are restrictions, they should be written for the user to see.

## Technical Overview

This project consists of a **front-end** android application to allow users to log their workouts and view their friend’s workouts and a **back-end** Firebase server for data to be saved and used.

### Front-end

For this application we will use **Android Studio** to create the applications. All of the **Android Studio** applications contain 3 important source folders.

- Manifests folder must have an AndroidManifest.xml in its root directory. The AndroidManifest.xml file contains all the information that the system required before it can run the app's code.
- Java folder contains all the java source code for application. It also include Junit test code.
- Res folder contains all the non-code resources. This the place to edit layout, UI strings, Image and more.

It is the best IDE for creating **Android** applications for the following reasons:
- It has all the tools and widgets we need to create activities and layouts to form the user interface.  
- **Android Studio** is developed by **Google** and it has all the libraries and tools to develop the best Android Applications.
- It uses **Java** and supports live code update and rendering.
- Has a great error checking and code compilers.
- Has a fast emulator to test the application.
- It features intelligent code editor **IntelliJ IDEA** which make code writing and analyze more easy and accurate.
- Supports many operating systems; *Windows*, *Mac* and *Linux* which make it the ideal IDE for our team. 

### Back-end

The application will use **Firebase** as its server to save user information and workouts. We are using **Firebase** instead of creating our own **SQL server** for the following reasons:
- **Firebase** is integrated with **Android Studio** making it very easy to start using.
- Lots of documentation for **Firebase**, much less code than **SQL**
- The database is hosted by **Google**.
- Backed by **Google**, making it a stable and trustworthy service.
- Handles authentication simply and securely.
- Has a real-time database, information is synced in milliseconds.
- Has cloud storage to store and server files at **Google** scale.
- Has many features for testing apps such as crash reporting and performance monitoring.
- Hosting an app through Firebase has minimal setup.
- Easy access to data in database such as video, photo and files.
- For more information on **Firebase** visit https://firebase.google.com/

## System Components

### Workout Login Tool
Used to login to the account of the user by simply entering users authentic username and password.
### Add Exercise Tool
Used to create and add exercises to the main screen log by entering the weight and rep information for each set completed. 

## Use Cases

### Logging an Exercise
**Pre-requisites**: The user has an account in the system and has logged in.  
**Actor**: User.  
**Use Case Successful Post-conditions**: The user logs an exercise.  

1.	The user clicks on **Add Exercise**.
2.	The muscle group page is shown for the user to select the muscle group he will want to see exercises for. 
3.	The user selects a muscle group.
    * User clicks the system **back** button to go back to the main page to see logged exercises.
4.	The exercises page is shown for the chosen muscle group for the user to select an exercise. 
5.	The user selects an exercise.
    * User clicks the system **back** button to go back to the muscle group page.
6.	The exercise details page is shown for the user to add the weight and number of reps for each set.
7.	User enters weight and number of reps and clicks **Save**.
    * User clicks the system **back** button to go back to the exercises page.
8.	The system saves the set info and displays it as a list under the save button. The user may continue to add more sets until done. The user may also delete sets by long clicking them.

