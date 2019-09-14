using System;
using System.Collections.Generic;

namespace OME.Logging.ExceptionLogging
{
    internal class LogWriter
    {
        private ILogFilter[] logFilter;
        private IDictionary<string, LogSource> traceSources;
        private LogSource emptyTraceSource1;
        private LogSource emptyTraceSource2;
        private LogSource errorsTraceSource;
        private string cONS_ERROR_CATEGORY;
        private bool v1;
        private bool v2;

        public LogWriter(ILogFilter[] logFilter, IDictionary<string, LogSource> traceSources, LogSource emptyTraceSource1, LogSource emptyTraceSource2, LogSource errorsTraceSource, string cONS_ERROR_CATEGORY, bool v1, bool v2)
        {
            this.logFilter = logFilter;
            this.traceSources = traceSources;
            this.emptyTraceSource1 = emptyTraceSource1;
            this.emptyTraceSource2 = emptyTraceSource2;
            this.errorsTraceSource = errorsTraceSource;
            this.cONS_ERROR_CATEGORY = cONS_ERROR_CATEGORY;
            this.v1 = v1;
            this.v2 = v2;
        }

        internal void Write(LogEntry ent)
        {
            //throw new NotImplementedException();
            Console.WriteLine(ent.Message);
        }
    }
}