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
# Filename: xmlparser.py
#
# author  cbhuds	BCME-####
#
# version BCME-####	2008-###-##	Initial version.
#-------------------------------------------------------------------------------

# Core libraries
from sys import *
from xml.dom import minidom
from xml.parsers.expat import *

class FileError(Exception):
    """
    Generic exception class for any kind of file error.
    """
    def __init__(self, message):
	import sys
        print "ERROR: %s" % message
        sys.exit()

class Parser:
    """
    Module for reading and writing xml config files.
    filename - Name of xml file.
    """
    def __init__(self, filename = None):
        if filename is not None:
            self.filename = filename
            self.objects = None
            self.loadFile()
        else:
            raise FileError, "Filename cannot be null"

    def loadFile(self):
        """
        Method loads xml file passed into Parser constructor
        """
        try:
            self.objects = minidom.parse(self.filename)
        except IOError, strerror:
            raise FileError, strerror
        except ExpatError, error :
			msg = ErrorString(error.code) + " at line " + str(error.lineno) + ", col " + str(error.offset)
			raise FileError, msg
        return self.objects

    def writeFile(self, xmlStr, outfile):
        """
        Method writes xml file passed into Parser constructor.
        xmlStr - String of xml to write out to file.
        outfile - Output file.
        """
        try:
            f = open(outfile, 'w')
            f.write(xmlStr)
            f.close()
        except IOError, strerror:
            raise FileError, strerror

    
