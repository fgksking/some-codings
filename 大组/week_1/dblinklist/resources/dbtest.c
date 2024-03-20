#define _CRT_SECURE_NO_WARNINGS
#include <dblist.h>

int main() {

	int choice;
	Node* head = NULL;
	do {
		menu1();
		printf("«Î—°‘Ò\n");
		scanf("%d", &choice);
		switch (choice)
		{
		case 1:head = initList();
			break;
		case 2:insertAtBeginning(head);
			break;
		case 3:insertAtEnd(head);
			break;
		case 4: deleteNode(head);
			break;
		case 5: head=freeList(head);
			break;
		case 6:printList(head);
			break;
		case 7:
			return 0;
		default:
			printf("—°‘Ò¥ÌŒÛ\n");
			break;
		}


	} while (choice != 7);

	return 0;
}