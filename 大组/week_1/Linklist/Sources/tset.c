#define _CRT_SECURE_NO_WARNINGS
#include<linklist.h>


int main()
{
	int choice;
	Node* head = NULL;
	do {
		menu();
		scanf("%d", &choice);
		switch (choice)
		{
		case 1:head = inital();
			break;
		case 2:head_insert(head);
			break;
		case 3:end_insert(head);
			break;
		case 4: Delete_node(head);
			break;
		case 5: head=freeList(head);
			break;
		case 6:findnode(head);
			break;
		case 7:print_node(head);
			break;
		case 8:head->next = reverseList_digui(head->next);
			break;
		case 9:whether_circle(head);
			break;
		case 10:return;
			break;
		default:
			printf("Ñ¡Ôñ´íÎó\n"); getchar();
		}
	} while (choice != 10);

}
