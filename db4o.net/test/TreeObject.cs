using System;

using Db4objects.Db4o;
using System.Linq;
using Db4objects.Db4o.Linq;
using Db4objects.Db4o.Query;
using Db4objects.Db4o.CS;
using Db4objects.Db4o.Ext;
using System.Threading;
using System.IO;

namespace db40
{
    public class Node
    {
        public string Name;
        public Node Left;
        public Node Right;
    }

    public class TreeProgram
    {
        public static void Run()
        {
            String file = "tree.n.db";
            File.Delete(file);

            var cfg = Db4oClientServer.NewServerConfiguration();
            cfg.Common.ObjectClass(typeof(Node)).CallConstructor(true);
            cfg.Common.ObjectClass(typeof(Node)).CascadeOnActivate(true);
            cfg.Common.ObjectClass(typeof(Node)).CascadeOnUpdate(true);
            cfg.Common.ObjectClass(typeof(Node)).CascadeOnDelete(true);
            using (var server = Db4oClientServer.OpenServer(cfg, file, 0))
            {
                using (var client = server.OpenClient())
                {
                    Node root = new Node();
                    root.Name = "Root";
                    root.Left = new Node();
                    root.Left.Name = "Left";
                    root.Right = new Node();
                    root.Right.Name = "Right";
                    root.Right.Right = new Node();
                    root.Right.Right.Name = "Right.Right";
                    client.Store(root);
                    client.Commit();
                }

                using (var client = server.OpenClient())
                {
                    var root = (from Node n in client
                                where n.Name == "Root"
                                select n).First();
                    Console.WriteLine(root.Right.Right.Name);
                }

                using (var client = server.OpenClient())
                {
                    var root = client.Query((Node n) => { return n.Name == "Root"; }).First();
                    Console.WriteLine(root.Right.Right.Name);
                }

                using (var client = server.OpenClient())
                {
                    var root = client.QueryByExample(new Node { Name = "Root" })[0] as Node;
                    Console.WriteLine(root.Right.Right.Name);
                }

                //Not Recommended
                using (var client = server.OpenClient())
                {
                    var q = client.Query();
                    q.Constrain(typeof(Node));
                    q.Descend("Name").Constrain("Root").Equal();
                    var root = q.Execute()[0] as Node;
                    Console.WriteLine(root.Right.Right.Name);
                }

                using (var client = server.OpenClient())
                {
                    var rr = (from Node n in client
                              where n.Name == "Right.Right"
                              select n).First();
                    Console.WriteLine(rr.Name);

                    rr.Name += ".Update";
                    client.Store(rr);
                    client.Commit();
                }

                using (var client = server.OpenClient())
                {
                    var root = (from Node n in client
                                where n.Name == "Root"
                                select n).First();
                    Console.WriteLine(root.Right.Right.Name);
                }


            }
        }
    }
}