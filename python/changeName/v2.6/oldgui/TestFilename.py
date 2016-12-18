import unittest
from test import test_support
from changeName import MyNameTrait, MyFilename

class TestFilename(unittest.TestCase):
  def setUp(self):
    pass

  def tearDown(self):
    pass

  def test_init(self):
    f = MyFilename(fn='clif',ex='hudson')
    self.assertNotEqual(f.getValue(),None)

  def test_setValue(self):
    f = MyFilename(fn='clif',ex='hudson')
    orig = f.getValue()
    self.assertEqual(orig, 'clif.hudson')
    f.setValue(fn='mac',ex='knife')
    self.assertNotEqual(orig,f.getValue())
    self.assertEqual(f.getValue(),'mac.knife')

  def test_setValue2(self):
    f = MyFilename(fn='clif',ex='')
    self.assertEqual(f.getValue(), 'clif')

  def test_toLower(self):
    f = MyFilename(fn='CLIF',ex='HUDSON')
    self.assertEqual('CLIF.HUDSON',f.getValue())
    f.toLower()
    self.assertEqual('clif.hudson',f.getValue())

  def test_toUpper(self):
    f = MyFilename(fn='clif',ex='hudson')
    self.assertEqual('clif.hudson',f.getValue())
    f.toUpper()
    self.assertEqual('CLIF.HUDSON',f.getValue())

  def test_prependName(self):
    f = MyFilename(fn='clif',ex='hudson')
    f.prependName(ch='^^^^^^')
    self.assertEqual('^^^^^^clif.hudson',f.getValue())

  def test_appendName(self):
    f = MyFilename(fn='clif',ex='hudson')
    f.appendName(ch='^^^^^^')
    self.assertEqual('clif^^^^^^.hudson',f.getValue())

  def test_getName(self):
    f = MyFilename(fn='clif',ex='hudson')
    self.assertEqual(f.getName().getValue(),'clif')

  def test_getExt(self):
    f = MyFilename(fn='clif',ex='hudson')
    self.assertEqual(f.getExt().getValue(),'hudson')

  def test_replace(self):
    f = MyFilename(fn='clif',ex='hudson')
    f.replace('u','x')
    self.assertEqual('clif.hxdson',f.getValue())




    
