#pragma once
#include<stdio.h>
#include<string.h>
#include<stdlib.h>



typedef struct Node {  //存储数据的节点
	int data;
	struct Node* next;
}Node;

//typedef struct list {
//	Node* phead;        //头节点
//	Node* pend;      //尾节点
//}List;

void menu() {
	
	printf("*1--初始化链表   2--头插数据\n3--尾插数据"
		"   4-删除节点\n5-销毁链表    6--查找节点\n"
	"7--打印链表    8--反转链表\n9--判断成环    10--退出");
	printf(" \n请选择：\n");
}


Node* inital() {
	Node* L;
	L = (Node*)malloc(sizeof(Node));      //开辟空间 
	if (L == NULL) {                     //判断是否开辟空间失败，这一步很有必要
		printf("申请空间失败");
		//exit(0);                  //开辟空间失败可以考虑直接结束程序
	}
	L->next = NULL; //指针指向空
	L->data = 0;

	return L;
}


void head_insert(Node* node) {
	if (node == NULL)
	{
		printf("链表为空\n"); return;
	}

	Node* L=(Node*)malloc(sizeof(Node));
	L->next = NULL;
	printf("请输入\n");
	int x;
	while (scanf("%d", &x) ==1) {
		if (node->next == NULL)
		{
			L->data = x;
			node->next = L;
			printf("操作成功\n");
			
		}
		else {
			L->data = x;
			L->next = node->next;
			node->next = L;
			printf("操作成功\n");
		}
		node->data++;//头节点记录节点数
		break;

	}

}
void end_insert(Node* p) {
	if (p == NULL)
	{
		printf("链表为空\n"); return;
	}

	Node* temp = p;
	while (temp->next != NULL)
	{

	
		temp = temp->next;
	}
	int x;
	Node* cur;
	while (scanf("%d", &x) ==1) {
		
		cur = (Node*)malloc(sizeof(Node));
		cur->data = x;
		temp->next = cur;
		p->data--;//头节点记录节点数
		cur->next = NULL;
		printf("操作成功\n");
		break;
	}

}
void Delete_node(Node* L) {
	if (L== NULL)
	{
		printf("链表为空\n"); return;
	}
	Node* p, * pre;    //pre为前驱结点，p为查找的结点。
	p = L->next;
	pre = L;
	int pos = 0;
	int x;
	printf("输入要删除的数据\n");
	scanf("%d", &x);

	while (p!=NULL) {    
		pos++; //查x的元素
		if (pos == x) {
			pre->next = p->next;
			free(p);
			printf("删除成功\n");
			L->data--;
			return;
		}
		pre = p;
		p = p->next;
	}
	printf("删除的数据不存在\n");
}

void print_node(Node* head) {
	if (head == NULL)
	{
		printf("链表为空\n"); return;
	}
	Node* temp = head->next;
	while (temp != NULL) {
		printf("%d -> ", temp->data);
		temp = temp->next;
	}
	printf("NULL \n");
}
//销毁链表
Node* freeList(Node* head) {
	Node* p = head;
	while (p != NULL) {
		Node* temp = p;
		p = p->next;
		free(temp);
		temp = NULL;
	}
	return NULL;
}
//循环
void reverseList(Node* head) {
	if (head == NULL)
	{
		printf("链表为空\n"); return;
	}
	Node* prev = NULL;
	Node* current = head->next;
	Node* next = NULL;

	while (current != NULL) {
		next = current->next;
		current->next = prev;
		prev = current;
		current = next;
	}
	head->next = prev; //头节点反向

	
}
//递归反转
Node* reverseList_digui(Node* head) {

	if (head == NULL || head->next == NULL) {
		return head;
	}

	Node* newHead = reverseList_digui(head->next);
	head->next->next = head;
	head->next = NULL;

	return newHead;    //返回头节点
	

}

void findnode(Node* head) {
	int values;
	printf("输入要查找的数据\n");
	scanf("%d", &values);
	if(head==NULL||head->next==NULL)
		{
		printf("查找不存在\n"); return;
		}
	Node* temp = head->next;
	while (temp != NULL)
	{
		if (temp->data == values)
		{
			printf("数据存在\n");
			return;
		}
		temp = temp->next;
	}

	printf("查找不存在\n");

}

//判断成环
void whether_circle(Node* head) {
	if (head == NULL || head->next == NULL)
	{
		printf("链表为空\n"); return;
	}
	Node* temp1=head;
	Node* temp2=head;
	while (temp1->next != NULL && temp2->next != NULL)
	{
		temp1 = temp1->next;
		if (temp2->next)
		{
			if (temp2->next->next)
			{
				temp2 = temp2->next->next;
			}
			else {
				printf("链表非环\n");
				return;
			}
		}
		else {
			printf("链表非环\n"); return;
		}
		if (temp1 == temp2) {
			printf("链表有环\n");
			return;
		}
		
	}

}