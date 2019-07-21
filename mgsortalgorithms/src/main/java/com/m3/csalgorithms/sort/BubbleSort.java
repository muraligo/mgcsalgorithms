package com.m3.csalgorithms.sort;

/**
 * You can take first element from array, 
 * and start comparing it with other element, 
 * swapping where it's lesser than the number you are comparing. 
 * You can start this comparison from start or from end, 
 * as we have compared elements from end in our bubble sort example.
 * We need at least N pass to sort the array completely and 
 * at the end of each pass one elements are sorted in its proper position.
 *
 * Compared to other sorting algorithms like quicksort, merge sort or 
 * shell sort, bubble sort performs poorly. 
 * This algorithm has average case complexity of O(NLogN), 
 * while average case complexity of bubble sort O(n^2). 
 * Ironically in best case bubble sort do better than 
 * quicksort with O(n) performance.  
 * Bubble sort is three times slower than quicksort or mergesort 
 * even for n = 100 but it's easier to implement and remember. 
 * Here is the summary of bubble sort performance and complexity :
 * Worst case 	O(n^2)
 * Best case 	O(n)
 * Average case	O(n^2)
 */
class BubbleSort {
    public void bubbleSort(int[] unsorted) {
        for (int i = 0; i < unsorted.length; i++) {
            for (int j = unsorted.length - 1; j > i; j--) {
                if (unsorted[j] < unsorted[j-1]) {
                	int temp = unsorted[j];
                	unsorted[j] = unsorted[j-1];
                	unsorted[j-1] = temp;
                }
            }
        }
    }

    public void bubbleSortImproved(int[] unsorted) {
        boolean swapped = true;
        int last = unsorted.length - 2;
        // only continue if swapping of number has occurred
        while (swapped) {
            swapped = false;
            for (int i = 0; i <= last; i++) {
                if (unsorted[i] > unsorted[i+1]) {
                    // pair is out of order, swap them swap(names, i, i + 1); 
                	int temp = unsorted[i];
                	unsorted[i] = unsorted[i+1];
                	unsorted[i+1] = temp;
                	swapped = true; // swapping occurred
                }
            }
            // after each pass largest element moved to end of array
            last--;
        }
    } 
}
