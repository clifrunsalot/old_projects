#!/usr/bin/env python

import os, sys, re, string, exceptions, shutil

sys.path.append('/Home/cbhuds/projects/python/mylogger')

from logutil import MyLogger

class MyNameTrait:
  """
  Encapsulates attributes and behavior of a string.
  """

  def __init__(self, val=''):
    """
    Initializes object.
    """
    self.value = val

  def setValue(self, newName):
    """
    Sets name.
    """
    self.value = newName

  def getValue(self):
    """
    Returns name.
    """
    return self.value

  def toLower(self):
    """
    Return the value in lowercase.
    """
    self.value = self.value.lower()
    return self

  def toUpper(self):
    """
    Return the value in uppercase.
    """
    self.value = self.value.upper()
    return self

  def replace(self, old='', new=''):
    """
    Replaces old with new by delegating call to string library.
    """
    self.value = self.value.replace(old,new)
    return self

class MyFilename:
  """
  Encapsulates attributes and behaviors associated with a complete filename.
  """

  def __init__(self, fn='', ex=''):
    """
    Initializes this object.
    """
    self.name = MyNameTrait(fn)
    self.ext = MyNameTrait(ex)

  def toLower(self):
    """
    Sets filename to lowercase.
    """
    self.name.toLower()
    self.ext.toLower()

  def toUpper(self):
    """
    Sets filename to uppercase.
    """
    self.name.toUpper()
    self.ext.toUpper()

  def getValue(self):
    """
    Returns filename.
    """
    fn = ''
    if self.ext.getValue().strip() != '':
      fn = '%s.%s' % (self.name.getValue(), self.ext.getValue())
    else:
      fn = self.name.getValue()
    return fn 

  def setValue(self, fn='', ex=''):
    """
    Sets name and extention of filename.
    """
    self.name = MyNameTrait(fn)
    self.ext = MyNameTrait(ex)

  def prependName(self, ch='_'):
    """
    Prepends the character(s) ch to self.name.
    """
    newName = '%s%s' % (ch, self.name.getValue())
    self.name = MyNameTrait(newName)

  def appendName(self, ch='_'):
    """
    Appends the character(s) ch to self.name.
    """
    newName = '%s%s' % (self.name.getValue(),ch)
    self.name = MyNameTrait(newName)

  def getName(self):
    """
    Returns name object.
    """
    return self.name

  def getExt(self):
    """
    Returns extention object.
    """
    return self.ext

  def replace(self,old='',new=''):
    """
    Delegates replacement to standard string function.
    """
    self.name.replace(old,new)
    self.ext.replace(old,new)
    return self


