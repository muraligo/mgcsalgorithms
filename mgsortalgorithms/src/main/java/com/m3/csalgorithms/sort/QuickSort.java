package com.m3.csalgorithms.sort;

class QuickSort {
    private int input[];
    private int length;

    public void sort(int[] numbers) {
        if (numbers == null || numbers.length == 0) return;
        input = numbers; 
        length = numbers.length; 
        quickSort(0, length - 1);
    }

    private void quickSort(int low, int high) {
        int i = low; int j = high;
        // pivot is middle index
        int pivot = input[low + (high - low) / 2];
        // Divide into two arrays
        while (i <= j) {
            while (input[i] < pivot) i++;
            while (input[j] > pivot) j--;
            if (i <= j) {
                // swap, then move index to next position on both sides
                int temp = input[i];
                input[i] = input[j];
                input[j] = temp;
                i++; j--;
            }
        }
        // calls quickSort() method recursively
        if (low < j)
            quickSort(low, j);
        if (i < high)
            quickSort(i, high);
    }
}
