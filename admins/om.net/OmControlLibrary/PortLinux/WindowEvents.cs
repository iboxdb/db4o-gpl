using System;
namespace OMControlLibrary.PortLinux
{
    public class WindowEvents
    {
        public static readonly WindowEvents Instance = new WindowEvents();
        private WindowEvents()
        {
        }

        public void OnWindowActivated(Window got, Window lost) {
            WindowActivated.Invoke(got, lost); 
        }
        public event Action<Window, Window> WindowActivated;
    }
}
