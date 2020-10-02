package com.spaceprogram.db4o.sql.util;

import com.db4o.reflect.ReflectClass;
import com.db4o.reflect.ReflectField;
import com.db4o.reflect.generic.GenericVirtualField;
import com.db4o.ext.StoredClass;
import com.db4o.ext.StoredField;
import com.db4o.ObjectContainer;

import java.util.*;
import java.text.Collator;

/**
 * User: treeder
 * Date: Aug 20, 2006
 * Time: 9:41:34 PM
 */
public class ReflectHelper {

	/**
	 * @param container
	 * @return list of ReflectClass objects that have been stored in database
	 */
	public static List getUserStoredClasses(ObjectContainer container) {
		String[] ignore = new String[]{
				// todo: decide which should be filtered for sure
				"java.lang.",
				"java.util.",
				"java.math.",
				"com.db4o.",
				"j4o.lang.AssemblyNameHint",
		};

		// Get the known classes
		ReflectClass[] knownClasses = container.ext().knownClasses();

		// Filter them
		List filteredList = new ArrayList();
		for (int i = 0; i < knownClasses.length; i++) {
			ReflectClass knownClass = knownClasses[i];
			if (knownClass.isArray() || knownClass.isPrimitive()) {
				continue;
			}
			boolean take = true;
			for (int j = 0; j < ignore.length; j++) {
				if (knownClass.getName().startsWith(ignore[j])) {
					take = false;
					break;
				}
			}
			if (!take) {
				continue;
			}
			StoredClass sc = container.ext().storedClass(knownClass.getName());
			if (sc == null) {
				continue;
			}
			filteredList.add(knownClass);
		}

		// Sort them
		Collections.sort(filteredList, new Comparator() {
			private Collator comparator = Collator.getInstance();

			public int compare(Object arg0, Object arg1) {
				ReflectClass class0 = (ReflectClass) arg0;
				ReflectClass class1 = (ReflectClass) arg1;

				return comparator.compare(class0.getName(), class1.getName());
			}
		});

		return filteredList;
	}

	public static ReflectField[] getDeclaredFieldsInHeirarchy(ReflectClass aClass) {
		List<ReflectField> ret = getDeclaredFieldsListInHeirarchy(aClass);
		return ret.toArray(new ReflectField[ret.size()]);
	}

	private static List<ReflectField> getDeclaredFieldsListInHeirarchy(ReflectClass aClass) {
		//System.out.println("getting fields for " + aClass);
		if(aClass == null) return null;
		List ret = getDeclaredFieldsList(aClass);
		ReflectClass parent = aClass.getSuperclass();
		if (parent != null) {
			ret.addAll(getDeclaredFieldsListInHeirarchy(parent));
		}
		return ret;
	}

	public static List<ReflectField> getDeclaredFieldsList(ReflectClass aClass) {
		List<ReflectField> ret = new ArrayList();
		ReflectField[] fields = aClass.getDeclaredFields();
		// need to filter here because some internal fields are coming through (v4oversion and v4ouuid)
		for (int i = 0; i < fields.length; i++) {
			ReflectField field = fields[i];
			if (!(field instanceof GenericVirtualField)) {
				ret.add(field);
			}
		}
		return ret;
	}

	public static StoredField[] getDeclaredFieldsInHeirarchy(StoredClass aClass) {
		List<ReflectField> ret = getDeclaredFieldsListInHeirarchy(aClass);
		return ret.toArray(new StoredField[ret.size()]);
	}

	private static List getDeclaredFieldsListInHeirarchy(StoredClass aClass) {
		//System.out.println("getting fields for " + aClass);
		if(aClass == null) return null;
		List ret = getDeclaredFieldsList(aClass);
		StoredClass parent = aClass.getParentStoredClass();
		if (parent != null) {
			ret.addAll(getDeclaredFieldsListInHeirarchy(parent));
		}
		return ret;
	}

	public static List<StoredField> getDeclaredFieldsList(StoredClass aClass) {
		List<StoredField> ret = new ArrayList();
		StoredField[] fields = aClass.getStoredFields();
		// need to filter here because some internal fields are coming through (v4oversion and v4ouuid)
		for (int i = 0; i < fields.length; i++) {
			StoredField field = fields[i];
			if (!(field instanceof GenericVirtualField)) {
				ret.add(field);
			}
		}
		return ret;
	}

	public static ReflectField[] getDeclaredFields(ReflectClass aClass) {
		List<ReflectField> ret = getDeclaredFieldsList(aClass);
		return ret.toArray(new ReflectField[ret.size()]);
	}

	public static ReflectField getDeclaredFieldInHeirarchy(ReflectClass reflectClass, String field) {
		ReflectField rf = reflectClass.getDeclaredField(field);
		if (rf == null) {
			// check up heirarchy
			ReflectClass parent = reflectClass.getSuperclass();
			if (parent != null) {
				return getDeclaredFieldInHeirarchy(parent, field);
			}
		}
		return rf;
	}
}
