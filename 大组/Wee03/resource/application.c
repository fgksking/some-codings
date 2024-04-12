#include<Sorts.h>

//0.1.2����
void sortfirstArray(int arr[], int n) {
    //����0ʱ����ǰ����λ��
    // ������2ʱ����p2λ�õ�Ԫ�ؽ�����������p2
     // ����1ʱ����������
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


//�ҵ�K��
int fastSortfindK(int arr[], int left, int right,int k) {
    //�ָ�����ٷ�ʱ���ж�i��k�Ĺ�ϵ
    int i = left;
    int j = right;
    int key = arr[i];
    int temp;
    while (i < j) {

        while (i < j && arr[j] >= key) {
            j--;
        }
        //��������
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





