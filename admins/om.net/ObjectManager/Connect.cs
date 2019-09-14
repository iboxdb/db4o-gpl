/* Copyright (C) 2004 - 2009  Versant Inc.  http://www.db4o.com */

using System;
using System.ComponentModel;
using System.Reflection;
using System.Windows.Forms;
using ObjectManager.PortImpl;
using OMAddinDataTransferLayer;
using OMControlLibrary;
using OMControlLibrary.Common;
using OMControlLibrary.PortLinux;
using OME.Logging.Common;
using OME.Logging.ExceptionLogging;
using OME.Logging.Tracing;
using Constants = OMControlLibrary.Common.Constants;

namespace OMAddin
{
    public class Connect
    {
        #region Private Variables

        private DTE2 _applicationObject;
        private AddIn _addInInstance;

        //-----------------------------------------------------//
        private CommandBarPopup oPopup;

        private CommandBarEvents dbConnectControlHandler;
        private CommandBarEvents omObjectBrowserControlHandler;

        private CommandBarEvents dbCreateDemoDbControlHandler;
        private CommandBarEvents omQueryBuilderControlHandler;
        private CommandBarEvents omPropertiesControlHandler;
        //--------------------------------------------------------//

        private CommandBarControl dbCreateDemoDbControl;
        private CommandBarControl connectDatabaseMenu;

        private CommandBarControl omObjectBrowserControl;
        private CommandBarControl omQueryBuilderControl;
        private CommandBarControl omPropertiesControl;
         


        private WindowEvents _windowsEvents;


        #endregion


        #region Private Constants

        private const string IMAGE_CONNECT = "OMAddin.Images.DBconnect.gif";
        private const string IMAGE_CONNECT_MASKED = "OMAddin.Images.DBconnect_Masked.bmp";

        private const string IMAGE_DISCONNECT = "OMAddin.Images.DB_DISCONNECT2_a.GIF";
        private const string IMAGE_DISCONNECT_MASKED = "OMAddin.Images.DB_DISCONNECT2_b.BMP";

        private const string IMAGE_XTREMECONNECT = "OMAddin.Images.XtremeConnct_2.gif";
        private const string IMAGE_XTREMECONNECT_MASKED = "OMAddin.Images.XtremeConnct_2_Masked.bmp";

        private const string IMAGE_SUPPORTCASES = "OMAddin.Images.SupportCases.gif";
        private const string IMAGE_SUPPORTCASES_MASKED = "OMAddin.Images.SupportCases_Masked.bmp";

        private const string IMAGE_HELP = "OMAddin.Images.support1.gif";
        private const string IMAGE_HELP_MASKED = "OMAddin.Images.support1_Masked.bmp";

        private const string COMMAND_NAME = "OMAddin.Connect.ObjectManager Enterprise";
        private const string DB4O_HOMEPAGE = "db4objects Homepage";
        private const string TOOLS = "Tools";
        private const string CONNECT = "Connect";
        private const string XTREME_CONNECT = "XtremeConnect";
        private const string SUPPORT_CASES = "Support Cases";
        private const string MAINTAINANCE = "Maintainance";
        private const string OPTIONS = "Options";
        private const string PROXYCONFIGURATIONS = "Proxy Configurations";
        private const string ASSEMBLY_SEARCH_PATH_CONFIG = "Assembly search path...";


        private const string BACKUP = "Backup";
        private const string DB4O_DEVELOPER_COMMUNITY = "db4objects Developer Community";
        private const string DB4O_DOWNLOADS = "db4objects Downloads";
        private const string DB4O_HELP = "Help";
        private const string ABOUT_OME = "About ObjectManager Enterprise";

        private const string CREATE_DEMO_DB = "Create Demo Database";

        private const string FAQ_PATH = @"FAQ/FAQ.htm";

        private const string URL_DB4O_DEVELOPER = "https://github.com/iboxdb/db4o-gpl/";
        private const string URL_DB4O_DOWNLOADS = "https://github.com/iboxdb/db4o-gpl/";
        private const string URL_DB4O_HOMEPAGE = "https://github.com/iboxdb/db4o-gpl/";

        #endregion




