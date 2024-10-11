#include <stdbool.h>
#include <stdlib.h>
#include <ctype.h>
#include "util.c"
#include <limits.h>

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

void pass(size_t x, size_t y, char schematic[SCHEMATIC_N][SCHEMATIC_N], int adjacents[2], size_t* adjidx) {
	size_t yi = (int)(y - 3) >= 0 ? (y - 3) : 0;
	size_t ye = (y + 3) <= (SCHEMATIC_N - 1) ? (y + 3) : (SCHEMATIC_N - 1);

	int num = 0;
	bool hasnum = false;
	for (size_t i = yi; i <= ye; i++) {
		char xy = schematic[x][i];
		if (isdigit(xy)) {
			hasnum = true;
			num = concat(num, xy);
		} else if(hasnum) {
			if (i > y - 1) {
				if (*adjidx == 2) return;
				adjacents[*adjidx] = num;
				(*adjidx)++;
			}
			num = 0;
			hasnum = false;
			if (i >= y + 1) break;
		}
		if (i == y && !isdigit(schematic[x][y + 1])) break;
	}
	if (hasnum) {
		if (*adjidx == 2) return;
		adjacents[*adjidx] = num;
		(*adjidx)++;
	}
}

int calc_ratio(size_t x, size_t y, char schematic[SCHEMATIC_N][SCHEMATIC_N]) {
	int adjacents[2] = {0};
	size_t adjidx = 0;

	if (x < SCHEMATIC_N - 1) pass(x - 1, y, schematic, adjacents, &adjidx);

	if (x > 0) pass(x + 1, y, schematic, adjacents, &adjidx);

	if (y > 0 && isdigit(schematic[x][y - 1])) {
		size_t yy = (int)(y - 3) >= 0 ? y - 3 : 0;
		int num = 0;
		for (size_t i = yy; i < y; i++) {
			if (!isdigit(schematic[x][i])) {
				num = 0;
				continue;
			}
			num = concat(num, schematic[x][i]);
		}
		if (adjidx == 2)return 0;
		adjacents[adjidx] = num;
		adjidx++;
	}
	if (y < (SCHEMATIC_N - 1) && isdigit(schematic[x][y + 1])) {
		int num = 0;
		for (size_t i = y + 1; i <= y + 3; i++) {
			if (!isdigit(schematic[x][i])) break;
			num = concat(num, schematic[x][i]);
		}

		if (adjidx == 2) return 0;
		adjacents[adjidx] = num;
		adjidx++;
	}

	return adjacents[0] != 0 && adjacents[1] != 0 ? adjacents[0] * adjacents[1] : 0;
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
				int num = 0;
				size_t yi = j;
				while (isdigit(schematic[i][j])) {
					num = concat(num, schematic[i][j]);
					if (j == SCHEMATIC_N - 1) break;
					j++;
				}
				NumCoord num_coord = { .x = i, .yi = yi, .ye = j - 1 };
				if (has_adjacent(&num_coord, schematic)) sum += num;
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
				sum += calc_ratio(i, j, schematic);
			}
		}
	}

	return sum;
}
