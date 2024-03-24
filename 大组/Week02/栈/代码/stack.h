#pragma once
#include<stdio.h>
#include<string.h>
#include<stdlib.h>

typedef struct StackNode {

	char data;
	double number;
	struct StackNode* next;
}Node;


typedef struct LinkStack {
	Node* top;

}LinkStack;



void push(char p, LinkStack* list);
char pop(LinkStack* list);

void cleary(LinkStack* stack, Node* head);

//检查格式是否正确
void change(char* arr, LinkStack* stack);

int priority(char p);

