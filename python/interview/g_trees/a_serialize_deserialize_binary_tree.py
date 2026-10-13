"""Serialize / deserialize a binary tree with preorder + '#' for null. O(n) both ways.

Preorder with null markers identifies a tree uniquely. A BST needs only preorder (use bounds).
Very deep trees: recursion limit -> iterative or BFS format.
"""
from tree_node import TreeNode, build


def serialize(root):
    out = []

    def write(n):
        if n is None:
            out.append("#")
            return
        out.append(str(n.val))
        write(n.left)
        write(n.right)

    write(root)
    return ",".join(out)


def deserialize(data):
    tokens = iter(data.split(","))

    def read():
        t = next(tokens)
        if t == "#":
            return None
        node = TreeNode(int(t))
        node.left, node.right = read(), read()
        return node

    return read()


if __name__ == "__main__":
    s = serialize(build(1, 2, 3, None, None, 4, 5))
    assert s == "1,2,#,#,3,4,#,#,5,#,#"
    assert serialize(deserialize(s)) == s
    assert serialize(deserialize(serialize(None))) == "#"
    print("all checks passed")
