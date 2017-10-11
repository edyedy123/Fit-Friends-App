# Test Plan

## Picking a Meeting

The following tests will be conducted in order to test the meet-picking tool:

| Number | Action | Output |
| :---: | --- | --- |
| 1 | A user tries to pick a meeting without being logged-in | The system lets the user know that he/she needs to login in order to pick an specific meeting |
| 2 | A logged-in user tries to pick a meeting that is no longer available | The system lets the user know that the chosen meeting is unavailable. |
| 3 | A logged-in user, from a group that hasn't yet picked a meeting, tries to pick a meeting that is currently available | The system updates the database with the new meeting. It sends an email to all members. The page refreshes and the meeting is shown in red. |
| 4 | A logged-in user, from a group that has already picked a meeting, tries to pick a meeting that is currently available | The system informs the users that he/she cannot pick a meeting because his/her group has already done so. |
