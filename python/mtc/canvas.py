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
# Filename: canvas.py
#
# author  cbhuds  BCME-####
#
# version BCME-#### 2008-###-## Initial version.
#-------------------------------------------------------------------------------

from Tkinter import *
from tkMessageBox import *

class ScrolledCanvas():
  """
  This canvas holds the host machines attributes.
  parent - Reference to parent panel.
  """
  def __init__(self, parent=None):
    """
    Constructor.
    """
    canvas = Canvas(parent)
    canvas.config(width=250, height=400)
    canvas.config(scrollregion=(5,5,200,1000))
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

class CheckbuttonAttribute:
  """
  This encapsulates a checkbox widget. This wraps the Checkbutton
  widget and adds other functionality necessary for manipulating
  the widget on the main test GUI.
  parent - Root parent panel.
  name   - Name and reference for widget.
  r      - Row
  c      - Column
  """
  def __init__(self, parent, name, r, c):
    """
    Constructor.
    """
    self.title  = name
    self.panel  = None
    self.row    = r
    self.col    = c
    self.var    = IntVar()
    self.createWidgets(parent)

  def createWidgets(self, parent):
    """
    Creates widgets on parent panel.
    parent - Root parent panel.
    """
    panel = Frame(parent)
    cb = Checkbutton(panel, text=self.title, variable=self.var)
    cb.grid(row=self.row, column=self.col, sticky=W)
    self.panel = panel

  def getTitle(self):
    """
    Returns name of attribute
    """
    return self.title

  def getValue(self):
    """
    Returns on / off status of widget.
    """
    return self

  def getPanel(self):
    """
    Returns reference to panel.
    """
    return self.panel

class EntryAttribute:
  """
  This object encapsulates an Entry widget with its label. This adds
  functionality necessary to manipulate the widget on the main
  test GUI.
  parent - Root parent panel.
  name   - Name and reference for widget.
  fLen   - Char width of Entry widget.
  r      - Row
  c      - Column
  """
  def __init__(self, parent, name, fLen, r, c):
    self.title    = name
    self.panel    = None
    self.fLength  = fLen
    self.label    = None
    self.entry    = None
    self.modified = False
    self.row    = r
    self.col    = c
    self.var    = StringVar()
    self.createWidgets(parent)
    
  def createWidgets(self, parent):
    """
    Creates panel just for label and Entry widget
    parent - Root parent panel.
    """
    panel = Frame(parent)
    # Setup label
    self.label = Label(panel, text=self.title)
    self.label.grid(row=self.row, column=self.col, sticky=W)
    # Setup entry widget
    self.entry = Entry(panel, width=self.fLength,
               justify=LEFT,
               textvariable=self.var)
    self.entry.grid(row=self.row, column=self.col + 1, sticky=E)
    self.panel = panel

  def getTitle(self):
    """
    Returns name of attribute
    """
    return self.title

  def setEntry(self, txt):
    """
    Sets text for entry widget.
    txt - Value for entry widget.
    """
    self.var.set(txt)

  def getPanel(self):
    """
    Returns panel references.
    """
    return self.panel

