# Test Plan

## Logging an Exercise

The following tests will be conducted in order to test the meet-picking tool:

| Number | Action | Output |
| :---: | --- | --- |
| 1 | A user tries to pick a logging an Exercise without being logged-in | The system lets the user know that he/she needs to login in order to log an specific Exercise |
| 2 | A logged-in user tries to enter an invalid exercise number, such as an negative number | The system lets the user know that the exercise number is invalid. |



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
