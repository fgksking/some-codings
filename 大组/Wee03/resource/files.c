#include<Sorts.h>


void generateData(int n) {
    FILE* file = fopen("test_data.txt", "w");
    if (file == NULL) {
        printf("Error opening file.\\n");
        return;
    }

    for (int i = 0; i < n; i++) {
        fprintf(file, "%d ", rand() % 100);  // ����0��999֮��������
    }

    fclose(file);
}

// ���ļ��ж�ȡ���ݲ���������
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






