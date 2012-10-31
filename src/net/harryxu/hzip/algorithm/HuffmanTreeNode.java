package net.harryxu.hzip.algorithm;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * This class defines tree nodes for Huffman Coding.
 * 
 * @author Harry Xu 
 * @see HuffmanTree
 * 
 */
public class HuffmanTreeNode<T> implements Cloneable, Serializable,
		Comparable<HuffmanTreeNode<T>> {
	/**
	 * The value in this position
	 */
	private T value;
	/**
	 * The weight of the Node
	 */
	private int freq;
	/**
	 * The parent of the Node
	 */
	private HuffmanTreeNode parent;
	/**
	 * The left child of the Node
	 */
	private HuffmanTreeNode left;
	/**
	 * The right child of the Node
	 */
	private HuffmanTreeNode right;
	
	public ArrayList<Integer> encoding;

	/**
	 * Creates a leaf node with a given frequency
	 * 
	 * @throws IllegalArgumentException
	 *             if freq is negative
	 */
	public HuffmanTreeNode(T value, int freq) {
		if (freq < 0)
			throw new IllegalArgumentException(
					"frequence needs to be non-negative");

		this.value = value;
		this.freq = freq;
	}

	/**
	 * Creates an internal node with the given childern.
	 * 
	 * @param l
	 *            The left child
	 * @param r
	 *            The right child
	 * @throws IllegalArgumentException
	 *             If either l or r is null.
	 */
	public HuffmanTreeNode(HuffmanTreeNode l, HuffmanTreeNode r) {
		if (l == null || r == null)
			throw new IllegalArgumentException(
					"Trying to create an internal node with a null child");

		left = l;
		right = r;

		left.parent = right.parent = this;

		this.freq = l.getFreq() + r.getFreq();
	}

	/**
	 * Compares two nodes by their frequences
	 */
	public int compareTo(HuffmanTreeNode node) {
		return freq - node.freq;
	}

	/**
	 * Checks if this is a leaf node
	 */
	public boolean isLeaf() {
		return left == null && right == null;
	}

	/** Checks if this is the root of the Huffman tree. */
	public boolean isRoot() {
		return parent == null;
	}

	/** Gets the frequency of this node. */
	public int getFreq() {
		return freq;
	}

	/**
	 * Gets the value of this node, or null if this is an internal node
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Gets the parent of this node, or null if this is the root of the Huffman
	 * tree.
	 */
	public HuffmanTreeNode getParent() {
		return parent;
	}

	/**
	 * Gets the left child of this node, or null if this is a leaf.
	 */
	public HuffmanTreeNode getLeft() {
		return left;
	}

	/**
	 * Gets the right child of this node, or null if this is a leaf.
	 */
	public HuffmanTreeNode getRight() {
		return right;
	}

}
