#echo-client.py

import sys
from socket import *

# default host and port
serverHost = 'localhost'
serverPort = 50007

# displays usage error
def usage():
        print ""
        print "Usage: echo-client <host> <port> <message>"
        print "example: echo-server localhost 50007 This is a test message"
        print ""

# sets up host and port
def setup(host, port):
        sockobj = socket(AF_INET, SOCK_STREAM)
        sockobj.connect((serverHost, serverPort))
        return sockobj

# sends message
def sendMsg(sckt, msg):
        sckt.send(msg.strip())
        data = sckt.recv(1024)
        print repr(data)
        sckt.close()
        exit()

# handles command line args
def processArgs(args):
        message = ''
        if len(args) > 2:
                serverHost = args[1]
                try:
                        serverPort = int(args[2])
                except:
                        usage()
                        exit()
                msgLen = len(args)
                message = ' '.join(args[3:msgLen])
                print "data: " + message
                mySckt = setup(serverHost, serverPort)
                if mySckt:
                        sendMsg(mySckt, message)
                else:
                        usage()
                        exit()
        else:
                exit()

# main process
if __name__ and '__main__':
        if len(sys.argv) < 3:
                usage()
        else:
                processArgs(sys.argv)

