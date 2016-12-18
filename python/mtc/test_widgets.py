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
# Filename: test_widgets.py
#
# author  cbhuds  BCME-####
#
# version BCME-#### 2008-###-## Initial version.
#-------------------------------------------------------------------------------

import os, sys

from Tkinter import *

class BaseWidget:
  """
  Base Widget Class for common behavior.
  """
  def __init__(self, type, title=''):
    """
    Constructor.
    """
    self.var = type
    self.title = title
    
  def getTitle(self):
    """
    Returns name of attribute.
    """
    return self.title

  def getValue(self):
    """
    Returns value of widget.
    """
    return self.var.get()

  def setValue(self, value):
    """
    Sets value for widget.
    value - 0 for off, 1 for on
    """
    self.var.set(value)

class ScrolledCanvas:
  """
  This canvas holds the host machines attributes.
  parent - Parent panel.
  logger - Logger reference.
  """
  def __init__(self, parent=None, logger=None):
    """
    Constructor.
    """
    self.logger = logger
    canvas = Canvas(parent)
    canvas.config(width=300, height=500)
    canvas.config(scrollregion=(5,5,300,500))
    sbar = Scrollbar(parent)
    sbar.config(command=canvas.yview)
    canvas.config(yscrollcommand=sbar.set)
    sbar.pack(side=RIGHT, fill=Y)
    canvas.pack(side=LEFT, expand=YES, fill=X)
    self.canvas = canvas
    self.canvas.pack()

  def getCanvas(self):
    """
    This returns the canvas reference.
    """
    return self.canvas

class GlobalAttribute(BaseWidget):
  """
  This class pairs an attribute with its checkbox.
  """
  def __init__(self, parent, wgt, r, c, ipadx=0, ipady=0, logger=None):
    """
    Constructor.
    """
    BaseWidget.__init__(self, IntVar(),'')
    self.panel      = parent
    self.sub_widget = wgt
    self.row        = r
    self.col        = c
    self.ipadx      = ipadx
    self.ipady      = ipady
    self.logger     = logger
    self.widget     = None
    self.var        = IntVar()
    self.createWidget(self.panel)

  def createWidget(self, parent):
    """
    Creates widgets on parent panel.
    parent - Parent panel.
    """
    self.widget = Checkbutton(parent, text='All', variable=self.var)
    self.widget.grid(row=self.row, column=self.col, ipadx=self.ipadx,
            ipady=self.ipady)

  def getWidget(self):
    """
    Returns sub widget.
    """
    return self.widget

  def getSubWidget(self):
    """
    Returns sub widget.
    """
    return self.sub_widget

class CheckbuttonAttribute(BaseWidget):
  """
  This encapsulates a checkbox attribute.
  parent - Parent panel.
  name   - Name/ref to widget.
  r      - Row
  c      - Column
  d      - Direction to flow widget on panel.
  ipadx  - x-axis padding.
  ipady  - y-axis padding.
  logger - Logger reference.
  """
  def __init__(self, parent, name, r, c, d, ipadx=0, ipady=0, logger=None):
    """
    Constructor.
    """
    BaseWidget.__init__(self,IntVar(),name)
    self.title     = name
    self.panel     = None
    self.widget    = None
    self.row       = r
    self.col       = c
    self.direction = d
    self.ipadx     = ipadx
    self.ipady     = ipady
    self.logger    = logger
    self.var       = IntVar()
    self.createWidgets(parent)

  def createWidgets(self, parent):
    """
    Creates widgets on parent panel.
    parent - Parent panel.
    """
    self.widget = Checkbutton(parent, text=self.title, variable=self.var)
    self.widget.grid(row=self.row, column=self.col, sticky=self.direction,
                    ipadx=self.ipadx, ipady=self.ipady)


  def getWidget(self):
    """
    Returns reference to panel.
    """
    return self.widget

class EntryAttribute(BaseWidget):
  """
  This object encapsulates a Entry field with its label
  parent - Parent panel.
  name   - Name/ref to widget.
  fLen   - Char width of entry widget.
  r      - Row
  c      - Column
  ipadx  - x-axis padding.
  ipady  - y-axis padding.
  logger - Logger reference.
  """
  def __init__(self, parent, name, fLen, r, c, ipadx=0, ipady=0, logger=None):
    BaseWidget.__init__(self,StringVar(), name)
    self.title    = name
    self.widget   = None
    self.fLength  = fLen
    self.label    = None
    self.entry    = None
    self.modified = False
    self.row      = r
    self.col      = c
    self.ipadx    = ipadx
    self.ipady    = ipady
    self.logger   = logger
    self.var      = StringVar()
    self.createWidgets(parent)
    
  def createWidgets(self, parent):
    """
    Creates panel just for label and Entry widget
    parent - Parent panel.
    """
    tpanel = Frame(parent)
    panel = Frame(tpanel)
    # Setup label
    self.label = Label(panel, text=self.title)
    self.label.grid(row=0, column=0, ipadx=self.ipadx,
                   ipady=self.ipady)
    # Setup entry widget
    self.entry = Entry(panel, width=self.fLength,
               justify=LEFT,
               textvariable=self.var)
    self.entry.grid(row=0, column=1, ipadx=self.ipadx,
                    ipady=self.ipady)
    tpanel.grid(row=self.row, column=self.col, sticky=W)
    panel.pack()
    if self.title.lower() == 'tcid':
      self.entry.config(state=DISABLED)
    self.widget = tpanel

  def getValue(self):
    """
    Gets text value for entry widget. Set a default of 'unknown' for blank
    values.
    """
    value = 'unknown'
    if self.var.get() == '':
      self.setValue(value)
    return self.var.get()

  def getEntry(self):
    """
    Returns ref to entry widget, which may then be used for
    event binding.
    """
    return self.entry

  def getWidget(self):
    """
    Returns panel references.
    """
    return self.widget

  def resetLabel(self, title):
    """
    Resets title of widget.
    title - New title for label.
    """
    self.label.config(text=title)

