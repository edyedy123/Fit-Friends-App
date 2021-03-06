# Database Design

In this document, we will present how all classes for our system, which are described in the domain_classes.md document, translate into a database design. At the bottom, we will show an image showcasing how different database tables are interconnected.

## User
* userName - Unique name that identifies the user in the system (String)
* FName - User's first name (String)
* LName - User's last name (String)
* Email - User's email address (EmailField)
* image - optional image for user profile (Image)
* dateCombo -Used for main screen looper to access user's logged workouts (DATE())
* workoutNumber - number of exercises  logged (Integer)
* friends  - Added friends list (ForeignKey->Friends)
* workouts - Logged workouts list (ForeignKey->Workouts)

## Workouts
* Name - Exercises name (String)
* Date - Date and time exercise was logged  (DATE())
* sets - List of sets (ForeignKey->Sets)

## Sets
* Weight -Weight used for set (double)
* Reps - number of repetitions for set (Integer)

## Friends
* userName - Unique name that identifies the user in the system. List of added friends. (String)
* mutualFriends - A friend users with whom this user is friends with (ForeignKey->MutualFriends)

## MutualFriends
* userName - Unique name that identifies the user in the system. List of friends of the user friends.(String)

## Firebase
* Representation of the database we are using.

## Interconnection between different tables

The figure below show how different tables are interconnected. 

![](/BTS530/Images/DatabaseDesign.png)
