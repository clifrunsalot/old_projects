#!/usr/bin/python

import os, sys, re, string, exceptions, tempfile, shutil

def usage():
  """
  Usage: [python] ./fix_hdr.py <list> <hdr_txt>

  where <list> is a file with the list of the absolute path filenames.
        <hdr_txt> is a file with the header text to insert

  Note: Version controlled files must be checked out before running this tool on them.
  """

def buildArray(fn):
  """
  Populates an array with lines from the header text file.
  """
  hdrArray = [ h.strip() for h in open(fn) ]
  return hdrArray

def foundHdrLine(ln, hdrArray):
  """
  Returns list index if string ln contains one of the strings in hdrArray.
  """
  found = -1
  for idx, h in enumerate(hdrArray):
    patt = re.compile('"' + h + '"', re.IGNORECASE)
#    print patt.pattern
    if patt.match(ln):
      print ln
      print 'fIdx, ln, h ===> ',fIdx,', ',ln,', ',h
      found = idx

  return found

def getHdrStart(fContents, hdrList):
  """
  Returns the index of the first line in the existing header.
  """
  startIdx = -1
  for currIdx, l in enumerate(fContents):
    if foundHdrLine(l, hdrList) > -1:
      startIdx = currIdx
      break
  return startIdx

def backup(fn):
  """
  Creates a backup of the file 'fn' in case the new file is inadequate.
  """
  bkup = fn + '.bak'
  shutil.copy(fn, bkup)
  if os.path.exists(bkup):
    print "Backup of %s created" % (fn)
  else:
    print "WARNING: Unable to create backup of %s" % (fn)

def removeOldLines(fContents, hdrArray):
  """
  Removes old lines in fContents that match replacement lines in hdrArray.
  """
  idx = -1
  for l in fContents:
    if foundHdrLine(l, hdrArray) > -1:
      fContents.remove(l)
  return fContents 

def process(fn, lst):
  """
  Processes actions on each file.
  """
  idx = 0
  fList = [ e.strip() for e in open(fn) ]
  for f in fList:
    if os.path.exists(f):
      print '\n\nProcessing %s' % (f)
#      backup(f)
      fContents = buildArray(f)
      display(fContents)
      idx = getHdrStart(fContents, lst)
      fContents = removeOldLines(fContents, lst)
      display(fContents)
#      fContents = updateHeader(fContents, lst)
    else:
      print "\n\nWARNING: Cannot find %s. Moving to next file.\n\n" % (f)

def display(lst):
  """
  Displays contents of lst array.
  """
  for e in lst:
    print e


if __name__ == "__main__":

  try:
    args = len(sys.argv[1:])

    if args == 2:
      
      if os.path.exists(sys.argv[1]) and os.path.exists(sys.argv[2]):

        hdrArray = buildArray(sys.argv[2])
        process(sys.argv[1], hdrArray)

      else:
        raise Exception, "You have passed non-existent files as arguments"

    else:
      raise Exception, "You have not entered the correct number of arguments"

  except Exception,e:
    print usage.__doc__
    print "  ERROR: %s\n" % (e)
