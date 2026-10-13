"""Lowest common ancestor.

Binary tree: return the node if it is p or q; if both subtrees return something, it's the LCA. O(n).
BST: walk down - both smaller go left, both larger go right, else here. O(h).
Many queries on a static tree: binary lifting.
"""
from tree_node import build, find


def lca(root, p, q):
    if root is None or root is p or root is q:
        return root
    left, right = lca(root.left, p, q), lca(root.right, p, q)
    if left and right:
        return root
    return left or right


def lca_bst(root, p, q):
    while root:
        if p < root.val and q < root.val:
            root = root.left
        elif p > root.val and q > root.val:
            root = root.right
        else:
            return root
    return None


if __name__ == "__main__":
    root = build(3, 5, 1, 6, 2, 0, 8, None, None, 7, 4)
    assert lca(root, find(root, 5), find(root, 1)).val == 3
    assert lca(root, find(root, 5), find(root, 4)).val == 5
    bst = build(6, 2, 8, 0, 4, 7, 9, None, None, 3, 5)
    assert lca_bst(bst, 2, 8).val == 6
    assert lca_bst(bst, 3, 5).val == 4
    print("all checks passed")
