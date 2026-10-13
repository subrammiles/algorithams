package p_design

import "math/rand"

// RandomizedSet: slice for O(1) random index + map value -> index; remove swaps with the last.
type RandomizedSet struct {
	values []int
	index  map[int]int
	rng    *rand.Rand
}

func NewRandomizedSet(seed int64) *RandomizedSet {
	return &RandomizedSet{index: map[int]int{}, rng: rand.New(rand.NewSource(seed))}
}

func (s *RandomizedSet) Insert(val int) bool {
	if _, ok := s.index[val]; ok {
		return false
	}
	s.index[val] = len(s.values)
	s.values = append(s.values, val)
	return true
}

func (s *RandomizedSet) Remove(val int) bool {
	i, ok := s.index[val]
	if !ok {
		return false
	}
	last := s.values[len(s.values)-1]
	s.values[i], s.index[last] = last, i
	s.values = s.values[:len(s.values)-1]
	delete(s.index, val)
	return true
}

func (s *RandomizedSet) GetRandom() int { return s.values[s.rng.Intn(len(s.values))] }
