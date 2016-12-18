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
# Filename: testcases_config.py
#
# author  cbhuds	BCME-####
#
# version BCME-####	2008-###-##	Initial version.
#-------------------------------------------------------------------------------

# Core libraries
from sys import *
from xml.dom.minidom import Node

# App libraries
from xmlparser import Parser
from testcase import Testcase
from host import Host

class ConfigError(Exception):
  """
  Class for handling config file errors
  """
  def __init__(self, value):
    print "ERROR: Unable to find value for tag <" + value + ">"

class Config:
  """
  Class for encapsulating the list of testcases. Use this class to
  create and manage each testcase object.
  """

  def __init__(self):
    """
    Constructor.
    """
    self.testcases = []

  def createTc(self):
    """
    Creates a new testcase.
    """
    hosts = []
    for h in range(5):
      hosts.append(
        Host('unknown',
            'False',
            'False',
            'False',
            'False', 
            'False',
            'unknown',
            'False',
            'unknown',
            'False',
            'False',
            'unknown',
            'unknow'))
            
    newTc = Testcase('unknown', 'unknown', 'unknown', 'False', hosts)
    return newTc

  def getSubNode(self, node, name):
    """
    Retrieves text value from subnode
    node - XML node from which to begin search for name
    name - Tag name to find

    """
    text = None
    try:
      sNode = node.getElementsByTagName(name)
      if len(sNode) > 0:
        for subNode in sNode:
          for textNode in subNode.childNodes:
            if textNode.nodeType == Node.TEXT_NODE:
              text = textNode.data
      else:
        raise ConfigError(name)

      if text is None:
        raise ConfigError(name)

    except:
      import sys
      ConfigError(name)
      sys.exit()
    return text
    
  def populateBattery(self, node):
    """
    Populates battery of testcases with contents of configuration file.
    node - Top-most XML node to begin search for testcase values.
    """
    testcases = []

    # Grab individual testcase nodes
    tcList = node.getElementsByTagName('Testcase')

    #
    # --Block A ---------------------------------------------------------------
    #
    for tcNode in tcList:

      # Initialize data
      tcid = ''
      testmasterhost = ''
      scriptexecutionhost = ''
      starttest = ''
      hosts = []
      hostname = None
      setupsoscoe = None
      setupservice = None
      setupdb = None
      stoptc = None
      startservices = None
      servicestostart = []
      stopservices = None
      servicestostop = []
      extractresults = None
      archive = None
      platformid = None
      port = None

      # Grab ID
      tcid = self.getSubNode(tcNode,'ID')
             
      # Grab Test Master Host 
      testmasterhost = self.getSubNode(tcNode,'TestMasterHost')

      # Grab Script Execution Host 
      scriptexecutionhost = self.getSubNode(tcNode,'ScriptExecutionHost')

      # Grab Start Test 
      starttest = self.getSubNode(tcNode,'StartTest')

      #
      # --Block B -------------------------------------------------------------
      #

      # Grab Hosts
      hostsNode = tcNode.getElementsByTagName('Hosts')

      #
      # --Block C -------------------------------------------------------------
      #
      for h in hostsNode:

        # Grab Host
        hostNode = h.getElementsByTagName('Host')

        #
        # --Block D -------------------------------------------------------------
        #
        for components in hostNode:

          # Grab Components
          componentsNode = components.getElementsByTagName('Components')
          for comp in componentsNode:

            # Grab Hostname
            hostname = self.getSubNode(comp, 'Hostname')
                   
            # Grab SetupSOSCOE
            setupsoscoe = self.getSubNode(comp, 'SetupSoscoe')
                   
            # Grab SetupService
            setupservice = self.getSubNode(comp, 'SetupService')
                  
            # Grab SetupDb
            setupdb = self.getSubNode(comp,'SetupDb')

            # Grab StopTC
            stoptc = self.getSubNode(comp, 'StopTc')
                   
            # Grab StartServices
            startservices = self.getSubNode(comp, 'StartServices')
                   
            # Grab ServicesToStart
            servicestostart = self.getSubNode(comp, 'ServicesToStart')
                   
            # Grab StopServices
            stopservices = self.getSubNode(comp, 'StopServices')
                   
            # Grab ServicesToStop
            servicestostop = self.getSubNode(comp, 'ServicesToStop')
                   
            # Grab ExtractResults
            extractresults = self.getSubNode(comp, 'ExtractResults')
                   
            # Grab Archive
            archive = self.getSubNode(comp, 'Archive')

            # Grab PlatformId
            platformid = self.getSubNode(comp, 'PlatformId')
                   
            # Grab Port
            port = self.getSubNode(comp, 'Port')

          host = Host(hostname,
            setupsoscoe,
            setupservice,
            setupdb,
            stoptc, 
            startservices,
            servicestostart,
            stopservices,
            servicestostop,
            extractresults,
            archive,
            platformid,
            port)
               
          hosts.append(host)
        #
        # --Block D End ------------------------------------------------------
        #

      #
      # --Block C End --------------------------------------------------------
      #

      testcase = Testcase(tcid, testmasterhost, scriptexecutionhost, starttest, hosts)
      testcases.append(testcase)

      #
      # --Block B End ---------------------------------------------------------
      #

    self.testcases = testcases
    #
    # --Block A End -----------------------------------------------------------
    #
    return testcases

  def getTcid(self, n):
    """
    Returns testcase id.
    n - The index of the testcase
    """
    if n in range(len(self.testcases)):
      return self.testcases[n]
    else:
      import sys
      print "ERROR: Index must be inside the range: 0 to ", len(self.testcases) - 1
      sys.exit()

  def getAttrs(self):
    """
    Returns the testcase attrs stringed together in key=value pairs
    """
    msg = ''
    for tc in self.testcases:
        msg += 'ID='                 + str(tc.getId()) + '@'
        msg += 'TestMasterHost='      + str(tc.getTestMasterHost()) + '@'
        msg += 'ScriptExecutionHost=' + str(tc.getScriptExecutionHost()) + '@'
        msg += 'StartTest='           + tc.getStartTest() + '@'
        for h in tc.getHosts():
          msg += 'Hostname='         + h.getHostname() + '@'
          msg += 'SetupSoscoe='     + h.getSetupSoscoe() + '@'
          msg += 'SetupService='    + h.getSetupService() + '@'
          msg += 'SetupDb='         + h.getSetupDb() + '@'
          msg += 'StopTc='          + h.getStopTc() + '@'
          msg += 'StartServices='   + h.getStartServices() + '@'
          msg += 'ServicesToStart=' + h.getServicesToStart() + '@'
          msg += 'StopServices='    + h.getStopServices() + '@'
          msg += 'ServicesToStop='  + h.getServicesToStop() + '@'
          msg += 'ExtractResults='  + h.getExtractResults() + '@'
          msg += 'Archive='         + h.getArchive() + '@'
          msg += 'PlatformId='      + h.getPlatformId() + '@'
          msg += 'Port='            + str(h.getPort())

    return msg

  def printOut(self, tcs):
    """
    Prints contents of each testcase
    tcs - List of testcases
    """
    msg = ''
    for tc in tcs:
      msg += '  ID                      ' + str(tc.getId()) + '\n'
      msg += '    TestMasterHost        ' + str(tc.getTestMasterHost()) + '\n'
      msg += '    ScriptExecutionHost   ' + str(tc.getScriptExecutionHost()) + '\n'
      msg += '    StartTest             ' + tc.getStartTest() + '\n'
      for h in tc.getHosts():
        msg += '    Hostname              ' + h.getHostname() + '\n'
        msg += '      SetupSoscoe         ' + h.getSetupSoscoe() + '\n'
        msg += '      SetupService        ' + h.getSetupService() + '\n'
        msg += '      SetupDb             ' + h.getSetupDb() + '\n'
        msg += '      StopTc              ' + h.getStopTc() + '\n'
        msg += '      StartServices       ' + h.getStartServices() + '\n'
        msg += '      ServicesToStart     ' + h.getServicesToStart() + '\n'
        msg += '      StopServices        ' + h.getStopServices() + '\n'
        msg += '      ServicesToStop      ' + h.getServicesToStop() + '\n'
        msg += '      ExtractResults      ' + h.getExtractResults() + '\n'
        msg += '      Archive             ' + h.getArchive() + '\n'
        msg += '      PlatformId          ' + h.getPlatformId() + '\n'
        msg += '      Port                ' + str(h.getPort()) + '\n'

    print msg

if __name__ == "__main__":
    """
    Main processing begins here.  This method allows the library to be
    invoked as a stand-alone application.
    """

    import sys
    infile = sys.argv[1]
    parser = Parser(infile)
    battery = parser.loadFile()
    config = Config()
    testcases = config.populateBattery(battery)
    print "\n"

    myTc = config.getTcid(0)
    config.printOut([myTc])
    print 
    print config.getAttrs([myTc])

