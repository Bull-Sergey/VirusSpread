import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

// класс для хранения координат клетки
public class Cell {
    int r, c;

    Cell(int r, int c) {
        this.r = r;
        this.c = c;
    }

    // метод заполнения матрицы очерели за 1 щаг
    public static int stepGrid(int infCount, int time, int n, int m, int[][] grid, Queue<Cell> queue) {
        int levelSize = queue.size();
        // Направления движения: вверх, вниз, влево, вправо
        // int[] dr = {-1, 1, 0, 0};
        // int[] dc = {0, 0, -1, 1};
        // Направления движения: вверх, вниз, влево, вправо и по диагонали
        int[] dr = {-1, 1, 0, 0, -1, -1, 1, 1}; // ряд
        int[] dc = {0, 0, -1, 1, -1, 1, 1, -1}; // столбец
        for (int i = 0; i < levelSize; i++) {
            Cell current = queue.poll(); // достаем клетку и удаляем из очереди

            // for (int d = 0; d < 4; d++) { // если не рассматривать диагонали как в задании
            for (int d = 0; d < dr.length; d++) {  // + рассматривае диагональ
                int nr = current.r + dr[d];
                int nc = current.c + dc[d];

                // Если соседняя клетка в пределах поля и еще не заражена
                if (nr >= 0 && nr < n && nc >= 0 && nc < m && grid[nr][nc] == -1) {
                    grid[nr][nc] = time; // записываем шаг заражения в клетку
                    queue.offer(new Cell(nr, nc));
                    infCount++;
                }
            }
        }
        return infCount;
    }

    // метод для создания матрицы
    public static int[][] startGrid(int n, int m) {
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = -1;
            }
        }
        return grid;
    }

    // метод заполнения вирусами матрицы
    public static Queue<Cell> virusGrid(int k, int n, int m, int[][] grid, Scanner scanner) {
        Queue<Cell> queue = new ArrayDeque<>();
        int infCount = 0;

        for (int i = 0; i < k; i++) {
            // Переводим координаты из 1-индексации (как в условии) в 0-индексацию
            int r = scanner.nextInt() - 1;
            int c = scanner.nextInt() - 1;

            // Проверяем корректность координат и не заражена ли клетка уже
            if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == -1) {
                grid[r][c] = 0;
                queue.offer(new Cell(r, c));
                infCount++;
            }
        }
        return queue;
    }


    // Метод для отрисовки поля псевдографикой
    public static void printGrid(int[][] grid, int time) {
        int n = grid.length;
        int m = grid[0].length;
        if (time > 0) {
            System.out.println("=== Минута " + time + " ===");
        } else {
            System.out.println("=== Минута " + time + " (Начальное состояние) ===");
        }

        // Верхняя рамка (ширина ячейки 4 символа для вмещения двузначных чисел)
        System.out.print("┌");
        for (int j = 0; j < m; j++) {
            System.out.print("────");
            System.out.print(j < m - 1 ? "┬" : "┐\n");
        }

        for (int i = 0; i < n; i++) {
            System.out.print("│");
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == -1) {
                    System.out.print("  . "); // Здоровая клетка
                } else if (grid[i][j] == 0) {
                    System.out.print("  V "); // Первичный очаг заражения
                } else {
                    // Выводим номер шага (0 для начальных, 1, 2, 3... для остальных)
                    System.out.printf(" %2d ", grid[i][j]);
                }
                System.out.print("│");
            }
            System.out.println();

            // Разделитель строк или нижняя рамка
            if (i < n - 1) {
                System.out.print("├");
                for (int j = 0; j < m; j++) {
                    System.out.print("────");
                    System.out.print(j < m - 1 ? "┼" : "┤\n");
                }
            } else {
                System.out.print("└");
                for (int j = 0; j < m; j++) {
                    System.out.print("────");
                    System.out.print(j < m - 1 ? "┴" : "┘\n");
                }
            }
        }
        System.out.println();
    }
}