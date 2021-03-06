import os, sys, re, string, shutil

from tkinter import *
from tkinter.filedialog import *

def usage(e):
  """
  Usage: [python] ./chg_nm.py <opt>

  where <opt> is '-new' or '-mod'
  Use '-new' when you want to completely change the names of the files.
  Use '-mod' when you want to modify the existing names of the files.

  This program displays a GUI with options for changing the traits of filenames.
  """
  print ('{0}'.format(usage.__doc__))
  print('  ERROR: {0}'.format(e))

class NameChanger:
  """
  Functionality for basic filename manipulation.
  """

  def __init__(self, master, opt='-new'):
    """
    Use this to initialize the class.
    """
    self.root = master
    self.opt = opt
    self.varList = {}

    # Select window based on option
    if self.opt == '-new':
      self.root.title('Replace Filenames')
      self.createNewPanel()
    elif self.opt == '-mod':
      self.root.title('Modify Filenames')
      self.createModifyPanel()
    else:
      raise Exception('Invalid option')
    self.createBottomPanel()

  def createWidget(self, nm, wgt, rlf, wd, an, rw, cl, sty, px, py, txt=''):
    """
    Convenience method for building widgets.
    """
    if isinstance(wgt,Label):
      wgt.config(text=txt, relief=rlf, width=wd, anchor=an)
      wgt.grid(row=rw, column=cl, sticky=sty, padx=px, pady=py)
    elif isinstance(wgt,Entry):
      # Create string var for referencing Entry field.
      self.varList[nm] = StringVar()
      wgt.config(relief=rlf, width=wd, textvariable=self.varList[nm])
      wgt.grid(row=rw, column=cl, sticky=sty, padx=px, pady=py)

  def createNewPanel(self):
    """
    Displays GUI.
    """
    self.new = Frame(self.root)

    self.createWidget(None, Label(self.new), FLAT, 20, W, 0, 1, E, 5, 0, '1. Enter name:')
    self.createWidget('newname',Entry(self.new), SUNKEN, 20, None, 0, 2, NS, 2, 4, None)

    self.createWidget(None, Label(self.new), FLAT, 20, W, 1, 1, E, 5, 0, '2. Enter extension:')
    self.createWidget('newextension',Entry(self.new), SUNKEN, 20, None, 1, 2, NS, 2, 4, None)

    self.createWidget(None, Label(self.new), FLAT, 20, W, 2, 1, E, 5, 0, '3. Enter padding character:')
    self.createWidget('newpaddingcharacter',Entry(self.new), SUNKEN, 5, None, 2, 2, W, 2, 4, None)

    self.createWidget(None, Label(self.new), FLAT, 20, W, 3, 1, E, 5, 0, '4. Enter starting index:')
    self.createWidget('newstartingindex',Entry(self.new), SUNKEN, 5, None, 3, 2, W, 2, 4, None)

    self.createWidget(None, Label(self.new), FLAT, 20, W, 4, 1, E, 5, 0, '5. Enter ending index:')
    self.createWidget('newendingindex',Entry(self.new), SUNKEN, 5, None, 4, 2, W, 2, 4, None)

    self.createWidget(None, Label(self.new), FLAT, 20, W, 5, 1, E, 5, 0, '6. Select files:')
    self.newSelectFiles = Button(self.new, width=10, anchor=CENTER)
    self.newSelectFiles['text'] = 'Browse'
    self.newSelectFiles['command'] = self.browse
    self.newSelectFiles.grid(row=5, column=2, sticky=W, pady=5)

    self.new.grid(row=0, column=1, sticky=N, pady=10)

  def createModifyPanel(self):
    """
    Displays GUI.
    """

    self.mod = Frame(self.root)

    self.createWidget(None, Label(self.mod), FLAT, 35, W, 0, 1, W, 2, 0, '1. Enter characters to prepend to name:')
    self.createWidget('modprependname',Entry(self.mod), SUNKEN, 20, None, 0, 2, W, 8, 4, None)

    self.createWidget(None, Label(self.mod), FLAT, 35, W, 1, 1, W, 2, 0, '2. Enter characters to append to name:')
    self.createWidget('modappendname',Entry(self.mod), SUNKEN, 20, None, 1, 2, W, 8, 4, None)

    self.createWidget(None, Label(self.mod), FLAT, 35, W, 2, 1, W, 2, 0, '3. Enter characters to prepend to extension:')
    self.createWidget('modprependextension',Entry(self.mod), SUNKEN, 20, None, 2, 2, W, 8, 4, None)

    self.createWidget(None, Label(self.mod), FLAT, 35, W, 3, 1, W, 2, 0, '4. Enter characters to append to extension:')
    self.createWidget('modappendextension',Entry(self.mod), SUNKEN, 20, None, 3, 2, W, 8, 4, None)

    self.createWidget(None, Label(self.mod), FLAT, 35, W, 4, 1, W, 2, 0, '5. Enter padding character:')
    self.createWidget('modpaddingcharacter',Entry(self.mod), SUNKEN, 5, None, 4, 2, W, 8, 4, None)

    self.createWidget(None, Label(self.mod), FLAT, 35, W, 5, 1, W, 2, 0, '6. Enter starting index:')
    self.createWidget('modstartingindex',Entry(self.mod), SUNKEN, 5, None, 5, 2, W, 8, 4, None)

    self.createWidget(None, Label(self.mod), FLAT, 35, W, 6, 1, W, 2, 0, '7. Enter ending index:')
    self.createWidget('modendingindex',Entry(self.mod), SUNKEN, 5, None, 6, 2, W, 8, 4, None)

  # def createRadiobutton(self, master, txt, r, c, px, py, rVar, val):
    self.createWidget(None, Label(self.mod), FLAT, 35, W, 7, 1, W, 2, 0, '8. Change name lettecase:')
    self.nameLcFrame = Frame(self.mod)
    self.nameLcFrame.grid(row=7, column=2)
    self.nameCaseVar= StringVar()
    modNameUcRb = self.createRadiobutton(self.nameLcFrame, 'Uppercase', 0, 0, 0, 0, self.nameCaseVar, 'uc')
    modNameLcRb = self.createRadiobutton(self.nameLcFrame, 'Lettercase', 0, 1, 0, 0, self.nameCaseVar, 'lc')
    self.nameCaseVar.set('uc')

    self.createWidget(None, Label(self.mod), FLAT, 35, W, 8, 1, W, 2, 0, '8. Change ext lettecase:')
    self.extLcFrame = Frame(self.mod)
    self.extLcFrame.grid(row=8, column=2)
    self.extCaseVar= StringVar()
    modExtUcRb = self.createRadiobutton(self.extLcFrame, 'Uppercase', 0, 0, 0, 0, self.extCaseVar, 'uc')
    modExtLcRb = self.createRadiobutton(self.extLcFrame, 'Lettercase', 0, 1, 0, 0, self.extCaseVar, 'lc')
    self.extCaseVar.set('uc')

    self.createWidget(None, Label(self.mod), FLAT, 35, W, 9, 1, W, 2, 0, '10. Select files:')
    self.modSelectFiles = Button(self.mod, width=10, anchor=CENTER)
    self.modSelectFiles['text'] = 'Browse'
    self.modSelectFiles['command'] = self.browse
    self.modSelectFiles.grid(row=9, column=2, sticky=W, pady=5)

    self.mod.grid(row=0, column=1, sticky=N, pady=10)

  def createBottomPanel(self):
    """
    Displays button panel.
    """
    self.bottom = Frame(self.root)

    self.rst = Button(self.bottom, width=10, anchor=CENTER)
    self.rst['text'] = 'Execute'
    self.rst['command'] = self.execute
    self.rst.grid(row=0, column=1, pady=5)

    self.exit = Button(self.bottom, width=10, anchor=CENTER)
    self.exit['text'] = 'Reset'
    self.exit['command'] = self.reset
    self.exit.grid(row=0, column=2, pady=5)

    self.exit = Button(self.bottom, width=10, anchor=CENTER)
    self.exit['text'] = 'Exit'
    self.exit['command'] = self.root.quit
    self.exit.grid(row=0, column=3, pady=5)

    self.bottom.grid(row=1, columnspan=3)

  def execute(self):
    """
    Resets all fields.
    """
    print('Executing changes')
    self.getSelectedFiles()

  def reset(self):
    """
    Resets all fields.
    """
    for k, v in self.varList.items():
      self.varList[k].set('')

  def getVars(self):
    """
    Returns entry fields values.
    """
    for k, v in self.varList.items():
      print(self.varList[k].get())

  def validate(self):
    """
    Validates all entries.
    """
    print('Validating')

  def createScrollBars(self, master):
    """
    Creates scrollable listbox.
    """
    y = Scrollbar(master, orient=VERTICAL)
    y.grid(row=0, column=1, sticky=N+S)
    x = Scrollbar(master, orient=HORIZONTAL)
    x.grid(row=1, column=0, sticky=E+W)
    return (x,y)

  def createPathEntry(self, master, dir):
    """
    Creates entry widget for current folder.
    """
    val = StringVar()
    val.set(dir)
    dirPathEntry = Entry(master, text=val, width=50)
    dirPathEntry.grid(row=0, columnspan=3)

  def createRadiobutton(self, master, txt, r, c, px, py, rVar, val):
    """
    Creates a couple of checkbuttons.
    """
    rb = Radiobutton(master, text=txt, variable=rVar, value=val)
    rb.grid(row=r, column=c, padx=px, pady=py)
    return rb

  def createCheckbutton(self, master, txt, r, c, px, py):
    """
    Creates a couple of checkbuttons.
    """
    cb = Checkbutton(master, text=txt)
    cb.grid(row=r, column=c, padx=px, pady=py)
    return cb

  def createOptions(self, master):
    """
    Creates options panel.
    """
    # Add options to show files and folders
    self.filesCbVar = IntVar()
    self.filesCb = self.createCheckbutton(master, 'Files', 1, 0, 0, 0)
    self.filesCb.config(variable=self.filesCbVar, command=self.filterList)
    self.filesCbVar.set(1)
    self.dirsCbVar = IntVar()
    self.dirsCb = self.createCheckbutton(master, 'Dirs', 1, 1, 0, 0)
    self.dirsCb.config(variable=self.dirsCbVar, command=self.filterList)
    self.dirsCbVar.set(1)
    self.linksCbVar = IntVar()
    self.linksCb = self.createCheckbutton(master, 'Links', 1, 2, 0, 0)
    self.linksCb.config(variable=self.linksCbVar, command=self.filterList)
    self.linksCbVar.set(1)

  def createFileListWindow(self):
    """
    Displays list of files from which to select.
    """
    self.listFrame = Frame(self.root, padx=10, pady=10)

    # Add place to display current folder.
    self.createPathEntry(self.listFrame, self.dirName)

    # Add panel for selectable display options.
    self.createOptions(self.listFrame)

    # Build the listbox
    self.listInnerFrame = Frame(self.listFrame)
    self.listContents = StringVar()
    (xSB, ySB) = self.createScrollBars(self.listInnerFrame)
    self.listBox = Listbox(self.listInnerFrame, xscrollcommand=xSB.set,
        yscrollcommand=ySB.set, listvariable=self.listContents, width=50,
        height=20, selectmode=EXTENDED)
    self.listBox.grid(row=0, column=0, sticky=N+S+E+W)
    xSB['command'] = self.listBox.xview
    ySB['command'] = self.listBox.yview

    self.listInnerFrame.grid(row=2, columnspan=3, sticky=N+S+E+W)

    # Insert list into listbox.
    self.filterList()
    self.listFrame.grid(row=0, column=2)

  def filterList(self):
    """
    Refreshes file list based on options selected.
    """
    self.refreshList(self.dirName, self.dirsCbVar.get(), 
        self.filesCbVar.get() == 1, self.linksCbVar.get() == 1)

  def refreshList(self, folder, showDirs=True, showFiles=True, showLinks=True):
    """
    Queries folder for list of files objects.
    """
    # Verify that object is a hard file or dir, not a link,
    # and then add to a list.
    tmpList = []
    for f in os.listdir(folder):
      nFull = os.path.join(folder, f)
      if os.path.isdir(nFull) and showDirs:
        tmpList.append(f)
      if os.path.isfile(nFull) and showFiles:
        tmpList.append(f)
      if os.path.islink(nFull) and showLinks:
        tmpList.append(f)

    # Temporarily replace spaces in filename with '^' and
    # then add to the list going into the listbox.
    retList = []
    for f in tmpList:
      fNew = re.sub(' ','^',f)
      retList.append(fNew)

    self.listContents.set(' '.join(retList))

  def browse(self):
    """
    Opens file browser.
    """
    print('Browsing')
    self.dirName = askdirectory()
    
    print('Loading file list')
    self.createFileListWindow()

  def getSelectedFiles(self):
    """
    Returns list of selected files from listbox.
    """
    indices = map(int, self.listBox.curselection())
    selects = [ self.listBox.get(i) for i in indices ]
    for s in selects:
      print(s)

if __name__ == "__main__":

#  print({0},(sys.argv))
  try:
    if len(sys.argv) == 2:
      root = Tk()
      nc = NameChanger(root, sys.argv[1])
      root.mainloop()
    else:
      raise Exception("Invalid number of arguments" )
  except Exception as e:
    usage(e)

