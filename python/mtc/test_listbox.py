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
# Filename: test_listbox.py
#
# author  cbhuds  BCME-####
#
# version BCME-#### 2008-###-## Initial version.
#-------------------------------------------------------------------------------

import os, sys

from Tkinter import *
from tkMessageBox import *
from utilities import Util

class TcidListbox:
  """
  Manages the contents of the listbox object.
  listbox - Listbox reference.
  tcHash  - Hash of testcases.
  attrObj - Object with list of widgets.
  logger  - Logger
  """
  def __init__(self, listbox=None, hashObj={}, attrObj=None, logger=None):
    """
    Constructor
    """
    self.listbox    = listbox
    self.hashObject = hashObj
    self.tcHash     = self.hashObject['hash']
    self.modified   = self.hashObject['modified']
    self.attrObject = attrObj
    self.logger     = logger
    self.activate()
    self.bindCommands()

  def activate(self, event=None):
    """
    Activates listbox on focus to prevent errors.
    event - Event triggered.
    """
    self.listbox.focus()
    self.listbox.activate(0)
    self.listbox.selection_set(0)

  def activateSelection(self, event):
    """
    Activates the selected row in listbox.
    event - Event triggered.
    """
    if self.isModified():
      self.warn()
    else:
      index = map(int, self.listbox.curselection())[0]
      self.listbox.focus()
      self.listbox.selection_set(index)
      id = self.listbox.get(index)
      self.logger.logInfo("Selected " + str(id))
      self.refresh(id)

  def bindCommands(self):
    """
    Binds up and down keys to listbox behavior
    """
    self.listbox.bind('<Up>', self.moveUp)
    self.listbox.bind('<Down>', self.moveDown)
    self.listbox.bind('<FocusIn>',self.activate)
    self.listbox.bind('<Double-1>',self.activateSelection)

  def clearListBox(self):
    """
    Remove items from listbox
    """
    if self.listbox is not None:
      size = self.listbox.size()
      if size > 0:
        self.listbox.delete(0, size)

  def isModified(self):
    """
    Returns True if any attribute is modified.
    """
    return self.modified

  def loadListbox(self, displayId=None):
    """
    Populate listbox widget
    displayId - If provided, matches list box selection to the
      displayed TCID.
    """
    if self.listbox is not None:
      self.clearListBox()
      if self.tcHash is not None:
        tcList = []
        for k,v in self.tcHash.iteritems():
          tcList.append(k)
        if len(tcList) > 0:
          tcList = Util().sortByNum(tcList)
          self.logger.logList(map(str,tcList))
          pos=0
          for id in tcList:
            self.listbox.insert(int(pos), id.encode("utf-8"))
            pos += 1
          # Populate testcases attributes with values from first tc
          self.refresh(tcList[0])
        else:
          self.logger.logError("Must have a list of testcases before they can be loaded")

  def moveDown(self, event):
    """
    Capture <Down> key event
    event - Event triggered.
    """
    if self.isModified():
      self.warn()
    else:
      if self.listbox is not None:
        self.listbox.focus()
        index = map(int, self.listbox.curselection())[0]
        # Compensate for quirky listbox behavior
        index += 1
        if index > self.listbox.size() - 1:
          index = self.listbox.size() - 1
        id = self.listbox.get(index)
        self.logger.logInfo("Selected " + str(id))
        self.refresh(id)

  def moveUp(self, event):
    """
    Capture <Up> key event
    event - Event triggered.
    """
    self.logger.logInfo(self.modified)
    if self.isModified():
      self.warn()
    else:
      if self.listbox is not None:
        self.listbox.focus()
        index = map(int, self.listbox.curselection())[0]
        # Compensate for quirky listbox behavior
        index -= 1
        if index < 0:
          index = 0
        id = self.listbox.get(index)
        self.logger.logInfo("Selected " + str(id))
        self.refresh(id)

  def refresh(self, id):
    """
    Refreshes Testcases attri panel.
    id - Key
    Precond - The local attrObject and TcHash must be populated before this
    is called.
    """
    if self.tcHash is not None:
      try:
        if self.tcHash.has_key(id):
          self.attrObject.populate(self.tcHash[id], 0)
          self.listbox.focus()
      except StandardError, errstr:
        self.logger.logError(errstr)

  def setHash(self, hashObj):
    """
    Sets hash for listbox.
    """
    self.hashObject = hashObj
    self.tcHash     = self.hashObject['hash']
    self.modified   = self.hashObject['modified']

  def warn(self):
    """
    Ask user to save file.
    """
    msg  = 'This testcase has been modified.\n\n'
    msg += 'To proceed, you must <save> the file.\n'
    msg += 'or <cancel> to remove the edits\n'
    showinfo('Save?',msg)

