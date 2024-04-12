#pragma once
#define _CRT_SECURE_NO_WARNINGS
#include<stdio.h>
#include<string.h>
#include<malloc.h>
#include <time.h>



//≤Â»Î≈≈–Ú
void InsertSort(int arr[],int n);

void fastSort(int arr[], int left, int right);
void CountSort1(int* arr, int n);
//πÈ≤¢
void MergeSort(int* arr, int n1, int n2);
void merge(int arr[], int l, int m, int r);

int getMax(int arr[], int n);
void countSort(int arr[], int n, int exp);
void radixSort(int arr[], int n);
void sortd(int arr[], int n);
