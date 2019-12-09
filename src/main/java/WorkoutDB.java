import java.sql.*;


public class WorkoutDB {

    private String db_url;


    WorkoutDB(String database) {

        this.db_url = database;

        //Creating the workout table in the DB
        try (Connection connection = DriverManager.getConnection(db_url);
             Statement statement = connection.createStatement() ) {

            String createWorkoutTable = "CREATE TABLE IF NOT EXISTS workout (name text UNIQUE, bodypart text, movements text, weight integer, dateinput text, enddateinput text)";
            statement.execute(createWorkoutTable);


            //Catching the SQL Exception, printing the error with creating the table
        } catch (SQLException sqle) {
            throw new RuntimeException("Error Creating Workout DB" , sqle);
        }



    }


    public void addNewWorkout(WorkoutModel workoutModel) {

        try (Connection connection = DriverManager.getConnection(db_url);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO workout values (?, ?, ?, ?, ?, ?)")  ) {

            preparedStatement.setString(1, workoutModel.getWorkoutName());
            preparedStatement.setString(2, workoutModel.getWorkoutBodyPart());
            preparedStatement.setString(3, workoutModel.getWorkoutMovements());
            preparedStatement.setInt(4, workoutModel.getWorkoutWeight());
            preparedStatement.setString(5, workoutModel.getWorkoutDate());
            preparedStatement.setString(6, workoutModel.getStartTime());


            preparedStatement.executeUpdate();


        } catch (SQLException sqle) {
            System.out.println("Error adding to the Workout DB: " + sqle);
        }
    }





    }

