package p_design

import "container/list"

// LFUCache: key -> element, count -> list of keys (front = most recent), minCount. O(1).
type LFUCache struct {
	capacity, minCount int
	items              map[int]*list.Element
	buckets            map[int]*list.List
}

type lfuEntry struct{ key, value, count int }

func NewLFUCache(capacity int) *LFUCache {
	return &LFUCache{capacity: capacity, items: map[int]*list.Element{}, buckets: map[int]*list.List{}}
}

func (c *LFUCache) bucket(count int) *list.List {
	if c.buckets[count] == nil {
		c.buckets[count] = list.New()
	}
	return c.buckets[count]
}

func (c *LFUCache) touch(e *list.Element) *list.Element {
	entry := e.Value.(*lfuEntry)
	old := c.bucket(entry.count)
	old.Remove(e)
	if old.Len() == 0 && entry.count == c.minCount {
		c.minCount++
	}
	entry.count++
	moved := c.bucket(entry.count).PushFront(entry)
	c.items[entry.key] = moved
	return moved
}

func (c *LFUCache) Get(key int) int {
	e, ok := c.items[key]
	if !ok {
		return -1
	}
	return c.touch(e).Value.(*lfuEntry).value
}

func (c *LFUCache) Put(key, value int) {
	if c.capacity == 0 {
		return
	}
	if e, ok := c.items[key]; ok {
		e.Value.(*lfuEntry).value = value
		c.touch(e)
		return
	}
	if len(c.items) == c.capacity {
		lowest := c.bucket(c.minCount)
		victim := lowest.Back() // least recently used among the least frequent
		lowest.Remove(victim)
		delete(c.items, victim.Value.(*lfuEntry).key)
	}
	c.items[key] = c.bucket(1).PushFront(&lfuEntry{key, value, 1})
	c.minCount = 1
}
