#include <stdlib.h>
#include <stdio.h>
#include <string.h>

char *convert_ascii_to_binary(char *);
char find_parity(char *);
int main(int argc, const char *argv[]) {
    if (argc < 3)
    {
        fprintf(stderr, "usage: %s <filename> <filename>\n", argv[0]);
        return 1;
    }
    
    FILE *input_ptr, *output_ptr;
    input_ptr = fopen(argv[1], "rb");
    if (input_ptr == NULL) {
        fprintf(stderr, "File open failure\n");
        return 1;
    }
    int count = 0;
    char text[100] = "";
    char buffer[100] = "";
    char parity[100] = "";
    int c = 0;
    while (c != EOF) {
        c = fgetc(input_ptr);
        if (strlen(buffer) < 4) {
            char val = (char) c;
            strncat(buffer, &val, 1);
            strncat(text, &val, 1);
        } else {
            char *binary = convert_ascii_to_binary(buffer);
            char pval = find_parity(binary);
            free(binary);
            strncat(parity, &pval, 1);
            count += strlen(buffer);
            //printf("text: {%s}\nbinary: {%s}\nbuffer: {%s}\nparity: {%s}\n\n", text, binary, buffer, parity);
            strcpy(buffer, "");
            char val = (char) c;
            strncat(buffer, &val, 1);
            if (strlen(parity) == 8) {
              int sum = 0;
              int n = 0;
              for (int i = strlen(parity) - 1; i >= 0; i--) {
                if (parity[i] == '1') {
                  if (n == 0) {
                    sum += 1;
                  } else if(n == 1) {
                    sum += 2;
                  } else if(n == 2) {
                    sum += 4;                
                  } else if(n == 3) {
                    sum += 8;
                  } else if(n == 4) {
                    sum += 16;
                  } else if(n == 5) {
                    sum += 32;
                  } else if(n == 6) {
                    sum += 64;
                  } else if(n == 7) {
                    sum += 128;
                  }
                }
                n++;
              }
              sum /= 2;
              char ch = (char) sum;
              strncat(text, &ch, 1);
              //printf("text: {%s}\nparity: {%s}\nchar: {%c} {%d}\n\n", text, parity, ch, sum);
              strcpy(parity, "");
              long old = ftell(input_ptr);
              fseek(input_ptr, 0, SEEK_END);
              if (old != ftell(input_ptr)) {
                char c = ' ';
                strncat(text, &c, 1);
                fseek(input_ptr, old, SEEK_SET);
              } 
            } else {
              strncat(text, &val, 1);
            }
        }
    }
    fclose(input_ptr);

    if (count < 32) {
        fprintf(stderr, "File format violation\n");
        return 1;
    }

    output_ptr = fopen(argv[2], "wb");
    
    fwrite(text, strlen(text), 1, output_ptr);
    
    fclose(output_ptr);
    
    
    return 0;
}

char *convert_ascii_to_binary(char *input) {
    if (input == NULL) return 0;
    size_t length = strlen(input);
    char *output = (char *) malloc(length * sizeof(char *) + 1);
    output[0] = '\0';
    for (size_t i = 0; i < length; i++) {
        char character = input[i];
        for (int j = 7; j >= 0; j--) {
            if (character & (1 << j)) {
                strcat(output, "1");
            } else {
                strcat(output, "0");
            }
        }
        if (i != length - 1) strcat(output, " ");
    }
    return output;
}

char find_parity(char *input) {
    if (input == NULL) return 0;
    int num = 0;
    for (size_t i = 0; i < strlen(input); i++) {
        if (input[i] == '1') {
            num++;
        }
    }
    if (num % 2 == 0) {
        return '0';
    } else {
        return '1';
    }
}
