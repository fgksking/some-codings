#include<Sorts.h>

int main() {
	int a[10000];
	int *b=(int*)malloc(sizeof(int)*50000);
	int *c=(int*)malloc(sizeof(int)*200000);
	int d[100] = {0};
	printf("请耐心等");
	clock_t start, end;
	double cpu_time_used;
	//for (int i = 0; i < 10000; i++) {
	//	a[i] = rand() % 100;
	//}
	//for (int i = 0; i < 50000; i++) {
	//	b[i] = rand() % 100;
	//}
	//for (int i = 0; i < 200000; i++) {
	//	c[i] = rand() % 100;
	//}
	for (int i = 0; i < 100; i++) {
		d[i] = rand() % 10;
	}
	int data1 = 10000;
	generateData(data1);
	int data2 = 50000;
	int data3 = 200000;
	readAndSortDataFromFile(a);
	generateData(data2);
	readAndSortDataFromFile(b);
	generateData(data3);
	readAndSortDataFromFile(c);


	//MergeSort(a, 0, p-1);

	//10000、50000、200000
	//100个数据*100k次排序
	int choice; int choice1;
	while (1) {
		printf("1.插入排序 2并归排序 3快排排序 4计数排序 5基数排序 6离开\n");
		scanf("%d", &choice);
		for (int i = 0; i < 10000; i++) {
			a[i] = rand() % 100;
		}
		for (int i = 0; i < 50000; i++) {
			b[i] = rand() % 100;
		}
		for (int i = 0; i < 200000; i++) {
			c[i] = rand() % 100;
		}
		for (int i = 0; i < 100; i++) {
			d[i] = rand() % 10;
		}
		switch (choice)
		{
		case 1:
			start = clock();
			InsertSort(a,10000);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("插入排序10000花费的时间：%f\n", cpu_time_used);
			start = clock();
			InsertSort(b,50000);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("插入排序50000花费的时间：%f\n", cpu_time_used);
			start = clock();
			InsertSort(c,200000);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("插入排序200000花费的时间：%f\n", cpu_time_used);
			

			printf("小量多次：\n");
			start = clock();
			for (int i = 0; i < 100000; i++) {
				
				InsertSort(d, 100);
				sortd(d, 100);
			}
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("插入小量排序花费的时间：%f\n", cpu_time_used);
			break;

		case 2:
			start = clock();
			MergeSort(a, 0, 9999);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("并归排序10000花费的时间：%f\n", cpu_time_used);
			start = clock();
			MergeSort(b, 0, 49999);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("并归排序50000花费的时间：%f\n", cpu_time_used);
			start = clock();
			MergeSort(c, 0, 199999);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("并归排序200000花费的时间：%f\n", cpu_time_used);
			for (int i = 300; i < 500; i++) {
				printf("%d ", a[i]);
			}
			printf("\n");
			printf("小量多次：\n");
			start = clock();
			for (int i = 0; i < 100000; i++) {
				
				MergeSort(d, 0, 99);
				sortd(d, 100);
			}
			
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("并归小量排序10000花费的时间：%f\n", cpu_time_used);

			break;
		case 3:
			start = clock();
			fastSort(a, 0, 9999);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("快速排序10000花费的时间：%f\n", cpu_time_used);
			start = clock();
			fastSort(b, 0, 49999);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("快速排序50000花费的时间：%f\n", cpu_time_used);
			start = clock();
			fastSort(c, 0, 199999);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("快速排序200000花费的时间：%f\n", cpu_time_used);
			for (int i = 300; i < 500; i++) {
				printf("%d ", a[i]);
			}

			printf("\n");
			printf("小量多次：\n");
			start = clock();
			for (int i = 0; i < 100000; i++) {

				fastSort(d, 0, 99);
				sortd(d, 100);
			}

			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("快排小量排序花费的时间：%f\n", cpu_time_used);

			break;
		case 4:

			start = clock();
			CountSort1(a, 10000);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("计数排序10000花费的时间：%f\n", cpu_time_used);
			start = clock();
			CountSort1(b, 50000);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("计数排序50000花费的时间：%f\n", cpu_time_used);
			start = clock();
			CountSort1(c, 200000);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("计数排序200000花费的时间：%f\n", cpu_time_used);
			for (int i = 300; i < 500; i++) {
				printf("%d ", a[i]);
			}


			printf("\n");
			printf("小量多次：\n");
			start = clock();
			for (int i = 0; i < 100000; i++) {

				CountSort1(d, 100);
				sortd(d, 100);
			}

			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("计数小量排序花费的时间：%f\n", cpu_time_used);


			break;
		case 5:

			start = clock();
			radixSort(a, 10000);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("基数排序10000花费的时间：%f\n", cpu_time_used);
			start = clock();
			radixSort(b, 50000);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("基数排序50000花费的时间：%f\n", cpu_time_used);
			start = clock();
			radixSort(c, 200000);
			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("基数排序200000花费的时间：%f\n", cpu_time_used);
			for (int i = 300; i < 500; i++) {
				printf("%d ", a[i]);
			}

			printf("\n");
			printf("小量多次：\n");
			start = clock();
			for (int i = 0; i < 100000; i++) {

				radixSort(d, 100);
				sortd(d, 100);
			}

			end = clock();
			cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
			printf("基数小量排序花费的时间：%f\n", cpu_time_used);

			break;
			
		default:
			free(b);
			free(c);
			
			return;
			
		}
	}


}
