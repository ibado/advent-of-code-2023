package main

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
