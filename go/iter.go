package main

import "iter"

func Map[T any, U any](seq iter.Seq[T], f func(T) U) iter.Seq[U] {
	return func(yield func(U) bool) {
		for s := range seq {
			if !yield(f(s)) {
				return
			}
		}
	}
}

func Filter[T any](seq iter.Seq[T], f func(T) bool) iter.Seq[T] {
	return func(yield func(T) bool) {
		for s := range seq {
			if f(s) && !yield(s) {
				return
			}
		}
	}
}

func SumOf[T any](seq iter.Seq[T], f func(T) int64) int64 {
	var sum int64 = 0
	for s := range seq {
		sum += f(s)
	}
	return sum
}
