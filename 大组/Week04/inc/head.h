#pragma once
#define _CRT_SECURE_NO_WARNINGS
#include<stdio.h>
#include<string.h>
#include<malloc.h>


typedef struct Node {
	int data;
	struct Node* left;
	struct Node* right;
}Node;

typedef struct stackNode {
	Node* root;
	struct stackNode* next;
}stackNode;

typedef struct stack {
	stackNode* top;
}stack;


typedef struct QueueNode {
	Node* data;
	struct QueueNode* next;
} QueueNode;
typedef struct Queue {
	QueueNode* front;
	QueueNode* rear;
} Queue;

Node* insertNode(Node* head, int data);
void Free(Node* root);
void leverTree(Node* head);
Node* dequeue(Queue* queue);
void enqueue(Queue* queue, Node* data);
void prestackprint(Node* head);
void search(Node* head);
Node* delete(Node* root, int data);
void frontprint(Node* root);

