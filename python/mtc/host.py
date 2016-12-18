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
# Filename: host.py
#
# author  cbhuds	BCME-####
#
# version BCME-####	2008-###-##	Initial version.
#-------------------------------------------------------------------------------

class Host:
  """
  Class for encapsulating host attributes. Use this object to
  hold information that is specific to each host machine.

  hostname       - Name of hostmachine on which to run the test.
  setupsoscoe    - True or False to setup soscoe prior to starting it.
  setupservice   - True or False to setup services prior to starting the
                   services involved in the test.
  setupdb        - True or False to prepare the db for the test. This is
                   primarily for startup data.
  stoptc         - True or False to perform any cleanup activities once
                   the testcase is complete. 
  startservices  - List of services to start prior to the test.
  stopservices   - True or False to stop the services upon completion
                   of the test.
  servicestostop - List of services to stop after the test.
  extractresults - True or False to collect the results of the test on
                   the hostmachine.
  archive        - True or False to archive all test data, test config files,
                   service config files, and all logs.
  platformid     - Platform ID assigned to this hostmachine. This must match
                   the assignments defined in the TO tables.
  port           - Port number to use for socket connections when rsh or ssh
                   connections fail. There is no implementation for this
                   type of comms, but it is available to simplify migrating
                   the comms implementation when the time arrives.
                   
  """
  def __init__(self,
               hostname        = None,
               setupsoscoe     = None,
               setupservice    = None,
               setupdb         = None,
               stoptc          = None,
               startservices   = None,
               servicestostart = [],
               stopservices    = None,
               servicestostop  = [],
               extractresults  = None,
               archive         = None,
               platformid      = None,
               port            = None):
    """
    Constructor
    """

    self.hostname        = hostname
    self.setupsoscoe     = setupsoscoe
    self.setupservice    = setupservice
    self.setupdb         = setupdb 
    self.stoptc          = stoptc
    self.startservices   = startservices
    self.servicestostart = servicestostart
    self.stopservices    = stopservices
    self.servicestostop  = servicestostop
    self.extractresults  = extractresults
    self.archive         = archive
    self.platformid      = platformid
    self.port            = port

  def getHostname(self):
    """
    Returns hostname of machine.
    """
    return self.hostname

  def getSetupSoscoe(self):
    """
    Returns True or False to setup soscoe.
    """
    return self.setupsoscoe

  def getSetupService(self):
    """
    Returns True or False to setup service.
    """
    return self.setupservice

  def getSetupDb(self):
    """
    Returns True or False to setup database.
    """
    return self.setupdb

  def getStopTc(self):
    """
    Returns True or False to stop testcase.
    """
    return self.stoptc

  def getStartServices(self):
    """
    Returns True or False to start services on host.
    """
    return self.startservices

  def getServicesToStart(self):
    """
    Returns list of services to start. Services must be
    separated with commas. This may be different from 
    services to stop.
    """
    return self.servicestostart

  def getStopServices(self):
    """
    Returns True or False to stop services on host.
    """
    return self.stopservices

  def getServicesToStop(self):
    """
    Returns list of services to stop. Services must be
    separated with commas. This may be different from
    services to start.
    """
    return self.servicestostop

  def getExtractResults(self):
    """
    Returns True or False to extract the results after the test.
    """
    return self.extractresults

  def getArchive(self):
    """
    Returns True or False to archive all test data after the test.
    """
    return self.archive

  def getPlatformId(self):
    """
    Returns the platformid for the host.
    """
    return self.platformid

  def getPort(self):
    """
    Returns port number for communicating to host.
    """
    return self.port

  def setHostname(self, name):
    """
    Sets hostname of machine.
    name - Hostname in octet or alias form.
    """
    self.hostname = name

  def setSetupSoscoe(self, truefalse):
    """
    Sets setup soscoe.
    truefalse - True to setup soscoe before restarting it.
    """
    self.setupsoscoe = truefalse

  def setSetupService(self, truefalse):
    """
    Sets setup service.
    truefalse - True to setup services prior to restarting them.
    """
    self.setupservice = truefalse

  def setSetupDb(self, truefalse):
    """
    Sets setup database.
    truefalse - True to prepare the DB with startup data.
    """
    self.setupdb = truefalse

  def setStopTc(self, truefalse):
    """
    Sets stop testcase.
    truefalse - True to perform cleanup activities after the test.
    """
    self.stoptc = truefalse

  def setStartServices(self, truefalse):
    """
    Sets start services.
    truefalse - True to start the script that starts the services.
    """
    self.startservices = truefalse

  def setServicesToStart(self, svcs):
    """
    Sets list of services to start.
    svcs - List of services to start prior to the test.
    """
    self.servicestostart = svcs

  def setStopServices(self, truefalse):
    """
    Sets stop services.
    truefalse - True to stop the services after the test.
    """
    self.stopservices = truefalse

  def setServicesToStop(self, svcs):
    """
    Sets list of services to stop.
    svcs - List of services to stop after the test.
    """
    self.servicestostop = svcs

  def setExtractResults(self, truefalse):
    """
    Sets extract results.
    truefalse - True to collect the results on the hostmachine.
    """
    self.extractresults = truefalse

  def setArchive(self, truefalse):
    """
    Sets archive all test.
    truefalse - True to archive all test data, test config files,
                svc config files, and all logs.
    """
    self.archive = truefalse

  def setPlatformId(self, plid):
    """
    Sets the platformid for the host.
    plid - Platform ID of hostmachine.
    """
    self.platformid = plid

  def setPort(self, port):
    """
    Sets port number for communicating to host.
    port - Port to use for socket connections.
    """
    self.port = port

  def writeHostname(self):
    """
    Writes hostname of machine.
    """
    return '<Hostname>' + str(self.hostname) + '</Hostname>\n'

  def writeSetupSoscoe(self):
    """
    Writes setup soscoe.
    """
    return '<SetupSoscoe>' + str(self.setupsoscoe) + '</SetupSoscoe>\n'

  def writeSetupService(self):
    """
    Writes setup service.
    """
    return '<SetupService>' + str(self.setupservice) + '</SetupService>\n'

  def writeSetupDb(self):
    """
    Writes setup database.
    """
    return '<SetupDb>' + str(self.setupdb) + '</SetupDb>\n'

  def writeStopTc(self):
    """
    Writes stop testcase.
    """
    return '<StopTc>' + str(self.stoptc) + '</StopTc>\n'

  def writeStartServices(self):
    """
    Writes start services.
    """
    return '<StartServices>' + str(self.startservices) + '</StartServices>\n'

  def writeServicesToStart(self):
    """
    Writes list of services to start.
    """
    return '<ServicesToStart>' + self.servicestostart + '</ServicesToStart>\n'

  def writeStopServices(self):
    """
    Writes stop services.
    """
    return '<StopServices>' + str(self.stopservices) + '</StopServices>\n'

  def writeServicesToStop(self):
    """
    Writes list of services to stop.
    """
    return '<ServicesToStop>' + self.servicestostop + '</ServicesToStop>\n'

  def writeExtractResults(self):
    """
    Writes extract results.
    """
    return '<ExtractResults>' + str(self.extractresults) + '</ExtractResults>\n'

  def writeArchive(self):
    """
    Writes archive all test.
    """
    return '<Archive>' + str(self.archive) + '</Archive>\n'

  def writePlatformId(self):
    """
    Writes the platformid for the host.
    """
    return '<PlatformId>' + str(self.platformid) + '</PlatformId>\n'

  def writePort(self):
    """
    Writes port number for communicating to host.
    """
    return '<Port>' + str(self.port) + '</Port>\n'

  def writeHost(self):
    """
    Writes hosts into xml-formatted string.
    """
    xml = '<Host>\n'
    xml += '<Components>\n'
    xml += self.writeHostname()
    xml += self.writeSetupSoscoe()
    xml += self.writeSetupService()
    xml += self.writeSetupDb()
    xml += self.writeStopTc()
    xml += self.writeStartServices()
    xml += self.writeServicesToStart()
    xml += self.writeStopServices()
    xml += self.writeServicesToStop()
    xml += self.writeExtractResults()
    xml += self.writeArchive()
    xml += self.writePlatformId()
    xml += self.writePort()
    xml += '</Components>\n'
    xml += '</Host>\n'

    return xml


