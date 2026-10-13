package main

import "fmt"

func getValueOfAChar(mychar rune) int {
	return int(mychar)
}

// shift moves every letter key places forward in the alphabet, wrapping z -> a (Caesar cipher).
// Non-letters are kept as they are.
func shift(myString string, key int) string {
	key = ((key % 26) + 26) % 26
	out := []rune(myString)
	for i, c := range out {
		switch {
		case c >= 'a' && c <= 'z':
			out[i] = 'a' + rune((getValueOfAChar(c)-'a'+key)%26)
		case c >= 'A' && c <= 'Z':
			out[i] = 'A' + rune((getValueOfAChar(c)-'A'+key)%26)
		}
	}
	return string(out)
}

func main() {
	fmt.Println(shift("Hello, World", 3)) // Khoor, Zruog
	fmt.Println(shift("abc xyz", 2))      // cde zab
	fmt.Println(shift(shift("round trip", 5), -5))
}
