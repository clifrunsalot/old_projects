#!/usr/bin/env python

#
# COMPANY NAME: Raytheon Company
# COPYRIGHT: Copyright (c) 2008 Raytheon Company
# UNPUBLISHED WORK
# ALL RIGHTS RESERVED
# PROJECT NAME: FCS BCME
# CONTRACT NUMBER: 3EC1721
#
#-------------------------------------------------------------------------------
# Filename: mtc_gui.py
#
# author  cbhuds  BCME-####
#
# version BCME-#### 2008-###-## Initial version.
#-------------------------------------------------------------------------------

import os, sys, time, thread
sys.path.append('..')
sys.path.append('../tabulator')

# Need this because of bug in python 2.3.4 when using askyesno
# widget; fixed in python 2.4
import Tkinter
Tkinter.wantobjects = 0

# Core libraries
from Tkinter import *
from tkMessageBox import *
from tkFileDialog import *
from xml.dom import minidom

# App libraries
from testcase import Testcase
from testcases_config import Config
from xmlparser import Parser
from utilities import Util
from writer import Writer
from logger import Logger
from test_widgets import ScrolledCanvas
from test_widgets import GlobalAttribute
from test_widgets import CheckbuttonAttribute
from test_widgets import EntryAttribute
from test_attribute import Attributes
from test_listbox import TcidListbox


class MasterTestControllerGui:
  """
  Encapsulates the GUI portion containing the individual testcase.
  This application is used to build and run a testcase or a battery
  of testcases.
  parent - Root parent.
  """
  def __init__(self, parent=None, logFile=None):
    """
    Constructor
    """
    self.panel      = parent
    self.listbox    = None
    self.hashObject = {}
    self.tcHash     = {}
    self.filename   = None
    self.listboxMgr = None
    self.modified   = False
    self.logger     = Logger(logFile)
    self.attrList   = Attributes(logger=self.logger)
    self.gAttrList  = {}
    self.makePanels(parent)
    self.panel.bind_all('<Control-q>', sys.exit)
    self.hashObject['hash'] = self.tcHash
    self.hashObject['modified'] = self.modified

  def applyGlobal(self):
    """
    Applies settings in global panel to all testcases.
    """
    lHash = {1:'Test Master Host',
              2:'Script Execution Host',
              3:'Start Testcase',
              4:'Setup Soscoe',
              5:'Setup Service',
              6:'Setup DB',
              7:'Stop Testcase',
              8:'Start Services',
              9:'Services To Start',
              10:'Stop Services',
              11:'Services To Stop',
              12:'Extract Results',
              13:'Archive',
              14:'Platform ID',
              15:'Port'}

    if self.isValid():
      msg =  'These changes will apply to all testcases\n'
      msg += 'and shall be saved to the current file.\n\n'
      msg += 'Do you want to continue?'
      choice = askyesno('Save?',msg)
      if choice:
        for id in self.tcHash:
          tc = self.tcHash[id]
          if self.gAttrList[lHash[1]].getValue():
            tc.setTestMasterHost(self.gAttrList[lHash[1]].getSubWidget().getValue())
          if self.gAttrList[lHash[2]].getValue():
            tc.setScriptExecutionHost(self.gAttrList[lHash[2]].getSubWidget().getValue())
          if self.gAttrList[lHash[3]].getValue():
            tc.setStartTest(Util().translateDigitToStr(self.gAttrList[lHash[3]].getSubWidget().getValue()))
          for h in tc.getHosts():
            if self.gAttrList[lHash[4]].getValue():
              h.setSetupSoscoe(Util().translateDigitToStr(self.gAttrList[lHash[4]].getSubWidget().getValue()))
            if self.gAttrList[lHash[5]].getValue():
              h.setSetupService(Util().translateDigitToStr(self.gAttrList[lHash[5]].getSubWidget().getValue()))
            if self.gAttrList[lHash[6]].getValue():
              h.setSetupDb(Util().translateDigitToStr(self.gAttrList[lHash[6]].getSubWidget().getValue()))
            if self.gAttrList[lHash[7]].getValue():
              h.setStopTc(Util().translateDigitToStr(self.gAttrList[lHash[7]].getSubWidget().getValue()))
            if self.gAttrList[lHash[8]].getValue():
              h.setStartServices(Util().translateDigitToStr(self.gAttrList[lHash[8]].getSubWidget().getValue()))
            if self.gAttrList[lHash[9]].getValue():
              h.setServicesToStart(self.gAttrList[lHash[9]].getSubWidget().getValue())
            if self.gAttrList[lHash[10]].getValue():
              h.setStopServices(Util().translateDigitToStr(self.gAttrList[lHash[10]].getSubWidget().getValue()))
            if self.gAttrList[lHash[11]].getValue():
              h.setServicesToStop(self.gAttrList[lHash[11]].getSubWidget().getValue())
            if self.gAttrList[lHash[12]].getValue():
              h.setExtractResults(Util().translateDigitToStr(self.gAttrList[lHash[12]].getSubWidget().getValue()))
            if self.gAttrList[lHash[13]].getValue():
              h.setArchive(Util().translateDigitToStr(self.gAttrList[lHash[13]].getSubWidget().getValue()))
            if self.gAttrList[lHash[14]].getValue():
              h.setPlatformId(self.gAttrList[lHash[14]].getSubWidget().getValue())
            if self.gAttrList[lHash[15]].getValue():
              h.setPort(self.gAttrList[lHash[15]].getSubWidget().getValue())

        # Do the same for the currently displayed testcase
        id = self.attrList.getCurrentId()
        hostIndex = self.attrList.getHostIndex()
        self.attrList.populate(self.tcHash[id], hostIndex)

      self.saveFile()
    else:
      showerror('Error','Invalid TCID')

  def bindWidget(self, widget, hash):
    """
    Binds widget to certain key/mouse events.
    widget - Widget object to bind.
    hash   - List of keybd/mouse tags to bind.
    """
    for keyName in hash:
      widget.bind(keyName, hash[keyName])

  def cancel(self):
    """
    Returns currently selected testcase to values after the last saved
    session.
    """
    if self.isValid():
      id = self.attrList.getCurrentId()
      hostIndex = self.attrList.getHostIndex()
      self.attrList.populate(self.tcHash[id], hostIndex)
      self.setModified(modified=False)
