package main

import (
	"iter"
	"maps"
)

type Node struct {
	left  string
	right string
}

func day8Part1(lines iter.Seq2[int, string]) int64 {
	dirs, m := parseInput(lines)

	return minStepsTo("AAA", dirs, m, func(k string) bool {
		return k == "ZZZ"
	})
}

func day8Part2(lines iter.Seq2[int, string]) int64 {
	dirs, m := parseInput(lines)
	keys := maps.Keys(m)
	ases := Filter(keys, func(s string) bool {
		return s[2] == 'A'
	})
	steps := Map(ases, func(key string) int64 {
		return minStepsTo(key, dirs, m, func(k string) bool {
			return k[2] == 'Z'
		})
	})
	var result int64 = 1
	for n := range steps {
		result = lcd(result, n)
	}
	return result
}

func minStepsTo(startKey string, dirs string, m map[string]Node, found func(string) bool) int64 {
	i := 0
	var steps int64 = 0
	for !found(startKey) {
		steps++
		node := m[startKey]
		switch dirs[i] {
		case 'L':
			startKey = node.left
		case 'R':
			startKey = node.right
		default:
			panic("invalid dir!")
		}
		i = (i + 1) % len(dirs)
	}
	return steps
}

func parseInput(lines iter.Seq2[int, string]) (string, map[string]Node) {
	var dirs string
	m := make(map[string]Node)

	for i, line := range lines {
		if i == 0 {
			dirs = line
			continue
		}
		if i == 1 {
			continue
		}
		key := line[0:3]
		left := line[7:10]
		right := line[12:15]
		m[key] = Node{left: left, right: right}
	}

	return dirs, m
}
