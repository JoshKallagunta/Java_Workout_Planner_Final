import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;


public class WorkoutManager extends JFrame {

    private JPanel MainJPanel;
    private JPanel AddNewWorkoutJPanel;
    private JTextField workoutNameTB;
    private JComboBox bodyPartTB;
    private JLabel numOfWeightTB;
    private JLabel dateLabel;
    private JTextField startTime;
    private JTextField endTime;
    private JButton saveButton;
    private JTable workoutShowTable;
    private JButton addToCalendarButton;
    private JComboBox workoutMovementsCB;
    private JSpinner dateStartSpinner;
    private JSpinner endDateSpinner;
    private JTextField weightTB;
    private JButton quitButton;
    private JButton deleteBTTN;

    //DB initlization
    private WorkoutDB workoutDB;

    //Setting up default table model for the JTABLE
    DefaultTableModel workoutTableModel = new DefaultTableModel();


    /**
     *
     * @param workoutDB
     */
    WorkoutManager(WorkoutDB workoutDB) {
        this.workoutDB = workoutDB;

        setContentPane(MainJPanel);
        setPreferredSize(new Dimension(900, 600));
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        //Configures both date spinners
        configureDateSpinner();
        configureEndDateSpinner();
        //Populates both CBs from lists in the model
        populateBodyPartCB();
        populateMovementCB();

        //Sets table model
        workoutShowTable.setModel(workoutTableModel);

        //Reloads the JTable when the GUI is opened
        populateJTable();


        /**
         * Action listener for the SaveButton on the GUI,
         * Checks for null values / shows helpful messages that say what is missing
         */
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Boolean field for checking if a name is already added
                boolean nameFound = false;

                //If empty, ask the user to populate it
                if (workoutNameTB.getText().isEmpty()) {

                    errorMessagePopUp("Please enter in a Workout Name");

                } else {
                        //Checks for duplicate name through method call
                        //If found, boolean will be true, will not add to DB
                        nameFound = checkForDupe();
                        System.out.println("Hit");


                }
                //If the name is NOT found, it will add it to the DB / Table
                if (!nameFound) {

                    //Calls addWorkout method, adds the input to the DB
                    addWorkout();

                    //Reloads the JTable with the new workout
                    populateJTable();
                }

            }
        });

        /**
         * Action listener for the Quit button on the GUI
         * Disposes of the GUI when the user clicks
         */
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                dispose();

            }
        });

        /**
         * Action listener for the AddToCalendar button on the GUI
         * Pushes the workout to Google Calendar through parmaters from Gui input
         */
        addToCalendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                addWorkoutToGoogleCalendar();
            }
        });

        /**
         * Action listener for the delete button
         * Calls the deleteWorkout method and deletes a selected row from the jtable
         */
        deleteBTTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Calls delete workout method
                deleteWorkout();

                //Reloads the JTable
                populateJTable();

            }
        });
    }



    /**
     * Method that checks for a duplicate entry in the DB
     * Asks the user to enter a Unique name
     */
    protected boolean checkForDupe() {
        //Gets all entries saved in the DB
        Vector<Vector> getAllWorkoutsVector = workoutDB.getAllWorkouts();

        String uniqueName = workoutNameTB.getText();

        uniqueName = uniqueName.trim();

        if (uniqueName.isEmpty() ) {
            return false;

        } else {
            //For loop that iterates trough the vector of saved workouts, element index = name field, goes down the rows (element i)
            for (int i = 0; i < getAllWorkoutsVector.size(); i++) {

                Vector dupeNameVector = getAllWorkoutsVector.elementAt(i);

                String testName = (String) dupeNameVector.elementAt(0);

                //If the element in the column name = the iteration, it is already in the DB
                //Asks user to input a unique name
                //Sets boolean to true
                if (testName.equalsIgnoreCase(uniqueName) ){
                    errorMessagePopUp("The Name is already added, please enter a unique name! ");

                    return true;
                }
            }

        }
        //Returns false if the name is unique
        return false;
    }

    /**
     * Method that gets the user input from the GUI and adds it to the DB
     */
    protected void addWorkout(){

        String workoutName = workoutNameTB.getText();
        //Rid of whitespace
        workoutName = workoutName.trim();
        String workoutBodyPart = bodyPartTB.getSelectedItem().toString();
        String movements = workoutMovementsCB.getSelectedItem().toString();
        int weight = Integer.parseInt(weightTB.getText());

        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(dateStartSpinner.getValue());
        String endDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(endDateSpinner.getValue());

        //Adds input into the workout model, will be used to add to the BD
        WorkoutModel workoutModel = new WorkoutModel(workoutName, workoutBodyPart, movements, weight, date, endDate);

        //Tries to add to the DB using the workout model
        try {

            workoutDB.addNewWorkout(workoutModel);

            //Catches any exception that may happen, prints the error
        } catch (Exception exe) {
            System.out.println("Error adding Workout to the DB" + exe);
        }

    }

    /**
     *Gets UI from the GUI and passes it to the myNewEvent method through paramaters
     */
    protected void addWorkoutToGoogleCalendar(){

        String workoutName = workoutNameTB.getText();
        //Rid of whitespace
        workoutName = workoutName.trim();
        String workoutBodyPart = bodyPartTB.getSelectedItem().toString();
        String movements = workoutMovementsCB.getSelectedItem().toString();
        int weight = Integer.parseInt(weightTB.getText());

        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(dateStartSpinner.getValue());
        String endDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(endDateSpinner.getValue());

        //Tries to send it to myNewEvent method
        try {

            CalendarQuickstart.myNewEvent(workoutName, workoutBodyPart, movements, weight, date, endDate);

            //Catches any Exception that rises and prints a message
        } catch (Exception exe) {
            System.out.println("Error adding Workout to the DB" + exe);
        }

    }


    /**
     *Deletes a workout from the JTable my getting the selected row
     * Uses Name as the identifier to delete
     */
    protected void deleteWorkout() {

        int row = workoutShowTable.getSelectedRow();

        if (row < 0) {
            System.out.println("message");
            return;
        }
        final int column = workoutShowTable.getSelectedColumn();

        Object obj = workoutShowTable.getValueAt(row,0);

        try {
            //Tries to delete the selected workout, since it's getting a value at row,0 , setting it .toString()
            //So the DB can delete the name
            workoutDB.DeleteWorkout(obj.toString());

            //Catches any Exception that rises and prints a message
        } catch (Exception exe) {

            System.out.println("Error deleting the workout" + exe);
        }


    }



    /**
     * Vector that sets the column names for the JTable
     * @return - returns the column names to be called in the populateJTable method
     */
    protected static Vector<String> getColumnNames(){

        Vector<String> columnNames  = new Vector<>();

        columnNames.add("Workout Name");
        columnNames.add("Bodypart");
        columnNames.add("Movements");
        columnNames.add("Weight");
        columnNames.add("Start Date / Time");
        columnNames.add("End Date / Time");

        return columnNames;

    }


    /**
     * Method that populates the JTable, gets all Workouts from the DB, adds it to the table model
     */
    protected void populateJTable() {

        Vector<Vector> workoutModelVector = workoutDB.getAllWorkouts();

        Vector workoutData = workoutModelVector;
        Vector getAllColumnNames = getColumnNames();

        //Sets the table model with column names and row names from DB
        workoutTableModel.setDataVector(workoutData, getAllColumnNames );

        }


    /**
     * Configures the  Start date/time spinner, lets the user edit what is in the spinner
     * uses DateEditor for the format so that it is yyyy-MM-dd, HH:mm:ss.
     * Example: 2019-12-12 01:30:00 (AM)
     * Example 2019-12-12 14:30:00 (PM)
     */
    private void configureDateSpinner() {

        SpinnerDateModel spinnerDateModel = new SpinnerDateModel(new Date(), new Date(0), new Date(30000000000000L), Calendar.DAY_OF_YEAR);
        dateStartSpinner.setModel(spinnerDateModel);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateStartSpinner, "yyyy-MM-dd HH:mm:ss");
        DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();

        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);

        dateStartSpinner.setEditor(editor);

    }

    /**
     * Configures the  End date/time spinner, lets the user edit what is in the spinner
     * uses DateEditor for the format so that it is yyyy-MM-dd, HH:mm:ss.
     * Example: 2019-12-12 01:30:00 (AM)
     * Example 2019-12-12 14:30:00 (PM)
     */
    private void configureEndDateSpinner(){
        SpinnerDateModel spinnerDateModel = new SpinnerDateModel(new Date(), new Date(0), new Date(3000000000000L), Calendar.DAY_OF_YEAR);
        endDateSpinner.setModel(spinnerDateModel);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd HH:mm:ss");
        DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();

        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);

        endDateSpinner.setEditor(editor);
    }


    /**
     * Populates the bodypart combobox from an array of bodyparts in the WorkoutModel class
     */
    private void populateBodyPartCB(){
        for (String bodyParts : WorkoutModel.bodyParts ) {

            bodyPartTB.addItem(bodyParts);

        }
    }

    /**
     * Populates the movement combobox from an array of movements in the WorkoutModel class
     */
    private void populateMovementCB() {
        for (String movements : WorkoutModel.movements ) {

            workoutMovementsCB.addItem(movements);
        }
    }


    /**
     *
     * @param message
     */
    private void errorMessagePopUp(String message) {
        JOptionPane.showMessageDialog(WorkoutManager.this, message, "Error" , JOptionPane.ERROR_MESSAGE);
    }

}

