using System;
using System.Drawing;
using System.Reflection;
using System.Windows.Forms;
using OMAddin;
using OMControlLibrary;
using OMControlLibrary.PortLinux;

namespace ObjectManager.PortImpl
{
    public class DTE2Impl : DTE2
    {
        public static MainForm mainForm;
        public DTE2Impl(MainForm _mainForm)
        {
            mainForm = _mainForm;
        }

        public override Windows2 Windows => new Windows2Impl();

        public override Events Events => new EventsImpl();


        public override OMControlLibrary.PortLinux.StatusBar StatusBar { get; } = new StatusBarImpl();

        public override CommandBars CommandBars { get; } = new CommandBarsImpl();

        public void Exec()
        {
            AddIn self = new AddIn();
            self.Name = "OMAddin GPL";
            this.AddIns.Add(self);
            var con = new Connect();
            Array tmp = null;
            con.OnConnection(this, ext_ConnectMode.ext_cm_Startup, self, ref tmp);
        }
        public void DisableMenu()
        {
            foreach(var e in mainForm.Menu.MenuItems)
            {
                ((MenuItem)e).Visible = false;
            }
        }
    }
    public class Windows2Impl : Windows2
    {
        public override Window CreateToolWindow2(AddIn addIn, string assemblypath, string toolWindowClass, string caption, string guidpos, ref object ctlobj)
        {
            Type tvb = typeof(ViewBase).Assembly.GetType(toolWindowClass);
            ViewBase vb = (ViewBase)Activator.CreateInstance(tvb);

            WindowImpl r = new WindowImpl();
            r.Form = new Form();
            r.Form.ControlBox = false;
            if (toolWindowClass != OMControlLibrary.Common.Constants.CLASS_NAME_OBJECTBROWSER)
            {
                r.Form.StartPosition = FormStartPosition.CenterScreen;

            }
            r.UserControl = vb;
            r.Form.Controls.Add(vb);
            r.Caption = caption;
            r.Form.Text = caption;
            return r;
        }

    }

    public class WindowImpl : Window
    {
        public Form Form;
        public ViewBase UserControl;


        public override bool Visible { get { return Form.Visible; } set { Form.Visible = value; if (true) Activate(); } }
        public override string Caption { get; set; }
        public override int Width { get { return Form.Width; } set { Form.Width = value; } }
        public override int Height { get { return Form.Height; } set { Form.Height = value; } }

        public override ViewBase Object { get { return UserControl; } }

        public override void Activate()
        {
            Form.Activate();
            WindowEvents.Instance.OnWindowActivated(this, null);
        }

        public override void Close(vsSaveChanges vsSaveChangesNo)
        {
         if ( Caption == OMControlLibrary.Common.Constants.LOGIN)
            {
                Form.Close();
            }
        }
         
    }

    public class StatusBarImpl : OMControlLibrary.PortLinux.StatusBar
    {
        public override string Text
        {
            get
            {
                return DTE2Impl.mainForm.statusBarPanel.Text;
            }
            set
            {
                DTE2Impl.mainForm.statusBarPanel.Text = value;
            }
        }
         
        public override void Clear()
        {
            DTE2Impl.mainForm.statusBarPanel.Text = "";
        }

        public override void Progress(bool v1, string v2, int v3, int v4)
        {
            Text = v2 = " , " + v3 + " , " + v4;
        }
    }
    public class CommandBarsImpl : CommandBars
    {
        public override CommandBar this[string name]
        {
            get
            {
                return new CommandBarImpl();
            }
        }

        public override CommandBar Add(string toolbarName, MsoBarPosition msoBarTop, object missing, bool v)
        {
            return new CommandBarImpl();
        }
    }

    public class CommandBarImpl : CommandBar
    {
        public override bool Visible { get; set; }
        public override bool Enabled { get; set; }
        public override string Caption { get; set; }
         

        public override CommandBarButton Control { get; }
        public override CommandBarControls Controls { get; set; } = new CommandBarControlsImpl();

        public override void Delete()
        {

        }

        public override void Delete(object p)
        {
        }
    }
    public class CommandBarButtonImpl : CommandBarButton
    {
        public MenuItem mItem;

        public override bool Visible { get; set; }
        public override bool Enabled { get; set; }
         
        public override string Caption { get { return mItem.Text; } set { mItem.Text = value; } }

        public override CommandBarButton Control { get { return this; } }
        public override CommandBarControls Controls { get; set; } = new CommandBarControlsImpl();

         public override void Delete(object p)
        {
        }
    }

    public class CommandBarControlsImpl : CommandBarControls
    {
        public override CommandBarControl Add(MsoControlType msoControlButton, object v1, object v2, object missing, bool v3)
        {
            switch (msoControlButton)
            {
                case MsoControlType.msoControlPopup:
                    return new CommandBarImpl();
                case MsoControlType.msoControlButton:
                    MenuItem mi = DTE2Impl.mainForm.Menu.MenuItems.Add("");
                    CommandBarButtonImpl btn = new CommandBarButtonImpl();
                    btn.mItem = mi;
                    mi.Tag = btn;
                    return btn;

            }
            return null;
        }
    }

    public class EventsImpl : Events2
    {
        public override DTEEvents DTEEvents => new DTEEvents();

        public override WindowEvents get_WindowEvents(object p)
        {
            return WindowEvents.Instance;
        }

        public override CommandBarEvents get_CommandBarEvents(CommandBarControl menuItem)
        {
            CommandBarButtonImpl btn = (CommandBarButtonImpl)menuItem;

            CommandBarEvents ev = new CommandBarEvents();


            btn.mItem.Click += (sender, e) =>
            {
                bool f = false;
                btn.OnClick(btn, ref f);
                ev.OnClick(btn, ref f, ref f);
            };

            return ev;
        }

        public override WindowVisibilityEvents get_WindowVisibilityEvents(Window queryResultToolWindow)
        {
            return new WindowVisibilityEvents();
        }
    }


}
