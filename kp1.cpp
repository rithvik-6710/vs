#include <iostream>
#include <climits>

using namespace std;

long long minProductSubset(int arr[], int n) {
    if (n == 1)
        return arr[0];
    
    int max_neg = INT_MIN;
    int min_pos = INT_MAX;
    int count_neg = 0, count_zero = 0;
    long long product = 1;
    
    for (int i = 0; i < n; i++) {
       
        if (arr[i] == 0) {
            count_zero++;
            continue;
        }
        
       
        if (arr[i] < 0) {
            count_neg++;
            max_neg = max(max_neg, arr[i]);
        }
        
        if (arr[i] > 0)
            min_pos = min(min_pos, arr[i]);
        
        product *= arr[i];
    }
    
    if (count_zero == n)
        return 0;
    
    if (count_neg == 0 && count_zero > 0)
        return 0;
    
    if (count_neg == 0)
        return min_pos;
    
    if (count_neg % 2 == 0)
        product /= max_neg;
    
    return product;
}

int main() {
    int arr[] = {-1,0};
    int n = sizeof(arr) / sizeof(arr[0]);
    
    cout << "Minimum product subset = " << minProductSubset(arr, n);
    return 0;
}