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
# Filename: master_controller.py
#
# author  cbhuds  BCME-####
#
# version BCME-#### 2008-###-## Initial version.
#-------------------------------------------------------------------------------

import os
import sys
import thread
import time

sys.path.append('./gui')
sys.path.append('./tabulator')
from xmlparser import Parser
from testcases_config import Config
from tabulate import processMasterList
from utilities import Util

def usage():
  """

  Usage: python master_controller.py [-t] <testcases_file>

  where [-t] is the option to tabulate all of the results into
             one file on the host that initiated the automated
             test.
        <testcases_file> is the name of the xml file containing
             the list of testcases. Each testcase to be executed
             must have its StartTest Tag set to True in order for
             it to be started.

  """
  print usage.__doc__


class MasterController:
  """
  This class is used to trigger the automated test. It is primarily
  for command-line use.  It reads a configuration file that contains
  the all of the testcases and then executes each testcase sequentially.
  This class may by used by itself or in conjunction with mtc_gui.py,
  which provides a GUI for building and launching the tests.

  file - Full path to config file containing the testcase to run.
  """

  def __init__(self, file = None):
    """
    Constructor.
    """
    self.file        = file
    self.testcases   = []
    self.config      = None
    self.tcids       = {} 
    self.hosts       = []
    self.setupTime   = 0
    self.executeTime = 0
    self.stopTime    = 0
    self.complete    = 'no'
    self.lock        = thread.allocate_lock()


  def beginTesting(self):
    """
    This loops through the list of testcases to run and calls the appropriate
    method of each testcase as it progresses through the entire test.
    """

    # Must sort hash keys first
    tcidList = []
    for k in self.tcids.iterkeys():
      tcidList.append(k)

    sortedTcids = Util().sortByNum(tcidList)

    for id in sortedTcids:
      tc = self.tcids[id]

      # Setup
      setupTime = self.getTime()
      tc.setup(setupTime)
      self.monitor('setup',setupTime, tc, id)

      # Allow processes to come up before proceeding 
      time.sleep(10)

      # Execute
      executeTime = self.getTime()
      tc.execute(executeTime)
      self.monitor('execute', executeTime, tc, id)

      # Stop
      stopTime = self.getTime()
      tc.stop(executeTime, stopTime)
      self.monitor('stop',stopTime, tc, id)

      time.sleep(5)

    self.stop()

  def getHostList(self):
    """
    Return list of hosts involved in testing.
    """
    return self.hosts

  def getTime(self):
    """
    Returns local time in yymmddhhmmss format.
    """
    return Util().getTime()


  def load(self):
    """
    Reads config file and populates battery of testcases.
    """
    parser = Parser(self.file)
    contents = parser.loadFile()
    self.config = Config()
    self.testcases = self.config.populateBattery(contents)

  def monitor(self, phase, timestr, tc, id):
    """
    This creates a thread to monitor the progress of a testcase as
    it progresses through a phase.
    phase   - One of phases: setup, execute, or stop.
    timestr - Timestamp in string format.
    tc      - Testcase object.
    id      - Testcase ID.
    """
    done = 0
    count = len(tc.getHostmachines())

    while not done:
      for machine in tc.getHostmachines():
        DIR = '/tmp/'
        line = id + '_' + str(machine) + '_' + phase + '_' + str(timestr) + '.txt'
        if line in os.listdir(DIR):
          count -= 1

        if count <= 1:
          done = 1
          break
        time.sleep(2)

    # Show status as phase completes
    print 'TC' + id + ' ' + phase.upper() + ' Phase Completed'

  def start(self):
    """
    Triggers behavior to start the automated test. It stores a list
    of TCs, where each is identified by a value of true in the StartTest
    tag of the testcases.xml file.
    Precond - The testcases obj must be populated before this
    method is called.
    """
    if len(self.testcases) > 0:
      for t in self.testcases:
        if t.getStartTest().lower() == 'true':
          self.tcids[t.getId()] = t

    if len(self.tcids) > 0:
      os.remove('/tmp/stop.txt')
      self.beginTesting()

  def stop(self):
    """
    This simply creates a file in the /tmp dir when the auto test
    is done.
    """
    nm = '/tmp/stop.txt'
    file = open(nm, 'w')
    timestr = Util().getTime()
    file.write('auto test completed at ' + timestr)
    file.close()
  
  def tabulate(self):
    """
    Invokes script to process all results.
    """
    for id in self.tcids:
      tc = self.tcids[id]
      if tc.getStartTest().lower() == 'true':
        for h in tc.getHosts():
          if h.getHostname() not in self.hosts:
            if h.getHostname().lower() != 'unknown':
              self.hosts.append(h.getHostname())
    
    processMasterList(self.hosts)

if __name__ == "__main__":
  """
  Main processing method.
  """

  tabulate = False
  config_file = None

  if len(sys.argv) == 3:
    try:
      if sys.argv[1] == '-t' and os.stat(sys.argv[2]):
        tabulate = True
        config_file = sys.argv[2]
      else:
        usage()
    except:
      print 'ERROR: Unable to open file ' + str(sys.argv[2])

  elif len(sys.argv) == 2:
    try:
      if os.stat(sys.argv[1]):
        config_file = sys.argv[1]
      else:
        usage()
    except:
      print 'ERROR: Unable to open file ' + str(sys.argv[1])

  else:
    usage()

  if config_file is not None:
        
    print 
    print "******************************************"
    print
    print "  Initiating Automated Testing Procedures"
    print
    print "******************************************"
    print
    print "Processing " + config_file + " ..."
 
    mc = MasterController(config_file)

    print
    print 

    try: 
      mc.load()
    except:
      print
      print "ERROR: Unable to read the file: " + config_file
      print

    mc.start()

    if tabulate:
      mc.tabulate()

    print
    print "******************************************"
    print
    print "  Ending Automated Testing Procedures"
    print
    print "******************************************"


