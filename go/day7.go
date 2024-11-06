package main

import (
	"bufio"
	"maps"
	"os"
	"slices"
	"strconv"
	"strings"
)

const (
	FIVE = iota
	FOUR
	FULL_HOUSE
	THREE
	TWO_PAIR
	ONE_PAIR
	HIGH_CARD
)

var cards []byte = []byte{'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'}

type Hand struct {
	Cards string
	Bid   int
	Type  int
}

func Day7Part1() int64 {
	f, _ := os.OpenFile("../input/7.txt", os.O_RDONLY, 0)
	defer f.Close()
	scanner := bufio.NewScanner(f)
	var hands []Hand
	for scanner.Scan() {
		s := strings.Split(scanner.Text(), " ")
		cards := s[0]
		bid, _ := strconv.Atoi(s[1])
		set := make(map[int32]int)
		for _, c := range cards {
			set[c] = 1 + set[c]
		}
		values := slices.Collect(maps.Values(set))
		m := slices.Max(values)
		t := 0
		switch m {
		case 5:
			t = FIVE
		case 4:
			t = FOUR
		case 3:
			if slices.Contains(values, 2) {
				t = FULL_HOUSE
			} else {
				t = THREE
			}
		case 2:
			slices.DeleteFunc(values, func(i int) bool {
				return i == 2
			})
			if len(values) == 3 {
				t = TWO_PAIR
			} else {
				t = ONE_PAIR
			}
		default:
			t = HIGH_CARD
		}
		hand := Hand{cards, bid, t}
		hands = append(hands, hand)
	}

	slices.SortFunc(hands, func(a, b Hand) int {
		if a.Type == b.Type {
			for i, _ := range a.Cards {
				aa := slices.Index(cards, a.Cards[i])
				bb := slices.Index(cards, b.Cards[i])
				if aa != bb {
					return aa - bb
				}
			}
			return 0
		} else {
			return a.Type - b.Type
		}
	})

	var result int64 = 0
	for i, h := range hands {
		partial := (len(hands) - i) * h.Bid
		result += int64(partial)
	}

	return result
}
