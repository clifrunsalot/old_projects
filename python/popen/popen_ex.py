#!/usr/bin/env python

import os, sys, re, string, exceptions
sys.path.append('../header_fix')
from logutil import MyLogger

try:

  mLog = MyLogger()
  (child_stdin, child_stdout, child_stderr) = os.popen3('ls /tmp | egrep scMaster; crap', mode='t', bufsize=-1)

  for l in child_stdout.readlines():
    mLog.log(l)

  for e in child_stderr.readlines():
    mLog.log(e)

except Exception, e:
  print "ERROR: %s" % (e)
