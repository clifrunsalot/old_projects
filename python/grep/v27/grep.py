'''
Created on Jul 29, 2010

@author: cbhuds
'''
import getopt, os, sys, string, re

def usage():
    '''
    Usage: python Grep --dir=<value> --str=<find>
    
    where 'value' is the start directory. The default is the current directory.
            'find' is the search string
            
    The search is always case insensitive.
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
        optList, leftover = getopt.getopt(lst, None, ['dir=', 'str='])
        for e in optList:
            if e[0] == '--dir' and e[1] is not None:
                retHash['dir'] = e[1]
                valid = True
            elif e[0] == '--str' and e[1] is not None:
                retHash['str'] = e[1]
                valid = True
            if not valid:
                break
    except Exception, e:
        raise e

    return (valid, retHash)

class Grep():
    '''
    Returns list of files containing string.
    '''
    
    def __init__(self, searchStr, startDir):
        '''
        Class initializer.
        '''
        self.searchStr = searchStr
        self.startDir = startDir
        self.resultFile = None
        self.found = []
        
    def writeResults(self):
        '''
        Gathers list of files.
        '''
        if len(self.found) > 0:
            usershome = os.getenv('HOME')
            archive = usershome + os.sep + 'grep.txt'
            self.resultFile = open(archive,'w')
            out = self.resultFile
            fileCnt = 0
            lineCnt = 0
            for f in self.found:
                if 'FILE' in f: fileCnt = fileCnt + 1
                if 'LINE' in f: lineCnt = lineCnt + 1
                out.write(f + '\n')
            out.close()
            print 'Files found: %i' % (fileCnt)
            print 'Lines found: %i' % (lineCnt)
            
        else:
            print "Search string was not found"
    
    def walkDirs(self, start, dirs, list):
        '''
        Traverses down the directory tree.
        '''
        try:
            # Setup regular expression
            # Capture whole line if string is found
            entireLine = ".*" + self.searchStr + ".*"
            patt = re.compile(entireLine, re.IGNORECASE)
            if dirs is not None:
                for f in list:
                    fullpath = os.path.join(dirs, f)
                    if os.path.isfile(fullpath):
                        found = patt.search(fullpath)
                        if found:
                            assemble = 'File: ' + string.strip(fullpath)
                            print assemble
                            self.found.append(assemble)
                        iter = patt.finditer(open(fullpath).read())
                        if iter:
                            self.found.append(fullpath)
                            for match in iter:
                                assemble = 'Line: ' + string.strip(match.group(0))
                                print assemble
                                self.found.append(assemble)
        except Exception, e:
            raise e 
    
    def search(self):
        '''
        Invokes os.path.walk.
        '''
        os.path.walk(self.startDir, self.walkDirs, None);

if __name__ == '__main__':
    print sys.argv
    usershome = os.getenv('HOME')
    archive = usershome + os.sep + 'grep.txt'
    print archive
    try:
        (valid, rtn) = isValidOpts(sys.argv[1:])
        if valid:
            app = Grep(rtn['str'], rtn['dir'])
            app.search()
            app.writeResults()
    except Exception, e:
        usage()
        
