using System;
using System.IO;
using System.Threading;
using Db4objects.Db4o;
using Db4objects.Db4o.CS;
namespace OMNTest
{
    class CreateDateTimeDatabase
    {
        static String FILE = "datetime.db";
        public void Run()
        {
            File.Delete(FILE);
            IObjectContainer objectContainer = Db4oEmbedded.OpenFile(FILE);
            Store(objectContainer);
            objectContainer.Close();
        }

        public void RunServer(int port)
        {
            var config = Db4oClientServer.NewServerConfiguration();
            config.TimeoutServerSocket = 1000 * 10;
            var server = Db4oClientServer.OpenServer(config, FILE, port);
            server.GrantAccess("uu", "pp");
            while(Console.ReadLine() != "exit")
            {
                Thread.Sleep(1000);
            }
            Environment.Exit(0);
        }

        private void Store(IObjectContainer objectContainer)
        {
            Item item = new Item();
            item._name = "First";
            item._dateTime = new DateTime(2009,1,14);
            objectContainer.Store(item);

            item = new Item();
            item._name = "Second";
            item._dateTime = new DateTime(2009, 1, 15);
            objectContainer.Store(item);
        }

        public class Item
        {
            public String _name;

            public DateTime _dateTime;


        }

    }

}
