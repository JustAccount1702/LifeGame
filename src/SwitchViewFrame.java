import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SwitchViewFrame extends JFrame {
    SwitchViewFrame (LifePainter lp) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = 360;
        int sizeHeight = 400;
        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeight) / 2;
        setBounds(locationX, locationY, sizeWidth, sizeHeight);

        setTitle("Морской бой");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                dispose();
            }
        });
        setLayout(null);

        JTextField xCoord = new JTextField(20);
        xCoord.setToolTipText("X cell");
        JTextField yCoord = new JTextField(20);
        yCoord.setToolTipText("Y cell");

        xCoord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lp.buttonGrid.viewField = new Point(Integer.parseInt(xCoord.getText()), lp.buttonGrid.viewField.y);
            }
        });

        yCoord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lp.buttonGrid.viewField = new Point(lp.buttonGrid.viewField.x, Integer.parseInt(yCoord.getText()));
            }
        });

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lp.refreshGrid();
                dispose();
            }
        });

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(xCoord);
        panel.add(yCoord);
        panel.add(updateButton);
        setContentPane(panel);
        setVisible(true);
    }


}
