#!/usr/bin/python

import sys
import re


print 'Program arguments: ',sys.argv
print 'Argument count:    ',len(sys.argv)

syslog=open(sys.argv[1],'r')
lines = syslog.readlines()
syslog.close()

# List of field 5 terms.
_field_5_names = []
for line in lines:
  _nm = line.split(' ')[4]
  nm = re.split('\[',_nm)
  _field_5_names.append(nm[0])

_uniq_field_5_names = set(_field_5_names)

print 'Unique field 5 names'
print '\n'.join(_uniq_field_5_names)


