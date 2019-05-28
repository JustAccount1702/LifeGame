import javax.swing.*;
import java.awt.*;


public class ButtonGrid extends JPanel{// Отображение поля в виде набора кнопок
    public LifeEngine lifeEngine;
    public JButton[][] grid;
    static final int N = 50;// Число кнопок
    public Point viewField;// Смещение от 0,0
    public ButtonGrid(){
        // Создание кнопок и задание их основных свойств

        lifeEngine = new LifeEngine();
        setLayout(new GridLayout(N, N));
        setBackground(Color.black);
        grid = new JButton[N][N];
        viewField = new Point(0,0);

        for(int y=0; y<N; y++){
            for(int x=0; x<N; x++){

                grid[x][y]=new JButton();
                grid[x][y].setForeground(Color.WHITE);
                grid[x][y].setFocusPainted(false);
                final int x_ = x;
                final int y_ = y;

                grid[x][y].addActionListener(e -> {
                    if (grid[x_][y_].getBackground().equals(Color.WHITE)){
                        grid[x_][y_].setBackground(Color.BLACK);
                        lifeEngine.field[x_+viewField.x+1][y_+viewField.y+1] = false;
                    }else{
                        grid[x_][y_].setBackground(Color.WHITE);
                        lifeEngine.field[x_+viewField.x+1][y_+viewField.y+1] = true;
                    }
                });

                add(grid[x][y]);
            }
        }

        setVisible(true);
    }
}
