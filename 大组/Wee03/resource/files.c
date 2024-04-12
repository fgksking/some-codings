#include<Sorts.h>


void generateData(int n) {
    FILE* file = fopen("test_data.txt", "w");
    if (file == NULL) {
        printf("Error opening file.\\n");
        return;
    }

    for (int i = 0; i < n; i++) {
        fprintf(file, "%d ", rand() % 100);  // 生成0到999之间的随机数
    }

    fclose(file);
}

// 从文件中读取数据并进行排序
void readAndSortDataFromFile(int arr[]) {
    FILE* file = fopen("test_data.txt", "r");
    if (file == NULL) {
        printf("Error opening file.\n");
        return;
    }

 
    int n = 0;

    while (fscanf(file, "%d", &arr[n]) != EOF) {
        n++;
    }

    fclose(file);

}






