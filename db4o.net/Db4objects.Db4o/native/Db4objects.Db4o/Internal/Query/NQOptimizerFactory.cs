/* Copyright (C) 2006   Versant Inc.   http://www.db4o.com */

namespace Db4objects.Db4o.Internal.Query
{
	using System;

	public class NQOptimizerFactory
	{
		public static INQOptimizer CreateExpressionBuilder()
		{
            //removed, use Linq instead
			Type type = Type.GetType("Db4objects.Db4o.NativeQueries.NQOptimizer, Db4objects.Db4o.NativeQueries", false);
            if (type == null)
            {
                type = Type.GetType("Db4objects.Db4o.NativeQueries.NQOptimizer", true);
            }
            return (INQOptimizer)Activator.CreateInstance(type);
		}
	}
}
