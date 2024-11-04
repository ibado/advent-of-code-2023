package main

import (
	"bufio"
	"os"
)

type Race struct {
	Time     int64
	Distance int64
}

func Day6Part1() int64 {
	f, _ := os.OpenFile("../input/6.txt", os.O_RDONLY, 0)
	defer f.Close()
	scanner := bufio.NewScanner(f)
	scanner.Scan()
	times := parse_nums(scanner.Bytes())
	scanner.Scan()
	distances := parse_nums(scanner.Bytes())

	var races []Race
	for i, _ := range times {
		races = append(races, Race{Time: times[i], Distance: distances[i]})
	}

	var m int64 = 1
	for _, race := range races {
		var c int64 = 0
		var i int64 = 1
		for ; i < race.Time; i++ {
			if i*(race.Time-i) > race.Distance {
				c++
			}
		}
		m *= c
	}

	return m
}

func parse_num(line []byte) int64 {
	var n int64 = 0
	i := 0
	for i < len(line) {
		if isDigit(line[i]) {
			for i < len(line) && isDigit(line[i]) {
				n = concat(n, line[i])
				i++
			}
		} else {
			i++
		}
	}
	return n

}

func Day6Part2() int64 {
	f, _ := os.OpenFile("../input/6.txt", os.O_RDONLY, 0)
	defer f.Close()
	scanner := bufio.NewScanner(f)
	scanner.Scan()
	time := parse_num(scanner.Bytes())
	scanner.Scan()
	distance := parse_num(scanner.Bytes())

	race := Race{Time: time, Distance: distance}

	var c int64 = 0
	var i int64 = 1
	for ; i < race.Time; i++ {
		if i*(race.Time-i) > race.Distance {
			c++
		}
	}

	return c
}
