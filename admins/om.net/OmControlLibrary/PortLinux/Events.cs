using System;
namespace OMControlLibrary.PortLinux
{
    public abstract class Events
    {
        public Events()
        {
        }

        public abstract DTEEvents  DTEEvents { get;  }

        public abstract WindowEvents get_WindowEvents(object p);

        public abstract CommandBarEvents get_CommandBarEvents(CommandBarControl menuItem);
   
    }

    public abstract class Events2 : Events
    {
        public abstract WindowVisibilityEvents get_WindowVisibilityEvents(Window queryResultToolWindow);
        
    }


    public class DTEEvents
    {
        public DTEEvents()
        {
        }

        public Action<vsIDEMode> ModeChanged { get; set; }
    }

    public enum ext_ConnectMode
    {
        ext_cm_AfterStartup,
        ext_cm_Startup
    }
    public enum vsIDEMode
    {

    }
    public enum ext_DisconnectMode
    {

    }
    public enum vsCommandStatusTextWanted
    {
        vsCommandStatusTextWantedNone
    }

    public enum vsCommandStatus
    {
        vsCommandStatusSupported,
        vsCommandStatusEnabled,
        vsCommandStatusUnsupported
    }
    public enum vsCommandExecOption
    {
        vsCommandExecOptionDoDefault
    }
}
