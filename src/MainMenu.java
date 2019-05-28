import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.concurrent.*;


public class MainMenu extends JFrame{
    public ScheduledExecutorService updateExecutor;
    public ButtonGrid buttonGrid;
    public Runnable oneIteration;
    public boolean isRunning;

    public MainMenu(){
        super("Life game");// Создание окна с заголовком Life Game
        isRunning = false;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// Установка действия при нажатии креста
        // Расстановка основных элементов управления программой
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 6));

        buttonGrid = new ButtonGrid();
        mainPanel.add(buttonGrid);
        getContentPane().add(mainPanel);

        updateExecutor = Executors.newSingleThreadScheduledExecutor();// Добавление многопоточного обработчика событий/обновления экрана

        oneIteration = () -> {
            buttonGrid.lifeEngine.update();
            refreshGrid();
        };
        // Обозначение всех кнопок и их действий по умолчанию

        // Кнопка начала обновления экрана
        JButton start = new JButton("Start");
        start.setBackground(Color.darkGray);
        start.setForeground(Color.green);
        start.setFocusPainted(false);
        start.addActionListener(e -> {
            if (!isRunning) {
                isRunning = true;
                updateExecutor = Executors.newSingleThreadScheduledExecutor();
                // Запуск многогопоточного обработчика событий с задержкой в 500 мс
                updateExecutor.scheduleAtFixedRate(oneIteration, 0, 500, TimeUnit.MILLISECONDS);
            }
        });
        buttonsPanel.add(start);

        // Кнопка остановки процесса обновления
        JButton stop = new JButton("Stop");
        stop.setBackground(Color.darkGray);
        stop.setForeground(Color.red);
        stop.setFocusPainted(false);
        stop.addActionListener(e -> {
            isRunning = false;
            updateExecutor.shutdown();
        });
        buttonsPanel.add(stop);

        // Кнопка сброса состояния поля
        JButton reset = new JButton("Reset");
        reset.setBackground(Color.darkGray);
        reset.setForeground(Color.WHITE);
        reset.setFocusPainted(false);
        reset.addActionListener(e -> {
            isRunning = false;
            resetGrid();
        });
        buttonsPanel.add(reset);

        // Кнопка премещения области просмотра
        JButton view = new JButton("View");
        view.setBackground(Color.darkGray);
        view.setForeground(Color.WHITE);
        view.setFocusPainted(false);
        view.addActionListener(e -> {
            new SwitchViewFrame(this);
        });
        buttonsPanel.add(view);

        // Кнопка загрузки поля из копии на диске
        JButton load = new JButton("Load");
        load.setBackground(Color.darkGray);
        load.setForeground(Color.yellow);
        load.setFocusPainted(false);
        load.addActionListener(e -> load());
        buttonsPanel.add(load);

        // Кнопка выгрузки поля из файла на диске
        JButton save = new JButton("Save");
        save.setBackground(Color.darkGray);
        save.setForeground(Color.yellow);
        save.setFocusPainted(false);
        save.addActionListener(e -> save());
        buttonsPanel.add(save);

        refreshGrid();// Обновление сетки кнопок
        mainPanel.add(buttonsPanel, BorderLayout.NORTH);// Добавление всех кнопок на экран
    }


    public void refreshGrid(){// Метод обновления цветов клеток
        for (int i = 0; i < buttonGrid.N; i++){
            for (int j = 0; j < buttonGrid.N; j++){

                buttonGrid.grid[i][j].setBackground((buttonGrid.lifeEngine.field[i+buttonGrid.viewField.x+1][j+buttonGrid.viewField.x+1])?Color.WHITE:Color.BLACK);
            }
        }
    }


    public void resetGrid(){// Метод очистки поля
        updateExecutor.shutdown();
        for (int i = 0; i < buttonGrid.N; i++){
            for (int j = 0; j < buttonGrid.N; j++){
                buttonGrid.grid[i][j].setBackground(Color.black);
                buttonGrid.lifeEngine.field[i+1][j+1] = false;
            }
        }
    }


    public void save(){// Метод сохранения поля на диск
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


    public void load(){// Метод загрузки поля с диска
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


    public static void main(String[] args){// Точка входа в программу
        JFrame frame = new MainMenu();// Создание основоного окна и последующая его настройка
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
