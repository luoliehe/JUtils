package org.victor.commonsbean;

/**
 * 只存放两个相同类型
 * @author llh
 * @param <T>
 */
public class Pair<T> {
	private T first;
	private T second;

	public Pair() {
		super();
	}

	public <E extends T> Pair(E first, E second) {
		super();
		this.first = first;
		this.second = second;
	}

	/**获得第一个值*/
	public T getFirst() {
		return first;
	}

	public void setFirst(T first) {
		this.first = first;
	}
	
	/**获得第二个值*/
	public T getSecond() {
		return second;
	}

	public void setSecond(T second) {
		this.second = second;
	}

}