        #region Connect Constructor
        public Connect()
        {
            try
            {
                OMETrace.Initialize();
            }
            catch (Exception ex)
            {

                ex.ToString();
            }

            try
            {
                ExceptionHandler.UpdateExceptionLogginInfo();

                ExceptionHandler.Initialize();
            }
            catch (Exception ex)
            {
                ex.ToString();//ignore
            }

            try
            {
                ApplicationManager.CheckLocalAndSetLanguage();
            }
            catch (Exception ex)
            {
                ex.ToString();//ignore
            }
        }

        #endregion
        DTEEvents _eve;

        #region OnConnection
        /// <summary>Implements the OnConnection method of the IDTExtensibility2 interface. Receives notification that the Add-in is being                       loaded.</summary>
        /// <param term='application'>Root object of the host application.</param>
        /// <param term='connectMode'>Describes how the Add-in is being loaded.</param>
        /// <param term='addInInst'>Object representing this Add-in.</param>
        /// <seealso class='IDTExtensibility2' />
        public void OnConnection(DTE2 application, ext_ConnectMode connectMode, AddIn addInInst, ref Array custom)
        {
            _applicationObject = (DTE2)application;
            _addInInstance = (AddIn)addInInst;
            ViewBase.ResetToolWindowList();
            OutputWindow.Initialize(_applicationObject);

            try
            {
                CreateMenu();

                try
                {
                    Events events = _applicationObject.Events;
                    _windowsEvents = events.get_WindowEvents(null);
                    _windowsEvents.WindowActivated += OnWindowActivated;
                    _eve = _applicationObject.Events.DTEEvents;
                }
                catch (Exception oEx)
                {
                    LoggingHelper.HandleException(oEx);
                }


            }
            catch (Exception oEx)
            {
                LoggingHelper.HandleException(oEx);
            }

        }

        #endregion



        #region OnWindowActivated
        /// <summary>
        /// This event handler gets the Activated event of tool window.
        /// When db4o Browser opens for first time, it creates menu option under ObjectManager Enterprise menu.
        /// Using this menu option, user can get back to db4o Browser if he has closed it before.
        /// </summary>
        /// <param name="gotFocus"></param>
        /// <param name="lostFocus"></param>
        void OnWindowActivated(Window gotFocus, Window lostFocus)
        {
            try
            {
                if (oPopup != null)
                {
                    if (gotFocus.Caption.Equals(Constants.DB4OBROWSER) && omObjectBrowserControl == null)
                    {

                        omObjectBrowserControlHandler = AddControlToToolbar(ref omObjectBrowserControl, Constants.DB4O_BROWSER_CAPTION);
                        omObjectBrowserControlHandler.Click += omObjectBrowserControlHandler_Click;

                    }
                    else if (gotFocus.Caption.Equals(Constants.QUERYBUILDER) && omQueryBuilderControl == null)
                    {

                        omQueryBuilderControlHandler = AddControlToToolbar(ref omQueryBuilderControl, Constants.QUERY_BUILDER_CAPTION);
                        omQueryBuilderControlHandler.Click += omQueryBuilderControlHandler_Click;

                    }
                    else if (gotFocus.Caption.Equals(Constants.DB4OPROPERTIES) && omPropertiesControl == null)
                    {
                        omPropertiesControlHandler = AddControlToToolbar(ref omPropertiesControl, Constants.PROPERTIES_TAB_CAPTION);
                        omPropertiesControlHandler.Click += omPropertiesControlHandler_Click;

                    }
                }


            }
            catch (Exception oEx)
            {
                LoggingHelper.HandleException(oEx);
            }
        }

        private CommandBarEvents AddControlToToolbar(ref CommandBarControl ctrl, string caption)
        {
            ctrl = oPopup.Controls.Add(MsoControlType.msoControlButton,
                                                         Missing.Value,
                                                         Missing.Value,
                                                         11, true);

            ctrl.Caption = Helper.GetResourceString(caption);
            return (CommandBarEvents)_applicationObject.Events.get_CommandBarEvents(ctrl);
        }

        #endregion


