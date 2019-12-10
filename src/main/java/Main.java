public class Main {

    public static void main(String[] args) {

        //DB CONNECTION URL
        String database = DBConfig.db_url;

        WorkoutDB workoutDB = new WorkoutDB(database);

        WorkoutManager gui = new WorkoutManager(workoutDB);

        //CalendarQuickstart.addEvent(event);





    }
}
