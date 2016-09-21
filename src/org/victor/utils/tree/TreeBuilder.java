package org.victor.utils.tree;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.reflect.ConstructorUtils;

/**
 * 树结构数据创建器<br>
 * 调用方法 {@link #builder(List, Class)} 可以进行一步到位的进行树的生成<br> 
 * 包装节点需继承{@link AbsNode}
 * 
 * @param <E> 真正的节点对象
 * 
 * @author victor.luo
 */
final public class TreeBuilder<E>{
	
	private Map<Integer, AbsNode<E>> map;
	
	private final Map<Integer, List<AbsNode<E>>> parentsMap;
	
	
	public TreeBuilder() {
		this.map = new HashMap<>();
		this.parentsMap = new HashMap<>();
	}
	
	/***
	 * 包装数据成节点对象，并且构建树结构数据，返回根节点数组
	 * @param list 需要包装的原始数据数组
	 * @param c 包装成的节点对象 c 基础于{@link AbsNode}
	 * @return 根节点数组
	 * @throws TreeBuilderException
	 */
	public List<AbsNode<E>> builder(List<E> list,
			Class<? extends AbsNode<E>> c) throws TreeBuilderException{
		try {
			return builder(warpToNode(list, c));
		} catch (Exception e) {
			throw new TreeBuilderException(e);
		}
		
	}
	
	/**
	 * 将数据结构组织成树
	 * 创建一个树对象，返回所有的 root 根列表
	 * @param list 返回构建好的所有的根节点列表
	 * @return
	 */
	public List<AbsNode<E>> builder(List<? extends AbsNode<E>> list){
		reset();

		for (AbsNode<E> node : list) {
			map.put(node.getSelfId(node.get()), node);
		}
		 
		List<AbsNode<E>> roots = new ArrayList<AbsNode<E>>();
		
		// 组织树结构关系
		for (Map.Entry<Integer, AbsNode<E>> entry : map.entrySet()) {
			AbsNode<E> node = entry.getValue();
			AbsNode<E> parent = map.get(node.getParentId(node.get()));
			if (parent == null) {
				roots.add(node);
			} else {
				parent.addChildren(node);
			}
		}
		
		return roots;
	}
	
	/**
	 * 找到整个直系的父节点
	 * @param id
	 * @return
	 */
	public List<AbsNode<E>> getParents(Integer id) {
		
		List<AbsNode<E>> list = parentsMap.get(id);
		
		if (list == null) {
			synchronized (parentsMap) {
				list = parentsMap.get(id);
				if (list == null) {
					AbsNode<E> node = getNode(id);
					if (node != null) {
						list = new ArrayList<AbsNode<E>>();
						list.add(node);
						while ((node = getNode(node.getParentId(node.get()))) != null) {
							list.add(node);
						}
						parentsMap.put(id, list);
					}
				}
			}
		}

		return list;
	}
	
	/**
	 * 获得一个节点
	 * @param id
	 * @return
	 */
	public AbsNode<E> getNode(Integer id){
		return map == null ? null : map.get(id);
	}
	
	/**
	 * 将原始对象包装成节点对象,节点对象需要覆盖 {@link AbsNode} 的构造方法 
	 * 
	 * @param list 需要包装的原始数据
	 * @param type 包装成的目标对象, 目标类型的为 {@link AbsNode}的子类
	 * @return 返回包装成 warpClass 类型的数组
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <E> List<AbsNode<E>> warpToNode(List<E> list, Class<? extends AbsNode<E>> type) 
			throws NoSuchMethodException, IllegalAccessException,InvocationTargetException, InstantiationException {
		
		if(list == null || list.isEmpty()){
			return null;
		}
		
		final List<AbsNode<E>> warpList = new ArrayList<AbsNode<E>>();
		
		for (E obj : list) {
			warpList.add(ConstructorUtils.invokeConstructor(type, obj));
		}
		
		return warpList;
	}
	
	private void reset() {
		if (map != null) {
			map.clear();
		}
		if (parentsMap != null) {
			parentsMap.clear();
		}
	}
}
