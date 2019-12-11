import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ViewSavedWorkouts extends JFrame {
    private JButton getAllWorkoutsButton;
    private JButton editWorkoutButton;
    private JButton deleteWorkoutButton;
    private JTable viewAllSavedWO;
    private JPanel viewEditDeletePanel;

    private WorkoutDB workoutDB;

    //
    DefaultTableModel viewsWorkoutTableModel = new DefaultTableModel();

    //private ViewSavedWorkouts openNewGui = new ViewSavedWorkouts();



    public ViewSavedWorkouts(WorkoutDB workoutDB) {

        this.workoutDB = workoutDB;
        setContentPane(viewEditDeletePanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);

        viewAllSavedWO.setModel(viewsWorkoutTableModel);




        getAllWorkoutsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


//                Vector<Vector> workoutModelVector = workoutDB.getAllWorkouts();
//
//
//                Vector workoutData = workoutModelVector;
//                Vector columnNames = WorkoutManager.getColumnNames();
//
//                viewsWorkoutTableModel.setDataVector(workoutData, columnNames );
//


            }
        });


        editWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        deleteWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


}
