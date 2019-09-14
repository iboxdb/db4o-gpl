using System;
using System.Windows.Forms;

namespace ObjectManager
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("Begin");
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new MainForm());
        }
    }
}
