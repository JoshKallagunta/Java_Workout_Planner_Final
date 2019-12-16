public class Main {

    public static void main(String[] args) {

        //DB CONNECTION URL
        String database = DBConfig.db_url;

        //Creates / initilizes the database for the application
        WorkoutDB workoutDB = new WorkoutDB(database);

        //Creates a new GUI with the DB parmater
        WorkoutManager gui = new WorkoutManager(workoutDB);


    }
}
