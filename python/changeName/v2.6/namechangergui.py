'''
Created on Jul 25, 2010

@author: clif
'''

from Tkconstants import *
from Tkinter import *
from quitter import Quitter
from tkFileDialog import Directory
from com.clif.utils.file.namechanger import NameChangerConsole

entryValue = None

class Application(Frame):
    '''
    Tests GUI.
    '''
    def __init__(self, parent=None):
        Frame.__init__(self, parent)
        self.configure(height=20, width=100)
        self.grid(sticky=N+S+E+W)
        self.dirname = None
        self.dirVar = StringVar()
        self.prependEntry = None
        self.appendEntry = None
        self.findEntry = None
        self.replaceEntry = None
        self.prependVar = StringVar()
        self.appendVar = StringVar()
        self.findVar = StringVar()
        self.replaceVar = StringVar()
        self.executeBtn = None
        self.resetBtn = None
        self.quitBtn = None
        self.browser = None
        self.makeWidgets(parent) 
    
    def createButton(self, parent, txt, func, rw, cl, stky, clr):
        btn = Button(parent, text=txt, command=func, relief=RAISED, bg=clr).grid(row=rw, column=cl, sticky=stky)
        return btn
    
    def createLabel(self, parent, txt, rw, cl, stky):
        Label(parent, text=txt, width=10, relief=RIDGE).grid(row=rw, column=cl, sticky=stky)

    def createEntry(self, parent, varHolder, rw, cl, stky):
        ent = Entry(parent, textvariable=varHolder).grid(row=rw, column=cl, sticky=stky)
        return ent

    def createBrowser(self, parent, anc):
        # Directory defaults
        self.file_opt = options = {}
        options['filetypes'] = [('all files', '.*')]
        options['initialdir'] = 'C:\\'
        options['parent'] = self
        return Button(parent, text='Browse', command=self.browse, bg='grey').grid(row=0, column=0, sticky=N+S+E+W)
    
    def browse(self):
        opendlg = Directory()
        self.dirname = opendlg.show()
        self.dirVar.set(self.dirname)
        
        return self.usageMsg.__doc__
    
    def execute(self):
        ncc = NameChangerConsole()
        try:
            ncc.setPrependStr(self.prependVar.get())
            ncc.setAppendStr(self.appendVar.get())
            ncc.setFindReplaceStr(self.findVar.get(), self.replaceVar.get())
            ncc.setStartDir(self.dirname)
            ncc.displayDirs(self.dirname)
        except Exception, e:
            msg = []
            msg.append('The following fields must be filled:\n')
            msg.append('   Prepend                          \n')
            msg.append('        and/or                      \n')
            msg.append('   Append                           \n')
            msg.append('        and/or                      \n')
            msg.append('   Find plus Replace                \n')
            msg.append('        or                          \n')
            msg.append('   all fields                       \n')
            error = Frame()
            Button(error, command=error.quit)
            Label(error, text='test')
            error.mainloop()
        
    def reset(self):
        self.prependVar.set('')
        self.appendVar.set('')
        self.findVar.set('')
        self.replaceVar.set('')
        
    def makeWidgets(self, parent):
        # Makes top window resizable
        top=self.winfo_toplevel()
        top.rowconfigure(0, weight=1)
        top.columnconfigure(0, weight=1)
        top.columnconfigure(1, weight=7)
        self.rowconfigure(0, weight=1)
        self.columnconfigure(0, weight=1)
        self.columnconfigure(1, weight=7)
        # Browser controls
        self.browser = self.createBrowser(parent, N+S+E+W)
        self.dirEntry = Entry(parent, textvariable=self.dirVar).grid(row=0, column=1, columnspan=2, sticky=N+S+E+W)
        # Prepend controls
        self.createLabel(parent, 'Prepend: ', 1, 0, N+S+E+W)
        self.prependEntry = self.createEntry(parent, self.prependVar, 1, 1, N+S+E+W)
        # Append controls
        self.createLabel(parent, 'Append: ', 2, 0, N+S+E+W)
        self.appendEntry = self.createEntry(parent, self.appendVar, 2, 1, N+S+E+W)
        # Find controls
        self.createLabel(parent, 'Find: ', 3, 0, N+S+E+W)
        self.findEntry = self.createEntry(parent, self.findVar, 3, 1, N+S+E+W)
        # Reset controls
        self.resetBtn = self.createButton(parent, 'Reset', self.reset, 3, 2, N+S+E+W, 'blue')
        # Replace controls
        self.createLabel(parent, 'Replace: ', 4, 0, N+S+E+W)
        self.replaceEntry = self.createEntry(parent, self.replaceVar, 4, 1, N+S+E+W)
        # Execute controls
        self.executeBtn = self.createButton(parent, 'Execute', self.execute, 4, 2, N+S+E+W, 'green')
        # Quit controls
        self.quitBtn = self.createButton(parent, 'Exit', self.quitApp, 5, 2, N+S+E+W, 'red')
        
    def quitApp(self):
        self.quit()
       
if __name__ == '__main__':
#    try:
    app = Application()
    app.master.title('Filename Changer')
    app.mainloop()
#    except Exception, e:
#        print e
        