        #region dbConnectControlHandler_Click
        /// <summary>
        /// This event handler opens the Login tool window.
        /// </summary>
        /// <param name="CommandBarControl"></param>
        /// <param name="Handled"></param>
        /// <param name="CancelDefault"></param>
        void dbConnectControlHandler_Click(object CommandBarControl, ref bool Handled, ref bool CancelDefault)
        {
            ((DTE2Impl)_applicationObject).DisableMenu();
            Cursor.Current = Cursors.WaitCursor;
            ConnectToDatabaseOrServer((CommandBarButton)CommandBarControl);
            Cursor.Current = Cursors.Default;

        }
        #endregion

        #region omObjectBrowserControlHandler_Click
        /// <summary>
        /// This event handler reopens db4o tool window. 
        /// </summary>
        /// <param name="CommandBarControl"></param>
        /// <param name="Handled"></param>
        /// <param name="CancelDefault"></param>
        static void omObjectBrowserControlHandler_Click(object CommandBarControl, ref bool Handled, ref bool CancelDefault)
        {
            try
            {
                Cursor.Current = Cursors.WaitCursor;
                ObjectBrowserToolWin.CreateObjectBrowserToolWindow();
                Cursor.Current = Cursors.Default;
            }
            catch (Exception oEx)
            {
                LoggingHelper.HandleException(oEx);
            }
        }

        static void omQueryBuilderControlHandler_Click(object CommandBarControl, ref bool Handled, ref bool CancelDefault)
        {
            try
            {
                Cursor.Current = Cursors.WaitCursor;
                Login.CreateQueryBuilderToolWindow();
                Cursor.Current = Cursors.Default;
            }
            catch (Exception oEx)
            {
                LoggingHelper.HandleException(oEx);
            }
        }

        static void omPropertiesControlHandler_Click(object CommandBarControl, ref bool Handled, ref bool CancelDefault)
        {
            try
            {
                Cursor.Current = Cursors.WaitCursor;
                PropertyPaneToolWin.CreatePropertiesPaneToolWindow(true);
                Cursor.Current = Cursors.Default;
            }
            catch (Exception oEx)
            {
                LoggingHelper.HandleException(oEx);
            }
        }

        #endregion

        #region AddSubMenu
        private int AddSubMenu(out CommandBarControl menuItem, CommandBarPopup parent, out CommandBarEvents eventHandler, int position, string caption)
        {
            return AddSubMenu(out menuItem, parent, out eventHandler, position, caption, string.Empty, string.Empty);
        }

        private int AddSubMenu(out CommandBarControl menuItem, CommandBarPopup parent, out CommandBarEvents eventHandler, int position, string caption, string imagePath, string maskedImagePath)
        {
            menuItem = parent.Controls.Add(MsoControlType.msoControlButton, Missing.Value, Missing.Value, position, true);
            menuItem.Caption = caption;

            eventHandler = (CommandBarEvents)_applicationObject.Events.get_CommandBarEvents(menuItem);

            return position + 1;
        }
        #endregion

        /// <summary>
        /// Creates Menu & Submenus under Tools menu.
        /// </summary>
        private void CreateMenu()
        {
            try
            {
                oPopup = _applicationObject.CommandBars[TOOLS];
                #region Creates submenu for Connect/Disconnect
                int position = AddSubMenu(out connectDatabaseMenu, oPopup, out dbConnectControlHandler, 1, CONNECT, IMAGE_CONNECT, IMAGE_CONNECT_MASKED);
                dbConnectControlHandler.Click += dbConnectControlHandler_Click;
                #endregion



                #region Creates submenu for Creating demo db
                position = AddSubMenu(out dbCreateDemoDbControl, oPopup, out dbCreateDemoDbControlHandler, position, CREATE_DEMO_DB);
                dbCreateDemoDbControlHandler.Click += dbCreateDemoDbControlHandler_Click;
                dbCreateDemoDbControl.Enabled = true;
                #endregion



            }
            catch (Exception oEx)
            {
                LoggingHelper.HandleException(oEx);
            }
        }




        #region Creating demo db handler click
        void dbCreateDemoDbControlHandler_Click(object CommandBarControl, ref bool Handled, ref bool CancelDefault)
        {
            try
            {
                ((DTE2Impl)_applicationObject).DisableMenu();
                ViewBase.ResetToolWindowList();

                Cursor.Current = Cursors.WaitCursor;

                if (Login.appDomain == null || Login.appDomain.workerAppDomain == null)
                {

                    Login l = new Login();
                    l.CreateAppDomain();

                }
                CreateDemoDbMethod();

                Cursor.Current = Cursors.Default;
            }
            catch (Exception e)
            {
                LoggingHelper.ShowMessage(e);
            }
        }
        #endregion

