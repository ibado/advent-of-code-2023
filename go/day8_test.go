package main

import (
	"iter"
	"strings"
	"testing"
)

func TestExample1(t *testing.T) {
	input := `RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)`

	result := day8Part1(strToSeq2(input))

	if result != 2 {
		t.Fatal("Expected 2 but was", result)
	}
}

func TestSample2(t *testing.T) {
	input := `LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)`

	result := day8Part1(strToSeq2(input))

	if result != 6 {
		t.Fatal("Expected 6 but was", result)
	}
}

func strToSeq2(s string) iter.Seq2[int, string] {
	return func(yield func(int, string) bool) {
		for i, line := range strings.Split(s, "\n") {
			if !yield(i, line) {
				return
			}
		}
	}
}

func strToSeq(s string) iter.Seq[string] {
	return func(yield func(string) bool) {
		for _, line := range strings.Split(s, "\n") {
			if !yield(line) {
				return
			}
		}
	}
}
