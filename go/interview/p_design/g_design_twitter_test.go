package p_design

import (
	"reflect"
	"testing"
)

func TestTwitter(t *testing.T) {
	tw := NewTwitter()
	tw.PostTweet(1, 5)
	if got := tw.GetNewsFeed(1); !reflect.DeepEqual(got, []int{5}) {
		t.Errorf("own tweet %v", got)
	}
	tw.Follow(1, 2)
	tw.PostTweet(2, 6)
	if got := tw.GetNewsFeed(1); !reflect.DeepEqual(got, []int{6, 5}) {
		t.Errorf("after follow %v", got)
	}
	tw.Unfollow(1, 2)
	tw.Unfollow(1, 1)
	if got := tw.GetNewsFeed(1); !reflect.DeepEqual(got, []int{5}) {
		t.Errorf("after unfollow %v", got)
	}
	if got := tw.GetNewsFeed(42); len(got) != 0 {
		t.Errorf("no tweets %v", got)
	}

	busy := NewTwitter()
	for i := 0; i < 8; i++ {
		busy.PostTweet(1, 100+i)
	}
	for i := 0; i < 8; i++ {
		busy.PostTweet(2, 200+i)
	}
	busy.Follow(1, 2)
	want := []int{207, 206, 205, 204, 203, 202, 201, 200, 107, 106}
	if got := busy.GetNewsFeed(1); !reflect.DeepEqual(got, want) {
		t.Errorf("feed of 10 %v", got)
	}
}
