public class searchinrotatedarr {
    public static void main(String[] args) {
        int[] arr = {7, 9, 11, 25, 42, 1, 2, 4};
        int tar = 42;  

        int res = searchinrotatedarr(arr, tar);

        if (res == -1)
            System.out.println("Element not found.");
        else
            System.out.println("Element found at index: " + res);
    }
    public static int searchinrotatedarr(int[] arr, int tar) {
        int l = 0, r = arr.length - 1;

        while (l<= r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] == tar)
                return mid;
            if (arr[l] <= arr[mid]) {
                if (tar >= arr[l] && tar < arr[mid])
                    r = mid - 1; 
                else
                    l = mid + 1;  
            } else {
                if (tar > arr[mid] && tar <= arr[r])
                    l = mid + 1;  
                else
                    r = mid - 1; 
            }
        }
        return -1; 
    }
    static int[] searchmatrix(int[][] mat,int)
    int l=0,r=mat[0].length-1;
    while(l<mat[0].length && r>=0) {
        if(mat[l][r]==tar) {
            return new int[]{l,r};
        } else if(mat[l][r]>tar) {
            r--;
        } else {
            l++;
        }
        return new int[]{-1,-1}; 
    }
    public static int searchmatrix(int[][] mat, int tar) {
        int arr[] = {}

        
     }                
}