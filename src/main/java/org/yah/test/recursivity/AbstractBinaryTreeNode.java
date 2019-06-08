package org.yah.test.recursivity;

import java.util.Objects;
import java.util.Optional;

/**
 * An abstract binary tree node.
 * 
 * It provides the base structure of the node and define methods that could
 * either be done using recursivity (calling the a method from itself), or using
 * iteration and a memory (heap) stack or queue to do the work.
 * 
 * The node properties (parent and children) are mutable in case someone would
 * like to implements some balancing of the binary tree (perhaps I'll do it one
 * day ...).
 * 
 * @param <N>
 *            The concrete node type. This allow to ensure that all the node of
 *            a tree are of the same type and expose the concrete type if parent
 *            and children to implementations.
 */
public abstract class AbstractBinaryTreeNode<N extends AbstractBinaryTreeNode<N>> {

	protected final String name;

	protected N parent;

	protected final N[] children;

	public AbstractBinaryTreeNode(String name, N parent, N[] children) {
		this.name = Objects.requireNonNull(name, "name is null");
		this.parent = parent;
		this.children = Objects.requireNonNull(children, "children is null");
	}

	public final String getName() {
		return name;
	}

	public final N getParent() {
		return parent;
	}

	public final boolean isRoot() {
		return parent == null;
	}

	public final Optional<N> left() {
		return Optional.ofNullable(children[0]);
	}

	public final void left(N left) {
		children[0] = left;
	}

	public final Optional<N> right() {
		return Optional.ofNullable(children[1]);
	}

	public final void right(N right) {
		children[1] = right;
	}

	@Override
	public String toString() {
		return name;
	}

	public String print() {
		return "";
	}

	/**
	 * The depth of the node is how many ancestor this node have. <br/>
	 * <ul>
	 * <li>The root node has no parent, depth is 0,</li>
	 * <li>First children have a depth of one (only one ancestor, the root node),
	 * and so on.</li>
	 * </ul>
	 * 
	 * @return the depth of this node.
	 */
	public abstract int depth();

	/**
	 * Depth first visit<br/>
	 * Too lazy to explain, check here
	 * {@link https://en.wikipedia.org/wiki/Tree_traversal}
	 */
	public abstract void depthFirst(Visitor<N> visitor);

	public interface Visitor<N extends AbstractBinaryTreeNode<N>> {
		void visit(N node);
	}
}
