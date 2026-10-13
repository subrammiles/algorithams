package backtracking

import (
	"reflect"
	"testing"
)

func TestGridAndStringBacktracking(t *testing.T) {
	board := [][]byte{[]byte("ABCE"), []byte("SFCS"), []byte("ADEE")}
	if !Exist(board, "ABCCED") || !Exist(board, "SEE") || Exist(board, "ABCB") {
		t.Error("word search")
	}
	if got := Partition("aab"); !reflect.DeepEqual(got, [][]string{{"a", "a", "b"}, {"aa", "b"}}) {
		t.Errorf("partition %v", got)
	}
	if got := RestoreIPAddresses("25525511135"); !reflect.DeepEqual(got, []string{"255.255.11.135", "255.255.111.35"}) {
		t.Errorf("ip %v", got)
	}
	if got := RestoreIPAddresses("0000"); !reflect.DeepEqual(got, []string{"0.0.0.0"}) {
		t.Errorf("ip zeros %v", got)
	}
	if got := RestoreIPAddresses("101023"); !reflect.DeepEqual(got, []string{"1.0.10.23", "1.0.102.3", "10.1.0.23", "10.10.2.3", "101.0.2.3"}) {
		t.Errorf("ip many %v", got)
	}
}
