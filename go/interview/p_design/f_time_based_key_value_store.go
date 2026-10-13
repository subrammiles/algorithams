package p_design

import "sort"

type version struct {
	timestamp int
	value     string
}

// TimeMap returns the value with the largest timestamp <= t (timestamps arrive increasing).
type TimeMap struct{ store map[string][]version }

func NewTimeMap() *TimeMap { return &TimeMap{store: map[string][]version{}} }

func (m *TimeMap) Set(key, value string, timestamp int) {
	m.store[key] = append(m.store[key], version{timestamp, value})
}

func (m *TimeMap) Get(key string, timestamp int) string {
	versions := m.store[key]
	i := sort.Search(len(versions), func(i int) bool { return versions[i].timestamp > timestamp })
	if i == 0 {
		return ""
	}
	return versions[i-1].value
}
