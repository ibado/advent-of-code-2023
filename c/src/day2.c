#include "util.c"
#include <ctype.h>
#include <string.h>

typedef struct {
	int red;
	int green;
	int blue;
} Round;

int solve(void(*acc)(int* count, Round* r, int gid)) {
	FILE* f = open_file("../../input/2.txt");
	char* line = (char*) malloc(sizeof(char)*LINE_LEN);
	ssize_t llen = 0;

	int count = 0;
	int gid = 1;
	while (llen != -1) {
		llen = read_line(f, LINE_LEN, &line);
		if (llen == -1) break;
		int id = 0;
		Round r = { .red = 0, .green = 0, .blue = 0 };
		for (ssize_t i = 0; i < llen; i++) {
			char curr = line[i];
			if (isdigit(curr)) {
				if (id == 0) {
					while(line[i] != ':') i++;
					id = 1;
					continue;
				}
				int num = 0;
				while (isdigit(line[i])) {
					num = concat(num, line[i]);
					i++;
				}
				i++;
				size_t idx = 0;
				char color[6] = {0};
				while (isalpha(line[i])) {
					color[idx] = line[i];
					i++;
					idx++;
				}
				if (strcmp(color, "red") == 0) {
					if (num > r.red) r.red = num;
				} else if (strcmp(color, "green") == 0 ) {
					if (num > r.green) r.green = num;
				} else if (strcmp(color, "blue") == 0 ) {
					if (num > r.blue) r.blue = num;
				} else {
					puts("boom! bad parsing");
					printf("i: %ld, gid: %d, num: %d, color: %s\n", i, gid, num, color);
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

void solve_part2(int* count, Round* r, int) {
	*count += r->red * r->green * r->blue;
}

int day2_part1() {
	return solve(solve_part1);
}

int day2_part2() {
	return solve(solve_part2);
}
