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

var cardsPart1 []byte = []byte{'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'}
var cardsPart2 []byte = []byte{'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'}

type Hand struct {
	Cards string
	Bid   int
	Type  int
}

func day7Part1() int64 {
	return solve(cardsPart1, false)

}

func day7Part2() int64 {
	return solve(cardsPart2, true)
}

func solve(cardSet []byte, useJoker bool) int64 {
	f, _ := os.OpenFile("../input/7.txt", os.O_RDONLY, 0)
	defer f.Close()
	scanner := bufio.NewScanner(f)
	var hands []Hand
	for scanner.Scan() {
		s := strings.Split(scanner.Text(), " ")
		cards := s[0]
		bid, _ := strconv.Atoi(s[1])

		hand := createHand(cards, bid, useJoker, cardSet)
		hands = append(hands, hand)
	}

	slices.SortFunc(hands, func(a, b Hand) int {
		if a.Type == b.Type {
			for i := 0; i < 5; i++ {
				aa := slices.Index(cardSet, a.Cards[i])
				bb := slices.Index(cardSet, b.Cards[i])
				if aa != bb {
					return bb - aa
				}
			}
			return 0
		} else {
			return b.Type - a.Type
		}
	})

	var result int64 = 0

	for i, h := range hands {
		partial := (i + 1) * h.Bid
		result += int64(partial)
	}

	return result
}

func createHand(cards string, bid int, useJoker bool, cardSet []byte) Hand {
	optim := cards
	if useJoker && strings.Contains(cards, "J") {
		optim = optimizeHand(cards, cardSet)
	}
	set := make(map[int32]int)
	for _, c := range optim {
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
	return Hand{cards, bid, t}
}

func optimizeHand(cards string, cardSet []byte) string {
	m := make(map[byte]int)
	for i := 0; i < len(cards); i++ {
		if cards[i] == 'J' {
			continue
		}
		c := cards[i]
		m[c] = 1 + m[c]
	}
	var jReplacement byte = 0
	max := 0

	for k, v := range m {
		jridx := slices.Index(cardSet, jReplacement)
		if v > max || v == max && (jridx == -1 || slices.Index(cardSet, k) < jridx) {
			max = v
			jReplacement = k
		}
	}

	return strings.ReplaceAll(cards, "J", string(jReplacement))
}
