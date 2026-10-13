package interview.g_trees;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import common.Check;

/*
 * Problem: Convert a binary tree to a string and back.
 *
 * Approach: Preorder DFS writing "#" for null children. Preorder + null markers
 * uniquely identifies a tree, so deserialisation just consumes tokens in the same order.
 * O(n) both ways.
 *
 * Senior discussion points:
 *  - Inorder alone is ambiguous; preorder+inorder works only for unique values.
 *  - For a BST, preorder alone suffices (use value bounds), giving a more compact format.
 *  - Very deep trees: recursion can overflow the stack -> iterative version or BFS format.
 */
public class A_SerializeDeserializeBinaryTree {

	static String serialize(TreeNode root) {
		StringBuilder sb = new StringBuilder();
		write(root, sb);
		return sb.substring(0, sb.length() - 1);
	}

	private static void write(TreeNode node, StringBuilder sb) {
		if (node == null) {
			sb.append("#,");
			return;
		}
		sb.append(node.val).append(',');
		write(node.left, sb);
		write(node.right, sb);
	}

	static TreeNode deserialize(String data) {
		return read(new ArrayDeque<>(Arrays.asList(data.split(","))));
	}

	private static TreeNode read(Deque<String> tokens) {
		String t = tokens.poll();
		if (t.equals("#")) return null;
		TreeNode node = new TreeNode(Integer.parseInt(t));
		node.left = read(tokens);
		node.right = read(tokens);
		return node;
	}

	public static void main(String[] args) {
		TreeNode root = TreeNode.build(1, 2, 3, null, null, 4, 5);
		String s = serialize(root);
		Check.eq("serialize", s, "1,2,#,#,3,4,#,#,5,#,#");
		Check.eq("round trip", serialize(deserialize(s)), s);
		Check.eq("empty", serialize(deserialize(serialize(null))), "#");
	}
}
