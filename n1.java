public class n1 {
    public static void main(String[] args) {
        int[][] matrix = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 12, 13},
            {14, 15, 16, 17}
        };

        int n = matrix.length;       
        int m = matrix[0].length;    

        int top = 0, bottom = n - 1, left = 0, right = m - 1;

        System.out.println("Spiral Traversal:");
        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) {
                System.out.println(matrix[top][j] + " ");
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                System.out.println(matrix[i][right] + " ");
            }
            right--;

            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    System.out.println(matrix[bottom][j] + " ");
                }
                bottom--;
            }

            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    System.out.println(matrix[i][left] + " ");
                }
                left++;
            }
            
        }
    }
}