#define _CRT_SECURE_NO_WARNINGS
#include<stack.h>


void push(char p, LinkStack* list,int a) {
	Node* stack = (Node*)malloc(sizeof(Node));
	stack->data = p;
	if (p >= '0' && p <= '9')
	{
		stack->number = a;
	}
	else {
		stack->number = 0;
	}
	stack->next = list->top;
	list->top = stack;
}
char pop(LinkStack* list) {
	if (list != NULL) {
		Node* temp = list->top->next;
		char a = list->top->data;
		list->top= temp;
		return a;
	}
	else if (list->top == NULL)
		return NULL;


}


//检查格式是否正确
void change(char *arr, LinkStack* stack) {
	Node* head = NULL;
	Node* end = NULL;
	Node* temp = NULL;
	//LinkStack* stack;
	stack->top = NULL;
//	printf("%c", *(arr++));
	
	

		for (int i = 0; i < strlen(arr); i++)
		{
			//printf("%c", arr[i]);



			//差分为一个个元素入站判断
			char p = arr[i];
			//初始化栈顶top

			//数字？
			if (p == '(') {
				
				push(p, stack,0);

			}
			else if (p == ')') {
				if (stack->top == NULL) {
					printf("格式错误\n");
					cleary(stack, head);
					return;
				}
				while (stack->top->data != '(') {
					Node* cur = (Node*)malloc(sizeof(Node));
					cur->data = pop(stack);
					cur->number = 0;
					cur->next = NULL;
					end->next = cur;
					end = cur;
					cur->next = NULL;
					if (stack->top == NULL) {
						printf("格式错误\n");
						cleary(stack, head);
						return;
					}
				}
				//弹出（
				pop(stack);


			}
			else if (p >= '0' && p <= '9') { //数字
				//两位数
				if (arr[i+1] >= '0' && arr[i+1] <= '9') {
					char* ptr = &arr[i];
					int wei = is(ptr);
					int ret = ismanynum(ptr);
					Node* cur = (Node*)malloc(sizeof(Node));
					cur->data = p;
					cur->number = ret;
					cur->next = NULL;
					i = i + wei - 1;
					temp = end;
					if (head == NULL) {
						head = cur;
						end = cur;
						continue;
					}

					temp->next = cur;
					end = cur;
					cur->next = NULL;
					

					continue;
				}
				Node* cur = (Node*)malloc(sizeof(Node));
				cur->data = p;
				cur->number = p - '0';
				cur->next = NULL;
				temp = end;
				if (head == NULL) {
					head = cur;
					end = cur;
					continue;
				}

				temp->next = cur;
				end = cur;
				cur->next = NULL;

			}
			else if (p == '+' || p == '-' || p == '*' || p == '/') {
				
				//判断符号的情况
				if (p == '-') {
					if (arr[i - 1] == '(' || i == 0) {
						char* ptr = &arr[i+1];
						int wei = is(ptr);
						int ret = ismanynum(ptr);
						Node* cur = (Node*)malloc(sizeof(Node));
						cur->data = '1';
						cur->number = ret*-1;
						cur->next = NULL;
						i = i + wei;
						temp = end;
						if (head == NULL) {
							head = cur;
							end = cur;
							continue;
						}

						temp->next = cur;
						end = cur;
						cur->next = NULL;


						continue;
					}
				}
				if (stack->top == NULL) {
					push(p, stack, 0);
					continue;
				}
				if (stack->top != NULL) {
					while (priority(p) <= priority(stack->top->data)) {
						Node* cur = (Node*)malloc(sizeof(Node));
						cur->data = pop(stack);
						cur->number = 0;
						cur->next = NULL;
						end->next = cur;
						end = cur;
						cur->next = NULL;
						if (stack->top == NULL) { break; }
					}
				}
				push(p, stack,0);


			}
			else {
				printf("格式错误\n");
				cleary(stack, head);
				return;
			}

			
		}
		temp = stack->top;
		while (temp != NULL) {
			if (temp->data == '('||temp->data==')')
			{
				printf("格式错误\n"); cleary(stack, head); return;
			}
			temp = temp->next;
		}

		//把栈的符号弹出
		while (stack->top != NULL) {
			Node* cur = (Node*)malloc(sizeof(Node));
			cur->data=pop(stack);
			cur->number = 0;
			cur->next = NULL;
			end->next = cur;
			end = cur;

		}
		
	//打印后辍表达式

	temp = head;
	while (temp != NULL) {
		if (temp->data >= '0' && temp->data <= '9') {
			printf("%d", (int)temp->number);
		}
		else {
			printf("%c", temp->data);
		}
		temp = temp->next;
	}
	printf("\n");

	//后辍表达式入栈
	temp = head;
	double sum ; char p;
	double front, behind;
	while (temp != NULL)
	{
		p = temp->data;
		if (temp->data >= '0' && temp->data <= '9') {
			push(temp->data, stack,temp->number);
			
		}
		else if (p == '+' || p == '-' || p == '*' || p == '/') {
			/*if (stack->top->data > '9' || stack->top->data < '0')
			{
				printf("格式错误\n");  cleary(stack, head);   return;
			}*/
			int behind = stack->top->number;
			pop(stack);
			if (stack->top == NULL) {
				printf("格式错误\n");  cleary(stack, head);   return;
			}
			/*if (stack->top->data > '9' || stack->top->data < '0')
			{
				printf("格式错误\n");  cleary(stack, head); return;
			}*/
			front = stack->top->number;
			 pop(stack);
			
			switch (p) {
			case '+': sum = front + behind; break;
			case '-': sum = front - behind; break;
			case '*': sum = front * behind; break;
			case '/': sum = front / behind; break;
			}
			
			push('1', stack,sum);

		}
		temp = temp->next;


	}
	//运算后辍表达式
	sum = stack->top->number;
	printf("%f", sum);
	cleary(stack, head);




}

void cleary(LinkStack* stack,Node* head) {
	Node* temp = NULL;
	while (stack->top != NULL)
	{
		temp = stack->top->next;
		free(stack->top);
		stack->top = temp;
	}
	stack->top = NULL;
	temp = head;
	while (temp != NULL) {
		temp = temp->next;
		free(head);
		head = temp;
	}temp = NULL; head = NULL;


}

int is(char* temp) {
	 int i = 0; char* p = temp;
	while (*p != '\0' && *p >= '0' && *p <= '9') {
		p++;
		i++;
	}
	return i;
}


int ismanynum(char* temp) {
	int sum = 0; int i = 0; char* p = temp;
	while (*p!= '\0'&&*p>='0'&&*p<='9') {
		p++;
		i++;
	}
	for (int j=0; j < i; j++) {
		sum = sum * 10;
		sum += temp[j] - '0';
	}
	return sum;

}

int priority(char p) {
	if (p == '+' || p == '-') {
		return 1;
	}
	else if (p == '*' || p == '/')
	{
		return 2;
	}
	return 0;

}