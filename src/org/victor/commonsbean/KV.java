package org.victor.commonsbean;

/**
 * 存放 key value值对象
 * @author llh
 * @param <K>
 * @param <V>
 */
public class KV<K, V> {

	private K key;
	private V value;

	public KV() {
		super();
	}
	
	public KV(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
}
