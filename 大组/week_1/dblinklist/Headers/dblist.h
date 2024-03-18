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
    printf("1-��ʼ������  2--ͷ������\n"
        "3-β������   4--ɾ���ڵ�\n"
        "5-��������   6--��ӡ   7--�˳�\n");


}

// ��ʼ��˫����
Node* initList() {
    Node* head = (Node*)malloc(sizeof(Node));
    head->data = -1; // ͷ��㲻�洢����
    head->prev = NULL;
    head->next = NULL;
    return head;
}

// ��˫����ͷ������ڵ�
void insertAtBeginning(Node* head) {
    if (head == NULL)
    {
        printf("����Ϊ��\n");
        return;
    }
    int data;
    printf("��������\n");
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

// ��˫����β������ڵ�
void insertAtEnd(Node* head) {
    if (head == NULL)
    {
        printf("����Ϊ��\n");
        return;
    }
    int data;
    printf("��������\n");
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

// ɾ���ڵ�
void deleteNode(Node* head) {
    if (head == NULL)
    {
        printf("����Ϊ��\n");
        return;
    }
    int data;
    printf("����Ҫɾ��������\n");
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

// ��ӡ˫����
void printList(Node* head) {
    if (head == NULL || head->next == NULL)
    {
        printf("����Ϊ��\n");
        return;
    }
    Node* p = head->next;
    while (p != NULL) {
        printf("%d ", p->data);
        p = p->next;
    }
    printf("\n");
}

// ����˫�����ڴ�
Node* freeList(Node* head) {
    if (head == NULL)
    {
        printf("����Ϊ��\n");
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