import java.util.Date;

public class WorkoutModel {

    //Array that contains the Body Parts that workoutNames will be based off of
    //Called in GUI combo box
    public static String[] bodyParts = {"Chest", "Back", "Legs", "Full Body"};
    public static String[] movements = {"Bench Press", "Dead Lift", "Squat"};




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



    String workoutName;

        String workoutBodyPart;

        String workoutMovements;

        int workoutWeight;

        String workoutDate;
        String startTime;



        public WorkoutModel(String workoutName, String workoutBodyPart, String workoutMovements, int workoutWeight, String workoutDate, String startTime) {
            this.workoutName = workoutName;
            this.workoutBodyPart = workoutBodyPart;
            this.workoutMovements = workoutMovements;
            this.workoutWeight = workoutWeight;
            this.workoutDate = workoutDate;
            this.startTime = startTime;


        }


        @Override
        public String toString() {

            return "You have created a new workout: " + workoutName + " with movements " + workoutBodyPart + " at " + workoutDate + " to " + startTime;
        }

}
