package problems.arrays.level4;



/*
Given two sorted arrays, find their union and intersection.
Input : arr1[] = {1, 3, 4, 5, 7}
       arr2[] = {2, 3, 5, 6} 
Output : Union : {1, 2, 3, 4, 5, 6, 7} 
        Intersection : {3, 5}

Input : arr1[] = {2, 5, 6}
       arr2[] = {4, 6, 8, 10} 
Output : Union : {2, 4, 5, 6, 8, 10} 
        Intersection : {6}

* */
public class IntersectionOfTwoSortedArrays {
	void printIntersection(int arr1[], int arr2[], int m, int n)
    {
        int i = 0, j = 0;
        while (i < m && j < n) {
            if (arr1[i] < arr2[j])
                i++;
            else if (arr2[j] < arr1[i])
                j++;
            else {
                System.out.print(arr2[j++] + " ");
                i++;
            }
        }
    }

}
