import os, sys, string

def getUnique(fIn):
  """
  Returns list of unique entries in list.
  """
  uniq = {}
  for r in fIn.readlines():
    uniq[r] = string.strip(r)
  fIn.close()

  nl = uniq.keys()
  nl.sort()
  
  fOut = open('sorted_nf_rids.txt','w')
  for f in nl:
    fOut.write(f)
  fOut.close()


if __name__ == "__main__":
  fInput = open(sys.argv[1])
  getUnique(fInput)
    
  
