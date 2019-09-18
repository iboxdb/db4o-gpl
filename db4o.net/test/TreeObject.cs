using System;

using Db4objects.Db4o;
using System.Linq;
using Db4objects.Db4o.Linq;
using Db4objects.Db4o.Query;
using Db4objects.Db4o.CS;
using Db4objects.Db4o.Ext;
using System.Threading;
using System.IO;
using Db4objects.Db4o.Config;
using Db4objects.Db4o.Config.Attributes;
using Db4objects.Db4o.TA;

namespace db40
{
    public class Node
    {
        [Indexed]
        public string Name;
        public Node Left;
        public Node Right;

        public int Size;
    }

    public class TreeProgram
    {
        public static void Run()
        {
            String file = "tree.n.db";
            File.Delete(file);
            long internalId;
            String objectId;

            var cfg = Db4oClientServer.NewServerConfiguration();
            cfg.Common.ObjectClass(typeof(Node)).CallConstructor(true);
            cfg.Common.ObjectClass(typeof(Node)).CascadeOnActivate(true);
            cfg.Common.ObjectClass(typeof(Node)).CascadeOnUpdate(true);
            cfg.Common.ObjectClass(typeof(Node)).CascadeOnDelete(true);
            
            cfg.Common.Add(new TransparentActivationSupport());
            cfg.Common.Add(new TransparentPersistenceSupport());
            cfg.File.GenerateUUIDs = ConfigScope.Globally;
            
            using (var server = Db4oClientServer.OpenServer(cfg, file, 0))
            {
                using (var client = server.OpenClient())
                {
                    var root = new Node();
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
                
                using (var oc = server.OpenClient()) {
                    var metaInfo = oc.Ext().StoredClass(typeof(Node));
                    // list a fields and check if they have a index
                    foreach (var field in metaInfo.GetStoredFields()) {
                        if (field.HasIndex()) {
                            Console.WriteLine("The field '" + field.GetName() + "' is indexed");
                        } else {
                            Console.WriteLine("The field '" + field.GetName() + "' isn't indexed");
                        }
                    }
                }
                using (var client = server.OpenClient())
                { 
                    var root = client.QueryByExample(new Node {Name = "Root"})[0];
                    Console.WriteLine(root.Right.Right.Name);
                    internalId = client.Ext().GetID(root);
                    objectId = client.Ext().GetObjectInfo(root).GetUUID().ToString();
                }
                
                using (var client = server.OpenClient()) {
                    Console.WriteLine(objectId);
                    var root = client.Ext().GetByUUID<Node>(objectId);
                    Console.WriteLine(root.Right.Right.Name);
                }
                
                using (var oc = server.OpenClient()) {
                    Console.WriteLine(internalId);
                    var root = oc.Ext().GetByID<Node>(internalId);
                    Console.WriteLine(root.Right.Right.Name);
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



            var ecfg = Db4oEmbedded.NewConfiguration();
            ecfg.Common.ObjectClass(typeof(Node)).CallConstructor(true);
            ecfg.Common.ObjectClass(typeof(Node)).CascadeOnActivate(true);
            ecfg.Common.ObjectClass(typeof(Node)).CascadeOnUpdate(true);
            ecfg.Common.ObjectClass(typeof(Node)).CascadeOnDelete(true);
            
            ecfg.Common.Add(new TransparentActivationSupport());
            ecfg.Common.Add(new TransparentPersistenceSupport());
            ecfg.File.GenerateUUIDs = ConfigScope.Globally;

            
            using (var oc = Db4oEmbedded.OpenFile(ecfg, file)) {

                Console.WriteLine(objectId);
                var root = oc.Ext().GetByUUID<Node>(objectId);
                Console.WriteLine(root.Right.Right.Name);

                Console.WriteLine(internalId);
                root = oc.Ext().GetByID<Node>( internalId);
                Console.WriteLine(root.Right.Right.Name);
            }
            
            
            ecfg = Db4oEmbedded.NewConfiguration();
            ecfg.Common.ObjectClass(typeof(Node)).CallConstructor(true);
            ecfg.Common.ObjectClass(typeof(Node)).CascadeOnActivate(true);
            ecfg.Common.ObjectClass(typeof(Node)).CascadeOnUpdate(true);
            ecfg.Common.ObjectClass(typeof(Node)).CascadeOnDelete(true);
            
            ecfg.Common.Add(new TransparentActivationSupport());
            ecfg.Common.Add(new TransparentPersistenceSupport());
            ecfg.File.GenerateUUIDs = ConfigScope.Globally;

            
            using (var oc = Db4oEmbedded.OpenFile(ecfg, file)) {
 
                using (var see = oc.Ext().OpenSession()) {
                    Console.WriteLine(objectId);
                    var root = see.Ext().GetByUUID<Node>(objectId);
                    Console.WriteLine(root.Right.Right.Name);
                }

                using (var see = oc.Ext().OpenSession()) {
                    Console.WriteLine(internalId);
                    var root = see.Ext().GetByID<Node>(internalId);
                    Console.WriteLine(root.Right.Right.Name);
                }
            }

        }
    }
}