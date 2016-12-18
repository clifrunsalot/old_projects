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
# Filename: test_attribute.py
#
# author  cbhuds  BCME-####
#
# version BCME-#### 2008-###-## Initial version.
#-------------------------------------------------------------------------------

import os, sys

from utilities import Util

class Attributes:
  """
  Decouples and manages the list of attributes from the MasterTestControllerGui.
  logger - Logger reference.
  """
  def __init__(self, logger=None):
    """
    Constructor.
    """
    self.hash      = {} 
    self.logger    = logger
    self.hostIndex = -1
    self.tc        = None
    self.id        = None
    self.util      = Util() 

  def append(self, title, element):
    """
    Adds element to list of controls from TestFrame application.
    title   - String reference to element used as the key.
    element - Object reference.
    """
    self.hash[title] = element

  def getCurrentTC(self):
    """
    Return entire object of currently selected testcase.
    """
    return self.tc

  def getCurrentId(self):
    """
    Returns current value of TCID field.
    """
    return self.id

  def getHostIndex(self):
    """
    Returns index of currently selected host.
    """
    return self.hostIndex

  def getAttr(self, title):
    """
    Returns ref to single widget named title.
    title - Returns object with title key.
    """
    val = None
    if self.hash.has_key(title):
      val = self.hash[title]
    return val

  def fill(self, title, value):
    """
    Sets value of field named by title
    title - Key string of object.
    value - Object reference.
    """
    item = self.hash[title]
    if value.lower() == 'true' or value.lower() == 'false':
      self.util.translateStrToDigit(item, value) 
    else:
      item.setValue(value)

  def populate(self, tc, index):
    """
    Populates all fields with attributes of currently selected testcase.
    tc    - Testcase object.
    index - Index of host.
    Precond - Assumes tc exists within list of testcases.
    """
    self.tc = tc
    self.id = self.tc.getId()
    self.fill('TCID',tc.getId())
    self.fill('Test Master Host', tc.getTestMasterHost())
    self.fill('Script Execution Host', tc.getScriptExecutionHost())
    self.fill('Start Testcase', tc.getStartTest())
    try:
      h = (tc.getHosts())[index]
      self.setHostnameLabel(index)
      self.populateHost(h)
      self.hostIndex = index
    except:
      self.logger.logError('Invalid host index: ' + str(index))
      self.populate(tc, 0)

  def populateHost(self, h):
    """
    Populates host attributes.
    h - Host object.
    """
    self.fill('Hostname', h.getHostname())
    self.fill('Setup Soscoe', h.getSetupSoscoe())
    self.fill('Setup Service', h.getSetupService())
    self.fill('Setup DB', h.getSetupDb())
    self.fill('Stop Testcase', h.getStopTc())
    self.fill('Start Services', h.getStartServices())
    self.fill('Services To Start', h.getServicesToStart())
    self.fill('Stop Services', h.getStopServices())
    self.fill('Services To Stop', h.getServicesToStop())
    self.fill('Extract Results', h.getExtractResults())
    self.fill('Archive', h.getArchive())
    self.fill('Platform ID', h.getPlatformId())
    self.fill('Port', h.getPort())

  def printTitles(self):
    """
    Prints out all attribute titles for testcase.
    """
    for k,v in self.hash:
      item = self.hash[k]
      self.logger.logInfo(str(k) + ':' + item.getTitle())

  def save(self):
    """
    Updates and returns the testcase object
    """
    tc = self.tc
    val = self.hash['TCID'].getValue() 
    tc.setId(val)
    val = self.hash['Test Master Host'].getValue()
    tc.setTestMasterHost(val)
    val = self.hash['Script Execution Host'].getValue()
    tc.setScriptExecutionHost(val)
    val = self.util.translateDigitToStr(self.hash['Start Testcase'].getValue())
    tc.setStartTest(val)
   
    # Select the current host, before saving.
    h = tc.getHosts()[self.hostIndex] 
    
    val = self.hash['Hostname'].getValue()
    h.setHostname(val)
    val = self.util.translateDigitToStr(self.hash['Setup Soscoe'].getValue())
    h.setSetupSoscoe(val)
    val = self.util.translateDigitToStr(self.hash['Setup Service'].getValue())
    h.setSetupService(val)
    val = self.util.translateDigitToStr(self.hash['Setup DB'].getValue())
    h.setSetupDb(val)
    val = self.util.translateDigitToStr(self.hash['Stop Testcase'].getValue())
    h.setStopTc(val)
    val = self.util.translateDigitToStr(self.hash['Start Services'].getValue())
    h.setStartServices(val)
    val = self.hash['Services To Start'].getValue()
    h.setServicesToStart(val)
    val = self.util.translateDigitToStr(self.hash['Stop Services'].getValue())
    h.setStopServices(val)
    val = self.hash['Services To Stop'].getValue()
    h.setServicesToStop(val)
    val = self.util.translateDigitToStr(self.hash['Extract Results'].getValue())
    h.setExtractResults(val)
    val = self.util.translateDigitToStr(self.hash['Archive'].getValue())
    h.setArchive(val)
    val = self.hash['Platform ID'].getValue()
    h.setPlatformId(val)
    val = self.hash['Port'].getValue()
    h.setPort(val)
    return tc

  def setHostnameLabel(self, idx):
    """
    Resets hostname label to index.
    idx - Index of host.
    """
    self.hash['Hostname'].resetLabel('Hostname ' + str(idx))
    self.hostIndex = idx

