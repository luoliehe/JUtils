package org.victor.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * this class extends {@link org.apache.commons.lang3.ClassUtils}
 * @author victor.luo
 */
public class ClassUtils extends org.apache.commons.lang3.ClassUtils {
	
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
		}
		if (cl == null) {
			cl = ClassUtils.class.getClassLoader();
		}
		return cl;
	}
	
	/**
	 * 获得class path 目录
	 * @return
	 */
	public static File getClassPath(){
		return new File(getDefaultClassLoader().getResource(".").getPath());
	}
	
	/**
	 * 找到指定包目录下的class
	 * @param packagePath 包路径
	 * @param isDeep 是否深层找，如果true，递归的找
	 * @return
	 */
	public static List<Class<?>> findClasses(String packagePath, boolean isDeep){
		
		List<String> classNames = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		Collection<File> files = FileUtils.listFiles(convertPackagePathToAbsolutePath(packagePath),
				new String[] { "class" }, isDeep);
		
		for (File f : files) {
			String r = FilenameUtils.getFullPath(getRelativePath(ClassUtils.getClassPath(), f));
			classNames.add(r.replaceAll("/|\\\\", ".") + FilenameUtils.getBaseName(f.getName()));
		}
		
		return ClassUtils.convertClassNamesToClasses(classNames);
	}
	
	private static File convertPackagePathToAbsolutePath(String packagePath){
		return new File(ClassUtils.getClassPath(), packagePath.replaceAll("\\.", "/"));
	}
	
	private static String getRelativePath(File base, File sub){
		return sub.getAbsolutePath().substring(base.getAbsolutePath().length()+1);
	}
	
	public static void main(String[] args) {
		for(Class<?> c : findClasses("", true)){
			System.out.println(c);
		}
		System.out.println("-------");
		for(Class<?> c : findClasses("org.victor.utils", false)){
			System.out.println(c);
		}
	}
	
}
