package graphs

import (
	"reflect"
	"testing"
)

func TestFindItinerary(t *testing.T) {
	cases := []struct {
		tickets [][2]string
		want    []string
	}{
		{[][2]string{{"MUC", "LHR"}, {"JFK", "MUC"}, {"SFO", "SJC"}, {"LHR", "SFO"}}, []string{"JFK", "MUC", "LHR", "SFO", "SJC"}},
		{[][2]string{{"JFK", "SFO"}, {"JFK", "ATL"}, {"SFO", "ATL"}, {"ATL", "JFK"}, {"ATL", "SFO"}}, []string{"JFK", "ATL", "JFK", "SFO", "ATL", "SFO"}},
		{[][2]string{{"JFK", "KUL"}, {"JFK", "NRT"}, {"NRT", "JFK"}}, []string{"JFK", "NRT", "JFK", "KUL"}},
	}
	for _, c := range cases {
		if got := FindItinerary(c.tickets); !reflect.DeepEqual(got, c.want) {
			t.Errorf("got %v want %v", got, c.want)
		}
	}
}
