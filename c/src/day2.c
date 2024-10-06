#include "util.c"
#include <ctype.h>
#include <string.h>

#define LINE_SIZE 128

typedef struct {
	int red;
	int green;
	int blue;
} Round;

int solve(void(*acc)(int* count, Round* r, int gid)) {
	FILE* f = open_file("../../input/2.txt");
	char* line = (char*) malloc(sizeof(char)*LINE_SIZE);
	ssize_t llen = 0;

	int count = 0;
	int gid = 1;
	while (llen != -1) {
		llen = read_line(f, LINE_SIZE, &line);
		if (llen == -1) break;
		int id = 0;
		Round r = { .red = 0, .green = 0, .blue = 0 };
		for (size_t i = 0; i < llen; i++) {
			char curr = line[i];
			if (isdigit(curr)) {
				if (id == 0) {
					while(line[i] != ':') i++;
					id = 1;
					continue;
				}
				size_t idx = 0;
				char num[3] = {0};
				while (isdigit(line[i])) {
					num[idx] = line[i];
					i++;
					idx++;
				}
				i++;
				idx = 0;
				char color[6] = {0};
				while (isalpha(line[i])) {
					color[idx] = line[i];
					i++;
					idx++;
				}
				int nnum = atoi(num);
				if (strcmp(color, "red") == 0) {
					if (nnum > r.red) r.red = nnum;
				} else if (strcmp(color, "green") == 0 ) {
					if (nnum > r.green) r.green = nnum;
				} else if (strcmp(color, "blue") == 0 ) {
					if (nnum > r.blue) r.blue = nnum;
				} else {
					puts("boom! bad parsing");
					printf("i: %ld, gid: %d, num: %d, color: %s\n", i, gid, nnum, color);
					exit(1);
				}
			}
		}
		acc(&count, &r, gid);
		gid++;
	}

	free(line);
	fclose(f);

	return count;
}

void solve_part1(int* count, Round* r, int gid) {
	if (r->red <= 12 && r->green <= 13 && r->blue <= 14) *count += gid;
}

void solve_part2(int* count, Round* r, int _gid) {
	*count += r->red * r->green * r->blue;
}

int day2_part1() {
	return solve(solve_part1);
}

int day2_part2() {
	return solve(solve_part2);
}