        private void CreateDemoDbMethod()
        {
            try
            {
                bw = new BackgroundWorker();
                bw.WorkerSupportsCancellation = true;
                bw.WorkerReportsProgress = true;

                bw.ProgressChanged += bw_ProgressChanged;
                bw.DoWork += bw_DoWork;
                bw.RunWorkerCompleted += bw_RunWorkerCompleted;

                isrunning = true;
                bw.RunWorkerAsync();

                for (double i = 1; i < 10000; i++)
                {
                    i++;
                    bw.ReportProgress((int)i * 100 / 1000);

                    if (isrunning == false)
                        break;
                }

            }
            catch (Exception oEx)
            {
                bw.CancelAsync();
                bw = null;
                LoggingHelper.HandleException(oEx);
            }
        }

        #region Function to create demo db

        void bw_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            OpenDemoDb();
            isrunning = false;
            _applicationObject.StatusBar.Clear();
            _applicationObject.StatusBar.Progress(false, "Creation successful", 0, 0);

            _applicationObject.StatusBar.Text = "Creation successful!";
        }

        void createdemo()
        {
            try
            {
                ViewBase.ApplicationObject = _applicationObject;
                DemoDb.Create();

            }
            catch (Exception e1)
            {
                ViewBase.ApplicationObject.StatusBar.Clear();
                LoggingHelper.HandleException(e1);
            }
        }
        #endregion


        void bw_DoWork(object sender, DoWorkEventArgs e)
        {
            try
            {
                createdemo();
            }
            catch (Exception oEx)
            {
                bw.CancelAsync();
                bw = null;
                LoggingHelper.HandleException(oEx);
            }

        }
        bool isrunning = true;
        BackgroundWorker bw;



        void bw_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            try
            {
                _applicationObject.StatusBar.Progress(true, "Creating Demo database.... ", e.ProgressPercentage * 10, 10000);

            }
            catch (Exception oEx)
            {
                LoggingHelper.HandleException(oEx);
            }
        }

        private void OpenDemoDb()
        {
            try
            {
                Assembly ThisAssembly = Assembly.GetExecutingAssembly();

                ObjectBrowserToolWin.CreateObjectBrowserToolWindow();
                ObjectBrowserToolWin.ObjBrowserWindow.Visible = true;
                Login.CreateQueryBuilderToolWindow();
                PropertyPaneToolWin.CreatePropertiesPaneToolWindow(true);
                PropertyPaneToolWin.PropWindow.Visible = true;
                dbCreateDemoDbControl.Enabled = false;
                connectDatabaseMenu.Caption = OMControlLibrary.Common.Constants.TOOLBAR_DISCONNECT;

            }
            catch (Exception oEx)
            {
                LoggingHelper.HandleException(oEx);
            }

        }
         


        #region ConnectToDatabaseOrServer
        private void ConnectToDatabaseOrServer(CommandBarButton Ctrl)
        {
            try
            {
                Assembly assembly = Assembly.GetExecutingAssembly();
                if (Ctrl.Caption.Equals(CONNECT))
                {
                    ViewBase.ApplicationObject = _applicationObject;
                    Login.CreateLoginToolWindow(assembly);
                }
                else
                {

                    Helper.SaveDataIfRequired();


                    dbCreateDemoDbControl.Enabled = true;
                    AppDomain.Unload(Login.appDomain.workerAppDomain);
                    Login.appDomain.workerAppDomain = null;
                    AssemblyInspectorObject.ClearAll();

                }
            }
            catch (Exception oEx)
            {
                LoggingHelper.HandleException(oEx);
            }
        }




        #endregion



        #region BackupDatabase
        private void BackupDatabase()
        {
            try
            {
                Backup backUp = new Backup();
                backUp.BackUpDataBase();
            }
            catch (Exception oEx)
            {
                LoggingHelper.ShowMessage(oEx);
            }
        }
        #endregion






    }


}