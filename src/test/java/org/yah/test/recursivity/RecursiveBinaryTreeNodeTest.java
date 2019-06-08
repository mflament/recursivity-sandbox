package org.yah.test.recursivity;

public class RecursiveBinaryTreeNodeTest extends AbstractBinaryTreeNodeTest<RecursiveBinaryTreeNode> {

	@Override
	protected RecursiveBinaryTreeNode createNode(String name, RecursiveBinaryTreeNode parent) {
		return new RecursiveBinaryTreeNode(name, parent);
	}

	@Override
	protected int maxDepthTestStep() {
		return 10;
	}

}
