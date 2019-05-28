public class LifeEngine {
    public boolean[][] field;
    static final int fieldSizeX = 144;// Создаем поле размером 144x146 = 20820
    static final int fieldSizeY = 146;

    LifeEngine(){// Конструктор по умолчанию
        field = new boolean[fieldSizeX][fieldSizeY];
        for (int i = 0; i < fieldSizeX ; i++)
            for (int j = 0; j < fieldSizeY ; j++)
                field[i][j] = false;// Заполянем все клетки пустыми элементами
    }


    private boolean get_new(int x, int y){// Метод, определяющий состояние клетки на следующем ходу
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


    public void update(){// Метод обновления позиций клеток
        boolean[][] nf = new boolean[fieldSizeX][fieldSizeY];// Создание нового поля
        for (int i = 1; i < fieldSizeX - 1; i++)
            for (int j = 1; j < fieldSizeY - 1; j++)
                nf[i][j] = get_new(i, j);// Заполнение поля обновлёнными элементами
        this.field = nf;// Присвоение нового поля
    }
}
