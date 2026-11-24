import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class VirusSpread {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("INPUT.TXT"));
            PrintWriter writer = new PrintWriter(new File("OUTPUT.TXT"))) {
            int n = scanner.nextInt(); // Количество строк
            int m = scanner.nextInt(); // Количество столбцов
            // Матрица для хранения времени заражения (-1 означает, что клетка здорова)
            int[][] grid = Cell.startGrid(n,m); // создаем матрицу
            int k = scanner.nextInt(); // Количество начальных вирусов
            Queue<Cell> queue = Cell.virusGrid(k, n, m, grid, scanner); // создаем очередь и заполняем вирусами
            int time = 0; // нулевой шаг
            Cell.printGrid(grid,time); // рисуем матрицу
            int totalCells = n * m; // всего клеток
            int infCount = queue.size(); // Сколько клеток заражено (в очереди)
            // Пока есть активные вирусы и заражены не все клетки
            while (!queue.isEmpty() && infCount < totalCells) {
                int levelSize = queue.size(); // Количество клеток, зараженных на текущем шаге
                time++;
                infCount = Cell.stepGrid(infCount,time, n, m,  grid, queue); // заполнение матрицы и очереди вирусов
                Cell.printGrid(grid,time); // рисуем матрицу
            }
            // Записываем ответ в выходной файл
            writer.println(time);
            System.out.println("Итоговое время заражения: " + time + " (записано в OUTPUT.TXT)");
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: Файл INPUT.TXT не найден в корневой директории проекта.");
        }
    }


}

