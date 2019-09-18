using System;
using System.Collections;
using System.Collections.Generic;
using Db4objects.Db4o.Ext;

namespace Db4objects.Db4o
{
    public class IObjectSet<T> : IList<T>, IEnumerable<T>
    {
        private IObjectSet set;

        public IObjectSet(IObjectSet _set)
        {
            set = _set;
        }


        public IExtObjectSet Ext()
        {
            return set.Ext();
        }

        public bool HasNext()
        {
            return set.HasNext();
        }

        public T Next()
        {
            return (T) set.Next();
        }

        public void Reset()
        {
            set.Reset();
        }

        public IEnumerator<T> GetEnumerator()
        {
            var e = new EEE();
            e.e = set.GetEnumerator();
            return e;
        }

        class EEE : IEnumerator<T>
        {
            internal IEnumerator e;

            public bool MoveNext()
            {
                return e.MoveNext();
            }

            public void Reset()
            {
                e.Reset();
            }

            public T Current
            {
                get { return (T) e.Current; }
            }

            object IEnumerator.Current => e.Current;

            public void Dispose()
            {
                (e as IDisposable)?.Dispose();
            }
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return set.GetEnumerator();
        }

        public void Add(T item)
        {
            set.Add(item);
        }

        public void Clear()
        {
            set.Clear();
        }

        public bool Contains(T item)
        {
            return set.Contains(item);
        }

        public void CopyTo(T[] array, int arrayIndex)
        {
            set.CopyTo(array, arrayIndex);
        }

        public bool Remove(T item)
        {
            set.Remove(item);
            return true;
        }

        public int Count
        {
            get { return set.Count; }
        }

        public bool IsReadOnly
        {
            get { return set.IsReadOnly; }
        }

        public int IndexOf(T item)
        {
            return set.IndexOf(item);
        }

        public void Insert(int index, T item)
        {
            set.Insert(index, item);
        }

        public void RemoveAt(int index)
        {
            set.RemoveAt(index);
        }

        public T this[int index]
        {
            get => (T) set[index];
            set => set[index] = value;
        }
    }
}