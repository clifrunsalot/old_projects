#!/usr/bin/env python
from Tkinter import *
from logging import MyLogger

logger = logging.getLogger('myapp')
hdlr = logging.FileHandler('./clifs.log')
formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
hdlr.setFormatter(formatter)
logger.addHandler(hdlr)
logger.setLevel(logging.INFO)

class Application(Frame):

  def __init__(self, master=None):
    Frame.__init__(self,master)
    self.pack()
    self.createWidgets()

  def say_hi(self):
    print "hi there, everyone!"
    logger.info('hello, everyone')

  def createWidgets(self):
    self.QUIT = Button(self)
    self.QUIT["text"] = "QUIT"
    self.QUIT["fg"] = "red"
    self.QUIT["command"] = self.quit
    self.QUIT.pack({"side":"left"})
    self.hi_there = Button(self)
    self.hi_there["text"] = "Hello"
    self.hi_there["command"] = self.say_hi
    self.hi_there.pack({"side":"left"})

app = Application()
app.mainloop()
