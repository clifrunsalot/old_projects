import os, sys, re

class LogUtilities():

  def __init__(self):
    """
    Initializer for internal variable, if any.
    """

  def extract(self, extr, entry):
    """
    Returns the first instance of string extr if extr exists 
    within each element in lst.
    extr - String or regular expression to extract.
    entry - String in which to search.
    """
    found = re.compile(extr).search(entry)
    if found:
      return found.group(1)
    

  def insert(self, ins, s, lst):
    """
    Insert string ins before string s if s exists in element in list lst.
    ins - String to insert.
    s - String after insert string.
    lst - List.
    """
    lst2 = []
    for i in range(len(lst)):
      entry = str(lst[i])
      entry = entry.replace(s, ins + s)
      lst[i] = entry
    return lst

  def prepend(self, pre, lst):
    """
    Prepends string pre to each element in list lst.
    pre - String to prepend.
    lst - List.
    """
    lst2 = []
    for i in range(len(lst)):
      s = str(lst[i])
      s = pre + s
      lst[i] = s
    return lst

  def printOut(self, lst):
    """
    Prints to std out the contents of the list lst.
    """
    for i in range(len(lst)):
      print lst[i]

  def printList(self, l, f):
    """
    Prints items in list.
    l - List
    f - Name of output file
    """
    outfile = open(f, 'w')
    for ln in l:
      # Print first item of each element in array.
      print str(ln)
      outfile.write(str(ln) + '\n')
    outfile.close();

  def removeSpecialChars(self, lst):
    """
    Returns list after stripping special chars.
    lst - List of strings.
    """
    try:
      for i in range(len(lst)):
        s = lst[i][0]
        s = s.replace('\t', '')
        s = s.replace('[0m','')
        s = s.replace('[34m','')
        s = s.replace('[35m','')
        lst[i] = s
    except Exception, e:
        print e
    return lst


  def removeNewLines(self, file):
    """
    Reads file, removes newlines, reformats each log entry and returns.
    list.  Also, strips rogue escape characters.
    file - Name of log.
    """
    fin = open(file)
    bigString = ""

    # Remove all newlines
    newList = fin.readlines()

    try:
      for i in newList:
        bigString = bigString + ' ' + i.replace('\x1b','').strip()
    except Exception, e:
      print e

    # Create pattern and compile
    start_patt = r'(Requirement|Debug).*?-\s'
    patt = re.compile('(' + start_patt + '.*?)(?=' + start_patt + ')')

    # Create new list
    # The findall method produces a list where each element consists of 
    # three items: ((a,b,c),(d,e,f),...)
    allEntries = re.findall(patt,bigString)
    return allEntries

