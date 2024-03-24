#pragma once
#include<stdio.h>
#include<string.h>
#include<stdlib.h>

typedef struct Node {
    void* data;
    struct Node* next;
} Node;

// 定义队列结构
typedef struct {
    Node* front;
    Node* rear;
} Queue;

