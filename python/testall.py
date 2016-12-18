#!/usr/bin/env python

import unittest, os, sys
from test import test_support

# Add paths here
sys.path.append('/Home/cbhuds/projects/python/changeName')

# Include imported test classes here
from TestName import TestNameTrait
from TestFilename import TestFilename

# Add Test Classes here
def suite():
  suite = unittest.TestSuite()
  suite.addTest(unittest.makeSuite(TestNameTrait))
  suite.addTest(unittest.makeSuite(TestFilename))
  return suite

# Run tests

if __name__ == '__main__':
  unittest.TextTestRunner(verbosity=2).run(suite())


