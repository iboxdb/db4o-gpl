using System;
using System.Diagnostics;
using System.Windows.Forms;
using ObjectManager.PortImpl;
using OMAddin;
using OMControlLibrary;

namespace ObjectManager
{
    public class MainForm : Form
    {  
        internal StatusBarPanel statusBarPanel;

        public MainForm()
        {
            this.SuspendLayout();

            this.Menu = new MainMenu();
            /*
            this.Menu.MenuItems.Add(" DB4O ").Click += (o,e) =>
            {
                MessageBox.Show("DB40 GPL");
            };
            */

            StatusBar statusBar1 = new StatusBar();
            statusBarPanel = new StatusBarPanel();
            statusBarPanel.BorderStyle = StatusBarPanelBorderStyle.Sunken;
            statusBarPanel.Text = "Ready...";
            statusBarPanel.AutoSize = StatusBarPanelAutoSize.Spring;
            statusBar1.ShowPanels = true;            
            statusBar1.Panels.Add(statusBarPanel);
            this.Controls.Add(statusBar1);

            new DTE2Impl(this).Exec();

            this.ResumeLayout();
            //this.Name = "DB4O GPL Object Manager";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Height = 100;
            this.Width = 600;
            this.MaximizeBox = false;
            this.Text = "DB4O GPL Object Manager (pid:" + Process.GetCurrentProcess().Id + ")";

        }


    }
   
}
