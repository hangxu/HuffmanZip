package net.harryxu.hzip;

import java.io.*;
import java.util.*;

import net.harryxu.hzip.util.*;
import net.harryxu.hzip.algorithm.*;

/**
 * This class define some method with using huffman algorithm and IO to encode a
 * compressed file
 * 
 * @author Harry Xu
 * 
 */
public class Encode {

	/** The magic number of the hzip file (XH, 0x58 - 0x48) */
	private static byte[] MAGIC = { 0x58, 0x48 };

	/** the root of the Huffman tree */
	private HuffmanTreeNode<Byte> root;

	/** it stores chars and their frequences */
	private Map<Byte, Integer> freqMap;

	/** it stores bytes and the encoding */
	private Map<Byte, Integer[]> encoding;

	/** it stores nodes to build tree */
	private MinimumHeap<HuffmanTreeNode<Byte>> nodeHeap;

	/**
	 * Reads-in the input file, compresses it and writes to the output file
	 * 
	 * @param reader
	 *            The reader of the input file
	 * @param writer
	 *            The writer of the output file
	 */
	public void encode(BitReader reader, BitWriter writer) {
		int fileBytes = reader.length();
		if (fileBytes == 0)
			return;

		countFrequencies(reader);
		HuffmanTree(freqMap);
		encoding = new HashMap<Byte, Integer[]>();
		buildTable(root);
		try {
			writeHeader(writer);
		} catch (IOException e) {
		}

		writer.writeInt(fileBytes);
		reader.reset();

		for (int i = 0; i < fileBytes; i++)
			encode((byte) reader.readByte(), writer);

		writer.flush();
	}

	/**
	 * 
	 * This method translate the giving byte to some bits by Huffman Coding
	 * 
	 * @param item
	 *            the giving byte to encode
	 * @param writer
	 *            The writer of the output file
	 * 
	 */
	public void encode(Byte item, BitWriter writer) {
		Integer[] code = encoding.get(item);
		for (Integer x : code) {
			writer.writeBit(x);
		}
	}

	/**
	 * This is a recursive method, which traverse the Huffman tree and build the
	 * encoding table
	 * 
	 * @param node
	 *            the Huffman Tree node
	 */
	public void buildTable(HuffmanTreeNode<Byte> node) {
		if (node == null)
			return;
		if (node == root) {
			root.encoding = new ArrayList<Integer>();
			if (root.isLeaf()) {
				root.encoding.add(0);
				this.encoding.put(root.getValue(),
						(Integer[]) root.encoding.toArray());
				return;
			}
			buildTable(root.getLeft());
			buildTable(root.getRight());
		} else {
			node.encoding = (ArrayList<Integer>) node.getParent().encoding
					.clone();
			if (node == node.getParent().getLeft())
				node.encoding.add(0);
			else
				node.encoding.add(1);
			if (node.isLeaf()) {
				this.encoding.put(node.getValue(), node.encoding
						.toArray(new Integer[node.encoding.size()]));
				return;
			}
			buildTable(node.getLeft());
			buildTable(node.getRight());
		}
	}

	/**
	 * Count the frequence of each byte in the giving file
	 * 
	 * @param reader
	 *            The reader of the input file
	 */
	public void countFrequencies(BitReader reader) {
		int ch;
		freqMap = new HashMap<Byte, Integer>();
		while ((ch = reader.readByte()) != -1) {
			if (freqMap.containsKey((byte) ch)) {
				freqMap.put((byte) ch, freqMap.get((byte) ch) + 1);
			} else {
				freqMap.put((byte) ch, 1);
			}
		}
	}

	/**
	 * Build the huffman tree for encoding by using the byte-frequencies map
	 * 
	 * @param map
	 *            the byte-frequencies map
	 */
	public void HuffmanTree(Map<Byte, Integer> map) {
		nodeHeap = new MinimumHeap<HuffmanTreeNode<Byte>>();
		for (Iterator<Map.Entry<Byte, Integer>> iterator = map.entrySet()
				.iterator(); iterator.hasNext();) {
			Map.Entry<Byte, Integer> entry = iterator.next();
			HuffmanTreeNode<Byte> node = new HuffmanTreeNode<Byte>(
					entry.getKey(), entry.getValue());
			nodeHeap.add((Comparable<HuffmanTreeNode<Byte>>) node);
		}
		if (nodeHeap != null && nodeHeap.size() > 0) {
			while (nodeHeap.size() >= 2) {
				HuffmanTreeNode<Byte> node1 = nodeHeap.pop();
				HuffmanTreeNode<Byte> node2 = nodeHeap.pop();
				HuffmanTreeNode<Byte> newNode = new HuffmanTreeNode<Byte>(
						node1, node2);
				nodeHeap.add((Comparable<HuffmanTreeNode<Byte>>) newNode);

			}
			root = nodeHeap.pop();
			nodeHeap = null;
			System.gc();
		}
	}

	/**
	 * Writes the header for the compressed file, which contains a
	 * byte-frequencies map
	 * 
	 * @param writer
	 *            The writer of the output file
	 * @throws IOException
	 */
	public void writeHeader(BitWriter writer) throws IOException {
		writer.writeBytes(MAGIC);
		writer.writeByte((byte) 0x01);// Version
		writer.writeByte((byte) 0xff);// Reserved Flags
		// writer.writeLong4((new Date()).getTime());
		writer.writeInt(freqMap.size());
		for (Iterator<Map.Entry<Byte, Integer>> iterator = freqMap.entrySet()
				.iterator(); iterator.hasNext();) {
			Map.Entry<Byte, Integer> entry = iterator.next();
			writer.writeByte(entry.getKey());
			writer.writeInt(entry.getValue());
		}
	}

}
