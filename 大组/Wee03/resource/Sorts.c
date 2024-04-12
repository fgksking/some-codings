#include<Sorts.h>

void InsertSort(int arr[],int n) {
	int i, key, j;
	for (i = 1; i <n; i++) {
		key = arr[i];
		j = i - 1;
		while (j >= 0 && arr[j] > key) {
			arr[j + 1] = arr[j];
			j = j - 1;
		}
		arr[j + 1] = key;

	}


}



void MergeSort(int arr[], int l, int r) {
    if (l < r) {
        int m = l + (r - l) / 2;
        MergeSort(arr, l, m);
        MergeSort(arr, m + 1, r);
        merge(arr, l, m, r);
    }
}

void merge(int arr[], int l, int m, int r) {
    int n1 = m - l + 1;
    int n2 = r - m;

    int* L = (int*)malloc(sizeof(int) * n1);
    int* R = (int*)malloc(sizeof(int) * n2);

    for (int i = 0; i < n1; i++) {
        L[i] = arr[l + i];
    }
    for (int j = 0; j < n2; j++) {
        R[j] = arr[m + 1 + j];
    }

    int i = 0, j = 0, k = l;
    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            arr[k] = L[i];
            i++;
        }
        else {
            arr[k] = R[j];
            j++;
        }
        k++;
    }

    while (i < n1) {
        arr[k] = L[i];
        i++;
        k++;
    }

    while (j < n2) {
        arr[k] = R[j];
        j++;
        k++;
    }

    free(L);
    free(R);
}
//快速排序
void fastSort(int arr[], int left, int right) {

    int i = left;
    int j = right;
    int key = arr[i];
    int temp;
    while (i < j) {

        while (i<j && arr[j]>=key) {
            j--;
        }
        //交换数据
        arr[i] = arr[j];
        while(i < j && arr[i] <= key) {
            i++;
        }
        arr[j] = arr[i];
    }
    arr[i] = key;
    if (i - 1 > left) {
        fastSort(arr, left, i-1);
    }
    if (i + 1 < right) {
        fastSort(arr, i+1, right);
    }
    return;


}
//计数排序
void CountSort1(int* arr, int n) {

    int max = arr[0];
    for (int i = 0; i < n; i++) {
        if (max < arr[i]) {
            max = arr[i];
        }
    }

    int* p = (int*)malloc(sizeof(int) * (max + 1));
    memset(p, 0, sizeof(int) * (max+1));
    for (int i = 0; i < n; i++) {
        p[arr[i]]++;
    }
    int j = 0;
    for (int i = 0; i < max + 1; i++) {
        while (p[i]--)
        {
            arr[j] = i;
            j++;
        }
    }
    free(p);

}

//基数排序
int getMax(int arr[], int n) {
    int max = arr[0];
    for (int i = 1; i < n; i++) {
        if (arr[i] > max) {
            max = arr[i];
        }
    }
    return max;
}

// 对数组按照指定位数进行计数排序
void countSort(int arr[], int n, int exp) {
    int* output=(int*)malloc(sizeof(int)*n);
    int count[10] = { 0 };

    for (int i = 0; i < n; i++) {
        count[(arr[i] / exp) % 10]++;
    }

    for (int i = 1; i < 10; i++) {
        count[i] += count[i - 1];
    }

    for (int i = n - 1; i >= 0; i--) {
        output[count[(arr[i] / exp) % 10] - 1] = arr[i];
        count[(arr[i] / exp) % 10]--;
    }

    for (int i = 0; i < n; i++) {
        arr[i] = output[i];
    }
}

// 基数排序算法
void radixSort(int arr[], int n) {
    int max = getMax(arr, n);

    for (int exp = 1; max / exp > 0; exp *= 10) {
        countSort(arr, n, exp);
    }
}


void sortd(int arr[], int n) {
    for (int i = 0; i < n; i++) {
        arr[i] = rand() % 10;
    }
}

