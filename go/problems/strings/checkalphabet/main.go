package main

import (
	"fmt"
)

func isCharacterIsAnAlphabet(character rune) {
	if (character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z') {
		fmt.Println("Yes ,Given character is an alphabet")
	} else {
		fmt.Println("No ,Given character is not an alphabet")
	}
}
func isCharacterIsAnAlphabet2(character byte) {
	if (character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z') {
		fmt.Println("Yes ,Given character is an alphabet")
	} else {
		fmt.Println("No ,Given character is not an alphabet")
	}
}
func main() {
	isCharacterIsAnAlphabet2('1')
	isCharacterIsAnAlphabet2('a')
}
