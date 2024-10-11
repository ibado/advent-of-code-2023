#ifndef _util_
#define _util_
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

#define LINE_LEN 128

ssize_t read_line(FILE* f, size_t len, char** line) {
	ssize_t line_len = getline(line, &len, f);
	if (line_len != -1) {
		(*line)[line_len - 1] = '\0';
	}

	return line_len;
}

FILE* open_file(const char* filename) {

	FILE* f = fopen(filename, "r");
	if (f == NULL) {
		puts("error reading file!");
		return NULL;
	}

	return f;
}

bool str_start_with(const char* str, const char* prefix) {
	return strncmp(str, prefix, strlen(prefix)) == 0;
}

int concat(int n, char c) {
	return n == 0 ? c - '0' : n * 10 + (c - '0');
}
#endif
