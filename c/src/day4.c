#include "util.c"
#include <ctype.h>

#define WININGS 10

void map_num(char* line, int* num, size_t* idx) {
	while (isdigit(line[*idx])) {
		int n = line[*idx] - '0';
		*num = *num == 0 ? n : *num * 10 + n;
		(*idx)++;
	}
}

int day4_part1() {
	FILE* f = open_file("../../input/4.txt");
	char* line = (char*)malloc(sizeof(char)*LINE_LEN);
	ssize_t len = 0;

	int sum = 0;
	while ((len = read_line(f, LINE_LEN, &line)) != -1) {
		int winnings[WININGS] = {0};
		size_t widx = 0;
		size_t i = 10;
		while (line[i] != '|') {
			int num = 0;
			map_num(line, &num, &i);
			if (num != 0) {
				winnings[widx] = num;
				widx++;
			} else i++;
		}


		int points = 0;
		for (size_t j = i; j < (size_t)len; j++) {
			int num = 0;
			map_num(line, &num, &j)	;
			if (num != 0) {
				for (size_t i = 0; i < WININGS; i++) {
					if (num == winnings[i]) {
						points = points == 0 ? 1 : points * 2;
					}
				}
			}
		}
		sum += points;
	}

	free(line);
	fclose(f);

	return sum;
}

int day4_part2() {
	return 0;
}
