package org.victor.utils.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象节点对象，需要使用 {@link TreeBuilder#builder(List)} 需要继承此类进行包装真正的节点对象
 * @author vector.luo
 * @param <E>节点中包装的 真正的用户节点对象
 */
public abstract class AbsNode<E> {
	
	private final E obj;
	private final List<AbsNode<E>> childs;

	public AbsNode(E obj) {
		this.obj = obj;
		this.childs = new ArrayList<AbsNode<E>>();
	}

	/**
	 * 获得自己的ID
	 * @return
	 */
	public abstract Integer getSelfId(E obj);

	/**
	 * 获得父节点ID
	 * @return
	 */
	public abstract Integer getParentId(E obj);

	/**
	 * 获得原始对象
	 * @return
	 */
	public E get() {
		return obj;
	}

	public void addChildren(AbsNode<E> node) {
		childs.add(node);
	}

	public List<AbsNode<E>> getChildren() {
		return childs;
	}

}