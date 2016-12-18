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
# Filename: logger.py
#
# author  cbhuds  BCME-####
#
# version BCME-#### 2008-###-## Initial version.
#-------------------------------------------------------------------------------

import os, sys

from utilities import Util

class Logger:
  """
  Provides mechanisms for logging errors and information to console
  or to a file.
  file - Output file.
  """
  def __init__(self, file=None):
    """
    Constructor.
    """
    self.file   = file
    self.logger = None
    self.startLogger(self.file)

  def logInfo(self, value=""):
    """
    Prints informational statements with non-list argument
    value - Text to log.
    """
    msg = "%s INFO : %s" % (Util().getTime(), value)
    self.writeOut(msg)

  def logError(self, value=""):
    """
    Prints error statements with non-list argument
    value - Text to log.
    """
    msg = "%s ERROR: %s" % (Util().getTime(), value)
    self.writeOut(msg)

  def logList(self, value=[]):
    """
    Prints informational statements with list argument
    value - Text to log.
    """
    msg = ','.join(value)
    self.logInfo(msg)

  def writeOut(self, msg):
    """
    Sends output to descriptor.
    msg - Text to log.
    """
    if self.logger is not None:
       self.logger.write(msg + '\n')
       self.logger.flush()
    else:
       print "%s" % msg

  def startLogger(self, name):
    """
    Create file for logging.
    name - Output file.
    """
    if name is not None:
      self.logger = open(name, 'w') 

