#Workout Planner# 

**Josh Kallagunta's project for ITEC 2454 Java Programming.**

- Allows a user to enter a workout that they've planned, with the name, workout details, and date/time.
- It has a database that saves the user's workouts to be viewed at a later time 
- It has a GUI and a Table for a workout entry and view 
- Users can add, delete, or create an event with the Google Calendar API to view on a calendar
- Helpful comments and user input handling so the App does'nt crash if there's bad data

**Known Bugs:**
- Users can enter string into the "weight" (int) textbox

**How to run:** 

- To get Google API functional, you will need to get your own credentials.json
- Go to this link: https://developers.google.com/calendar/quickstart/js 
- Click "Enable the Google Calendar API" and you will be provided with a credentials.json through a download 
- Take that file, and add it into Workout>src>main>resources (if there is no 'resources' folder, create one in the 'main' directory)
- (Pom.xml already has support for all libraries used in this App)
- (DB url will work with any computer)
- Finally: Run Main and start using the App

When you first run the App, and try to add something to your Google Calendar, you will be prompted with 
an authorization where the App asks for permission to view/edit/make changes to the calendar,
clicking allow will let you add a Workout to the calendar. 

(If you are having authorization issues, try deleting the "StoredCredential" file in the file "tokens" and try again)




