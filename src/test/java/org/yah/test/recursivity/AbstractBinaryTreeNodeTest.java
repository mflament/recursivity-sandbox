package org.yah.test.recursivity;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public abstract class AbstractBinaryTreeNodeTest<N extends AbstractBinaryTreeNode<N>> {

	protected final class TreeBuilder {

		private final TreeBuilder parent;

		private final N node;

		public TreeBuilder(String name) {
			this.parent = null;
			this.node = createNode(name, null);
		}

		public N build() {
			TreeBuilder current = this;
			while (current.parent != null)
				current = current.parent;
			return current.node;
		}

		public TreeBuilder upTo(String name) {
			TreeBuilder current = this;
			while (current != null && !current.node.name.equals(name))
				current = current.parent;
			if (current == null)
				throw new IllegalStateException("Parent node " + name + " no found from " + node);
			return current;
		}

		private TreeBuilder(TreeBuilder parent, N node) {
			this.parent = parent;
			this.node = node;
		}

		public TreeBuilder left(String name) {
			N newNode = createNode(name, node);
			node.left(newNode);
			return new TreeBuilder(this, newNode);
		}

		public TreeBuilder right(String name) {
			N newNode = createNode(name, node);
			node.right(newNode);
			return new TreeBuilder(this, newNode);
		}

		private N createNode(String name, N parent) {
			N node = AbstractBinaryTreeNodeTest.this.createNode(name, parent);
			nodesMap.put(name, node);
			return node;
		}

		public TreeBuilder up() {
			return parent;
		}

	}

	private N root;

	private Map<String, N> nodesMap;

	/**
	 * <code>
	 * 							A
	 * 						   / \
	 * 						  /   \  
	 * 						 B     C
	 *					    / \     \
	 *					   D   E     F
	 *					    \
	 *						 G
	 * </code>
	 *
	 */
	@Before
	public void setUp() throws Exception {
		nodesMap = new HashMap<>();
		root = new TreeBuilder("A").left("B")
			.left("D")
			.right("G")
			.upTo("B")
			.right("E")
			.upTo("A")
			.right("C")
			.right("F")
			.build();
	}

	protected abstract N createNode(String name, N parent);

	protected N node(String name) {
		N res = nodesMap.get(name);
		if (res == null)
			throw new IllegalArgumentException("Unknown node " + name);
		return res;
	}

	@Test
	public void testDepth() {
		assertEquals(0, node("A").depth());
		assertEquals(1, node("B").depth());
		assertEquals(2, node("D").depth());
		assertEquals(3, node("G").depth());
	}

	@Test
	public void testDepthFirst() {
		List<String> names = new ArrayList<>(nodesMap.size());
		root.depthFirst(n -> names.add(n.getName()));
		assertEquals(Arrays.asList("A", "B", "D", "G", "E", "C", "F"), names);
	}

	@Test
	public void testMaxDepth() {
		int startDepth = 1000;
		int step = maxDepthTestStep();
		N root = createNode("0", null);
		N leaf = addDepth(root, startDepth);
		long nextLog = System.currentTimeMillis() + 1000;
		int depth = 0;
		while (true) {
			try {
				root.depthFirst(n -> {});
				depth = depth == 0 ? startDepth : depth + step;
				leaf = addDepth(leaf, step);
				if (System.currentTimeMillis() >= nextLog) {
					System.out.println(String.format("Current depth %,d", depth));
					nextLog = System.currentTimeMillis() + 1000;
				}
			} catch (StackOverflowError e) {
				System.out.println("Max depth with " + root.getClass().getSimpleName() + " : " + depth);
				System.out.println("Endend with " + e.getClass().getName());
				break;
			} catch (OutOfMemoryError e) {
				System.out.println("Max depth with " + root.getClass().getSimpleName() + " : " + depth);
				System.out.println("Endend with " + e.getClass().getName());
				break;
			}
		}
	}

	protected abstract int maxDepthTestStep();

	private N addDepth(N leaf, int depth) {
		if (leaf.left().isPresent())
			throw new IllegalArgumentException("Not a leaf");
		int from = Integer.parseInt(leaf.getName()) + 1;
		int to = from + depth;
		N current = leaf;
		for (int i = from; i < to; i++) {
			N child = createNode(Integer.toString(i), current);
			current.left(child);
			current = child;
		}
		return current;
	}

}
