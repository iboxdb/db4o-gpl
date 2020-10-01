

using System;
using Db4objects.Db4o;
using Db4objects.Db4o.Collections;
using Db4objects.Db4o.Config;
using Db4objects.Db4o.Config.Attributes;
using Db4objects.Db4o.Diagnostic;
using Db4objects.Db4o.IO;
using Db4objects.Db4o.TA;
using Db4objects.Db4o.Linq;
using System.Linq;

namespace db40
{
    public class Detail
    {

        [Indexed]
        public long type;

        public String memo;

        public Record record;
    }

    public class Record
    {

        [Indexed]
        public String name;

        public String noName;

        [Indexed]
        public double indexField;

        public double noIndexField;

        public Detail detail;
    }



    public class LinqIndex
    {
        public static void Run()
        {

            String dbname = "index.j.db";
            var ecfg = Db4oEmbedded.NewConfiguration();
            var memory = new MemoryStorage();
            ecfg.File.Storage = memory;
            ecfg.Common.Add(new TransparentActivationSupport());
            ecfg.Common.Add(new TransparentPersistenceSupport());
            ecfg.File.GenerateUUIDs = ConfigScope.Globally;

            //ecfg.Common.Diagnostic.AddListener(new DiagnosticToConsole());

            using (var oc = Db4oEmbedded.OpenFile(ecfg, dbname))
            {
                //Test Data
                using (var see = oc.Ext().OpenSession())
                {
                    var list = new ActivatableList<Record>();
                    for (int i = 0; i < 1000; i++)
                    {
                        for (int j = 0; j < 50; j++)
                        {
                            var r = new Record();
                            r.name = "Name-" + i;
                            r.noName = r.name;
                            r.indexField = (double)i;
                            r.noIndexField = i;
                            r.detail = new Detail();
                            r.detail.record = r;
                            r.detail.type = j;
                            r.detail.memo = r.name + " on meno";
                            list.Add(r);
                        }
                    }
                    see.Store(list);
                    see.Commit();
                }
                using (var see = oc.Ext().OpenSession())
                {
                    Console.WriteLine(see.Query<Record>().Count);
                }

                var ran = new Random();
                for (int i = 0; i < 3; i++)
                {
                    double value = (double)ran.Next(1000);
                    String name = "Name-" + (int)value;

                    Console.WriteLine("======Value: " + value + " =====");
                    using (var see = oc.Ext().OpenSession())
                    {
                        var st = DateTime.Now;

                        var rs = from Detail d in see where d.type == 10 select d;

                        Console.WriteLine("Size: " + rs.Count());
                        Console.WriteLine("Name: " + rs.ElementAt(0).memo);
                        Console.WriteLine("Name: " + rs.ElementAt(0).record.name);
                        var end = DateTime.Now;
                        Console.WriteLine("Detail Index Time /ms: " + (end - st).TotalMilliseconds + "\r\n");
                    }

                    using (var see = oc.Ext().OpenSession())
                    {
                        var st = DateTime.Now;

                        var rs = from Record r in see where r.name.StartsWith("Name-") select r;

                        Console.WriteLine("Size: " + rs.Count());
                        var end = DateTime.Now;
                        Console.WriteLine("Recrod StartsWith Time /ms: " + (end - st).TotalMilliseconds + "\r\n");
                    }

                    using (var see = oc.Ext().OpenSession())
                    {
                        var st = DateTime.Now;

                        var rs = from Record r in see where r.name.Length>0 select r;

                        Console.WriteLine("Size: " + rs.Count());
                        var end = DateTime.Now;
                        Console.WriteLine("Recrod Name.Length Time /ms: " + (end - st).TotalMilliseconds + "\r\n");
                    }

                    using (var see = oc.Ext().OpenSession())
                    {
                        var st = DateTime.Now;

                        var rs = from Record r in see where r.name == name select r;

                        Console.WriteLine("Size: " + rs.Count());
                        Console.WriteLine("Name: " + rs.ElementAt(0).name);
                        var end = DateTime.Now;
                        Console.WriteLine("Recrod Index Time /ms: " + (end - st).TotalMilliseconds + "\r\n");
                    }

                    using (var see = oc.Ext().OpenSession())
                    {
                        var st = DateTime.Now;

                        var rs = from Record r in see where r.indexField == value select r;

                        Console.WriteLine("Size: " + rs.Count());
                        var end = DateTime.Now;
                        Console.WriteLine("Recrod Index Time /ms: " + (end - st).TotalMilliseconds + "\r\n");
                    }


                    using (var see = oc.Ext().OpenSession())
                    {
                        var st = DateTime.Now;

                        var rs = from Record r in see where r.indexField == value && r.noName == name select r;

                        Console.WriteLine("Size: " + rs.Count());
                        var end = DateTime.Now;
                        Console.WriteLine("Recrod Index Time /ms: " + (end - st).TotalMilliseconds + "\r\n");
                    }

                    using (var see = oc.Ext().OpenSession())
                    {
                        var st = DateTime.Now;

                        var rs = from Record r in see where r.noIndexField == value select r;

                        Console.WriteLine("Size: " + rs.Count());
                        var end = DateTime.Now;
                        Console.WriteLine("Recrod NoIndex Time /ms: " + (end - st).TotalMilliseconds + "\r\n");
                    }


                    using (var see = oc.Ext().OpenSession())
                    {
                        var st = DateTime.Now;

                        var rs = from Record r in see where r.noName == name select r;

                        Console.WriteLine("Size: " + rs.Count());
                        var end = DateTime.Now;
                        Console.WriteLine("Recrod NoIndex Time /ms: " + (end - st).TotalMilliseconds + "\r\n");
                    }
                }

            }

            Console.WriteLine("End Linq.");
        }
    }

}