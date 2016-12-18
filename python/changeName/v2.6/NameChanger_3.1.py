'''
Created on Jul 24, 2010

@author: clif
'''
# Python
import os, string, sys, getopt

# List of options passed in.
lst = {}

def usage(Exception):
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
    print(usage.__doc__)
    print(eval(e))
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
    except Exception as e:
        raise e

    return (valid, retHash)

def isValid(p):
    result = 'Invalid'
    if os.path.exists(p):
        result = 'Valid'
    print('{0} - {1}'.format(result, p))

def walkit(start):
    try:
        for (start, subdirs, files) in os.walk(start):
            for d in subdirs:
                dirpath = os.path.join(start, d)
                print('[' + dirpath + ']')
                isValid(dirpath)

            for f in files:
                fullpath = os.path.join(start, f)
                for k in lst.keys():
                    if k == 'pre':
                        f = os.path.basename(fullpath)
                        pre = lst['pre'] + f
                        newfullpath = os.path.join(start, pre)
                        os.rename(fullpath, newfullpath)
                        fullpath = newfullpath
                    if k == 'app':
                        f = os.path.basename(fullpath)
                        app = f + lst['app']
                        newfullpath = os.path.join(start, app)
                        os.rename(fullpath, newfullpath)
                        fullpath = newfullpath
                    if k == 'repl':
                        f = os.path.basename(fullpath)
                        (old, new1) = str.split(lst['repl'], ',')
                        if new1 == 'None':
                            nw = str.replace(f, old, '')
                        else:
                            nw = str.replace(f, old, new1)
                        newfullpath = os.path.join(start, nw)
                        os.rename(fullpath, newfullpath)
                        fullpath = newfullpath
                print(fullpath)
                isValid(fullpath)

    except Exception as e:
        raise e

if __name__ == "__main__":
    print(sys.argv)
    try:
        valid = None
        lst = {}
        (valid, lst) = isValidOpts(sys.argv[1:])
        if valid:
            walkit(lst['dir'])
        else:
            usage()
    except Exception as e:
        usage()
        print(eval(e))

            
