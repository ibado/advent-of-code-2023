package main

import "testing"

func TestPart1Sample(t *testing.T) {
	input := `0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45`

	result := day9Part1(strToSeq(input))

	if result != 114 {
		t.Fatal("should be 114 but was: ", result)
	}
}
