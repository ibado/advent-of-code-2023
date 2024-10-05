#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

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
