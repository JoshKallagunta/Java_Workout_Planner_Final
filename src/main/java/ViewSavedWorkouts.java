import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewSavedWorkouts extends JFrame {
    private JButton getAllWorkoutsButton;
    private JButton editWorkoutButton;
    private JButton deleteWorkoutButton;
    private JTable ViewAllSavedWorkouts;
    private JPanel viewEditDeletePanel;


    public ViewSavedWorkouts() {

        setContentPane(viewEditDeletePanel);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setVisible(true);


        getAllWorkoutsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
