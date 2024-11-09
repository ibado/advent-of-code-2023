package main

import "math/big"

// TODO: Try to implement GCD algo myself
func lcd(a, b int64) int64 {
	abig := new(big.Int)
	abig.SetInt64(a)
	bbig := new(big.Int)
	bbig.SetInt64(b)
	gcd := new(big.Int)
	gcd = gcd.GCD(nil, nil, abig, bbig)
	lcm := new(big.Int)
	lcm = lcm.Div(abig, gcd)
	lcm = lcm.Mul(lcm, bbig)
	return lcm.Int64()
}
