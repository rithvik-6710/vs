#include<iostream>
#include<vector>

using namespace std;
bool jump(vector<int>arr)
{
int maxindex=0;
int n=arr.size();
for(int i=0;i<n;i++)
{
    if(i>maxindex)
    {
        return false;
    }
    maxindex=max(maxindex,i+arr[i]);

}
return false;
}
int main()
{
    vector<int>arr={2,3,1,4,1,1,1,2};
    if(jump(arr))
    {
        cout<<"true";
    }
    else
    {
        cout<<"false";
    }
    return 0;
}