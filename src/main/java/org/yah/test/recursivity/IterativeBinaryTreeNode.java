package org.yah.test.recursivity;

import java.util.Deque;
import java.util.LinkedList;

/**
 *
 * Implementation of {@link AbstractBinaryTreeNode} that use iteration and heap
 * memory instead of stack.<br/>
 * 
 */
public class IterativeBinaryTreeNode extends AbstractBinaryTreeNode<IterativeBinaryTreeNode> {

	public IterativeBinaryTreeNode(String name) {
		this(name, null);
	}

	public IterativeBinaryTreeNode(String name, IterativeBinaryTreeNode parent) {
		super(name, parent, new IterativeBinaryTreeNode[2]);
	}

	@Override
	public int depth() {
		int res = 0; // start at 0
		// To avoid recursivity, we do the same locally to this method
		// use a node variable to keep track of 'this' node in case of recursive method
		// call
		IterativeBinaryTreeNode next = parent;
		// loop until root
		while (next != null) {
			// one more ancestor
			res++;
			// move to parent, equivalent to the recursive call to parent.depth()
			next = next.parent;
		}
		return res;
	}

	@Override
	public void depthFirst(Visitor<IterativeBinaryTreeNode> visitor) {
		Deque<IterativeBinaryTreeNode> stack = new LinkedList<>();
		stack.push(this);
		while (!stack.isEmpty()) {
			IterativeBinaryTreeNode node = stack.pop();
			visitor.visit(node);
			// since we use a stack (LIFO), push children in reverse order to have the same
			// behavior than recursive node (left to right iteration)
			node.right().ifPresent(stack::push);
			node.left().ifPresent(stack::push);
		}
	}

}
