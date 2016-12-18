#!/usr/bin/env python

import os, sys, string

list = ['50_12','50_2','50_34','50_1','50_10','51_1','52_1']

pairList = []

# transform values of XX_YY into XXYY
# and then reassign new value to hash key
for i in list:
  a = i.split('_')
  # pad component two with 0 if single digit
  if a[1] in ['1','2','3','4','5','6','7','8','9']:
    a[1] = '0'+str(a[1]) 
  b = ''.join(a)
  pairList.append([b,i])

print "1 ==> " + str(pairList)

# cast component one to an int so that it can be sorted later
casted = []
for v in pairList:
  k = int(v[0])
  casted.append([k,v[1]])

print "2 ==> " + str(casted)

casted.sort()

print "3 ==> " + str(casted)

# create a list of component two of each element in the list
sortedList = [ v[1] for v in casted ]

print "4 ==> " + str(sortedList)




