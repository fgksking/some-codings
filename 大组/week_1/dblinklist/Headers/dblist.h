#pragma once

#include<stdio.h>
#include<string.h>
#include<stdlib.h>

typedef struct Node {
    int data;
    struct Node* prev;
    struct Node* next;
} Node;


void menu1() {
    printf("1-初始化链表  2--头插数据\n"
        "3-尾插数据   4--删除节点\n"
        "5-销毁链表   6--打印   7--退出\n");


}

// 初始化双链表
Node* initList() {
    Node* head = (Node*)malloc(sizeof(Node));
    head->data = -1; // 头结点不存储数据
    head->prev = NULL;
    head->next = NULL;
    return head;
}

// 在双链表头部插入节点
void insertAtBeginning(Node* head) {
    if (head == NULL)
    {
        printf("链表为空\n");
        return;
    }
    int data;
    printf("输入数据\n");
    scanf("%d", &data);
    Node* newNode = (Node*)malloc(sizeof(Node));
    newNode->data = data;

    newNode->next = head->next;
    newNode->prev = head;
    if (head->next != NULL) {
        head->next->prev = newNode;
    }
    head->next = newNode;
}

// 在双链表尾部插入节点
void insertAtEnd(Node* head) {
    if (head == NULL)
    {
        printf("链表为空\n");
        return;
    }
    int data;
    printf("输入数据\n");
    scanf("%d", &data);
    Node* newNode = (Node*)malloc(sizeof(Node));
    newNode->data = data;
    newNode->next = NULL;

    Node* p = head;
    while (p->next != NULL) {
        p = p->next;
    }

    p->next = newNode;
    newNode->prev = p;
}

// 删除节点
void deleteNode(Node* head) {
    if (head == NULL)
    {
        printf("链表为空\n");
        return;
    }
    int data;
    printf("输入要删除的数据\n");
    scanf("%d", &data);
    Node* p = head->next;
    while (p != NULL) {
        if (p->data == data) {
            p->prev->next = p->next;
            if (p->next != NULL) {
                p->next->prev = p->prev;
            }
            free(p);
            return;
        }
        p = p->next;
    }
}

// 打印双链表
void printList(Node* head) {
    if (head == NULL || head->next == NULL)
    {
        printf("链表为空\n");
        return;
    }
    Node* p = head->next;
    while (p != NULL) {
        printf("%d ", p->data);
        p = p->next;
    }
    printf("\n");
}

// 销毁双链表内存
Node* freeList(Node* head) {
    if (head == NULL)
    {
        printf("链表为空\n");
    }
    Node* p = head;
    while (p != NULL) {
        Node* temp = p;
        p = p->next;
        free(temp);
        temp = NULL;
    }
    return NULL;
}