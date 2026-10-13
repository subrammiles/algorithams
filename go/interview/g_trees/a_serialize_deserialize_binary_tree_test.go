package g_trees

import "testing"

func TestSerialize(t *testing.T) {
	s := Serialize(Build(1, 2, 3, Null, Null, 4, 5))
	if s != "1,2,#,#,3,4,#,#,5,#,#" || Serialize(Deserialize(s)) != s || Serialize(Deserialize(Serialize(nil))) != "#" {
		t.Fatalf("serialize %q", s)
	}
}
