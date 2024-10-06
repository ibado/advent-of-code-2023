#include <stdlib.h>
#include <ctype.h>
#include "util.c"

#define SCHEMATIC_N 140

typedef struct {
	size_t x;
	size_t yi;
	size_t ye;
} NumCoord;

bool is_symbol(char s) {
	return s != '.' && !isdigit(s);
}

bool has_adjacent(NumCoord* nc, char schematic[SCHEMATIC_N][SCHEMATIC_N]) {
	if (nc->x > 0) {
		size_t yi = nc->yi == 0 ? 0 : (nc->yi - 1);
		size_t ye = nc->ye == (SCHEMATIC_N - 1) ? (SCHEMATIC_N - 1) : (nc->ye + 1);
		for (size_t j = yi; j <= ye; j++) {
			char xy = schematic[nc->x - 1][j];
			if (is_symbol(xy)) return true;
		}
	}

	if (nc->yi > 0 && is_symbol(schematic[nc->x][nc->yi - 1])) return true;
	if (nc->ye < (SCHEMATIC_N - 1) && is_symbol(schematic[nc->x][nc->ye + 1])) return true;

	if (nc->x < SCHEMATIC_N - 1) {
		size_t yi = nc->yi == 0 ? 0 : (nc->yi - 1);
		size_t ye = nc->ye == (SCHEMATIC_N - 1) ? (SCHEMATIC_N - 1) : (nc->ye + 1);
		for (size_t j = yi; j <= ye; j++) {
			char xy = schematic[nc->x + 1][j];
			if (is_symbol(xy)) return true;
		}
	}

	return false;
}

void fill_schematic(char schematic[SCHEMATIC_N][SCHEMATIC_N]) {
	FILE* f = open_file("../../input/3.txt");
	char* line = (char*) malloc(sizeof(char)*140);
	ssize_t len = 0;

	size_t i = 0;
	while (len != -1) {
		len = read_line(f, LINE_LEN, &line);
		if (len == -1) break;

		for (ssize_t j = 0; j < len; j++) {
			schematic[i][j] = line[j];
		}
		i++;
	}

	free(line);
	fclose(f);
}

int day3_part1() {

	char schematic[SCHEMATIC_N][SCHEMATIC_N];
	fill_schematic(schematic);

	int sum = 0;

	for(size_t i = 0; i < SCHEMATIC_N; i++) {
		for (size_t j = 0; j < SCHEMATIC_N; j++) {
			if (isdigit(schematic[i][j])) {
				size_t idx = 0;	
				char num[4] = {0};
				size_t yi = j;
				while (isdigit(schematic[i][j])) {
					num[idx] = schematic[i][j];
					idx++;
					if (j == SCHEMATIC_N - 1) break;
					j++;
				}
				NumCoord num_coord = { .x = i, .yi = yi, .ye = j - 1 };
				if (has_adjacent(&num_coord, schematic)) sum += atoi(num);
			}
		}
	}

	return sum;
}

int day3_part2() {
	char schematic[SCHEMATIC_N][SCHEMATIC_N];
	fill_schematic(schematic);

	int sum = 0;

	for(size_t i = 0; i < SCHEMATIC_N; i++) {
		for (size_t j = 0; j < SCHEMATIC_N; j++) {
			if (schematic[i][j] == '*') {
				// TODO: sum += calc_ratio(i, j, schematic);
			}
		}
	}

	return sum;
}
