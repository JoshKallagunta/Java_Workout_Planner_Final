import java.util.Date;

public class WorkoutModel {

    /**
     * Arrays of bodyparts and movements
     */
    //Array that contains the Body Parts that workoutNames will be based off of
    //Called in GUI combo box
    public static String[] bodyParts = {"Chest", "Back", "Legs", "Full Body"};
    public static String[] movements = {"Bench Press", "Dead Lift", "Squat"};


    /**
     *Getters and setters of all the permaters used in the model
     * Getters are used in the DB when adding a workout
     * @return
     */
    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutBodyPart() {
        return workoutBodyPart;
    }

    public void setWorkoutBodyPart(String workoutBodyPart) {
        this.workoutBodyPart = workoutBodyPart;
    }

    public String getWorkoutMovements() {
        return workoutMovements;
    }

    public void setWorkoutMovements(String workoutMovements) {
        this.workoutMovements = workoutMovements;
    }


    public int getWorkoutWeight() {
        return workoutWeight;
    }

    public void setWorkoutWeight(int workoutWeight) {
        this.workoutWeight = workoutWeight;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    /**
     * Used for setting up getters and setters, also used for setting up model
     */
    String workoutName;

        String workoutBodyPart;

        String workoutMovements;

        int workoutWeight;

        String workoutDate;
        String startTime;


    /**
     * Constructor for the workout model, takes all values that are used in the GUI / DB
     * @param workoutName
     * @param workoutBodyPart
     * @param workoutMovements
     * @param workoutWeight
     * @param workoutDate
     * @param startTime
     */
        public WorkoutModel(String workoutName, String workoutBodyPart, String workoutMovements, int workoutWeight, String workoutDate, String startTime) {
            this.workoutName = workoutName;
            this.workoutBodyPart = workoutBodyPart;
            this.workoutMovements = workoutMovements;
            this.workoutWeight = workoutWeight;
            this.workoutDate = workoutDate;
            this.startTime = startTime;


        }

    /**
     *  String that displays when the user has added a workout to the DB
     * @return String Message
     */
        @Override
        public String toString() {

            return "You have created a new workout: " + workoutName + " with movements " + workoutBodyPart + " at " + workoutDate + " to " + startTime;
        }

}
