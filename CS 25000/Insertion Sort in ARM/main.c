#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void insertionSort(int *, int);
int checkOrder(int *, int);

int main(int argc, const char * argv[]) {

    if (argc < 2) {
        printf("Usage: %s <N>\n", argv[0]);
        puts("Program generates N random integers then sorts them.\n");
        return 1;
    }

    int nNumbers = atoi(argv[1]);
    if (nNumbers < 1 || nNumbers > 999) {
        puts("Please choose a value greater than 0 and less than 1000.");
        return 0;
    }

    srand(time(NULL));
    int * array = (int *)(malloc(sizeof(int)*nNumbers));
    if (array != NULL) {
        int i = 0;
        printf("The unsorted array is: ");
        for (i = 0; i < nNumbers; ++i) {
            array[i] = rand() % 2333;
            printf("%d ", array[i]);
        }
    }
    else {
        puts("No memory allocated for array.\n");
        return 0;
    }
    printf("\n");

    insertionSort(array, nNumbers);
    int isInorder = checkOrder(array, nNumbers);
    printf("The array is %s order after sorting.\n", (isInorder ? "in" : "not in"));

    free(array);
}

int checkOrder(int *array, int n) {
    int i = 0;
    int noError = 1; // set noError to True
    printf("The sorted array is:   ");
    for (i = 0; i < n-1; ++i) {
        if (array[i] > array[i+1]) {
            noError = 0; // array element out of order, set noError to False
        }
        printf("%d ",array[i]);
    }
    printf("%d\n",array[n-1]);

    return noError;
}
