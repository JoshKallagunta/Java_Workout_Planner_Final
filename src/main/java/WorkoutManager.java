import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;




public class WorkoutManager extends JFrame {


    private JPanel MainJPanel;
    private JPanel AddNewWorkoutJPanel;
    private JTextField workoutNameTB;
    private JComboBox bodyPartTB;
    private JTextField textField1;
    private JLabel numOfSetsTB;
    private JTextField textField2;
    private JLabel numOfRepsTB;
    private JTextField WeightTB;
    private JLabel numOfWeightTB;
    private JLabel dateLabel;
    private JTextField startTime;
    private JTextField endTime;
    private JButton saveButton;
    private JTable table1;
    private JButton addToCalendarButton;
    private JComboBox workoutMovementsCB;
    private JSpinner dateSpinner;


    WorkoutManager() {
        setContentPane(MainJPanel);
        setPreferredSize(new Dimension(600, 600));
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        configureDateSpinner();
        //populateBodyPartCB();
        //populateMovementCB();



        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                addWorkout();

            }
        });
    }




    protected void addWorkout(){

        String workoutName = workoutNameTB.getText();
        String workoutBodyPart = bodyPartTB.getSelectedItem().toString();
        String movements = workoutMovementsCB.getSelectedItem().toString();
        int weight = Integer.parseInt(WeightTB.getText());

        String date = new SimpleDateFormat("YYYY, mm, DD").format(dateSpinner.getValue());

        int start = Integer.parseInt(startTime.getText());
        int end = Integer.parseInt(endTime.getText());


        WorkoutModel workoutModel = new WorkoutModel(workoutName, workoutBodyPart, movements, weight, date, start, end);

        try {

            //workoutDB.addNewWorkout(workoutModel);




        } catch (Exception exe) {
            System.out.println("Error adding Workout to the DB" + exe);
        }



    }

    private void configureDateSpinner() {

        // Dates between Jan 1, 1970 and some time in 2920. I don't suppose this program will be around this long though...
        SpinnerDateModel spinnerDateModel = new SpinnerDateModel(new Date(), new Date(0), new Date(30000000000000L), Calendar.DAY_OF_YEAR);
        dateSpinner.setModel(spinnerDateModel);
        // Create a DateEditor to configure the way dates are displayed and edited
        // Define format the dates will have
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "YYYY-mm-dd");
        DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();
        // Attempt to prevent invalid input
        formatter.setAllowsInvalid(false);
        // Allow user to type as well as use up/down buttons
        formatter.setOverwriteMode(true);
        // And tell the serviceDataSpinner to use this Editor
        dateSpinner.setEditor(editor);

        //yyyy-mm-dd

    }

    private void populateBodyPartCB(){
        for (String bodyParts : WorkoutModel.bodyParts ) {

            bodyPartTB.addItem(bodyParts);

        }
    }

    private void populateMovementCB() {
        for (String movements : WorkoutModel.movements ) {

            workoutMovementsCB.addItem(movements);
        }
    }



}

