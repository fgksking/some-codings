#include<head.h>
Node* insertNode(Node* root,int data) {

	//递归到末节点插入
	//比较左右结点
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
		printf("插入失败，值重复");
	}
	return root;


}
void search(Node* head) {
	Node* temp = head;
	int n;
	printf("输入查找的数据：\n");
	scanf("%d", &n);
	while (temp != NULL) {

		if (temp->data == n) {
			printf("查找存在:%d\n", temp->data);
			return;
		}

		if (n < temp->data) {
			temp = temp->left;
		}
		else {
			temp = temp->right;
		}


	}
	printf("查找不存在");
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

		//该节点有两个子节点
		Node* temp = find(root->right);
		root->data = temp->data;
		root->right = delete(root->right, temp->data);
	}

	return root;

}
//中序打印
void midprint(Node* root) {

	if (root != NULL) {
		midprint(root->left);
		printf("%d ", root->data);
		midprint( root->right);
	}
}
//先序
void frontprint(Node* root) {
	if (root != NULL) {
		printf("%d ", root->data);
		frontprint(root->left);
		frontprint(root->right);
	}
}

//后续
void backprint(Node* root) {
	if (root != NULL) {
		backprint(root->right);
		backprint(root->left);
		printf("%d ", root->data);
	}
}
//以下是非递归
//用栈实现先序

void prestackprint(Node* head) {
	//出栈后先右节点入栈 
	Node* temp = NULL;
	temp = head;
	if (temp == NULL)
		return;
	//入栈
	//stack* s = (stack*)malloc(sizeof(stack));
	stackNode* top = NULL;
	//s->top = NULL;
//	printf("%d ", temp->data);
	while (temp != NULL||(top!=NULL)) {
		//右节点入栈
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
		//	//清空栈
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
		////出栈
		//// 
		////往下遍历
		//if (top->root != NULL) {
		//	printf("%d ", top->root->data);
		//	//出栈
		//	top = top->next;

		//}
		////左节点到头时
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



//层序排序




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
		printf("为空\n");
		return;
	}
	
	Queue* quene = (Queue*)malloc(sizeof(Queue));
	quene->front = NULL;
	quene->rear = NULL;
	enqueue(quene, temp);
//	printf("层序遍历：\n");
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

//测试
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

		printf("1插入(不支持小数)  2查找  3删除 4排序 5离开\n");
		printf("请输入\n");
		int n,data;
		char ch;
		scanf("%d", &n);
		switch (n) {
		case 1:
			printf("输入插入的数据\n");
			scanf("%d", &data);
			fflush(stdout);
			while ((ch = getchar()) != '\n' && ch != EOF);
			head=insertNode(head, data);
			break;
		case 2:

			search(head);
			break;
		case 3:
			printf("输入要删除的数据\n");
			scanf("%d", &data);
			while ((ch = getchar()) != '\n' && ch != EOF);
			fflush(stdin);
			head = delete(head, data);
			break;
		case 4:
			printf("先序排序");
			frontprint(head);
			printf("\n中序排序");
			midprint(head);
			printf("\n后序排序");
			backprint(head);
			printf("\n先序排序（非递归）");
			prestackprint(head);
			printf("\n层序排序");
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


