#include "util.c"
#include <limits.h>
#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

#define MAPS 7

typedef struct Range {
	long from;
	long to;
} Range;

bool range_contains(Range* r, long i) {
	return r->from <= i && i <= r->to;
}

typedef struct {
	Range src_range;
	Range dest_range;
} MapEntry;

#define TYPE MapEntry
#define TYPED(t) MapEntry ## t
#include "darray.h"

size_t parse_nums(long* out, char* line, size_t len) {
	size_t sc = 0;
	for (size_t i = 0; i < len; i++) {
		char curr = line[i];
		if (isdigit(curr)) {
			long n = 0;
			while(isdigit(line[i])) {
				n = concat(n, line[i]);
				i++;
			}
			out[sc++] = n;
		}
	}
	return sc;
}

long day5_part1() {
	FILE* f = open_file("../../input/5.txt");

	char* line = (char*) malloc(sizeof(char)*LINE_LEN);
	ssize_t len = 0;

	long seeds[32] = {0};
	MapEntryArr arr[MAPS] = { 0 };
	for (size_t i = 0; i < MAPS; i++) {
		arr[i] = MapEntryArr_new();
	}

	len = read_line(f, LINE_LEN, &line);
	size_t seedc = parse_nums(seeds, line, len);
	size_t arrc = 0;
	bool scanning = false;
	while ((len = read_line(f, LINE_LEN, &line)) != -1) {
		if (isdigit(line[0])) {
			scanning = true;
			long map[3] = { 0 };
			assert(parse_nums(map, line, len) == 3);
			Range dstrng = { .from = map[0], .to = map[0] + map[2] };
			Range srcrng = { .from = map[1], .to = map[1] + map[2] };
			MapEntry me = { .src_range = srcrng, .dest_range = dstrng };
			MapEntryArr_add(&arr[arrc], me);
		} else if (scanning) {
			scanning = false;
			arrc++;
		}
	}

	long min = LONG_MAX;
	for (size_t i = 0; i < seedc; i++) {
		long s = seeds[i];
		for (size_t j = 0; j < MAPS; j++) {
			for (size_t k = 0; k < arr[j].len; k++) {
				MapEntry me = MapEntryArr_get(&arr[j], k);
				if (range_contains(&me.src_range, s)) {
					s = me.dest_range.from + s - me.src_range.from;
					assert(s > 0);
					break;
				}
			}
		}
		min = min(min, s);
	}

	for (size_t i = 0; i < MAPS; i++) {
		MapEntryArr_free(&arr[i]);
	}

	free(line);
	fclose(f);

	return min;
}

long day5_part2() {
	return 0;
}
