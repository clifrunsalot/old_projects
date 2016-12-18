#!/usr/bin/env python

#
# COMPANY NAME: Raytheon Company
# COPYRIGHT: Copyright (c) 2008 Raytheon Company
# UNPUBLISHED WORK
# ALL RIGHTS RESERVED
# PROJECT NAME: FCS BCME
# CONTRACT NUMBER: 3EC1721
#
#-------------------------------------------------------------------------------
# Filename: utilities.py
#
# author  cbhuds  BCME-####
#
# version BCME-#### 2008-###-## Initial version.
#-------------------------------------------------------------------------------

import os, sys, time

class Util:
  """
  Class for misc utilities beyond logging.
  """

  def getTime(self):
    """
    Returns local time in yymmddhhmmss format.
    """
    (yr,mon,day,hr,min,sec,wdy,ydy,dst) = time.localtime()
    timenow = "%4d%02d%02d%02d%02d%02d" % (yr,mon,day,hr,min,sec)
    return timenow

  def translateDigitToStr(self, value):
    """
    Sets widget to true or false, depending on value.
    value - Digit to convert.
    """
    translated = 'False'
    if value == 1:
      translated = 'True'
    return translated

  def translateStrToDigit(self, obj, value):
    """
    Sets widget to true or false, depending on value.
    obj   - Checkbutton obj.
    value - True or False.
    """
    obj.setValue(0)
    if value.lower() == 'true':
      obj.setValue(1)

  def sortByNum(self, list):
    """
    Sorts list numerically.
	 list - A list that resembles [ xx_y, xx_z, xx_a, ... ]
	 sortList - A list that is sorted.
    """
	 # create a list with elements that are lists themselves.
    pairList = []
    
    # transform values of XX_YY into XXYY
    # and then reassign new value to hash key
    for i in list:
      a = i.split('_')
      if a[1] in ['1','2','3','4','5','6','7','8','9']:
        a[1] = '0'+str(a[1]) 
      b = ''.join(a)
      pairList.append([b,i])
    
    casted = []
    for v in pairList:
      k = int(v[0])
      casted.append([k,v[1]])
    
    casted.sort()

    sortedList = [ v[1] for v in casted ]

    return sortedList

