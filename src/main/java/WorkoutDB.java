import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


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


    public void ReloadTableData(WorkoutModel workoutModel) {

        try (Connection connection = DriverManager.getConnection(db_url);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT rowid, name, bodypart, movements, weight, dateinput, enddateinput") ) {

            ResultSet resultSet = preparedStatement.executeQuery();


        } catch (SQLException sqle) {
            System.out.println("Error getting data from the Workout table" + sqle);
        }

    }

    public void DeleteWorkout(WorkoutModel workoutModel) {

        try (Connection connection = DriverManager.getConnection(db_url);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM workout WHERE name = ?") ){

            preparedStatement.setString(1, workoutModel.getWorkoutName());

            preparedStatement.executeUpdate();

        } catch (SQLException sqle){
            System.out.println("Error deleting Product: " + sqle);
        }


    }

    public void UpdateWorkout(WorkoutModel workoutModel) {

        try (Connection connection = DriverManager.getConnection(db_url);
             PreparedStatement update = connection.prepareStatement("UPDATE workout SET name = ?, bodypart = ?, movements = ?, weight = ?, dateinput = ?, enddateinput = ?, WHERE rowid = ? ") ){

            update.setString(1, workoutModel.getWorkoutName());
            update.setString(2, workoutModel.getWorkoutBodyPart());
            update.setString(3, workoutModel.getWorkoutMovements());
            update.setInt(4, workoutModel.getWorkoutWeight());
            update.setString(5, workoutModel.getWorkoutDate());
            update.setString(6, workoutModel.getStartTime());


            update.executeUpdate();

        } catch (SQLException sqle){
            System.out.println("Error deleting Product: " + sqle);
        }

    }

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


                //
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


        } catch (SQLException sqle) {
            throw new RuntimeException("Error getting all Workouts: " + sqle);

        }

        }



    }

