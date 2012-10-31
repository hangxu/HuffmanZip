package net.harryxu.hzip.algorithm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A heap with all the element sorted ascending
 * <p>
 * 由小到大元素排列的堆
 * @author Harry Xu 许行
 * @param <T>
 *            The type of the element in the heap
 */
public class MinimumHeap<T> {
	private List<Comparable<T>> list;

	/**
	 * Get a instance of heap, which has elements sorted ascending.
	 * <p>
	 * 得到一个由小到大元素排列的堆实例
	 */
	public MinimumHeap() {
		list = new LinkedList<Comparable<T>>();
	}

	private int getIndex(Comparable<T> e) {
		int start = 0;
		int end = list.size() - 1;
		int mid = (start + end) / 2;
		while (start <= end) {
			mid = (start + end) / 2;
			Comparable<T> midT = list.get(mid);
			int tmp = e.compareTo((T) midT);
			if (tmp > 0)
				start = mid + 1;
			else if (tmp < 0)
				end = mid - 1;
			else
				break;
		}
		return start;
	}

	public void add(Comparable<T> e) {
		int index = getIndex(e);
		list.add(index, e);
	}

	public T pop() {
		if (list.isEmpty()) {
			return null;
		} else {
			return (T) list.remove(0);
		}
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return (list.size() == 0);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (Iterator<Comparable<T>> iterator = list.iterator(); iterator
				.hasNext();) {
			Comparable<T> item = iterator.next();
			sb.append(item.toString() + ",");
		}
		int len = sb.length();
		if (sb.charAt(len - 1) == ',') {
			sb.deleteCharAt(len - 1);
			sb.append(']');
		}
		return sb.toString();

	}
}
