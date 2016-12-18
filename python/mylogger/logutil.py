import os, sys, logging

class MyLogger:
  """
  Creates a generic use logger.

  The class is meant to be a Generic Logger. To use the class, do the following:

  (1) Append the class file to the script's existing PATH value with something like:
      "sys.path.append('/Home/cbhuds/projects/python/mylogger')"

  (2) Add an import like:
      "from logutil import MyLogger"

  (3) Instantiate a logger with:
      "mLog = MyLogger()", if you want the default log level INFO and log file location of current dir.

            or

      "mLog = MyLogger('<full pathname to log>', logging.<LEVEL>)"

  (4) To log an entry, do:
      "mLog.log(<msg>)"

  """

  def __init__(self, loc='./app.log', lvl=logging.INFO):
    """
    Initializes the class.
    """
    try:
      self.logger = logging.getLogger('mylogger')
      self.hdlr = logging.FileHandler(loc)
      self.formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
      self.hdlr.setFormatter(self.formatter)
      self.logger.addHandler(self.hdlr)
      self.logger.setLevel(lvl)
    except Exception, e:
      #raise Exception, 'ERROR: Please see usage for this class'
      print MyLogger.__doc__

  def log(self,msg):
    """
    Logs msg.
    """
    self.logger.log(self.logger.level, msg)

  def setLevel(self,l):
    """
    Sets log level.
    """
    self.logger.setLevel(l)

  def getLogger(self):
    """
    Returns logger.
    """
    return self.logger

