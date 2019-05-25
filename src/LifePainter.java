import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.concurrent.*;


public class LifePainter extends JFrame{
    public ScheduledExecutorService updateExecutor;
    public ButtonGrid buttonGrid;
    public Runnable oneIteration;
    public boolean isRunning;

    public LifePainter(){
        super("\"Life\" game");
        isRunning = false;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 6));

        buttonGrid = new ButtonGrid();
        mainPanel.add(buttonGrid);
        getContentPane().add(mainPanel);

        updateExecutor = Executors.newSingleThreadScheduledExecutor();

        oneIteration = () -> {
            buttonGrid.lifeEngine.update();
            refreshGrid();
        };

        JButton start = new JButton("Start");
        start.setBackground(Color.darkGray);
        start.setForeground(Color.green);
        start.setFocusPainted(false);
        start.addActionListener(e -> {
            if (!isRunning) {
                isRunning = true;
                updateExecutor = Executors.newSingleThreadScheduledExecutor();
                updateExecutor.scheduleAtFixedRate(oneIteration, 0, 500, TimeUnit.MILLISECONDS);
            }
        });
        buttonsPanel.add(start);

        JButton stop = new JButton("Stop");
        stop.setBackground(Color.darkGray);
        stop.setForeground(Color.red);
        stop.setFocusPainted(false);
        stop.addActionListener(e -> {
            isRunning = false;
            updateExecutor.shutdown();
        });
        buttonsPanel.add(stop);

        JButton reset = new JButton("Reset");
        reset.setBackground(Color.darkGray);
        reset.setForeground(Color.WHITE);
        reset.setFocusPainted(false);
        reset.addActionListener(e -> {
            isRunning = false;
            resetGrid();
        });
        buttonsPanel.add(reset);

        JButton view = new JButton("View");
        view.setBackground(Color.darkGray);
        view.setForeground(Color.WHITE);
        view.setFocusPainted(false);
        view.addActionListener(e -> {
            setView();
        });
        buttonsPanel.add(view);

        JButton load = new JButton("Load");
        load.setBackground(Color.darkGray);
        load.setForeground(Color.yellow);
        load.setFocusPainted(false);
        load.addActionListener(e -> load());
        buttonsPanel.add(load);

        JButton save = new JButton("Save");
        save.setBackground(Color.darkGray);
        save.setForeground(Color.yellow);
        save.setFocusPainted(false);
        save.addActionListener(e -> save());
        buttonsPanel.add(save);

        refreshGrid();
        mainPanel.add(buttonsPanel, BorderLayout.NORTH);
    }


    public void refreshGrid(){
        for (int i = 0; i < buttonGrid.N; i++){
            for (int j = 0; j < buttonGrid.N; j++){

                buttonGrid.grid[i][j].setBackground((buttonGrid.lifeEngine.field[i+buttonGrid.viewField.x+1][j+buttonGrid.viewField.x+1])?Color.WHITE:Color.BLACK);
            }
        }
    }


    public void resetGrid(){
        updateExecutor.shutdown();
        for (int i = 0; i < buttonGrid.N; i++){
            for (int j = 0; j < buttonGrid.N; j++){
                buttonGrid.grid[i][j].setBackground(Color.black);
                buttonGrid.lifeEngine.field[i+1][j+1] = false;
            }
        }
    }

    public void setView(){
        SwitchViewFrame sw = new SwitchViewFrame(this);
    }

    public void save(){
        try {
            FileOutputStream fos = new FileOutputStream("src/field");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(buttonGrid.lifeEngine.field);
            oos.close();
            refreshGrid();
        }catch (Exception e){
            System.out.println("File exception! " + e.toString());
        }
    }


    public void load(){
        try {
            FileInputStream fis = new FileInputStream("src/field");
            ObjectInputStream ois = new ObjectInputStream(fis);
            buttonGrid.lifeEngine.field = (boolean[][]) ois.readObject();
            ois.close();
            refreshGrid();
        }catch(Exception e){
            System.out.println("File exception!");
        }
    }


    public static void main(String[] args){
        JFrame frame = new LifePainter();
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
