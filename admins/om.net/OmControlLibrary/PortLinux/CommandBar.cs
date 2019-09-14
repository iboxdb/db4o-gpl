using System;
using System.Collections.Generic;

namespace OMControlLibrary.PortLinux
{

    public abstract class CommandBarPopup : CommandBarControl
    {

    }
    public abstract class CommandBar : CommandBarPopup
    {
        public CommandBar()
        {
        }

        public  abstract bool Visible { get;  set; }

    public abstract void Delete();

    }



    public class CommandBarEvents
    {
        public event CommandBarClickDelegate Click;
        public void OnClick(object CommandBarControl, ref bool Handled, ref bool CancelDefault)
        {
            Click?.Invoke(CommandBarControl, ref Handled, ref CancelDefault);
        }
    }
    public delegate void CommandBarClickDelegate(object CommandBarControl, ref bool Handled, ref bool CancelDefault);

    public abstract class CommandBarControls //: List<CommandBarControl>
    {
        //n.Add(Type, Id, Parameter, Before, Temporary)
        public abstract CommandBarControl Add(MsoControlType msoControlButton, object v1, object v2, object missing, bool v3);

        /*
            switch (msoControlButton)
            {
                case MsoControlType.msoControlButton:
                    return new CommandBarButton();
                case msoControlPopup :
                    return new  CommandBarPopup
            }
          */
    }
    /*
    public class Missing
    {
        public Object Value;
    }
    */
}
