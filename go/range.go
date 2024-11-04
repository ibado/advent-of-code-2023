package main

import "fmt"

type Range struct {
	From int64
	To   int64
}

func (r Range) Contains(n int64) bool {
	return r.From <= n && n <= r.To
}

// It returns true and the union Range if the intersection is not empty
// It returns false, nil otherwise
func (r *Range) Union(other *Range) (bool, *Range) {
	if r.Intersct(other) || other.Intersct(r) {
		fmt.Println("intersecato")
		return true, &Range{
			From: min(r.From, other.From),
			To:   max(r.To, other.To),
		}
	}

	return false, nil
}

func (r *Range) Intersct(other *Range) bool {
	return r.Contains(other.From) || r.Contains(other.To)
}
