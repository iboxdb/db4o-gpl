using System.Diagnostics;

namespace OME.Logging.ExceptionLogging
{
    internal class LogEntry
    {
        public TraceEventType Severity { get; internal set; }
        public string Message { get; internal set; }
    }
}