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
| 1 | A user tries to view friend's Exercise without being logged-in | The system lets the user know that he/she needs to login in order to view friend's specific Exercise |

## Adding Friend to Users Network

The following tests will be conducted in order to test the meet-picking tool:

| Number | Action | Output |
| :---: | --- | --- |
| 1 | A user tries to Add an Friend without being logged-in | The system lets the user know that he/she needs to login in order to Add an Friend |
| 2 | A logged-in user tries to enter an invalid username| The system lets the user know that the username is not exist. |
| 3 | A logged-in user, tries to add himself | The system lets the user know that his/her action is invalid |
| 4 | A logged-in user, tries to add an user that already in his network | The system informs the users that her/his action is invalid |
