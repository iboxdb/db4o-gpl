using System;
namespace OMControlLibrary.PortLinux
{
    public class WindowVisibilityEvents
    {
        public WindowVisibilityEvents()
        {
        }

        public event Action<Window> WindowHiding;
    }
}
