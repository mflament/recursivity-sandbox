package org.yah.test.recursivity;

/**
 *
 * Implementation of {@link AbstractBinaryTreeNode} that use recursive method
 * call.<br/>
 *
 */
public class RecursiveBinaryTreeNode extends AbstractBinaryTreeNode<RecursiveBinaryTreeNode> {

	public RecursiveBinaryTreeNode(String name) {
		this(name, null);
	}

	public RecursiveBinaryTreeNode(String name, RecursiveBinaryTreeNode parent) {
		super(name, parent, new RecursiveBinaryTreeNode[2]);
	}

	@Override
	public int depth() {
		if (parent == null) // root node, no ancestor
			return 0;

		// otherwise, our depth is 1 more that the one of our parent
		return 1 + parent.depth();
	}

	@Override
	public void depthFirst(Visitor<RecursiveBinaryTreeNode> visitor) {
		// visit this node
		visitor.visit(this);
		// and then the left leg (recursively, so all the leg will be visited after this
		// call)
		left().ifPresent(l -> l.depthFirst(visitor));
		// and then the right leg
		right().ifPresent(r -> r.depthFirst(visitor));
	}

}
