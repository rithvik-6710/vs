public class new  {
    public static void main(String[] args) {
        int[][] matrix = {
            {1, 2, 3, 4},
            {3, 4, 5, 5},
            {5, 7, 8, 9},
            {6, 8, 9, 11}
        };

        int n = matrix.length;       // number of rows
        int m = matrix[0].length;    // number of columns

        System.out.println("Zig-Zag Traversal:");
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
               
                for (int j = 0; j < m; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
            } else {
                for (int j = m - 1; j >= 0; j--) {
                    System.out.print(matrix[i][j] + " ");

                }
            }
        }
    }
}