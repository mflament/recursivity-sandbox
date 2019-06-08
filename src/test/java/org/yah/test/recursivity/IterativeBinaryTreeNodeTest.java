package org.yah.test.recursivity;

public class IterativeBinaryTreeNodeTest extends AbstractBinaryTreeNodeTest<IterativeBinaryTreeNode> {

	@Override
	protected IterativeBinaryTreeNode createNode(String name, IterativeBinaryTreeNode parent) {
		return new IterativeBinaryTreeNode(name, parent);
	}

	@Override
	protected int maxDepthTestStep() {
		return 10_000;
	}

}