#      self.modified = False
      self.panel.title("Master Test Controller - " + str(self.filename))
    else:
      showerror('Error','Invalid TCID')

  def create(self, newId, copyFromId=None):
    """
    Creates a new testcase.
    newId - TCID of new testcase.
    copyFromId - If present, create copy and rename newId.
    """
    newTc = Config().createTc()

    if copyFromId is not None:
      copyFromTc = self.tcHash[copyFromId]
      newTc.setId(newId)
      newTc.setTestMasterHost(copyFromTc.getTestMasterHost())
      newTc.setScriptExecutionHost(copyFromTc.getScriptExecutionHost())
      newTc.setStartTest(copyFromTc.getStartTest())
      newTc.setHosts(copyFromTc.getHosts())
      self.tcHash[newId] = newTc
    else:
      newTc.setId(newId)
      self.tcHash[newId] = newTc

    self.loadTestcases()
    self.attrList.populate(self.tcHash[newId], 0)
    self.saveFile()

  def createTc(self, newId):
    """
    Asks user to confirm the creation of a new testcase.
    newId - TCID of new testcase.
    """
    choice = False
    if self.tcHash.has_key(newId):
      choice = askyesno('Save?', 'Do you want to overwrite an existing testcase?')
      if choice:
        id = self.attrList.getCurrentId()
        self.create(newId, id)
      else:
        self.cancel()
    else:
      self.create(newId)

  def createWidget(self, attr, x, y, canvas):
    """
    Builds widgets on a canvas.
    attr   - Widget object.
    x      - Row
    y      - Column.
    canvas - Panel on which to place the attr.
    """
    self.attrList.append(attr.getTitle(), attr)
    canvas.create_window(x, y, window=attr.getWidget(), anchor=W)

  def isValid(self):
    """
    Returns True if the tcid is exist in the most recently saved
    version of the self.tcHash.
    """
    valid = False
    if self.attrList:
      id = self.attrList.getCurrentId()
      if id:
        valid = self.tcHash.has_key(id)
    return valid

  def launch(self):
    """
    Begins automated test of all testcases with Start Testcase tags
    set to True.
    """
    if self.isValid():
      timeStart = Util().getTime()
      cmd = 'python master_controller.py -t ' + self.filename
      thread.start_new_thread(self.launchCmd, (cmd,))
      thread.start_new_thread(self.monitorStop, ('stop', timeStart))
    else:
      showerror('Error','Invalid TCID')

  def launchCmd(self, cmd):
    """
    Spawns thread for test execution so that the GUI remains
    responsive.
    """
    try:
      os.system(cmd)
    except StandardError, errStr:
      self.logger.logError("Unable to perform cmd: " + cmd)

  def loadTestcases(self):
    """
    Loads testcases.
    """
    if self.listboxMgr is None:
      self.listboxMgr = TcidListbox(listbox=self.listbox,
                     hashObj=self.hashObject, attrObj=self.attrList,
                     logger=self.logger)
      self.listboxMgr.loadListbox()
    else:
      self.listboxMgr.setHash(self.hashObject)
      self.listboxMgr.loadListbox()
 
  def loadConfigFile(self, file):
    """
    Loads file that contains all of the testcases. This file must conform
    to the format and structure expected in testcases_config.py.
    file - Name of file.
    """
    parser = Parser(file)
    battery = parser.loadFile()
    config = Config()
    tcList = config.populateBattery(battery)
    self.logger.logInfo ("Loading: " + str(len(tcList)) + ' Testcases')

    # Populate local hash for easier referencing
    for t in tcList:
      self.tcHash[t.getId()] = t

    self.listboxMgr = TcidListbox(listbox=self.listbox,
                     hashObj=self.hashObject, attrObj=self.attrList,
                     logger=self.logger)
    self.listboxMgr.loadListbox()
    self.listboxMgr.activate()
    self.filename = file
    self.panel.title("Master Test Controller - " + str(self.filename))
 
  def makeBottomPanel(self, parent):
    """
    Creates bottom panel.
    parent - Parent panel.
    """
    bpanel = Frame(parent)
    newTcBtn = Button(bpanel, text='New Testcase', command=self.showCreateTc, underline=0)
    deleteTcBtn = Button(bpanel, text='Delete Testcase', command=self.showAskDeleteTc, underline=0)
    launchBtn = Button(bpanel, text='Launch', command=self.launch)
    saveBtn = Button(bpanel, text='Save', command=self.saveFile, underline=0)
    cancelBtn = Button(bpanel, text='Cancel', command=self.cancel, underline=0)
    newTcBtn.grid(row=0, column=0, sticky=EW, padx=5, pady=10)
    deleteTcBtn.grid(row=0, column=1, sticky=EW, padx=5, pady=10)
    launchBtn.grid(row=0, column=2, sticky=EW, padx=5, pady=10)
    saveBtn.grid(row=0, column=3, sticky=EW, padx=5, pady=10)
    cancelBtn.grid(row=0, column=4, sticky=EW, padx=5, pady=10)
    bpanel.pack()

    newTcBtn.bind_all('<Control-n>',self.showCreateTc)
    deleteTcBtn.bind_all('<Control-d>',self.showAskDeleteTc)
    saveBtn.bind_all('<Control-s>',self.saveFileInt)
    cancelBtn.bind('<Control-c>',self.cancel)

  def makeGlobalPanel(self, parent):
    """
    Creates panel for global settings.
    parent - Parent panel.
    """
    panel = Frame(parent)
    len = 20 
    panel.pack()
    gpanel = Frame(panel)
    gpanel.pack()

    lbl = Label(gpanel, text="Global Settings")
    lbl.grid(row=0, column=0, columnspan=2)

    c = EntryAttribute(gpanel, 'Test Master Host', len, 1, 1, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 1, 0, 5, 5, logger=self.logger)
    self.gAttrList['Test Master Host'] = g

    c = EntryAttribute(gpanel, 'Script Execution Host', len, 2, 1, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 2, 0, 5, 5, logger=self.logger)
    self.gAttrList['Script Execution Host'] = g

    c = CheckbuttonAttribute(gpanel, 'Start Testcase', 3, 1, W, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 3, 0, 5, 5, logger=self.logger)
    self.gAttrList['Start Testcase'] = g

    c = CheckbuttonAttribute(gpanel, 'Setup Soscoe', 4, 1, W, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 4, 0, 5, 5, logger=self.logger)
    self.gAttrList['Setup Soscoe'] = g

    c = CheckbuttonAttribute(gpanel, 'Setup Service', 5, 1, W, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 5, 0, 5, 5, logger=self.logger)
    self.gAttrList['Setup Service'] = g

    c = CheckbuttonAttribute(gpanel, 'Setup DB', 6, 1, W, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 6, 0, 5, 5, logger=self.logger)
    self.gAttrList['Setup DB'] = g

    c = CheckbuttonAttribute(gpanel, 'Stop Testcase', 7, 1, W, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 7, 0, 5, 5, logger=self.logger)
    self.gAttrList['Stop Testcase'] = g

    c = CheckbuttonAttribute(gpanel, 'Start Services', 8, 1, W, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 8, 0, 5, 5, logger=self.logger)
    self.gAttrList['Start Services'] = g

    c = EntryAttribute(gpanel, 'Services To Start', len, 9, 1, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 9, 0, 5, 5, logger=self.logger)
    self.gAttrList['Services To Start'] = g

    c = CheckbuttonAttribute(gpanel, 'Stop Services', 10, 1, W, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 10, 0, 5, 5, logger=self.logger)
    self.gAttrList['Stop Services'] = g

    c = EntryAttribute(gpanel, 'Services To Stop', len, 11, 1, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 11, 0, 5, 5, logger=self.logger)
    self.gAttrList['Services To Stop'] = g

    c = CheckbuttonAttribute(gpanel, 'Extract Results', 12, 1, W, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 12, 0, 5, 5, logger=self.logger)
    self.gAttrList['Extract Results'] = g

    c = CheckbuttonAttribute(gpanel, 'Archive', 13, 1, W, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 13, 0, 5, 5, logger=self.logger)
    self.gAttrList['Archive'] = g

    c = EntryAttribute(gpanel, 'Platform ID', len, 14, 1, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 14, 0, 5, 5, logger=self.logger)
    self.gAttrList['Platform ID'] = g

    c = EntryAttribute(gpanel, 'Port', len, 15, 1, 5, 5, logger=self.logger)
    g = GlobalAttribute(gpanel, c, 15, 0, 5, 5, logger=self.logger)
    self.gAttrList['Port'] = g

    globalSetBtn = Button(gpanel, text='Apply Global Set', command=self.applyGlobal)
    globalSetBtn.grid(row=16, column=0, columnspan=2)

  def makeMenu(self, parent):
    """
    Creates menu bar.
    parent - Parent panel.
    """
    top = Menu(parent, relief=FLAT, borderwidth=1)
    parent.config(menu=top)

    def newMsg():
      """
      Display short msg about creating TCs.
      """
      msg =  'To create a new file or new TC, \n'
      msg += 'press the [New Testcase] button, \n'
      msg += 'create a testcase, and then\n'
      msg += 'save the file.'
      showinfo('Information',msg)

    file = Menu(top)
    file.add_command(label='New...', command=newMsg)
    file.add_command(label='Save...', command=self.saveFile)
    file.add_command(label='Save As...', command=self.showAskSaveAsFile)
    file.add_command(label='Open...', command=self.showAskOpenFile)
    file.add_command(label='Quit...', command=(lambda: sys.exit()))
    top.add_cascade(label='File', menu=file, underline=0)

    top.bind_all('<Alt-f>',top.activate(0))

  def makeLeftPanel(self, parent):
    """
    Creates left panel.
    parent - Parent panel.
    """
    lpanel = Frame(parent)
    sbar = Scrollbar(lpanel)
    lb = Listbox(lpanel, relief=SUNKEN, height=35, selectmode=BROWSE)
    sbar.config(command=lb.yview)
    lb.config(yscrollcommand=sbar.set, width=10)
    sbar.pack(side=RIGHT, fill=Y)
    lb.pack(side=LEFT, expand=YES, fill=BOTH)
    lpanel.pack(padx=5, ipady=2)
    self.listbox = lb
            
  def makeMiddlePanel(self, parent):
    """
    Creates widgets for application.
    parent - Parent panel.
    """
    x = 15
    y = 25
    yIncr = 30
    len = 20 
    mpanel = Frame(parent)
    mpanel.grid(row=0, column=1)
    dpanel = Frame(mpanel)
    dpanel.pack(side=TOP, anchor=CENTER)
    scanvas = ScrolledCanvas(dpanel, logger=self.logger)

    e = EntryAttribute(scanvas.getCanvas(), 'TCID', len, 0, 0, logger=self.logger)
    self.bindWidget(e.getEntry(), {'<KeyPress>': self.setModifiedChar,
                                   '<BackSpace>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = EntryAttribute(scanvas.getCanvas(), 'Test Master Host', len, 1, 0, logger=self.logger)
    self.bindWidget(e.getEntry(), {'<KeyPress>':self.setModifiedChar,
                                   '<BackSpace>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = EntryAttribute(scanvas.getCanvas(), 'Script Execution Host', len, 2, 0, logger=self.logger)
    self.bindWidget(e.getEntry(), {'<KeyPress>':self.setModifiedChar,
                                   '<BackSpace>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = CheckbuttonAttribute(scanvas.getCanvas(), 'Start Testcase', 3, 0, W, logger=self.logger)
    self.bindWidget(e.getWidget(), {'<space>':self.setModified,
                                    '<Button-1>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    btn = Button(scanvas.getCanvas(), text='Host 0', command=self.populateHost0, width=20)
    self.attrList.append('Host 0', btn)
    scanvas.getCanvas().create_window(x, y, window=btn, anchor=W)
    
    y += yIncr
    btn = Button(scanvas.getCanvas(), text='Host 1', command=self.populateHost1, width=20)
    self.attrList.append('Host 1', btn)
    scanvas.getCanvas().create_window(x, y, window=btn, anchor=W)
    
    y += yIncr
    btn = Button(scanvas.getCanvas(), text='Host 2', command=self.populateHost2, width=20)
    self.attrList.append('Host 2', btn)
    scanvas.getCanvas().create_window(x, y, window=btn, anchor=W)
    
    y += yIncr
    btn = Button(scanvas.getCanvas(), text='Host 3', command=self.populateHost3, widt=20)
    self.attrList.append('Host 3', btn)
    scanvas.getCanvas().create_window(x, y, window=btn, anchor=W)
    
    y += yIncr
    btn = Button(scanvas.getCanvas(), text='Host 4', command=self.populateHost4, width=20)
    self.attrList.append('Host 4', btn)
    scanvas.getCanvas().create_window(x, y, window=btn, anchor=W)
    
  def makePanels(self, parent):
    """
    Builds application back panel.
    parent - Parent panel.
    """
    # menu bar
    self.makeMenu(parent)
    toppanel = Frame(parent, relief=SOLID, borderwidth=1)
    lowerpanel = Frame(parent, relief=SOLID, borderwidth=1)
    # left panel
    lpanel = Frame(toppanel, relief=FLAT, borderwidth=1)
    self.makeLeftPanel(lpanel)
    lpanel.grid(row=0, column=0, sticky=N)
    # middle panel
    mpanel = Frame(toppanel, relief=FLAT, borderwidth=1)
    self.makeMiddlePanel(mpanel)
    mpanel.grid(row=0, column=1, sticky=N)
    # right panel
    rpanel = Frame(toppanel, relief=FLAT, borderwidth=1)
    self.makeRightPanel(rpanel)
    rpanel.grid(row=0, column=2, sticky=N)
    # global panel
    gpanel = Frame(toppanel, relief=FLAT, borderwidth=1)
    self.makeGlobalPanel(gpanel)
    gpanel.grid(row=0, column=3, sticky=N)
    # bottom panel
    bpanel = Frame(lowerpanel, relief=FLAT, borderwidth=1)
    self.makeBottomPanel(bpanel)
    bpanel.grid(row=1, column=0, columnspan=3, sticky=S)
    toppanel.pack(pady=5, padx=5, expand=YES, fill=BOTH)
    lowerpanel.pack(pady=5, padx=5, expand=YES, fill=BOTH)

  def makeRightPanel(self, parent):
    """
    Creates right panel.
    parent - Parent panel.
    """
    x = 15
    y = 25
    yIncr = 30
    len = 20 
    cpanel = Frame(parent)
    cpanel.pack()
    dpanel = Frame(cpanel)
    dpanel.pack(side=TOP, anchor=CENTER)
    scanvas = ScrolledCanvas(dpanel, logger=self.logger)

    e = EntryAttribute(scanvas.getCanvas(), 'Hostname', len, 1, 0, logger=self.logger)
    self.bindWidget(e.getEntry(), {'<KeyPress>':self.setModifiedChar,
                                   '<BackSpace>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = CheckbuttonAttribute(scanvas.getCanvas(), 'Setup Soscoe', 2, 0, W, logger=self.logger)
    self.bindWidget(e.getWidget(), {'<space>':self.setModified,
                                    '<Button-1>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = CheckbuttonAttribute(scanvas.getCanvas(), 'Setup Service', 3, 0, W, logger=self.logger)
    self.bindWidget(e.getWidget(), {'<space>':self.setModified,
                                    '<Button-1>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = CheckbuttonAttribute(scanvas.getCanvas(), 'Setup DB', 4, 0, W, logger=self.logger)
    self.bindWidget(e.getWidget(), {'<space>':self.setModified,
                                    '<Button-1>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = CheckbuttonAttribute(scanvas.getCanvas(), 'Stop Testcase', 5, 0, W, logger=self.logger)
    self.bindWidget(e.getWidget(), {'<space>':self.setModified,
                                    '<Button-1>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = CheckbuttonAttribute(scanvas.getCanvas(), 'Start Services', 6, 0, W, logger=self.logger)
    self.bindWidget(e.getWidget(), {'<space>':self.setModified,
                                    '<Button-1>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = EntryAttribute(scanvas.getCanvas(), 'Services To Start', len, 7, 0, logger=self.logger)
    self.bindWidget(e.getEntry(), {'<KeyPress>':self.setModifiedChar,
                                   '<BackSpace>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = CheckbuttonAttribute(scanvas.getCanvas(), 'Stop Services', 8, 0, W, logger=self.logger)
    self.bindWidget(e.getWidget(), {'<space>':self.setModified,
                                    '<Button-1>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = EntryAttribute(scanvas.getCanvas(), 'Services To Stop', len, 9, 0, logger=self.logger)
    self.bindWidget(e.getEntry(), {'<KeyPress>':self.setModifiedChar,
                                   '<BackSpace>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = CheckbuttonAttribute(scanvas.getCanvas(), 'Extract Results', 10, 0, W, logger=self.logger)
    self.bindWidget(e.getWidget(), {'<space>':self.setModified,
                                    '<Button-1>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = CheckbuttonAttribute(scanvas.getCanvas(), 'Archive', 11, 0, W, logger=self.logger)
    self.bindWidget(e.getWidget(), {'<space>':self.setModified,
                                    '<Button-1>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = EntryAttribute(scanvas.getCanvas(), 'Platform ID', len, 12, 0, logger=self.logger)
    self.bindWidget(e.getEntry(), {'<KeyPress>':self.setModifiedChar,
                                   '<BackSpace>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

    y += yIncr
    e = EntryAttribute(scanvas.getCanvas(), 'Port', len, 13, 0, logger=self.logger)
    self.bindWidget(e.getEntry(), {'<KeyPress>':self.setModifiedChar,
                                   '<BackSpace>':self.setModified})
    self.createWidget(e, x, y, scanvas.getCanvas())

  def monitorStop(self, phrase, timeStart):
    """
    This creates a thread to monitor the progress of the autotest. Essentially
    at some point in time, a file named 'phrase.txt' will be created
    in the /tmp dir. This monitor will watch for that and display a notice to the
    user.
    phrase  - A string to name the file.
    """
    done = 0
    while not done:
      DIR = '/tmp/'
      line = phrase + '.txt'
      if line in os.listdir(DIR):
        done = 1 
      time.sleep(2)

    timeStop = Util().getTime()
    msg = 'The automated test started at\n'
    msg += str(timeStart) + '\n'
    msg += 'and completed at\n'
    msg += str(timeStop)
    showinfo('Test Complete!',msg)

  def notDone(self):
    """
    Displays notice for unfinished feature.
    """
    showerror('Not implemented', 'Not yet available')

  def populateHost0(self):
    """
    Populates the testcase attributes of the selected host.
    """
    if self.isValid():
      id = self.attrList.getCurrentId()
      if self.modified:
        self.showAskSaveFile()
      self.attrList.populate(self.tcHash[id], 0)
    else:
      showerror('Error','Invalid TCID')

  def populateHost1(self):
    """
    Populates the testcase attributes of the selected host.
    """
    if self.isValid():
      id = self.attrList.getCurrentId()
      if self.modified:
        self.showAskSaveFile()
      self.attrList.populate(self.tcHash[id], 1)
    else:
      showinfo('Error','Invalid TCID')

  def populateHost2(self):
    """
    Populates the testcase attributes of the selected host.
    """
    if self.isValid():
      id = self.attrList.getCurrentId()
      if self.modified:
        self.showAskSaveFile()
      self.attrList.populate(self.tcHash[id], 2)
    else:
      showinfo('Error','Invalid TCID')

  def populateHost3(self):
    """
    Populates the testcase attributes of the selected host.
    """
    if self.isValid():
      id = self.attrList.getCurrentId()
      if self.modified:
        self.showAskSaveFile()
      self.attrList.populate(self.tcHash[id], 3)
    else:
      showinfo('Error','Invalid TCID')

  def populateHost4(self):
    """
    Populates the testcase attributes of the selected host.
    """
    if self.isValid():
      id = self.attrList.getCurrentId()
      if self.modified:
        self.showAskSaveFile()
      self.attrList.populate(self.tcHash[id], 4)
    else:
      showinfo('Error','Invalid TCID')

  def saveFileInt(self, event):
    """
    Acts as an intermediate call before invoking the saveFile method.
    This is necessary to throw away the event.
    """
    self.saveFile()

  def saveFile(self, name=None):
    """
    Saves the current version of the testcases.
    name - Output file.
    """
    if self.isValid():
      if name is None:
        if self.filename is None:
          self.showAskSaveAsFile()
        else:
          self.saveFile(self.filename)
      else:
        self.filename = name
        id = self.attrList.getCurrentId()
        hostIndex = self.attrList.getHostIndex()
        self.tcHash[id] = self.attrList.save()
        self.attrList.populate(self.tcHash[id], hostIndex)
        w = Writer(self.tcHash, logger=self.logger)
        file = open(self.filename, 'w')
        file.write(w.write())
        file.close()
        self.setModified(modified=False)
#        self.modified = False
        self.panel.title("Master Test Controller - " + str(self.filename))
        self.listboxMgr.activate()
    else:
      showinfo('Error','Invalid TCID')

  def setModifiedChar(self, event=None):
    """
    Executes behavior on a set of keys, not all of them.
    event - Event triggered.
    """
    chars = '1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM-_.,'
    if chars.find(event.char) > -1 and event.char != '':
      self.setModified(evt=event)

  def setModified(self, modified=True, evt=None):
    """
    Sets the modified flag to True.
    event - Event triggered.
    """
    if evt is not None:
      evt.widget.focus()
    self.modified = modified 
    self.panel.title("Test Master Controller - " + str(self.filename) + " * Modified *")
    self.hashObject['modified'] = self.modified
    self.listboxMgr.setHash(self.hashObject)


  def showAskDeleteTc(self, event):
    """
    Asks user to confirm or deny deletion.
    """
    if self.isValid():
      if len(self.tcHash) > 1:
        choice = askyesno('Delete?',
                  'Do you want to permanently delete this testcase?')
        if choice:
          id = self.attrList.getCurrentId()
          del self.tcHash[id]
          # retrieve first tcid and populate fields
          tmpList = self.tcHash.keys()
          self.logger.logList(tmpList)
          tmpList.sort()
          id = tmpList[0]
          self.attrList.populate(self.tcHash[id], 0)
          self.loadTestcases()
          self.saveFile()
      else:
        msg  = 'You cannot delete the last testcase.\n'
        msg += 'Simply edit the values and save it.'
        showerror('Error',msg)

    else:
      showinfo('Error','Invalid TCID')

  def showAskOpenFile(self):
    """
    Displays file open dialog
    """
    configFile = askopenfilename()
    if configFile:
      self.loadConfigFile(configFile)
      
    self.filename = configFile

  def showAskSaveAsFile(self):
    """
    Displays file open dialog
    """
    configFile = asksaveasfilename()
    if configFile:
      self.filename = configFile
      self.saveFile(configFile)

  def showAskSaveFile(self):
    """
    Asks user to confirm or deny change.
    """
    choice = askyesno('Save?', 'Save?')
    if choice:
      self.saveFile(self.filename)
    else:
      self.setModified(modified=False)
#      self.modified = False

  def showCreateTc(self, event=None):
    """
    Displays dialog for creating a new TCID.
    """
    panel = Toplevel()
    panel.focus()
    panel.geometry('+200+200')
    panel.minsize(width=200, height=50)
    panel.title('Enter New TCID')
    v = StringVar()
    e = Entry(panel, textvariable=v)
    e.grid(row=0, column=0, columnspan=2)
    e.focus()

    def handler(event):
      """
      Inline function to capture text inside entry field.
      """
      self.createTc(v.get())
      panel.withdraw()

    def handler2(event):
      """
      Inline function to close panel upon Escape event.
      """
      panel.withdraw()

    okBtn = Button(panel, text='Ok', command=handler)
    okBtn.grid(row=1, column=0, sticky=W)
    cancelBtn = Button(panel, text='Cancel', command=panel.destroy)
    cancelBtn.grid(row=1, column=1, sticky=E)

    e.bind('<Return>', handler)
    panel.bind('<Escape>', handler2)

if __name__ == "__main__":

  """
  Main processing begins here
  """
  root = Tk()
  root.title("Master Test Controller")
  try:
    tf = MasterTestControllerGui(root, sys.argv[1])
  except:
    tf = MasterTestControllerGui(root)
  root.mainloop()


