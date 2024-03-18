#pragma once
#include<stdio.h>
#include<string.h>
#include<stdlib.h>



typedef struct Node {  //�洢���ݵĽڵ�
	int data;
	struct Node* next;
}Node;

//typedef struct list {
//	Node* phead;        //ͷ�ڵ�
//	Node* pend;      //β�ڵ�
//}List;

void menu() {
	
	printf("*1--��ʼ������   2--ͷ������\n3--β������"
		"   4-ɾ���ڵ�\n5-��������    6--���ҽڵ�\n"
	"7--��ӡ����    8--��ת����\n9--�жϳɻ�    10--�˳�");
	printf(" \n��ѡ��\n");
}


Node* inital() {
	Node* L;
	L = (Node*)malloc(sizeof(Node));      //���ٿռ� 
	if (L == NULL) {                     //�ж��Ƿ񿪱ٿռ�ʧ�ܣ���һ�����б�Ҫ
		printf("����ռ�ʧ��");
		//exit(0);                  //���ٿռ�ʧ�ܿ��Կ���ֱ�ӽ�������
	}
	L->next = NULL; //ָ��ָ���
	L->data = 0;

	return L;
}


void head_insert(Node* node) {
	if (node == NULL)
	{
		printf("����Ϊ��\n"); return;
	}

	Node* L=(Node*)malloc(sizeof(Node));
	L->next = NULL;
	printf("������\n");
	int x;
	while (scanf("%d", &x) ==1) {
		if (node->next == NULL)
		{
			L->data = x;
			node->next = L;
			printf("�����ɹ�\n");
			
		}
		else {
			L->data = x;
			L->next = node->next;
			node->next = L;
			printf("�����ɹ�\n");
		}
		node->data++;//ͷ�ڵ��¼�ڵ���
		break;

	}

}
void end_insert(Node* p) {
	if (p == NULL)
	{
		printf("����Ϊ��\n"); return;
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
		p->data--;//ͷ�ڵ��¼�ڵ���
		cur->next = NULL;
		printf("�����ɹ�\n");
		break;
	}

}
void Delete_node(Node* L) {
	if (L== NULL)
	{
		printf("����Ϊ��\n"); return;
	}
	Node* p, * pre;    //preΪǰ����㣬pΪ���ҵĽ�㡣
	p = L->next;
	pre = L;
	int pos = 0;
	int x;
	printf("����Ҫɾ��������\n");
	scanf("%d", &x);

	while (p!=NULL) {    
		pos++; //��x��Ԫ��
		if (pos == x) {
			pre->next = p->next;
			free(p);
			printf("ɾ���ɹ�\n");
			L->data--;
			return;
		}
		pre = p;
		p = p->next;
	}
	printf("ɾ�������ݲ�����\n");
}

void print_node(Node* head) {
	if (head == NULL)
	{
		printf("����Ϊ��\n"); return;
	}
	Node* temp = head->next;
	while (temp != NULL) {
		printf("%d -> ", temp->data);
		temp = temp->next;
	}
	printf("NULL \n");
}
//��������
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
//ѭ��
void reverseList(Node* head) {
	if (head == NULL)
	{
		printf("����Ϊ��\n"); return;
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
	head->next = prev; //ͷ�ڵ㷴��

	
}
//�ݹ鷴ת
Node* reverseList_digui(Node* head) {

	if (head == NULL || head->next == NULL) {
		return head;
	}

	Node* newHead = reverseList_digui(head->next);
	head->next->next = head;
	head->next = NULL;

	return newHead;    //����ͷ�ڵ�
	

}

void findnode(Node* head) {
	int values;
	printf("����Ҫ���ҵ�����\n");
	scanf("%d", &values);
	if(head==NULL||head->next==NULL)
		{
		printf("���Ҳ�����\n"); return;
		}
	Node* temp = head->next;
	while (temp != NULL)
	{
		if (temp->data == values)
		{
			printf("���ݴ���\n");
			return;
		}
		temp = temp->next;
	}

	printf("���Ҳ�����\n");

}

//�жϳɻ�
void whether_circle(Node* head) {
	if (head == NULL || head->next == NULL)
	{
		printf("����Ϊ��\n"); return;
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
				printf("����ǻ�\n");
				return;
			}
		}
		else {
			printf("����ǻ�\n"); return;
		}
		if (temp1 == temp2) {
			printf("�����л�\n");
			return;
		}
		
	}

}