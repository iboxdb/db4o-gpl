using System;
namespace OMControlLibrary.PortLinux
{
    public abstract class DTE2
    {
        public AddIns AddIns { get; } = new AddIns();


        public abstract Windows2 Windows { get; }
        public abstract Events Events { get; }
        public abstract StatusBar StatusBar { get; }
        public abstract CommandBars CommandBars { get; }
    }

    public abstract class CommandBars
    {
        public abstract CommandBar Add(string toolbarName, MsoBarPosition msoBarTop, object missing, bool v);
        public abstract CommandBar this[string name] { get; }

    }
    public abstract class StatusBar
    {
        public abstract string Text { get;  set; }

        public abstract void Clear();

        public abstract void Progress(bool v1, string v2, int v3, int v4);

        //public abstract void Animate(bool v, vsStatusAnimation vsStatusAnimationBuild);
        
    }
     
    public enum vsStatusAnimation
    {
        vsStatusAnimationBuild
    }
    public enum MsoBarPosition
    {
        msoBarTop
    }
}
