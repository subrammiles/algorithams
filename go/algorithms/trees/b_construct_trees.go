package trees

// BuildFromPreIn: preorder[0] is the root; its inorder index splits the subtrees. O(n).
func BuildFromPreIn(preorder, inorder []int) *TreeNode {
	index := map[int]int{}
	for i, v := range inorder {
		index[v] = i
	}
	p := 0
	var build func(lo, hi int) *TreeNode
	build = func(lo, hi int) *TreeNode {
		if lo > hi {
			return nil
		}
		root := &TreeNode{Val: preorder[p]}
		p++
		mid := index[root.Val]
		root.Left = build(lo, mid-1)
		root.Right = build(mid+1, hi)
		return root
	}
	return build(0, len(inorder)-1)
}

// BuildFromInPost: the root is the LAST postorder value; build the right subtree first.
func BuildFromInPost(inorder, postorder []int) *TreeNode {
	index := map[int]int{}
	for i, v := range inorder {
		index[v] = i
	}
	p := len(postorder) - 1
	var build func(lo, hi int) *TreeNode
	build = func(lo, hi int) *TreeNode {
		if lo > hi {
			return nil
		}
		root := &TreeNode{Val: postorder[p]}
		p--
		mid := index[root.Val]
		root.Right = build(mid+1, hi)
		root.Left = build(lo, mid-1)
		return root
	}
	return build(0, len(inorder)-1)
}

func SortedArrayToBST(a []int) *TreeNode {
	if len(a) == 0 {
		return nil
	}
	mid := len(a) / 2
	return &TreeNode{Val: a[mid], Left: SortedArrayToBST(a[:mid]), Right: SortedArrayToBST(a[mid+1:])}
}

// BSTIterator: controlled inorder walk, amortised O(1) Next, O(h) memory.
type BSTIterator struct{ stack []*TreeNode }

func NewBSTIterator(root *TreeNode) *BSTIterator {
	it := &BSTIterator{}
	it.pushLeft(root)
	return it
}

func (it *BSTIterator) pushLeft(n *TreeNode) {
	for ; n != nil; n = n.Left {
		it.stack = append(it.stack, n)
	}
}

func (it *BSTIterator) HasNext() bool { return len(it.stack) > 0 }

func (it *BSTIterator) Next() int {
	n := it.stack[len(it.stack)-1]
	it.stack = it.stack[:len(it.stack)-1]
	it.pushLeft(n.Right)
	return n.Val
}

func height(n *TreeNode) int {
	if n == nil {
		return 0
	}
	return 1 + max(height(n.Left), height(n.Right))
}
