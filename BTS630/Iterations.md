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
