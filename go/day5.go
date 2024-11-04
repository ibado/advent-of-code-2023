package main

import (
	"bufio"
	"math"
	"os"
	"sync"
)

type MapEntry struct {
	Src Range
	Dst Range
}

func parse_nums(line []byte) []int64 {
	var s []int64
	i := 0
	for i < len(line) {
		if isDigit(line[i]) {
			var n int64 = 0
			for i < len(line) && isDigit(line[i]) {
				n = concat(n, line[i])
				i++
			}
			s = append(s, n)
		} else {
			i++
		}
	}
	return s

}

func computeRanges(seeds []int64) []Range {
	var r []Range
	for i := 0; i < len(seeds); i += 2 {
		r = append(r, Range{From: seeds[i], To: seeds[i] + seeds[i+1]})
	}
	return r
}

func processRange(rng Range, ch chan int64, wg *sync.WaitGroup, maps [7][]MapEntry) {
	var res int64 = math.MaxInt64
	for s := rng.From; s <= rng.To; s++ {
		ss := s
		for _, m := range maps {
			for _, entry := range m {
				if entry.Src.Contains(ss) {
					ss = entry.Dst.From + ss - entry.Src.From
					break
				}
			}
		}
		res = min(res, ss)
	}
	ch <- res
	wg.Done()
}

func Day5Part2() int64 {
	f, _ := os.OpenFile("../input/5.txt", os.O_RDONLY, 0)
	defer f.Close()
	scanner := bufio.NewScanner(f)
	scanner.Scan()
	seeds := parse_nums(scanner.Bytes())
	var maps [7][]MapEntry
	mapc := 0
	scanning := false
	for scanner.Scan() {
		line := scanner.Bytes()
		if len(line) > 0 && isDigit(line[0]) {
			scanning = true
			nums := parse_nums(line)
			assert(len(nums) == 3)
			dstrng := Range{From: nums[0], To: nums[0] + nums[2]}
			srcrng := Range{From: nums[1], To: nums[1] + nums[2]}
			entry := MapEntry{Src: srcrng, Dst: dstrng}
			maps[mapc] = append(maps[mapc], entry)
		} else if scanning {
			mapc += 1
			scanning = false
		}
	}

	seedRanges := computeRanges(seeds)

	var wg sync.WaitGroup
	ch := make(chan int64)
	for _, rng := range seedRanges {
		wg.Add(1)
		go processRange(rng, ch, &wg, maps)
	}

	go func() {
		wg.Wait()
		close(ch)
	}()

	var result int64 = math.MaxInt64
	for r := range ch {
		result = min(result, r)
	}

	return result
}
