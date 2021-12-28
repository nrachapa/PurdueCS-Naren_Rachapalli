#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* sub_string(char *in_string, int start_index, int end_index)
{
  char *out_string, end = '\0';
  out_string = (char *) malloc(sizeof(char) * (end_index - start_index + 1));
  for (int i = start_index - 1; i < end_index; i++) {
	  strncat(out_string, &in_string[i], 1);
//	  printf("func call: %s\n", out_string);
  }
  strncat(out_string, &end, 1);
// Your C code here; you may call strlen() to find the length of the User input string
// Have only the one return statement shown here.

  return out_string;
}
