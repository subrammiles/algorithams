// Package p_design: low-level design questions asked in senior interviews.
package p_design

import "container/list"

// LRUCache: map key -> list element; container/list is a doubly linked list. O(1) get/put.
// Not safe for concurrent use - wrap with a sync.Mutex (or shard by key) if needed.
type LRUCache struct {
	capacity int
	items    map[int]*list.Element
	order    *list.List // front = most recently used
}

type lruEntry struct{ key, value int }

func NewLRUCache(capacity int) *LRUCache {
	return &LRUCache{capacity: capacity, items: map[int]*list.Element{}, order: list.New()}
}

func (c *LRUCache) Get(key int) int {
	e, ok := c.items[key]
	if !ok {
		return -1
	}
	c.order.MoveToFront(e)
	return e.Value.(*lruEntry).value
}

func (c *LRUCache) Put(key, value int) {
	if e, ok := c.items[key]; ok {
		e.Value.(*lruEntry).value = value
		c.order.MoveToFront(e)
		return
	}
	if c.order.Len() == c.capacity {
		lru := c.order.Back()
		c.order.Remove(lru)
		delete(c.items, lru.Value.(*lruEntry).key)
	}
	c.items[key] = c.order.PushFront(&lruEntry{key, value})
}
