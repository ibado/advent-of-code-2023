package main

import (
	"bufio"
	"fmt"
	"iter"
	"os"
)

func readLines(day uint8) iter.Seq2[int, string] {
	assert(day <= 25)
	return func(yield func(int, string) bool) {
		fpath := fmt.Sprintf("../input/%d.txt", day)
		f, _ := os.OpenFile(fpath, os.O_RDONLY, 0)
		defer f.Close()

		scanner := bufio.NewScanner(f)

		i := -1
		for scanner.Scan() {
			i++
			if !yield(i, scanner.Text()) {
				return
			}
		}
	}
}

func assert(cond bool) {
	if !cond {
		panic("assertion fail!")
	}
}

func min(a, b int64) int64 {
	if a < b {
		return a
	} else {
		return b
	}
}

func max(a, b int64) int64 {
	if a > b {
		return a
	} else {
		return b
	}
}

func isDigit(n byte) bool {
	return '0' <= n && n <= '9'
}

func concat(n int64, c byte) int64 {
	if n == 0 {
		return int64(c - '0')
	} else {
		return n*10 + int64(c-'0')
	}
}
