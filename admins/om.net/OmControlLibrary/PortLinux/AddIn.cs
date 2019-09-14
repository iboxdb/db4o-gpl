using System;
using System.Collections.Generic;

namespace OMControlLibrary.PortLinux
{
    public class AddIn
    {
        public AddIn()
        {
        }

        public string Name { get;  set; }
    }

    public class AddIns : List<AddIn>
    {

    }
}
