using System;
using System.Drawing;

namespace OMControlLibrary.PortLinux
{
    public abstract class CommandBarControl
    { 

        public abstract bool Enabled { get; set; }
        public abstract string Caption { get;  set; }
        public abstract CommandBarButton Control { get; }

        //public abstract string TooltipText { get; set; }
        //public abstract bool BeginGroup { get; set; }

        public abstract void Delete(object p);

        public abstract CommandBarControls Controls { get; set; }

    }

    public abstract class CommandBarButton : CommandBarControl
    {
    
        //public abstract Image Picture { get; set; }
         
        public override CommandBarButton Control { get { return this; } }

        public abstract bool Visible { get; set; }
        //public abstract MsoButtonStyle Style { get; set; }
        //public abstract MsoButtonState State { get; set; }

        public event CommandBarButtonClick Click;
        public void OnClick(CommandBarButton Ctrl, ref bool CancelDefault)
        {
            Click?.Invoke(Ctrl, ref CancelDefault);
        }
    }
    public delegate void CommandBarButtonClick(CommandBarButton Ctrl, ref bool CancelDefault);
    public enum MsoControlType
    {
        msoControlButton,
        msoControlPopup
    }
    public enum MsoButtonStyle
    {
        msoButtonIcon
    }
    public enum MsoButtonState
    {
        msoButtonUp
    }
}
