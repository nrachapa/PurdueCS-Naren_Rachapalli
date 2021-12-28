//Naren Rachapalli
//CS 240 
//April 4, 2021
//Professor Zhiquan Li

#define _GNU_SOURCE
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include "linked_list.h"
#include "word_list.h"


//Sizes 
#define L2_SIZE 2
#define L3_SIZE 9
#define L4_SIZE 18
#define ENTITY_SIZE 13

//helper functions statements
void remove_leading_trailing_whitespace(char *);





int main(int argc, char *argv[]) {
    bool ctag = false, ftag = false;
    char htmlname[20] = "", outputname[20] = "", freqWord[20] = ""; 
    //Check Arguments
    if (argc < 3) {
        fprintf(stderr, "basic usage: %s <html filename> <text filename>\n", argv[0]);
        return 1;
    }
    for (int i = 1; i < argc; i++) {
      if (argv[i][0] == '-') {
        if (argv[i][1] == 'c') {
          ctag = true;
        } else if (argv[i][1] == 'f') {
          ftag = true;
          strcat(freqWord, argv[i + 1]);
          i++;
        }
      } else if (strstr(argv[i], ".html") != NULL) {
        strcat(htmlname, argv[i]);
      } else if (strstr(argv[i], ".txt") != NULL) {
        strcat(outputname, argv[i]);
      }
    }


    //Open Files
    FILE *hl;
    FILE *tf; //hl -> html file, tf -> text file
    hl = fopen(htmlname, "r");
    if (hl == NULL) {
        fprintf(stderr, "HTML File Open Failure\n");
        return 0;
    }
    tf = fopen(outputname, "w+");
    if (tf == NULL) {
        fprintf(stderr, "Text File Open Failure\n");
        return 0;
    }

    //levels

    //level 1
    const char *start_tag_L1 = "<html>", *end_tag_L1 = "</html>";
    
    //level 2
    const char *start_tags_L2[L2_SIZE] = {"<title", "<body"};
    const char *end_tags_L2[L2_SIZE] = {"</title>", "</body>"};
    
    //level 3
    const char *start_tags_L3[L3_SIZE] = {"<h1", "<h2", "<h3", "<h4", "<h5", "<h6", "<p", "<ul", "<ol"};
    const char *end_tags_L3[L3_SIZE] = {"</h1>", "</h2>", "</h3>", "</h4>", "</h5>", "</h6>", "</p>", "</ul>", "</ol>"};
    
    //level 4
    const char *start_tags_L4[L4_SIZE] = {"<li", "<b", "<i", "<strong", "<em", "<small", "<code", "<pre", "<sub", "<sup", "<a", "<label", "<abbr", "<q", "<blockquote", "<div", "<cite", "<addr"};
    const char *end_tags_L4[L4_SIZE] = {"</li>", "</b>", "</i>", "</strong>", "</em>", "</small>", "</code>", "</pre>", "</sub>", "</sup>", "</a>", "</label>", "</abbr>", "</q>", "</blockquote>", "</div>", "</cite>", "</addr>"};

    //entities
    const char *entity_name[ENTITY_SIZE] = {"&nbsp;", "&lt;", "&gt;", "&amp;", "&quot;", "&apos;", "&cent;", "&pound;", "&yen;", "&euro;", "&copy;", "&reg;", "&sect;"};
    const char *entity_num[ENTITY_SIZE] = {"&#160;", "&#60;", "&#62;", "&#38;", "&#34;", "&#39;", "&#162;", "&#163;", "&#165;", "&#8364;", "&#169;", "&#174;", "&#167;"};
    const char *entity_replacement[ENTITY_SIZE] = {"\u00A0", "<", ">", "&", "\"", "\'", "cent", "pound", "yen", "euro", "copyright", "trademark", "section"};
    
    //basic characters
    const char new_line = '\n', white_space = ' ';
    bool in_tags = false;


    //Read from html file into a single line
    char text[5000] = "\0";
    char line[1000];
    if (fgets(line, sizeof(line), hl) == NULL) {
        fclose(hl);
        return 0;

    } else {
        remove_leading_trailing_whitespace(line);
        if (strcasestr(line, "<!DOCTYPE html>") == NULL) {
            if (strcmp(line, "") != 0 && strcmp(line, " ") != 0 && strcmp(line, "\n") != 0) {
                strcat(text, line);
                strncat(text, &new_line, 1);
            }
        }
        while (fgets(line, sizeof(line), hl)) {
            remove_leading_trailing_whitespace(line);
            if (strcasestr(line, "<!DOCTYPE html>") == NULL) {
                    if (strcmp(line, "") != 0 && strcmp(line, " ") != 0 && strcmp(line, "\n") != 0) {
                        strcat(text, line);
                        strncat(text, &new_line, 1);
                    }
            }
        }
    }
    fclose(hl);
    
    char buffer[3000] = "\0";
    
    //testing purposes
    //strcpy(buffer, text);

    char output_text[3000] = "\0";
  
    //parse between tags
    char tag[250] = "\0";
    long int a = strlen(text);
    for (int i = 0; i < a; i++) {
      if (in_tags) {
        if (text[i] != '>') {
          strncat(tag, &text[i], 1);
        } else {
          strncat(tag, &text[i], 1);
          bool found = false; 
          if (!found) {
            if (strcasestr(tag, start_tag_L1) != NULL) {
              found = true;
            } else if(strcasestr(tag, end_tag_L1) != NULL) {
              found = true;
            }
          }
          if (!found) {
            for (int j = 0; j < L2_SIZE; j++) {
              if (strcasestr(tag, start_tags_L2[j]) != NULL) {
                int index = strlen(start_tags_L2[j]);
                if (tag[index] == ' ' || tag[index] == '>') {
                  found = true;
                }
              } else if(strcasestr(tag, end_tags_L2[j]) != NULL) {
                found = true;
              }
            }
          }
          if (!found) {
            for (int j = 0; j < L3_SIZE; j++) {
              if (strcasestr(tag, start_tags_L3[j]) != NULL) {
                int index = strlen(start_tags_L3[j]);
                if (tag[index] == ' ' || tag[index] == '>') {
                  found = true;
                }
              } else if(strcasestr(tag, end_tags_L3[j]) != NULL) {
                found = true;
              }
            }
          }
          if (!found) {
            for (int j = 0; j < L4_SIZE; j++) {
              if (strcasestr(tag, start_tags_L4[j]) != NULL) {
                int index = strlen(start_tags_L4[j]);
                if (tag[index] == ' ' || tag[index] == '>') {
                  found = true;
                }
              } else if(strcasestr(tag, end_tags_L4[j]) != NULL) {
                found = true;
              }
            }
          }
          if (found) {
            if (strcasestr(tag, "title=\"") != NULL) {
              bool in_quotes = false;
              for (int z = 0; z < strlen(tag); z++) {
                if (in_quotes) {
                  if (tag[z] == '\"') {
                    strncat(buffer, &white_space, 1);
                    in_quotes = false;
                  } else {
                    strncat(buffer, &tag[z], 1);
                  }
                } else {
                  if (tag[z] == '\"') {
                    in_quotes = true;
                  }
                }
              }
            }
          } else {
            if (tag[1] != '/') {
              char fake_end[50] = "\0", dash = '/', fir = tag[0];
              strncat(fake_end, &fir, 1);
              strncat(fake_end, &dash, 1);
              strcat(fake_end, tag + 1);
              if (strcasestr(text + i + 1, fake_end) != NULL) {
                i++;
                while (text[i] != '>') i++;
              }
              fake_end[0] = '\0';
            }
          }
          tag[0] = '\0';
          in_tags = false;
        }
      } else {
        if (text[i] == '<') {
          strncat(tag, &text[i], 1);
          in_tags = true;
        } else {
          strncat(buffer, &text[i], 1);
        }
      }
    }




    //entity # & name swap
    bool in_entity = false;
    char entity[50] = "\0";
    for (int i = 0; i < strlen(buffer); i++) {
      if (in_entity) {
        if (buffer[i] == ';') {
          strncat(entity, &buffer[i], 1);
          bool found = false;
          for (int j = 0; j < ENTITY_SIZE; j++) {
            if (strcmp(entity, entity_name[j]) == 0 || strcmp(entity, entity_num[j]) == 0) {
              found = true;
              strcat(output_text, entity_replacement[j]);
              break;
            }
          }
          if (!found) {
            strncat(output_text, &white_space, 1);
          }
          entity[0] = '\0';
          in_entity = false;
          
        } else {
          strncat(entity, &buffer[i], 1);
        }
      } else {
        if (buffer[i] != '&') {
          strncat(output_text, &buffer[i], 1);
        } else {
          in_entity = true;
          strncat(entity, &buffer[i], 1);
        }
      }
    }
    fputs(output_text, tf);
    fclose(tf);





    //word count and frequency
    if (ctag || ftag) {
      struct Word_List *dict = NULL;
      char *word = strtok(output_text, " \u00A0,.!?<>&#$%^*@()\'\"\n");
      dict = create_node(word);
      word = strtok(NULL, " \u00A0,.!?<>&#$%^*@()\'\"\n");
      while (word) {
        struct Word_List *traverse = dict;
        bool exists = false;
        while (traverse) {
          if (strcasecmp(traverse->word, word) == 0) {
            traverse->freq += 1;
            exists = true;
            break;
          }
          traverse = traverse->next;
        }
        if (!exists) {
          struct Word_List *new_node = create_node(word);
          new_node->next = dict;
          dict = new_node; 
        }
        word = strtok(NULL, " \u00A0,.!?<>&#$%^*@()\'\"\n");
      }
      //print_dict(dict);
      free(word);
      word = NULL;
      if(ctag && !ftag) {
        int count = word_count(dict);
        printf("Word Count: %d.\n", count);
      } else if (!ctag && ftag) {
        int count = freq_count(dict, freqWord);
        format_string(freqWord);
        printf("%s Frequency: %d.\n", freqWord, count);
      } else {
        int word_c = word_count(dict);
        int freq = freq_count(dict, freqWord);
        format_string(freqWord);
        printf("Word Count: %d. %s Frequency: %d.\n", word_c, freqWord, freq);
      }
      free_word_list(dict);
    }
    
    
    return 0; //end of main 
}





//helper functions definitions
void remove_leading_trailing_whitespace(char *temp) {
    char newstr[200];
    int count = 0, k = 0;
    while (temp[count] == ' ' || temp[count] == '\t') {
        count++;
    }
    for (int j = count; temp[j] != '\0'; j++) {
        newstr[k] = temp[j];
        k++;
    }
    if (newstr[k - 1] == '\n') {
        newstr[k - 1] = '\0';
    }
    else {
        newstr[k] = '\0';
    }
    strcpy(temp, newstr);
    return;
}





