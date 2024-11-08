package main

import "iter"

type Node struct {
	left  string
	right string
}

func day8Part1(lineSeq2 iter.Seq2[int, string]) int64 {
	var dirs string
	m := make(map[string]Node)

	for i, line := range lineSeq2 {
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

	i := 0
	key := "AAA"
	var steps int64 = 0
	for key != "ZZZ" {
		steps++
		node := m[key]
		switch dirs[i] {
		case 'L':
			key = node.left
		case 'R':
			key = node.right
		default:
			panic("invalid dir!")
		}
		i = (i + 1) % len(dirs)
	}

	return steps
}

func day8Part2() int64 {
	return 0
}
