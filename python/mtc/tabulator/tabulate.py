#!/usr/bin/python

#
# This script collates the overall test results.
#
# This script does the following:
# (1) Collects the requirement result files from across all nodes
# (2) Untars and unzips each in the /tmp dir
# (3) Sorts the contents of each reqResults.log file into an array
# (4) Provides stats on the status of each requirement tested
#
#

import os, sys, time

class Result:
  """
  Encapsulates attributes of a requirement result.
  """

  def __init__(self,tcid,rid,svc,log,host,dtg,result):
    """
    Constructor.
    """
    self.tcid   = tcid
    self.rid    = rid
    self.svc    = svc
    self.log    = log
    self.host   = host
    self.dtg    = dtg
    self.result = result

  def writeOut(self):
    """
    Writes out entire object.
    """
    str = self.tcid + ',' + self.rid + ',' + self.svc + ',' + self.log + ',' + self.host + ',' + self.dtg + ',' + self.result

    return str

def processMasterList(hosts):
  """
  Coordinates the tabulation of all results files
  """
  if len(hosts) > 0:
    copyToThisHost(hosts)
    unpack()
    sortEntries()
    print
    print "All results are saved in the file at /tmp/Master_Results_<timestamp>.txt"
    print

def copyToThisHost(hosts):
  """
  Copies over all testcase tarballs from each node involved in 
  the battery of testcases executed. The host on which this 
  script is executed shall be the destination for all tarballs.
  """
  if len(hosts) > 0:
    for h in hosts:
      remote_files = ':/tmp/*_Test_Data_*.tgz'
      dest_dir = '/tmp/.'
      remote_cmd = 'rcp ' + h + remote_files + ' ' + dest_dir + ' &>/dev/null'
      print
      print "Collecting testcase files from host: ",h
      print
      os.system(remote_cmd)

def unpack():
  """
  This unpacks the testcase tarballs.
  """
  # Default collection dir is /tmp
  DIR = '/tmp'

  # Unique string within tarball name
  patt = 'Test_Data'

  # List of tarballs to unpack
  tarballs = []

  # Create list of all tarball files in /tmp dir
  for f in os.listdir(DIR):
    if f.find(patt) > -1:
      fullpath = os.path.join(DIR, f)
      tarballs.append(fullpath)
 
  # Change to the /tmp dir before unpacking
  os.chdir(DIR)

  # Now, unpack 
  for tb in tarballs:
    tar_cmd = 'tar -zxvf ' + tb
    os.system(tar_cmd)

def sortEntries():
  """
  This processes each testcase requirement results file by sorting,
  formatting, and writing the all of the results to one file.
  """

  # Default location for all results
  RESULTS_DIR = '/tmp/tmp'
  DIR = '/tmp'
  os.chdir(DIR)

  # localtime([seconds]) -> (tm_year,tm_mon,tm_day,tm_hour,tm_min,tm_sec,tm_wday,tm_yday,tm_isdst)

  # Get current timestamp
  (yr,mon,day,hr,min,sec,wdy,ydy,dst) = time.localtime()
  timenow = "%4d%02d%02d%02d%02d%02d" % (yr,mon,day,hr,min,sec)

  # Name of master results file
  master = 'Master_Test_Results_' + timenow + '.txt'

  # List of files to interrogate
  files = []

  # List of combined results from all files
  resultsList = []

  # Expected unique string in filename
  patt = 'reqResults'

  # Create list of all results files in /tmp/tmp dir
  for f in os.listdir(RESULTS_DIR):
    if f.find(patt) > -1:
      fullpath = os.path.join(RESULTS_DIR, f)
      files.append(fullpath)

  # Build list of req results
  for r in files:
    for line in open(r,'r'):
      contents = line.split(',')
      result = Result(contents[0],
                      contents[1],
                      contents[2],
                      contents[3],
                      contents[4],
                      contents[5],
                      contents[6])

      resultsList.append(result)

  # Write contents out to master file
  outfile = open(master,'w')
  for r in resultsList:
    str = r.writeOut()
    outfile.write(str)

  outfile.close()




