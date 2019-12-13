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
    //private JTextField WeightTB;
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

    //
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

        //
        configureDateSpinner();
        configureEndDateSpinner();
        //
        populateBodyPartCB();
        populateMovementCB();

        //
        workoutShowTable.setModel(workoutTableModel);

        //
        populateJTable();


        /**
         * Action listener for the SaveButton on the GUI,
         * Checks for null values / shows helpful messages that say what is missing
         */
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean nameFound = false;

                if (workoutNameTB.getText().isEmpty()) {

                    MessageDialogPopUp("Please enter in a Workout Name", "");

                } else {

                        nameFound = checkForDupe();
                        System.out.println("Hit");


                }
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

                //Add Are you sure you want to leave

                //if or switch statement

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
        deleteBTTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                deleteWorkout();

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
//                Object dupeName = getAllWorkoutsVector.elementAt(0);
//                Object iteration = getAllWorkoutsVector.elementAt(i);

                Vector dupeNameVector = getAllWorkoutsVector.elementAt(i);

                String testName = (String) dupeNameVector.elementAt(0);

                //If the element in the column name = the iteration, it is already in the DB
                //Asks user to input a unique name
                if (testName.equalsIgnoreCase(uniqueName) ){
                    MessageDialogPopUp("The Name is already added, please enter a unique name! ", "");
                    return true;
                }
            }

        }

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


        WorkoutModel workoutModel = new WorkoutModel(workoutName, workoutBodyPart, movements, weight, date, endDate);

        //
        try {

            workoutDB.addNewWorkout(workoutModel);

            //Catches any exception that may happen, prints the error
        } catch (Exception exe) {
            System.out.println("Error adding Workout to the DB" + exe);
        }

    }

    /**
     *
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

        try {

            CalendarQuickstart.myNewEvent(workoutName, workoutBodyPart, movements, weight, date, endDate);

        } catch (Exception exe) {
            System.out.println("Error adding Workout to the DB" + exe);
        }

    }


    /**
     *
     */


    protected void deleteWorkout() {

        int row = workoutShowTable.getSelectedRow();

        if (row < 0) {
            System.out.println("message");
            return;
        }
        final int column = workoutShowTable.getSelectedColumn();

        Object obj = workoutShowTable.getValueAt(row,0);

        workoutDB.DeleteWorkout(obj.toString());

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

        //
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

        //
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
     * String used for user input validation, if something is NULL in a REQUIRED value,
     * this will make a popup with the message saying what is null and sets it to ""
     * @param message
     * @param initialValue
     * @return
     */
    String MessageDialogPopUp(String message, String initialValue) {
        return JOptionPane.showInputDialog(this, message, initialValue);
    }


}

