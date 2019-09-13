using System;
using System.Collections.Generic;

namespace Db4objects.Db4o.Monitoring
{
    public class PerformanceCounter :IDisposable
    {
        public PerformanceCounter(string categoryName, string counterName, string instanceName, bool readOnly)
        {
            //throw new NotImplementedException();
        }

        public void RemoveInstance()
        {
            //throw new System.NotImplementedException();
        }

        public void IncrementBy(int bytesRead)
        {
            //throw new System.NotImplementedException();
        }

        public void Dispose()
        {
            //throw new System.NotImplementedException();
        }

        public void Increment()
        {
            //throw new System.NotImplementedException();
        }

        public event Action Disposed;
        public int RawValue { get; set; }
    }

    public enum PerformanceCounterType
    {
        RateOfCountsPerSecond32,
        NumberOfItems32
    }

    public class CounterCreationData
    {
        public CounterCreationData(string id, string description, PerformanceCounterType counterType)
        {
            //throw new NotImplementedException();
        }
    }

    public class CounterCreationDataCollection : 
        System.Collections.Generic.List <CounterCreationData>
    {
        public CounterCreationDataCollection(CounterCreationData[] creationData)
        :base(creationData)
        {
        }
    }

    public class PerformanceCounterCategory
    {
        public static void Delete(string categoryName)
        {
            //throw new NotImplementedException();
        }

        public static void Create(string categoryName, string db4oPerformanceCounters, PerformanceCounterCategoryType multiInstance, CounterCreationDataCollection collection)
        {
            //throw new NotImplementedException();
        }

        public static bool Exists(string categoryName)
        {
            //throw new NotImplementedException();
            return false;
        }
    }

    public enum PerformanceCounterCategoryType
    {
        MultiInstance
    }
}