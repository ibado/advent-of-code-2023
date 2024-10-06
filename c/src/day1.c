#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include "util.c"

const char* numbers[] = {
    "one", 
    "two", 
    "three", 
    "four", 
    "five", 
    "six", 
    "seven", 
    "eight",
    "nine"
};

int day1_part1() {
	FILE* f = open_file("../../input/1.txt");
	char* line = (char*) malloc(sizeof(char)*50);
	ssize_t llen = 0;
	int sum = 0;

	while (llen != -1) {
		llen = read_line(f, 50, &line);
		if (llen == -1) break;
		char first = 0;
		char last = 0;
		for (size_t i = 0; i < llen; i++) {
			if (isdigit(line[i])) {
				if (first == 0) first = line[i];
				last = line[i];
			}
		}
		char num[3] = {0};
		sprintf(num, "%c%c", first, last);
		sum += atoi(num);
	}

	free(line);
	fclose(f);
	return sum;
}

int day1_part2() {
	FILE* f = open_file("../../input/1.txt");
	char* line = (char*) malloc(sizeof(char)*50);
	ssize_t llen = 0;
	int sum = 0;

	while (llen != -1) {
		llen = read_line(f, 50, &line);
		if (llen == -1) break;
		int first = 0;
		int last = 0;
		for (size_t i = 0; i < llen; i++) {
			if (isdigit(line[i])) {
				int t = line[i] - '0';
				if (first == 0) first = t;
				last = t;
				continue;;
			}
			for (int n = 0; n < 9; n++) {
				const char* num = numbers[n];
				if (str_start_with(&line[i], num)) {
					int t = n + 1;
					if (first == 0) first = t;
					last = t;
					continue;
				}
			}
		}
		char num[3] = {0};
		sprintf(num, "%d%d", first, last);
		sum += atoi(num);
	}

	free(line);
	fclose(f);
	return sum;
}
