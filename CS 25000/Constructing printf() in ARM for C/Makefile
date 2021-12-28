test: main.o myprintf.o printx.o printd.o
	gcc -o test main.o myprintf.o printx.o printd.o
	./test
main.o: main.c
	gcc -c main.c
myprintf.o: myprintf.c
	gcc -c myprintf.c
printx.o: printx.s
	gcc -c printx.s
printd.o: printd.s
	gcc -c printd.s
clean:
	rm *.o
	rm test
