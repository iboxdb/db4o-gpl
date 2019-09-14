using System.Collections.Generic;

namespace OME.Logging.ExceptionLogging
{
    internal class LogSource
    {
        private string v;

        public LogSource(string v)
        {
            this.v = v;
        }

        public LogSource(string v, System.Diagnostics.SourceLevels all)
        {
            this.v = v;
        }

        public string Name { get { return v; }  }
        public ICollection<object> Listeners { get; internal set; } = new List<object>();
    }
}