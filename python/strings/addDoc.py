import os, sys, re;


def printList(l):
  """
  Prints list with indices.
  l - List of strings
  """
  list = l;
  idx = 0;
  for e in list:
    e.strip();
    print '[',idx,'] ',e;
    idx = idx + 1;

def mkArray(m, d=' '):
  """
  Returns splits string into array based on delimiter.
  m - String
  d - Delimiter character
  """
  msg = m;
  delimit = d;
  list = [s.strip() for s in msg.split(delimit)];
  return list;

def prepend(c, s):
  """
  Prepends character c to string s.
  c - Character to prepend
  s - String to modify
  """
  return c+s;

def createHeaderWithEquals(o, m, f):
  """
  Prints documentation header.
  o - Original string.
  m - Modified string.
  f - File object.
  """
  old = o;
  msg = m;
  fout = f;
  list = mkArray(m);
  varNm = list[len(list) - 3];
  varNm = varNm.replace(';','');

  fout.write('    /**\n');
  fout.write('     * Reference to '+varNm+'.\n');
  fout.write('     * \n');
  fout.write('     */\n');
  fout.write('    '+old+'\n');
  fout.write('\n');

def createHeaderNoEquals(o, m, f):
  """
  Prints documentation header.
  o - Original string.
  m - Modified string.
  f - File out.
  """
  old = o;
  msg = m;
  fout = f;
  list = mkArray(m);
  varNm = list[len(list) - 1];
  varNm = varNm.replace(';','');

  fout.write('    /**\n');
  fout.write('     * Reference to '+varNm+'.\n');
  fout.write('     * \n');
  fout.write('     */\n');
  fout.write('    '+old+'\n');
  fout.write('\n');

def createDoc(f):
  """
  Reads in file, creates headers, and prints to new file.
  f - file
  """
  fin = open(f);
  fout = open(f+'.new','w');
  
  for ln in fin.readlines():
    l = ln.strip();
    if l.endswith(';'):
      if l.find('=') == -1:
        createHeaderNoEquals(l, l, fout);
      elif (l.find('=') > -1):
        createHeaderWithEquals(l, l, fout);
    else:
      fout.write(l+'\n');

  fout.close();
    

if __name__ == "__main__":

  f = sys.argv[1];
  createDoc(f);



  
