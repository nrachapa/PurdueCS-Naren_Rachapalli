#include <stdio.h>

int myprintf(const char *, ...);
int main() {
	int a = 106;
	char c = 'H';
	char s[6] = "World\0";
	int b = 902382, res = (b * -1);
	myprintf("%cello %s: a = %x and %X, b = -(%d) = %d\n", c, s, a, a, b, res);
        return 0;
}
