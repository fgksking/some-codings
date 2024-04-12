#include<Sorts.h>

//0.1.2排序
void sortfirstArray(int arr[], int n) {
    //遇到0时与最前交换位置
    // 当遇到2时，与p2位置的元素交换，并更新p2
     // 遇到1时，继续遍历
    int p0 = 0; 
    int p2 = n - 1; 
    int i = 0; 
    while (i <= p2) {
        if (arr[i] == 0) {
            int temp = arr[p0];
            arr[p0] = arr[i];
            arr[i] = temp;
            p0++;
            i++;
        }
        else if (arr[i] == 2) {
            int temp = arr[p2];
            arr[p2] = arr[i];
            arr[i] = temp;
            p2--;
        }
        else {
            
            i++;
        }
    }
}


//找第K大
int fastSortfindK(int arr[], int left, int right,int k) {
    //分割到不能再分时，判断i与k的关系
    int i = left;
    int j = right;
    int key = arr[i];
    int temp;
    while (i < j) {

        while (i < j && arr[j] >= key) {
            j--;
        }
        //交换数据
        arr[i] = arr[j];
        while (i < j && arr[i] <= key) {
            i++;
        }
        arr[j] = arr[i];

    }
    arr[i] = key;
    if (right - left == 2) {
        if (k == left+1) {
            return arr[left+1];
        }
        else {
            return arr[right+1];
        }
    }
    if (k == i + 1) {
        return arr[k];
    }
    if (i - 1 > left) {
        fastSort(arr, left, i - 1,k);
    }
    if (i + 1 < right) {
        fastSort(arr, i + 1, right,k);
    }
    return;


}





