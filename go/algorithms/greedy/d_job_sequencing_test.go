package greedy

import (
	"reflect"
	"testing"
)

func TestJobSequencing(t *testing.T) {
	jobs := []Job{{"a", 2, 100}, {"b", 1, 19}, {"c", 2, 27}, {"d", 1, 25}, {"e", 3, 15}}
	order := Schedule(jobs)
	if !reflect.DeepEqual(order, []string{"c", "a", "e"}) || TotalProfit(jobs, order) != 142 {
		t.Errorf("schedule %v", order)
	}
	jobs2 := []Job{{"x", 4, 20}, {"y", 1, 10}, {"z", 1, 40}, {"w", 1, 30}}
	if TotalProfit(jobs2, Schedule(jobs2)) != 60 {
		t.Error("profit 2")
	}
}
