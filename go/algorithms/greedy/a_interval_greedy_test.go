package greedy

import (
	"reflect"
	"testing"
)

func TestIntervalGreedy(t *testing.T) {
	if MaxActivities([]int{1, 3, 0, 5, 8, 5}, []int{2, 4, 6, 7, 9, 9}) != 4 {
		t.Error("activities")
	}
	if FindMinArrowShots([][2]int{{10, 16}, {2, 8}, {1, 6}, {7, 12}}) != 2 || FindMinArrowShots([][2]int{{1, 2}, {3, 4}, {5, 6}, {7, 8}}) != 4 ||
		FindMinArrowShots([][2]int{{1, 2}, {2, 3}, {3, 4}, {4, 5}}) != 2 {
		t.Error("arrows")
	}
	if got := PartitionLabels("ababcbacadefegdehijhklij"); !reflect.DeepEqual(got, []int{9, 7, 8}) {
		t.Errorf("partition labels %v", got)
	}
	if MinPlatforms([]int{900, 940, 950, 1100, 1500, 1800}, []int{910, 1200, 1120, 1130, 1900, 2000}) != 3 {
		t.Error("platforms")
	}
}
