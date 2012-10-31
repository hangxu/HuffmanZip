package net.harryxu.hzip;

import java.io.EOFException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.harryxu.hzip.algorithm.HuffmanTreeNode;
import net.harryxu.hzip.algorithm.MinimumHeap;
import net.harryxu.hzip.util.*;

/**
 * This class define some method with using huffman algorithm and IO to decode a
 * compressed file
 * 
 * @author Harry Xu
 * 
 */
public class Decode {

	/** The magic number of the hzip file (XH, 0x58 - 0x48) */
	private static int[] MAGIC = { 0x58, 0x48 };

	/** the root of the Huffman tree */
	private HuffmanTreeNode<Byte> root;

	/** it stores characters and their frequences */
	private Map<Byte, Integer> freqMap;

	/** it stores nodes to build tree */
	private MinimumHeap<HuffmanTreeNode<Byte>> nodeHeap;

	/**
	 * 
	 * Reads-in the input file, decodes it and writes to the output file
	 * 
	 * @param reader
	 *            The reader of the input file
	 * @param writer
	 *            The writer of the output file
	 */
	public void decode(BitReader reader, BitWriter writer) {
		if (reader.length() == 0)
			return;

		int mag0 = reader.readByte();
		int mag1 = reader.readByte();
		int version = reader.readByte();
		reader.readByte();
		if (mag0 != MAGIC[0] && mag1 != MAGIC[1] && version != 0x01)
			throw new RuntimeException("Unsupported Zip File");
		HuffmanTree(reader);
		int fileBytes = 0;
		try {
			fileBytes = reader.readInt();
		} catch (EOFException e) {
		}

		for (int i = 0; i < fileBytes; i++)
			writer.writeByte((byte) decode(reader));

		writer.flush();
	}

	/**
	 * 
	 * Reads the header from a compressed file and builds the Huffman tree for
	 * decoding.
	 * 
	 * @param reader
	 *            The reader of the input file
	 */
	public void HuffmanTree(BitReader reader) {
		freqMap = new HashMap<Byte, Integer>();
		try {
			int size = reader.readInt();
			for (int i = 0; i < size; i++) {
				byte b = (byte) reader.readByte();
				int in = reader.readInt();
				freqMap.put(b, in);
			}
			nodeHeap = new MinimumHeap<HuffmanTreeNode<Byte>>();
			for (Iterator<Map.Entry<Byte, Integer>> iterator = freqMap
					.entrySet().iterator(); iterator.hasNext();) {
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
		} catch (EOFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method reads bits from the reader and traverse the Huffman tree to
	 * get the value stored in a leaf.
	 * 
	 * @param reader
	 *            The reader of the input file
	 * @return The next byte in the input compressed file
	 */
	public Byte decode(BitReader reader) {
		Byte result;
		HuffmanTreeNode<Byte> node = root;
		while (true) {

			if (node.isLeaf()) {
				result = node.getValue();
				break;
			}

			if (reader.readBit() == 0)
				node = node.getLeft();
			else
				node = node.getRight();

		}
		return result;
	}

}
