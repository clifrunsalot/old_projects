#!/usr/bin/env python

from Tkinter import *

class Application(Frame):

  def __init__(self, master=None):
    Frame.__init__(self, master)
    self.grid(sticky=N+S+E+W)
    self.createWidgets2()

  def createWidgets(self):
    self.quitButton = Button(self)
    self.quitButton['text'] ='Quit'
    self.quitButton['command'] = self.quit
    self.quitButton.grid()

  def createWidgets2(self):
    top = self.winfo_toplevel()
    top.rowconfigure(1, weight=1)
    top.columnconfigure(0, weight=1)
    self.rowconfigure(0, weight=1)
    self.columnconfigure(0, weight=1)
    self.quitButton = Button(self)
    self.quitButton['text'] ='Quit'
    self.quitButton['command'] = self.quit
    self.quitButton.grid(row=0, column=0, sticky=N+S+E+W)

app = Application()
app.master.title('Sample Application')
app.mainloop()

    
