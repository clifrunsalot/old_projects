'''
Created on Jul 24, 2010

@author: clif
'''
# Python
import os, string, sys, getopt

# List of options passed in.
lst = {}

def usage():
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
    print usage.__doc__
    exit(0)

def isValidOpts(lst):
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

def myfun(start, dirs, list):
    for nm in list:
        fullpath = os.path.join(dirs, nm)
        if os.path.isdir(fullpath):
            isValid(fullpath)
        else:
            for k in lst.keys():
                if k == 'pre':
                    pre = lst['pre'] + nm
                    newfullpath = os.path.join(dirs, pre)
                    os.rename(fullpath, newfullpath)
                    fullpath = newfullpath
                if k == 'app':
                    nm = os.path.basename(fullpath)
                    app = nm + lst['app']
                    newfullpath = os.path.join(dirs, app)
                    os.rename(fullpath, newfullpath)
                    fullpath = newfullpath
                if k == 'repl':
                    nm = os.path.basename(fullpath)
                    (old,new1) = string.split(lst['repl'], ',')
                    if new1 == 'None':
                        nw = string.replace(nm, old,'')
                    else:
                        nw = string.replace(nm, old, new1)
                    newfullpath = os.path.join(dirs, nw)
                    os.rename(fullpath, newfullpath)
                    fullpath = newfullpath
            isValid(fullpath)

def displayDirs(start):            
    os.path.walk(start, myfun, None)
    
def isValid(p):
    valid = 'Invalid'
    if os.path.exists(p):
        valid = 'Valid'
    print valid, ' - ', p

if __name__ == "__main__":
    print sys.argv
    valid = None
    lst = {}
    (valid, lst) = isValidOpts(sys.argv[1:])
    if valid:
        displayDirs(lst['dir'])
    else:
        usage()

            
