using System;
using System.Collections.Generic;
using System.Drawing;

namespace OMControlLibrary.PortLinux
{
    public abstract class Window
    { 

        public abstract bool Visible { get; set; }
        public abstract string Caption { get; set; }
        public abstract int Width { get;  set; }
        public abstract int Height { get; set; }
        public abstract ViewBase Object { get;   }

        public abstract void Activate();
       
        public abstract void Close(vsSaveChanges vsSaveChangesNo);

         

    }

    public abstract class Windows2   
    {
        public abstract Window CreateToolWindow2(AddIn addIn, string assemblypath, string toolWindowClass,
            string caption, string guidpos, ref object ctlobj);
         

    }

    public enum vsSaveChanges
    {
        vsSaveChangesNo,
        vsSaveChangesYes
    }
}
