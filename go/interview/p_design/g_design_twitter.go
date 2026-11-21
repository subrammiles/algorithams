package p_design

import "container/heap"

// Twitter: fan-out on read (full explanations and scaling follow-ups in the Java version).
// Per-user tweet lists stamped with a global clock; the feed is a k-way merge with a max-heap
// that stops after 10. Post/Follow O(1), feed O(f + 10 log f).
type Twitter struct {
	clock     int
	tweets    map[int][]tweet // user -> tweets, oldest first
	following map[int]map[int]bool
}

type tweet struct{ time, id int }

type feedItem struct{ time, id, user, index int }

type feedHeap []feedItem

func (h feedHeap) Len() int           { return len(h) }
func (h feedHeap) Less(i, j int) bool { return h[i].time > h[j].time }
func (h feedHeap) Swap(i, j int)      { h[i], h[j] = h[j], h[i] }
func (h *feedHeap) Push(x any)        { *h = append(*h, x.(feedItem)) }
func (h *feedHeap) Pop() any {
	old := *h
	x := old[len(old)-1]
	*h = old[:len(old)-1]
	return x
}

const feedSize = 10

func NewTwitter() *Twitter {
	return &Twitter{tweets: map[int][]tweet{}, following: map[int]map[int]bool{}}
}

func (t *Twitter) PostTweet(userID, tweetID int) {
	t.tweets[userID] = append(t.tweets[userID], tweet{t.clock, tweetID})
	t.clock++
}

func (t *Twitter) GetNewsFeed(userID int) []int {
	h := &feedHeap{}
	sources := map[int]bool{userID: true}
	for u := range t.following[userID] {
		sources[u] = true
	}
	for u := range sources {
		if list := t.tweets[u]; len(list) > 0 {
			i := len(list) - 1
			*h = append(*h, feedItem{list[i].time, list[i].id, u, i})
		}
	}
	heap.Init(h)
	feed := []int{}
	for h.Len() > 0 && len(feed) < feedSize {
		top := heap.Pop(h).(feedItem)
		feed = append(feed, top.id)
		if i := top.index - 1; i >= 0 {
			older := t.tweets[top.user][i]
			heap.Push(h, feedItem{older.time, older.id, top.user, i})
		}
	}
	return feed
}

func (t *Twitter) Follow(followerID, followeeID int) {
	if followerID == followeeID {
		return
	}
	if t.following[followerID] == nil {
		t.following[followerID] = map[int]bool{}
	}
	t.following[followerID][followeeID] = true
}

func (t *Twitter) Unfollow(followerID, followeeID int) {
	delete(t.following[followerID], followeeID) // delete on a nil map is a no-op
}
