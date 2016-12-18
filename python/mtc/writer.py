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
# Filename: writer.py
#
# author  cbhuds  BCME-####
#
# version BCME-#### 2008-###-## Initial version.
#-------------------------------------------------------------------------------

import os, sys

class Writer:
  """
  Writes testcases to file.
  hash   - Hash of testcases.
  logger - Logger reference.
  """
  def __init__(self, hash=None, logger=None):
    """
    Constructor
    """
    self.hash = hash
    self.logger = logger
  
  def write(self):
    """
    Saves values for all testcases.
    """
    xml = ''
    if self.hash is not None:
      xml = '<?xml version="1.0" ?>\n'
      xml += '<Testcases>\n'
      for k in self.hash:
        tc = self.hash[k]
        xml += tc.writeTestcase()
      xml += '</Testcases>\n'
    return xml

