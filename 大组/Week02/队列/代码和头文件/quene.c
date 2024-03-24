#define _CRT_SECURE_NO_WARNINGS
#include<stdio.h>
#include<string.h>
#include<stdlib.h>


typedef struct Node {
	void* data;
	struct Node* next;
} Node;

// ������нṹ
typedef struct {
	Node* front;
	Node* rear;
} Queue;


void initQueue(Queue* queue) {
    queue->front = NULL;
    queue->rear = NULL;
}

// �ж϶����Ƿ�Ϊ��
int isEmpty(Queue* queue) {
    return queue->front == NULL;
}

// ���
void enqueue(Queue* queue,void* data) {
   
    Node* newNode = (Node*)malloc(sizeof(Node));
    
    newNode->data = data;
    newNode->next = NULL;

    if (isEmpty(queue)) {
        queue->front = newNode;
        queue->rear = newNode;
    }
    else {
        queue->rear->next = newNode;
        queue->rear = newNode;
    }
}

// ����
void* dequeue(Queue* queue) {
    if (isEmpty(queue)) {
        printf("����Ϊ�գ��޷�����\n");
        return NULL;
    }

    void* data = queue->front->data;
    Node* temp = queue->front;

    if (queue->front == queue->rear) {
        queue->front = NULL;
        queue->rear = NULL;
    }
    else {
        queue->front = queue->front->next;
    }

    free(temp);
    return data;
}

// ��ȡ��ͷԪ��
void* peek(Queue* queue) {
    if (isEmpty(queue)) {
        printf("����Ϊ�գ��޷���ȡ��ͷԪ��\\n");
        return NULL;
    }

    return queue->front->data;
}

// ��ӡ����Ԫ�أ��ٶ�Ԫ����int,char���ͣ�
void printQueue(Queue* queue,int make) {
    if (isEmpty(queue)) {
        printf("����Ϊ��\n");
        return;
    }

    Node* current = queue->front;
    printf("����Ԫ��Ϊ: ");
    if (make == 1) {
        while (current != NULL) {
            printf("%d ", (int*)(current->data));
            current = current->next;
        }
        printf("\n");
    }
    else if (make == 2) {
        while (current != NULL) {
            printf("%s ", ( (char*)(current->data)));
            current = current->next;
        }
        printf("\n");
    }
}

void delete(Queue* queue) {
    if (isEmpty(queue)) {
        printf("����Ϊ��\n");
        return;
    }

    Node* current = queue->front;
    while (current != NULL) {
        
        current = current->next;
        free(queue->front);
        queue->front = current;
    }
    current = NULL;
    queue->front = NULL;
    queue->rear = NULL;
}

void menu1() {
    printf("1���   2����\n3��ȡͷԪ��    4��ӡ����    5���ٶ���\n");

}
int menu() {
    printf("1.���д��淽ʽ��int      2.���д洢��ʽ���ַ���\n");
    int a;
    scanf("%d", &a);
    if (a == 1) {
        return 1;
    }
    else if (a == 2) {
        return 2;
    }
    else {
        return 0;
    }
}

int main() {

   
   while (1) {
        Queue queue;
        initQueue(&queue);
        int choice;
        int make = menu();
        if (make == 0)
        {
            printf("�������\n");
            continue;
        }
       

 /*   char p[20];
    scanf("%s", p);
    int a = strlen(p);
    char* m = p;
    int b = sizeof(p);
    printf("%d\n", a);
    printf("%d\n", m) */
   
       while (1) {
         menu1();
         printf("����\n");
         scanf("%d", &choice);
         int a; char b[20];
         void* data;
         int juge;
         int number;
         switch (choice) {
         case 1:
             if (make == 1) {
                 while (scanf("%d", &number) != 1)
                 {
                     while (getchar() != '\n')
                     {
                         printf("enter a int number.\n");
                     }
                 }
                 printf("%d\n", number);
                 enqueue(&queue, number);
             }
             else {

                 scanf("%s", b);
                 enqueue(&queue, b);
             }
             break;
         case 2:
             
             data=dequeue(&queue);
             if (make == 1) {
                 printf("%d", (int*)data);
             }
             else {
                 printf("%s", (char*)data);
             }
             
             break;
         case 3:
             data=peek(&queue);
             if (make == 1) {
                 printf("%d", (int*)data);
             }
             else {
                 printf("%s", (char*)data);
             }

             break;
         case 4:
             printQueue(&queue, make);
             break;
         case 5:
             delete(&queue);
             break;
         default:
             printf("�������\n");
             break;
         
         
         
         }






     }
   }


}



