import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class VirusSpread {

    // Вспомогательный класс для хранения координат клетки
    static class Cell {
        int r, c;

        Cell(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("INPUT.TXT"));
             PrintWriter writer = new PrintWriter(new File("OUTPUT.TXT"))) {

            if (!scanner.hasNextInt()) return;
            int n = scanner.nextInt(); // Количество строк
            int m = scanner.nextInt(); // Количество столбцов

            // Матрица для хранения времени заражения (-1 означает, что клетка здорова)
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    grid[i][j] = -1;
                }
            }

            int k = scanner.nextInt(); // Количество начальных вирусов
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

            int time = 0;
            System.out.println("=== Шаг " + time + " (Начальное состояние) ===");
            printGrid(grid);

            // Направления движения: вверх, вниз, влево, вправо
            int[] dr = {-1, 1, 0, 0};
            int[] dc = {0, 0, -1, 1};

            int totalCells = n * m;

            // Пока есть активные вирусы и заражены не все клетки
            while (!queue.isEmpty() && infCount < totalCells) {
                int levelSize = queue.size(); // Количество клеток, зараженных на текущем шаге
                time++;

                for (int i = 0; i < levelSize; i++) {
                    Cell current = queue.poll();

                    for (int d = 0; d < 4; d++) {
                        int nr = current.r + dr[d];
                        int nc = current.c + dc[d];

                        // Если соседняя клетка в пределах поля и еще не заражена
                        if (nr >= 0 && nr < n && nc >= 0 && nc < m && grid[nr][nc] == -1) {
                            grid[nr][nc] = time;
                            queue.offer(new Cell(nr, nc));
                            infCount++;
                        }
                    }
                }

                System.out.println("=== Шаг " + time + " ===");
                printGrid(grid);
            }

            // Записываем ответ в выходной файл
            writer.println(time);
            System.out.println("Итоговое время заражения: " + time + " (записано в OUTPUT.TXT)");

        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: Файл INPUT.TXT не найден в корневой директории проекта.");
        }
    }

    // Метод для отрисовки поля псевдографикой
    private static void printGrid(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

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

