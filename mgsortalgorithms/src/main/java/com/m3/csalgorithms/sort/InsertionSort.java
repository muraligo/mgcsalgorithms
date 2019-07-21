package com.m3.csalgorithms.sort;

/**
 * Insertion sort is based upon insertion of an element in a sorted list. 
 * To start, we assume that first element is already sorted. 
 * Then we pick the next element and put it in second place, 
 * we compare this number with the first element and if they are not in sorted order, 
 * we swap them. This gives a new sorted list of 2 elements. 
 * Now we pick the third element and put it in the 3rd place and 
 * compare it with the 2nd placed number, if they are not in sorted order, 
 * we swap them again, if all three elements are still not in sorted order 
 * then we again swap the 1st and 2nd element, 
 * now we have a sorted list of three numbers.
 *
 * So in best case, we just need one comparison and no swapping, 
 * while In the worst case we need to compare new number with each number 
 * in the sorted list i.e. k comparison, and k -1 swapping, 
 * where k is the length of sorted list.
 */
class InsertionSort {
    public void insertionSort(int[] unsorted) {
        for (int i = 1; i < unsorted.length; i++) {
            int current = unsorted[i];
            int j = i;
            // create the right place by moving elements
            while (j > 0 && unsorted[j-1] > current) {
                unsorted[j] = unsorted[j-1];
                j--;
            }
            // found the right place, insert now
            unsorted[j] = current;
        }
    }
}
