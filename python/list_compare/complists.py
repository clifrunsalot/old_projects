#!/usr/bin/python

import os, sys, exceptions, sets

def usage():
	"""
	Usage: [python] compList.py <file_1> <file_2>

	where <file_1> and <file_2> are files, each with a list of strings to compare.

	The output is the following:
		1) A list of strings in both files
		2) A list of strings only in file_1
		3) A list of strings only in file_2

	"""

def compareThem(n1, l1, n2, l2):
	"""
	Calls subfuncs that produces lists
	n1, n2 - Names of files
	l1, l2 - Sets to compare
	"""
	showCommon(l1, l2)
	showOnly(n1, l1, l2)
	showOnly(n2, l2, l1)

def fillList(f):
	"""
	Returns a set of strings.
	f - Path to file and name
	"""
	
	f1 = open(f,'r')
	lst = sets.Set()

	for i in f1.readlines():
		lst.add(i.strip())

	f1.close()
	return lst


def printList(l):
	"""
	Prints list to console.
	l - List
	"""
	for i in l:
		print i, ' '

def showCommon(l1, l2):
	"""
	Prints list of common entries from both lists.
	l1, l2 - Sets to compare
	"""
	common = l1 & l2
	lst = []
	for i in common:
		lst.append(i)
	lst.sort()

	print "Common Lines:".strip()
	for i in lst:
		print i

	print "Total: ", len(lst)
	print

def showOnly(n, l1, l2):
	"""
	Display items that exist only in l1.
	n - Name of file
	l1, l2 - Sets to compare
	"""
	only = l1 - l2
	lst = []
	for i in only:
		lst.append(i)
	lst.sort()
	print "Only in ", n
	for i in lst:
		print i

	print "Total: ", len(lst)
	print
	

if __name__ == "__main__":
	try:
		lst_1 = fillList(sys.argv[1])
		lst_2 = fillList(sys.argv[2])
		compareThem(sys.argv[1], lst_1, sys.argv[2], lst_2)
	except Exception, e:
		print usage.__doc__
		print "\tERROR: %s\n" % (e)

