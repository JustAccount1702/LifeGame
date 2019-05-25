public class LifeEngine {
    public boolean[][] field;
    public int fieldSize = 150;// Создаем поле размером 150x150 = 22500

    LifeEngine(){
        field = new boolean[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize ; i++)
            for (int j = 0; j < fieldSize ; j++)
                field[i][j] = false;
    }


    public boolean get_new(int x, int y){
        int count = 0;
        // Ищем "живые" клетки вогруг данной
        if (field[x][y - 1])
            count += 1;
        if (field[x][y + 1])
            count += 1;
        if (field[x - 1][y])
            count += 1;
        if (field[x + 1][y])
            count += 1;
        if (field[x + 1][y + 1])
            count += 1;
        if (field[x + 1][y - 1])
            count += 1;
        if (field[x - 1][y + 1])
            count += 1;
        if (field[x - 1][y - 1])
            count += 1;
        return  ((count == 2 && field[x][y]) || count == 3);
        // Примнимаем решение на следующий ход клетки
    }


    public void update(){
        boolean[][] nf = new boolean[fieldSize][fieldSize];
        for (int i = 1; i < fieldSize - 1; i++)
            for (int j = 1; j < fieldSize - 1; j++)
                nf[i][j] = get_new(i, j);
        this.field = nf;
    }
}
