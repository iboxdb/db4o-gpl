using System;

using Db4objects.Db4o;
using System.Linq;
using Db4objects.Db4o.Linq;
using Db4objects.Db4o.Query;
using Db4objects.Db4o.CS;
using Db4objects.Db4o.Ext;
using System.Threading;

namespace db40
{
    public class Person
    {

        public Person(string v)
        {
            Name = v;
        }

        public string Name { get; set; }
    }
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            //IObjectContainer db = Db4oFactory.OpenFile("/tmp/x.db");

            var server = Db4oClientServer.OpenServer("/tmp/x2.db", 7881);
            server.GrantAccess("user", "password");
            IObjectContainer client =
                    Db4oClientServer.OpenClient("localhost", 7881, "user", "password");
            var db = client;
            try
            {

                // Store a few Person objects

                db.Store(new Person("Petra"));

                db.Store(new Person("Gallad"));

                // Retrieve the Person

                var results = db.Query<Person>(x => x.Name == "Petra");

                Person p = results.First();

                Console.WriteLine(p.Name);

                var result2 = from Person tp in db
                              where tp.Name == "Petra"
                              select p;
                p = results.First();

                // Update the Person
                Console.WriteLine(p.Name);

                var uid = client.Ext().GetObjectInfo(p).GetInternalID();

                p = (Person)client.Ext().GetByID(uid);
                Console.WriteLine(p.Name);

                p.Name = "Peter";
                db.Store(p);


                // Delete the person
                db.Delete(p);

                // Don't forget to commit!
                db.Commit();
            }

            catch (Exception ex)
            {
                db.Rollback();
                Console.WriteLine(ex.ToString());
                throw ex;
            }

            finally
            {

                // Close the db cleanly
                client.Close();
                Environment.Exit(0);

            }


        }
    }
}
