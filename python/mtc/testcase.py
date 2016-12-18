#!/usr/bin/python

#
# COMPANY NAME: Raytheon Company
# COPYRIGHT: Copyright (c) 2008 Raytheon Company
# UNPUBLISHED WORK
# ALL RIGHTS RESERVED
# PROJECT NAME: FCS BCME
# CONTRACT NUMBER: 3EC1721
#
#-------------------------------------------------------------------------------
# Filename: testcase.py
#
# author  cbhuds  BCME-####
#
# version BCME-#### 2008-###-## Initial version.
#-------------------------------------------------------------------------------

import sys
import thread
import time
import tempfile

import tempfile
import os

class Testcase:
  """
  This is the main module used to handle each testcase. All attributes for
  a testcase are stored within this class. When possible, attributes
  that can contain other attributes should be made into their own modules
  to support OOP.  The current structure is:
      Testcase
          > id
          > scriptexecutionhost
          > starttest
          > hosts
  """

  def __init__(self,
               id = None,
               testmasterhost = None,
               scriptexecutionhost = None,
               starttest = None,
               hosts = []):
    self.id                  = id
    self.testmasterhost      = testmasterhost
    self.scriptexecutionhost = scriptexecutionhost
    self.starttest           = starttest
    self.hosts               = hosts
    self.status              = {}
    self.beginBkt            = '<'
    self.endBkt              = '>'

  def execute(self, timestamp):
    """
    Executes the testcase script that actually begins the thread test.
    """
    params  = 'ID='             + self.id             + '@'
    params += 'TESTMASTERHOST=' + self.testmasterhost + '@'
    params += 'TIMESTAMP='      + timestamp

    path='/fcs/current/test/BCME/bin/testcases/common/controller/'
    remote_cmd = path + 'Cmn_Start.sh ' + params
    self.invoke(str(self.scriptexecutionhost), remote_cmd, 'execute')

  def getId(self):
    """
    Returns Testcase ID
    """
    return self.id

  def getTestMasterHost(self):
    """
    Returns Test Master Host in either octet or alias form. This may be the
    same as the script execution host.
    """
    return self.testmasterhost

  def getScriptExecutionHost(self):
    """
    Returns script execution host in either octet or alias form. This may be the
    same as the test master host.

    """
    return self.scriptexecutionhost

  def getStartTest(self):
    """
    Returns True or False to start test on script execution host 
    """
    return self.starttest

  def getHosts(self):
    """
    Returns list of testcase attrs per host object 
    """
    return self.hosts

  def getHostmachines(self):
    """
    Returns list of hostnames involved in test. 
    """
    list = []
    for h in self.hosts:
      list.append(h.getHostname())

    return list

  def getTime(self):
    """
    Returns local time in yymmddhhmmss format.
    """
    (yr,mon,day,hr,min,sec,wdy,ydy,dst) = time.localtime()
    timenow = "%4d%02d%02d%02d%02d%02d" % (yr,mon,day,hr,min,sec)
    return timenow

  def invoke(self, host, remote_cmd, phase):
    """
    This performs the rsh call to a host.
    """
    try:

      if host.lower() != 'unknown':

        # Assemble command
        timestr = self.getTime()
        file = '/tmp/' + self.id + '_' + host + '_' + phase + '_stdout_error_' + str(timestr) + '.txt'
        complete_cmd  = 'rsh ' + host + ' "' + remote_cmd + ' &> ' + file + '"'

        print
        print 'TC' + self.id + ' ' + phase.upper() + ' Phase Initiated at ' + str(timestr)
        # Send command
        os.system(complete_cmd)
        print 'TC' + self.id + ' ' + phase.upper() + ' Phase Results in ' + file

    except Exception, e:
      print
      print "ERROR: " + str(e)
      print

  def setup(self, timestamp):
    """
    Sets up the testcase on each host
    """
    hostname        = ''
    testmasterhost  = self.testmasterhost
    setupSoscoe     = ''
    setupService    = ''
    setupDb         = ''
    startServices   = ''
    servicesToStart = []
    platformId      = ''

    for h in self.hosts:
      hostname        = h.getHostname()
      setupSoscoe     = h.getSetupSoscoe()
      setupService    = h.getSetupService()
      setupDb         = h.getSetupDb()
      startServices   = h.getStartServices()
      servicesToStart = h.getServicesToStart()
      platformId      = h.getPlatformId()

      # Format the params for sending as part of rsh command
      params  = 'ID='              + self.id         + '@'
      params += 'SETUPSOSCOE='     + setupSoscoe     + '@'
      params += 'SETUPSERVICE='    + setupService    + '@'
      params += 'SETUPDB='         + setupDb         + '@'
      params += 'STARTSERVICES='   + startServices   + '@'
      params += 'SERVICESTOSTART=' + servicesToStart + '@'
      params += 'PLATFORMID='      + platformId      + '@'
      params += 'TESTMASTERHOST='  + testmasterhost  + '@'
      params += 'TIMESTAMP='       + timestamp
    
      path='/fcs/current/test/BCME/bin/testcases/common/controller/'
      remote_cmd = path + 'Cmn_Setup.sh ' + params
      self.invoke(str(hostname), remote_cmd,'setup')

  def stop(self, starttime, timestamp):
    """
    Performs cleanup after the thread test is completed.
    """
    hostname       = ''
    testmasterhost = self.testmasterhost
    stopTc         = ''
    stopServices   = ''
    servicesToStop = []
    extractResults = ''
    archive        = ''

    for h in self.hosts:
      hostname       = h.getHostname()
      stopTc         = h.getStopTc()
      stopServices   = h.getStopServices()
      servicesToStop = h.getServicesToStop()
      extractResults = h.getExtractResults()
      archive        = h.getArchive()

      # Format the params for sending as part of rsh command
      params  = 'ID='             + self.id        + '@'
      params += 'STOPTC='         + stopTc         + '@'
      params += 'STOPSERVICES='   + stopServices   + '@'
      params += 'SERVICESTOSTOP=' + servicesToStop + '@'
      params += 'EXTRACTRESULTS=' + extractResults + '@'
      params += 'ARCHIVE='        + archive        + '@'
      params += 'STARTTIME='      + starttime      + '@'
      params += 'TESTMASTERHOST=' + testmasterhost + '@'
      params += 'TIMESTAMP='      + timestamp
    
      path='/fcs/current/test/BCME/bin/testcases/common/controller/'
      remote_cmd = path + 'Cmn_Stop.sh ' + params
      self.invoke(str(hostname), remote_cmd,'stop')

  def setId(self, id):
    """
    Sets Testcase ID
    """
    self.id = id

  def setTestMasterHost(self, tmh):
    """
    Sets test master host.
    """
    self.testmasterhost = tmh

  def setScriptExecutionHost(self, seh):
    """
    Sets script execution host.
    """
    self.scriptexecutionhost = seh

  def setStartTest(self, st):
    """
    Sets start test tag. 
    """
    self.starttest = st

  def setHosts(self, hts):
    """
    Sets hosts
    """
    self.hosts = hts

  def writeId(self):
    """
    Writes Testcase ID
    """
    return '<ID>' + str(self.id) + '</ID>\n'

  def writeTestMasterHost(self):
    """
    Writes test master host.
    """
    return '<TestMasterHost>' + str(self.testmasterhost) + '</TestMasterHost>\n'

  def writeScriptExecutionHost(self):
    """
    Writes script execution host.
    """
    return '<ScriptExecutionHost>' + str(self.scriptexecutionhost) + '</ScriptExecutionHost>\n'

  def writeStartTest(self):
    """
    Writes start test tag. 
    """
    return '<StartTest>' + str(self.starttest) + '</StartTest>\n'

  def writeHosts(self):
    """
    Writes hosts
    """
    xml = '<Hosts>\n'
    for h in self.hosts:
      xml += h.writeHost()
    xml += '</Hosts>\n'
    return xml

  def writeTestcase(self):
    """
    Write complete testcase.
    """
    xml = '<Testcase>\n'
    xml += self.writeId()
    xml += self.writeTestMasterHost()
    xml += self.writeScriptExecutionHost()
    xml += self.writeStartTest()
    xml += self.writeHosts()
    xml += '</Testcase>\n'
    return xml



