public class LifeEngine {
    public boolean[][] field;
    static final int fieldSizeX = 144;// Создаем поле размером 144x146 = 20820
    static final int fieldSizeY = 146;

    LifeEngine(){
        field = new boolean[fieldSizeX][fieldSizeY];
        for (int i = 0; i < fieldSizeX ; i++)
            for (int j = 0; j < fieldSizeY ; j++)
                field[i][j] = false;
    }


    private boolean get_new(int x, int y){
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
        boolean[][] nf = new boolean[fieldSizeX][fieldSizeY];
        for (int i = 1; i < fieldSizeX - 1; i++)
            for (int j = 1; j < fieldSizeY - 1; j++)
                nf[i][j] = get_new(i, j);
        this.field = nf;
    }
}
