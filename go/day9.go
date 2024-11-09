package main

import (
	"iter"
	"slices"
	"strconv"
	"strings"
)

func parseNums(line string) []int64 {
	nums := Map(slices.Values(strings.Split(line, " ")), func(s string) int64 {
		i64, _ := strconv.ParseInt(s, 10, 64)
		return i64
	})

	return slices.Collect(nums)
}

func day9Part1(lines iter.Seq[string]) int64 {
	return SumOf(lines, func(l string) int64 {
		nums := parseNums(l)
		return findNext(nums)
	})
}

func findNext(nums []int64) int64 {
	var i, j int64
	var ln int64 = int64(len(nums))
	var lines [][]int64
	lines = append(lines, nums)
	for j = ln - 1; j > 0; j-- {
		allZeros := true
		var line []int64
		for i = 0; i < j; i++ {
			n := nums[i+1] - nums[i]
			if n != 0 {
				allZeros = false
			}
			line = append(line, n)
		}
		if allZeros {
			break
		} else {
			lines = append(lines, line)
			nums = line
		}
	}

	var sum int64 = 0
	for _, l := range lines {
		sum += l[len(l)-1]
	}

	return sum
}
