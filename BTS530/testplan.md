# Test Plan

## Logging an Exercise

The following tests will be conducted in order to test the meet-picking tool:

| Number | Action | Output |
| :---: | --- | --- |
| 1 | A user selects a body part category from the list. | The system displays the exercises saved for that category. |
| 2 | A user selects an exercise from the list. | The system displays the set input page. |
| 3 | A user enters valid weight and rep values and clicks save. | The system saves the values to the database and displays the inputted information in a list under the input fields. The input is updated as the user continues to add sets. |
| 4 | Enter an invalid weight or rep values, such as an negative number | The system restricts input below zero and only numbers may be inputted. |



## Viewing a Friend's Workout

The following tests will be conducted in order to test the meet-picking tool:

| Number | Action | Output |
| :---: | --- | --- |
| 1 | A user clicks on friends from the main page but has no added friends | The system shows a "no friends added" message in the background  of the empty list. |
| 2 | A user clicks on friends from the main page and has added friends | The system shows the list of added friends |
| 3 | A user selects an added friend. | The system shows the selected friend's workouts in a list sorted by date. |


## Adding Friend to Users Network

The following tests will be conducted in order to test the meet-picking tool:

| Number | Action | Output |
| :---: | --- | --- |
| 1 | A user enter friend's name in search text | The system shows a suggest of friends that have similar spelling usernames.  |
| 2 | A user click on desired friend | The system shows the profile account of desired friend.  |
| 3 | A user click add friend button | The system sends a friend request  |


## User Login

The following tests will be conducted in order to test the meet-picking tool:

| Number | Action | Output |
| :---: | --- | --- |
| 1 | A user opens the application. | The systems display login page with the option of “Need an Account?” button for non-members. |
| 2 | A user enters authenticated username and password. Then click login button | The system validate the username and password through matching against firebase system records. If the user information is valid, the system will redirect to user profile account, and if it invalid, the system display error message “ The username and password you entered did not match our records. Please double-check and try again”.  |
