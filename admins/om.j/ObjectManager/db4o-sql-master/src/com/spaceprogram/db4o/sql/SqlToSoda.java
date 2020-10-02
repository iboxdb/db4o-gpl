package com.spaceprogram.db4o.sql;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.reflect.ReflectClass;
import com.db4o.reflect.ReflectField;
import com.db4o.reflect.jdk.JdkReflector;
import com.db4o.query.Query;
import com.db4o.query.Constraint;
import com.spaceprogram.db4o.sql.query.WhereExpression;
import com.spaceprogram.db4o.sql.query.SqlQuery;
import com.spaceprogram.db4o.sql.query.OrderBy;
import com.spaceprogram.db4o.sql.util.ReflectHelper;

import java.util.List;
import java.util.Date;

/**
 * This is a core class that will take a parsed SQL string, convert it to a soda query, then execute it.
 * <p/>
 * User: treeder
 * Date: Aug 1, 2006
 * Time: 5:15:56 PM
 */
public class SqlToSoda {
	// this is here just in case we want this field to be configurable
	public static boolean allowClassNotFound = true;


	public static List<Result> execute(ObjectContainer oc, SqlQuery q) throws Sql4oException {
		//System.out.println("QUERY: " + q);
		Query query = oc.query();
		for (int i = 0; i < q.getFrom().getClassRefs().size(); i++) {
			ClassRef classRef = q.getFrom().getClassRefs().get(i);
			String className = classRef.getClassName();

			// Class may not be on classpath, so lets use the generic reflector
			ReflectClass reflectClass = oc.ext().reflector().forName(className);
			if (reflectClass == null) {
				throw new Sql4oException("Class not stored: " + className);
			}
			query.constrain(reflectClass);
			// todo: where should we restrict to one class?  Or allow joins??

			verifySelectFields(reflectClass, q);

			// todo: apply value based where conditions to specific classes, join conditions can then be done after
			applyWhere(reflectClass, query, q);

			applyOrderBy(reflectClass, query, q);

		}

		ObjectSet results = query.execute();
		ObjectSetWrapper resultWrapper = new ObjectSetWrapper(oc, q, results);

		return resultWrapper;
	}

	private static void verifySelectFields(ReflectClass reflectClass, SqlQuery q) throws Sql4oException {
		if (q.getSelect() != null) {
			List<String> selFields = q.getSelect().getFields();
			if (selFields != null) {
				// check for asterisk
				if (selFields.size() == 1 && selFields.get(0).equals("*")) {
					return;
				}
				ReflectField[] fields = ReflectHelper.getDeclaredFieldsInHeirarchy(reflectClass);
				for (int i = 0; i < selFields.size(); i++) {
					String field = selFields.get(i);
					boolean fieldOk = false;
					for (int j = 0; j < fields.length; j++) {
						ReflectField reflectField = fields[j];
						//System.out.println("sel: " + field + " rf:" + reflectField.getName());
						if (reflectField.getName().equals(field)) {
							fieldOk = true;
							break;
						}
					}
					if (!fieldOk) {
						throw new Sql4oException("Field not found: " + field);
					}
				}
			}
		}
	}


	private static void applyWhere(ReflectClass reflectClass, Query dq, SqlQuery q) throws Sql4oException {
		if (q.getWhere() != null) {
			WhereExpression where = q.getWhere().getRoot();
			applyWhereRecursive(reflectClass, dq, q, where);
		}
	}

	private static void applyWhereRecursive(ReflectClass reflectClass, Query dq, SqlQuery q, WhereExpression where) throws Sql4oException {
		try {
			List<WhereExpression> expressions = where.getExpressions();
			Constraint previousConstraint = null;
			// then sub constraint, todo: make this happen somehow: AND's and OR's, etc
			// ok, first round: just or/and the expressions to the previous constraint
			if (!where.isRoot()) {
				// start bracket (
			}
			for (int i = 0; i < expressions.size(); i++) {
				WhereExpression whereExpression = expressions.get(i);
				if (whereExpression.getExpressions() != null && whereExpression.getExpressions().size() > 0) {
					applyWhereRecursive(reflectClass, dq, q, whereExpression);
				} else {
					Constraint constraint = makeConstraint(reflectClass, dq, whereExpression, q);
					if (previousConstraint != null) {
						if (whereExpression.getType().equalsIgnoreCase(WhereExpression.OR)) {
							//System.out.println("oring");
							previousConstraint.or(constraint);
						} else {
							//System.out.println("anding");
							//previousConstraint.and(constraint); // should be equivalent to not adding this
						}
					}
					previousConstraint = constraint;
				}
			}
			if (!where.isRoot()) {
				// end bracket )
			}

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new Sql4oException("Could not apply where conditions.  Exception: " + e.getMessage());
		}
	}

