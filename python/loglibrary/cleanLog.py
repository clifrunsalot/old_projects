import os, sys, glob, re

from loglibrary import LogUtilities

def usage(self):
  """
  Usage: [python] ./formatLog <file>

  where <file> is the file.

  The output is a file of the same name with '.clean' appended.

  """
  print usage.__doc__

def getTcid(fn):
  """
  Returns testcase ID based on filename.
  fn - Filename.
  """
  tcid = 'unset'
  patt = '.*?t(\d+)[cs].txt'
  id = re.compile(patt).match(fn)
  if id:
    tcid = 'MGVC.' + id.group(1)
  return tcid

def extractRid(lst):
  """
  Extracts the RID from each log entry in the list.
  """
  util = LogUtilities()
  for i in range(len(lst)):
    s = lst[i]
    fnd = util.extract(r'(BCME-(IRS|SRS)-\d+?):', s)
    if fnd:
      lst[i] = fnd + "\t" + s
    else:
      lst[i] = 'None' + "\t" + s
  return lst

if __name__ == "__main__":
  try:
    if len(sys.argv) == 2:
      lst = glob.glob(sys.argv[1])
      if len(lst) > 0:
        for f in lst:
          tcid = getTcid(str(f))
          util = LogUtilities()
          tList = util.removeNewLines(f)
          tList = util.removeSpecialChars(tList)
          tList = extractRid(tList)
          tList = util.prepend(tcid + "\t",tList)
          outfile = f + '.clean'
          util.printList(tList, outfile)
          #util.printOut(tList)
    else:
      usage()

  except Exception,e:
    msg = 'Error: ' + sys.argv[0] + ' was unable to process ' + sys.argv[1] + '. Check log.'
    print msg
  
