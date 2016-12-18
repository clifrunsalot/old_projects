import os, sys, decimal

secsInMin = 60
minsInDeg = 60
hunsInSec = 100

def usage():
  """
  Usage: [python] getDeg.py <lat|lon> < [ d m s h | f ] >

  where d is degree part of coordinate
        m is minute part of coordinate
        s is second part of coordinate
        h is hundreds part of coordinate
        f is decimal form of degree coordinate

        -180 <= d <= 179 for Longitude
        -89 <= g < 80 for latitude

  """
  print usage.__doc__

def isValidDeg(l, d):
  """
  Return TRUE if deg is in valid range for type of coordinate.
  ind = 'lat' or 'lon'
  d = degree
  """
  ind = l
  deg = int(d)
  ok = False
  if ind == 'lon' and deg in range(-180,180):
    ok = True 
  elif ind == 'lat' and deg in range(-90,90):
    ok = True
  return ok

def isValidMinSec(ms):
  """
  Returns TRUE if ms is between 0 and 99 
  minsec = ms
  """
  minsec = int(ms)
  ok = False
  if minsec in range(0,99):
    ok = True
  return ok

def isValidHundreth(h):
  """
  Returns TRUE if hundreth is between 0 and 999
  h - hundreths of a second
  """
  hun = int(h)
  ok = False
  if hun in range(0,999):
    ok = True
  return ok

def getDeg(i, d, m, s, h):
  """
  Returns Decimal form of degree
  ind - latitude or longitude designation
  d - Degree
  m - Minute
  s - Second
  h - Hundreths of Seconds
  """
  ind = i
  deg = int(d)
  min = float(m)
  sec = float(s)
  hun = float(h)
  fraction = float(0)
  ok = False

  if isValidDeg(ind,deg) and isValidMinSec(min) and isValidMinSec(sec) and isValidHundreth(hun):

    fraction = float((hun/(hunsInSec*secsInMin*minsInDeg))) + float((sec/(secsInMin*minsInDeg))) + float((min/(minsInDeg)))
    sFrac = str(fraction)
    print str(d) + '.' + sFrac.split('.')[1]
  else:
    usage()

def getDMS(f):
  """
  Returns parsed degrees, minutes, and seconds of decimal form of degrees.
  f - Decimal form of degree
  """
  whole = f.split('.')[0]
  print 'whole ', whole
  frac = '0.' + f.split('.')[1]
  print 'frac', frac
  
  minStr = str(float(frac)*60)
  min = minStr.split('.')[0]
  print 'minStr ',minStr
  print 'min ',min
  secStr = minStr[1:]
  sec = (str(float(secStr)*60)).split('.')[0]
  print 'secStr ',secStr
  print 'sec ',sec
  hunStr = secStr[1:]
  hun = (str(float(hunStr)*100)).split('.')[0]
  print 'hunStr ',hunStr
  print 'hun ',hun
  print whole + ' ' + min + ' ' + sec + ' ' + hun
  
if __name__ == "__main__":
  """
  Main method
  """

  lst = ['lat','lon']
  if sys.argv[1] in lst:

    try:
      if len(sys.argv) == 6:
        try:
          getDeg(sys.argv[1], 
                int(sys.argv[2]), 
                int(sys.argv[3]), 
                int(sys.argv[4]),
                int(sys.argv[5]))
        except Exception, e:
          raise
      else:
        try:
          getDMS(str(float(sys.argv[2])))
        except Exception, e:
          raise
    except Exception, e:
      usage()
  else:
    usage()