	private static Constraint makeConstraint(ReflectClass reflectClass, Query dq, WhereExpression where, SqlQuery q) throws CloneNotSupportedException, Sql4oException {
		//System.out.println("adding constraint: " + where);
		String[] fieldSplit = where.getField().split("\\.");
		//System.out.println("split size: " + fieldSplit.length);

		Query sub = dq;
		ReflectField field = null;
		ReflectClass fieldClass = reflectClass;
		// find this field in down the tree
		out:
		for (int i = 0; i < fieldSplit.length; i++) {
			String f = fieldSplit[i];
			// check if first field is an alias
			if (i == 0) {
				List<ClassRef> froms = q.getFrom().getClassRefs();
				for (int j = 0; j < froms.size(); j++) {
					ClassRef classRef = froms.get(j);
					String alias = classRef.getAlias();
					if (alias != null && alias.equals(fieldSplit[0])) {
						continue out;
					}
				}
			}
			//System.out.println("checking field: " + f );
			field = ReflectHelper.getDeclaredFieldInHeirarchy(fieldClass, f);
			if (field == null) throw new Sql4oException("Field not found: " + where.getField());
			fieldClass = field.getFieldType();
			sub = sub.descend(f);
		}

		if (field == null) throw new Sql4oException("Field not found: " + where.getField());

		Class c = JdkReflector.toNative(fieldClass);
		// convert to proper object type
		Object val = null;
		try {
			val = convertStringToObjectValue(c, where);
		} catch (Exception e) {
			val = null;
			// will throw in next if
		}
		if (val == null) {
			throw new Sql4oException("Could not create where condition value object! " + where.getValue() + " for field type " + fieldClass);
		}
		Constraint constraint = sub.constrain(val);
		applyOperator(reflectClass, constraint, where.getOperator(), dq, where, q);
		return constraint;
	}

	private static Object convertStringToObjectValue(Class to, WhereExpression where) throws Exception {
		String from = where.getValue();
		if (to == String.class) {
			from = cleanValue(from);
		} else if (to == Date.class) {
			from = cleanValue(from);
		}
		return Converter.convertFromString(to, from);
	}


	private static String cleanValue(String value) {
		// strip quotes
		value = value.replace("'", "");
		return value;
	}

	private static void applyOperator(ReflectClass reflectClass, Constraint constraint, String operator, Query dq, WhereExpression where, SqlQuery q) throws CloneNotSupportedException, Sql4oException {
		//System.out.println("operator:" + operator);
		if (operator.equals(WhereExpression.OP_GREATER)) {
			constraint.greater();
		} else if (operator.equals(WhereExpression.OP_LESS)) {
			constraint.smaller();
		} else if (operator.equals(WhereExpression.OP_GREATER_OR_EQUAL)) {
			constraint.greater();
			// have to OR this with an equals query too since db4o doesn't support this yet
			WhereExpression where2 = (WhereExpression) where.clone();
			where2.setOperator(WhereExpression.OP_EQUALS);
			constraint.or(makeConstraint(reflectClass, dq, where2, q));
		} else if (operator.equals(WhereExpression.OP_LESS_OR_EQUAL)) {
			constraint.smaller();
			// have to OR this with an equals query too since db4o doesn't support this yet
			WhereExpression where2 = (WhereExpression) where.clone();
			where2.setOperator(WhereExpression.OP_EQUALS);
			constraint.or(makeConstraint(reflectClass, dq, where2, q));
		} else if (operator.equals(WhereExpression.OP_NOT_EQUAL) || operator.equals(WhereExpression.OP_NOT_EQUAL_2)) {
			constraint.not();
		} else {
			constraint.equal(); // default
		}

	}

	private static void applyOrderBy(ReflectClass reflectClass, Query query, SqlQuery q) {
		OrderBy orderBy = q.getOrderBy();
		if (orderBy != null) {
			List<OrderBy.Field> fields = orderBy.getFields();
			for (int i = 0; i < fields.size(); i++) {
				OrderBy.Field field = fields.get(i);
				System.out.println("ordering by: " + field);
				if (field.isAscending()) {
					query.descend(field.getName()).orderAscending();
				} else {
					query.descend(field.getName()).orderDescending();
				}
			}
		}
	}
}