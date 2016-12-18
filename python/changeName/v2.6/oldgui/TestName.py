#!/usr/bin/env python

import unittest
from test import test_support
from changeName import MyNameTrait, MyFilename

class TestNameTrait(unittest.TestCase):
  def setUp(self):
    pass

  def tearDown(self):
    pass

  def test_init(self):
    nt = MyNameTrait('clifhudson')
    self.assertNotEqual(nt, None)

  def test_setValue(self):
    nt = MyNameTrait('clif')
    self.assertEqual('clif', nt.getValue())
    nt.setValue('changed')
    self.assertEqual('changed', nt.getValue())
    self.assertNotEqual('change', nt.getValue())

  def test_getValue(self):
    nt = MyNameTrait('clif')
    self.assertEqual('clif', nt.getValue())

  def test_toLower(self):
    nt = MyNameTrait('CLIF')
    nt.toLower()
    self.assertEqual('clif', nt.getValue())

  def test_toUpper(self):
    nt = MyNameTrait('clif')
    nt.toUpper()
    self.assertEqual('CLIF', nt.getValue())

  def test_replace(self):
    nt = MyNameTrait('clif')
    nt.replace('i','x')
    self.assertEqual('clxf', nt.getValue())




    
