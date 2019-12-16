import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class WorkoutDB {

    private String db_url;

    /**
     *Creates the workout table if it does not exist
     * Name is unique , the rest is optional
     * Validation for having a unique name is done in a seperate method
     * @param database
     */
    WorkoutDB(String database) {

        this.db_url = database;

        //Creating the workout table in the DB
        try (Connection connection = DriverManager.getConnection(db_url);
             Statement statement = connection.createStatement() ) {

            String createWorkoutTable = "CREATE TABLE IF NOT EXISTS workout (name text UNIQUE, bodypart text, movements text," +
                    " weight integer, dateinput text, enddateinput text)";
            statement.execute(createWorkoutTable);


            //Catching the SQL Exception, printing the error with creating the table
        } catch (SQLException sqle) {
            throw new RuntimeException("Error Creating Workout DB" , sqle);
        }



    }

    /**
     *Adds a workout from UI
     * Uses workout model as the parameters to pass the values into the DB
     * @param workoutModel
     */
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


    /**
     *Deletes an entry from the DB where name = the UI
     * @param WorkoutName
     */
    public void DeleteWorkout(String WorkoutName) {

        try (Connection connection = DriverManager.getConnection(db_url);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM workout WHERE name LIKE (?)") ){

            preparedStatement.setString(1, WorkoutName);

            preparedStatement.executeUpdate();

        } catch (SQLException sqle){
            System.out.println("Error deleting Product: " + sqle);
        }

    }

    /**
     * A Vector of vectors that contain all the workouts that were saved to the DB
     * @return a vector of workouts
     */
    public Vector<Vector> getAllWorkouts() {

        try (Connection connection = DriverManager.getConnection(db_url);
             Statement statement = connection.createStatement() ) {

            ResultSet workoutRS = statement.executeQuery("SELECT * FROM workout");

            Vector<Vector> workoutModelVector = new Vector<>();

            while (workoutRS.next() ) {

                String name = workoutRS.getString("name");
                String bodypart = workoutRS.getString("bodypart");
                String movements = workoutRS.getString("movements");
                int weight = workoutRS.getInt("weight");
                String dateInput = workoutRS.getString("dateinput");
                String endDateInput = workoutRS.getString("enddateinput");


                //New vector that contains all the workouts that were saved to the DB
                Vector vector = new Vector();

                vector.add(name);
                vector.add(bodypart);
                vector.add(movements);
                vector.add(weight);
                vector.add(dateInput);
                vector.add(endDateInput);

                workoutModelVector.add(vector);

            }

            return workoutModelVector;

            //Catches SQL exception, prints a message with the error
        } catch (SQLException sqle) {
            throw new RuntimeException("Error getting all Workouts: " + sqle);

        }

        }

    }

