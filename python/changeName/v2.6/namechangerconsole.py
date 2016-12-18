'''
Created on Jul 24, 2010

@author: clif
'''
import os, string, sys, getopt

class NameChangerConsole():
    
    def __init__(self, parent=None):
        self.lst = {}
        
    def usage(self):
        '''
        Usage: namechanger.py <--dir=value> [ --option=value ]
        
        where dir and value is the directory to start the search and
        option may be:
            pre and value is the text to prepend to the filename or
            app and value is the text to append to the filename or
            repl and value consists of two comma separated sets of chars:
                first set are contiguous chars to remove and
                the second set are contiguous chars to insert.
                
        Example:
            python NameChanger.py --dir=c:\\temp \\
                                    --app=attheend \\ 
                                    --pre=atthefront \\ 
                                    --repl=attheend,atthefront
            
            This script will start in the 'c:\\temp' directory, 
                append 'attheend' to every filename,
                prepend 'atthefront to every filename,
                repl every instance of 'attheend' with 'atthefront'
            
            The --app, --pre, and --repl can be independently executed.
            This script will recursively descend through all subdirectories.
            
        '''
        print self.usage.__doc__
        exit(0)
    
    def isValidOpts(self, lst):
        """
        Validates list of command line options.
        """
        optList = []
        retHash = {}
        leftover = []
        valid = False
        try:
            optList, leftover = getopt.getopt(lst, None, ['dir=', 'pre=', 'app=', 'repl='])
            for e in optList:
                if e[0] == '--dir' and e[1] is not None:
                    retHash['dir'] = e[1]
                    valid = True
                elif e[0] == '--pre' and e[1] is not None:
                    retHash['pre'] = e[1]
                    valid = True
                elif e[0] == '--app' and e[1] is not None:
                    retHash['app'] = e[1]
                    valid = True
                elif e[0] == '--repl' and e[1] is not None:
                    retHash['repl'] = e[1]
                    valid = True
                if not valid:
                    break
        except Exception, e:
            raise e
    
        return (valid, retHash)
    
    def walkDirs(self, start, dirs, list):
        for nm in list:
            fullpath = os.path.join(dirs, nm)
            if os.path.isdir(fullpath):
                self.isValid(fullpath)
            else:
                for k in self.lst.keys():
                    if k == 'pre':
                        pre = self.lst['pre'] + nm
                        newfullpath = os.path.join(dirs, pre)
                        os.rename(fullpath, newfullpath)
                        fullpath = newfullpath
                    if k == 'app':
                        nm = os.path.basename(fullpath)
                        app = nm + self.lst['app']
                        newfullpath = os.path.join(dirs, app)
                        os.rename(fullpath, newfullpath)
                        fullpath = newfullpath
                    if k == 'repl':
                        nm = os.path.basename(fullpath)
                        (old,new1) = string.split(self.lst['repl'], ',')
                        if new1 == 'None':
                            nw = string.replace(nm, old,'')
                        else:
                            nw = string.replace(nm, old, new1)
                        newfullpath = os.path.join(dirs, nw)
                        os.rename(fullpath, newfullpath)
                        fullpath = newfullpath
                self.isValid(fullpath)
    
    def displayDirs(self, start):            
        os.path.walk(start, self.walkDirs, None)
        
    def isValid(self, p):
        valid = 'Invalid'
        if os.path.exists(p):
            valid = 'Valid'
        print valid, ' - ', p
        
    def setStartDir(self, dir):
        self.lst['dir'] = dir
        
    def setPrependStr(self, pre):
        self.lst['pre'] = pre
        
    def setAppendStr(self, app):
        self.lst['app'] = app
        
    def setFindReplaceStr(self, fnd, repl):
        self.lst['repl'] = fnd + ',' + repl
        
if __name__ == "__main__":
    print sys.argv
    app = NameChangerConsole()
    valid = None
    lst = {}
    (valid, lst) = app.isValidOpts(sys.argv[1:])
    if valid:
        app.displayDirs(lst['dir'])
    else:
        app.usage()
