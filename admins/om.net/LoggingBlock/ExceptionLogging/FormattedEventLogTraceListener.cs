using System.Diagnostics;

namespace OME.Logging.ExceptionLogging
{
    internal class FormattedEventLogTraceListener
    {
        private EventLog eventLog;
        private TextFormatter formatter;

        public FormattedEventLogTraceListener(EventLog eventLog, TextFormatter formatter)
        {
            this.eventLog = eventLog;
            this.formatter = formatter;
        }
    }
}