/* myprintf.c */

#include <stdlib.h>
#include <string.h>
#include <stdio.h>

void printx(int, int);	// ARM assembly function to handle %x and %X conversion
void printd(int);	// ARM assembly function to handle %d conversion next weekZZ

int myprintf(const char * format, ...)
{
	int *arg = (int *) (&format);
	arg++;
	for (int i = 0; format[i] != '\0'; i++) {
		if (format[i] == '%') {
			switch (format[++i]) {
				case '%':
					putchar(format[i]);
					break;
				case 'c':
					putchar(*arg++);
					break;
				case 's': ;
					 char *buf = *((char **) arg++);
					for (int j = 0; buf[j] != '\0'; j++) {
						putchar(buf[j]);
					}
					break;
				case 'x':
					printx(*arg++,0);
					break;
				case 'X':
					printx(*arg++,1);
					break;
				case 'd':
					printd(*arg++);
					break;
			}
		
		} else {
			putchar(format[i]);
		}


	
	}
	return 0;
}
