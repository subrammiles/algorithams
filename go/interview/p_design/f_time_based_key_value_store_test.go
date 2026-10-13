package p_design

import "testing"

func TestTimeMap(t *testing.T) {
	kv := NewTimeMap()
	kv.Set("foo", "bar", 1)
	if kv.Get("foo", 1) != "bar" || kv.Get("foo", 3) != "bar" {
		t.Fatal("first version")
	}
	kv.Set("foo", "bar2", 4)
	if kv.Get("foo", 4) != "bar2" || kv.Get("foo", 0) != "" || kv.Get("nope", 5) != "" {
		t.Fatal("versions")
	}
}
