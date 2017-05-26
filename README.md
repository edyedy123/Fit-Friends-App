# Group_24
## Group Members:
    ●   Cha Li
    ●   Eduardo Sorozabal
    ●   Riyad Almalki
    ●   Gerald Encabo
    
### Client information
For this project, We will be acting as our own client. 
##### Eduardo Sorozabal
I am a Software development student at Seneca@york. I have programed in a variety of languages in my studies including C/C++, HTML/CSS/JavaScript, and Java. I enjoy android app making the most because it uses Java, a language I am comfortable with, and it isn’t too hard to make a good app. I also have experience with photoshop.

##### Cha Li 
I am a seneca student currently in BSD program. I have learned a lots of different language through the learning in seneca college that include C++, Java and more. Android is great platform which has massive of users. I enjoy develop Android application and share my experience with others.
##### Riyad Almalki
----
##### Gerald Encabo
I am Gerald Encabo and currently studying at Seneca College @ York Campus as Software Developer. During my first and second year of studies, I have learned variety of programming languages such as HTML, CSS, JavaScript, Basic C, C++, Java, Bootstrap, ASP.net, JQuery, PHP and SQL. I love building websites and apps especially making android application because I enjoy coding using Java which is a language that is easy to learn and play around it!


## Project Overview

### Business Statement
Fit Friends is an app that allows users to record their workouts with a simple and easy to use interface and be able to view their friend’s workouts.

#### Similar apps in the Google Play Store:

##### FitNotes:

FitNotes is an app that lets you log your workouts in a simple and easy to use way.

##### GymRun Fitness Notepad Diary:

GymRun Fitness lets you log your workouts with pictures of your workouts and other added features.

Fit Friends is similar to FitNotes and other apps like it in the functionality of logging workouts, but unlike these apps, it allows you to view your friend’s workouts in real time to improve your own workouts. The app market is currently missing an app that gives the user a simple way to access friend’s workouts and Fit Friends is the solution.

Fit Friends will allow the user to sign up for an account and log in. The user will be able to view all his workouts by date in the home screen. The user can the add workouts by selecting a workout and inputting the weight and reps for each set. When the user adds a workout it will be viewable by all his friends on Fit Friends. The user can add friends and view their workouts on the friends screen.

Fit Friends is ideal for any passionate gym goers who are constantly trying to improve and want feedback from their friends. No longer will you have to nag your friends into typing out their workouts on the group chat or over text, you will be able to quickly and effortlessly view their workouts yourself. This will help new gym goers learn new workouts from their friends without all the hassle of research or experienced gym goers to compete with their friends and try new workouts. Use Fit Friends online or offline, the app will update when you get internet again!

### Key System Requirements
In what follows, we present a list of important requirements that the system must have in order to solve the bussiness problem:

#### General Requirements   
* It must be availabe 24/7 through Firebase.
* It must have offline capability.
* Should be accesible to most android devices.

#### Security Requirements
* Users must sign in before being able to access their information.
* Passwords and user information must be saved in a secure manner(Through Google Firebase).
* It should mantain user's sign in authentication, even if they switch to other views.
* Username and password must be at least minimum length of 8 characters and maximum of 32 characters.
* Password must contain at least 1 lowercase and 1 uppercase letter.
#### Logical Requirements
* Users should only be able to see workouts of friends that have added accepted their friend request.
* Workouts must be able to be added, deleted and edited.
* App will prevent user from check friend's workout unless they were log in
#### Communication Requirements
* Error handling should be handled through toasts with meaningful error messages whenever invalid data is entered. If there are restrictions, they should be written for the user to see.
