import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;



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
    private JButton newGuiBTTN;

    //DB initlization
    private WorkoutDB workoutDB;

    //
    DefaultTableModel workoutTableModel = new DefaultTableModel();



    WorkoutManager(WorkoutDB workoutDB) {

        this.workoutDB = workoutDB;


        setContentPane(MainJPanel);
        setPreferredSize(new Dimension(600, 600));
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



        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (workoutNameTB.getText().isEmpty() || weightTB.getText().isEmpty() ) {

                    MessageDialogPopUp("Please enter in a Workout Name and or Weight", "");

                }

                addWorkout();

                populateJTable();



            }
        });
        newGuiBTTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //Add Are you sure you want to leave

                //if or switch statement

                dispose();

                //new ViewSavedWorkouts();

            }
        });
        addToCalendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                addWorkoutToGoogleCalendar();
            }
        });
    }




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

        try {

            workoutDB.addNewWorkout(workoutModel);


        } catch (Exception exe) {
            System.out.println("Error adding Workout to the DB" + exe);
        }



    }

    protected void addWorkoutToGoogleCalendar(){

        String workoutName = workoutNameTB.getText();
        //Rid of whitespace
        workoutName = workoutName.trim();
        String workoutBodyPart = bodyPartTB.getSelectedItem().toString();
        String movements = workoutMovementsCB.getSelectedItem().toString();
        int weight = Integer.parseInt(weightTB.getText());

        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(dateStartSpinner.getValue());
        String endDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(endDateSpinner.getValue());


        //WorkoutModel workoutModel = new WorkoutModel(workoutName, workoutBodyPart, movements, weight, date, endDate);

        try {

            CalendarQuickstart.myNewEvent(workoutName, workoutBodyPart, movements, weight, date, endDate);



        } catch (Exception exe) {
            System.out.println("Error adding Workout to the DB" + exe);
        }

    }


    //
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


    //
    protected void populateJTable() {

        Vector<Vector> workoutModelVector = workoutDB.getAllWorkouts();

        Vector workoutData = workoutModelVector;
        Vector getAllColumnNames = getColumnNames();

        workoutTableModel.setDataVector(workoutData, getAllColumnNames );

        }



    //
    private void configureDateSpinner() {

        SpinnerDateModel spinnerDateModel = new SpinnerDateModel(new Date(), new Date(0), new Date(30000000000000L), Calendar.DAY_OF_YEAR);
        dateStartSpinner.setModel(spinnerDateModel);

        //
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateStartSpinner, "yyyy-MM-dd'T'HH:mm:ssXXX");
        DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();

        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);

        dateStartSpinner.setEditor(editor);

    }

    //
    private void configureEndDateSpinner(){
        SpinnerDateModel spinnerDateModel = new SpinnerDateModel(new Date(), new Date(0), new Date(3000000000000L), Calendar.DAY_OF_YEAR);
        endDateSpinner.setModel(spinnerDateModel);

        //
        JSpinner.DateEditor editor = new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd'T'HH:mm:ssXXX");
        DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();

        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);

        endDateSpinner.setEditor(editor);
    }


    //
    private void populateBodyPartCB(){
        for (String bodyParts : WorkoutModel.bodyParts ) {

            bodyPartTB.addItem(bodyParts);

        }
    }

    //
    private void populateMovementCB() {
        for (String movements : WorkoutModel.movements ) {

            workoutMovementsCB.addItem(movements);
        }
    }

    //
    String MessageDialogPopUp(String message, String initialValue) {
        return JOptionPane.showInputDialog(this, message, initialValue);
    }


}

