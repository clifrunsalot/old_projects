#echo-server.py

from socket import *
import re
import sys

# default host and port
myHost = 'localhost'
myPort = 50007

# Prep socket object
def setup(myHost, myPort):
    try:
        pport = int(myPort)
    except:
        usage()
        exit()
    sockobj = socket(AF_INET, SOCK_STREAM)
    sockobj.bind((myHost, int(myPort)))
    sockobj.listen(5)
    return sockobj

# Greps log for string
def grepLog(str):
        logFile = ".\\tmp_log.txt"
        list = []
        matchObj = re.compile('.*' + str + '.*')
        for line in open(logFile).readlines():
                found = matchObj.search(line)
                if found:
                        list.append(line)
#        print list
        return list

# Displays usage error
def usage():
    print ""
    print "Usage: echo-server <host> <port>"
    print "example: echo-server localhost 50007"
    print ""

# starts loop to handle socket connection activity
def start(sckt):
    end = 0
    while not end:
        connection, address = sckt.accept()
        print 'Server connected by', address
        while not end:
            data = connection.recv(1024)
            if not data: break
            print "data: " + data
            if re.compile("\AEXIT\Z").match(data):
                end = 1
            elif re.compile("\AGREPLOG:.*\Z").match(data):
                    str = ((data.split(':', 2))[1]).strip()
                    data = grepLog(str)
            else:
                    print "Bad choice"
                    break
            if len(data) > 0:
                    msg = '\n'.join(data)
                    print msg
                    connection.send(msg)
        connection.close()
    exit()

# main process
if __name__ and '__main__':
    if len(sys.argv) < 3:
        usage()
    else:
        socket = setup(sys.argv[1], sys.argv[2])
        if socket:
            start(socket)
        else:
            usage()


