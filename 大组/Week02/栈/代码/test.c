#define _CRT_SECURE_NO_WARNINGS
#include<stack.h>

int main() {




	LinkStack* list = (LinkStack*)malloc(sizeof(LinkStack));
	list->top = NULL;
	while (1) {
		printf("请输入正确格式\n");
		char arr[30];
		//char* ptr = "6+6+6";
		scanf("%s", arr);
		//printf("%s",arr);
		change(arr, list);
		printf("\n");
	}
	



}