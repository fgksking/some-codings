#include<head.h>
Node* insertNode(Node* root,int data) {

	//�ݹ鵽ĩ�ڵ����
	//�Ƚ����ҽ��
	if (root == NULL) {
		Node* newNode = (Node*)malloc(sizeof(Node));
		newNode->data = data;
		newNode->left = newNode->right = NULL;
		return newNode;
	}
	if (data < root->data) {
		root->left = insertNode(root->left, data);
	}
	else if (data > root->data) {
		root->right = insertNode(root->right, data);
	}
	else {
		printf("����ʧ�ܣ�ֵ�ظ�");
	}
	return root;


}
void search(Node* head) {
	Node* temp = head;
	int n;
	printf("������ҵ����ݣ�\n");
	scanf("%d", &n);
	while (temp != NULL) {

		if (temp->data == n) {
			printf("���Ҵ���:%d\n", temp->data);
			return;
		}

		if (n < temp->data) {
			temp = temp->left;
		}
		else {
			temp = temp->right;
		}


	}
	printf("���Ҳ�����");
	return;
}
Node* find(Node* root) {
	while (root->left != NULL) {
		root = root->left;
	}
	return root;
}

Node* delete(Node* root, int data) {
	if (root == NULL) {
		return root;
	}
	if (data < root->data) {
		root->left = delete(root->left, data);
	}
	else if (data > root->data) {
		root->right = delete(root->right, data);
	}
	else {
		if (root->left == NULL) {
			Node* temp = root->right;
			free(root);
			return temp;
		}
		else if (root->right == NULL) {
			Node* temp = root->left;
			free(root);
			return temp;
		}

		//�ýڵ��������ӽڵ�
		Node* temp = find(root->right);
		root->data = temp->data;
		root->right = delete(root->right, temp->data);
	}

	return root;

}
//�����ӡ
void midprint(Node* root) {

	if (root != NULL) {
		midprint(root->left);
		printf("%d ", root->data);
		midprint( root->right);
	}
}
//����
void frontprint(Node* root) {
	if (root != NULL) {
		printf("%d ", root->data);
		frontprint(root->left);
		frontprint(root->right);
	}
}

//����
void backprint(Node* root) {
	if (root != NULL) {
		backprint(root->right);
		backprint(root->left);
		printf("%d ", root->data);
	}
}
//�����Ƿǵݹ�
//��ջʵ������

void prestackprint(Node* head) {
	//��ջ�����ҽڵ���ջ 
	Node* temp = NULL;
	temp = head;
	if (temp == NULL)
		return;
	//��ջ
	//stack* s = (stack*)malloc(sizeof(stack));
	stackNode* top = NULL;
	//s->top = NULL;
//	printf("%d ", temp->data);
	while (temp != NULL||(top!=NULL)) {
		//�ҽڵ���ջ
		//if (temp->right != NULL) {
		//	stackNode* p = (stackNode*)malloc(sizeof(stackNode));
		//	p->root = temp->right;
		//	p->next = top;
		//	top = p;
		//}
		//
		//if (temp->left != NULL) {
		//	stackNode* p = (stackNode*)malloc(sizeof(stackNode));

		//	p->root = temp->left;
		//	p->next = top;
		//	top = p;
		//	}
		//if (temp->left == NULL && temp->right == NULL) {
		//	//���ջ
		//	while (top != NULL) {
		//		printf("%d ", top->root->data);
		//		temp = top->root;
		//		top = top->next;
		//	}
		//	if (temp->left== NULL&&temp->right==NULL) {
		//		break;
		//	}
		//	continue;
		//}
		////��ջ
		//// 
		////���±���
		//if (top->root != NULL) {
		//	printf("%d ", top->root->data);
		//	//��ջ
		//	top = top->next;

		//}
		////��ڵ㵽ͷʱ
		//if (temp->left == NULL&&top!=NULL) {
		//	temp = top->root;
		//}
		//else {
		//	temp = temp->left;
		//}
		////
		while (temp != NULL) {
			printf("%d ", temp->data);
			stackNode* p = (stackNode*)malloc(sizeof(stackNode));
			p->root = temp;
			p->next = top;
			top = p;
			temp = temp->left;
		}
		if (top != NULL) {
			temp = top->root;
			top = top->next;
			temp = temp->right;
		}

	}




}



//��������




void enqueue(Queue* queue, Node* data) {
	QueueNode* newNode = (QueueNode*)malloc(sizeof(QueueNode));
	newNode->data = data;
	newNode->next = NULL;
	if (queue->rear == NULL) {
		queue->front = newNode;
		queue->rear = newNode;
	}
	else {
		queue->rear->next = newNode;
		queue->rear = newNode;
	}
}

Node* dequeue(Queue* queue) {
	if (queue->front == NULL) {
		return NULL;
	}
	QueueNode* temp = queue->front;
	Node* data = temp->data;
	queue->front = queue->front->next;
	if (queue->front == NULL) {
		queue->rear = NULL;
	}
	free(temp);
	return data;
}
void leverTree(Node* head) {

	Node* temp = head;
	if (temp == NULL)
	{
		printf("Ϊ��\n");
		return;
	}
	
	Queue* quene = (Queue*)malloc(sizeof(Queue));
	quene->front = NULL;
	quene->rear = NULL;
	enqueue(quene, temp);
//	printf("���������\n");
	while (quene->front!=NULL) {
		Node* cur = dequeue(quene);
		printf("%d ", cur->data);
		if (cur->left != NULL) {
			enqueue(quene, cur->left);
		}
		if (cur->right != NULL) {
			enqueue(quene, cur->right);
		}

	}




}

void Free(Node* root) {

	if (root == NULL) {
		return;
	}

	Free(root->left);
	Free(root->right);

	free(root);
}

//����
int main() {
	Node* head = NULL;
	//head= insertNode(head,5);
	//head= insertNode(head,3);
	//head= insertNode(head,8);
	//head= insertNode(head,7);
	//head= insertNode(head,1);
	//
	//frontprint(head);
	//printf("\n");
	//backprint(head);
	//printf("\n");
	//prestackprint(head);
	//printf("\n");
	//leverTree(head);

	while (1) {

		printf("1����(��֧��С��)  2����  3ɾ�� 4���� 5�뿪\n");
		printf("������\n");
		int n,data;
		char ch;
		scanf("%d", &n);
		switch (n) {
		case 1:
			printf("������������\n");
			scanf("%d", &data);
			fflush(stdout);
			while ((ch = getchar()) != '\n' && ch != EOF);
			head=insertNode(head, data);
			break;
		case 2:

			search(head);
			break;
		case 3:
			printf("����Ҫɾ��������\n");
			scanf("%d", &data);
			while ((ch = getchar()) != '\n' && ch != EOF);
			fflush(stdin);
			head = delete(head, data);
			break;
		case 4:
			printf("��������");
			frontprint(head);
			printf("\n��������");
			midprint(head);
			printf("\n��������");
			backprint(head);
			printf("\n�������򣨷ǵݹ飩");
			prestackprint(head);
			printf("\n��������");
			leverTree(head);
			printf("\n");
			break;
		case 5:
			Free(head);
			return;
		default :
			break;

		
		}
		

		

	}

}


