#!/usr/bin/python

import sys
import re

def usage():
	'''
	Parses /var/log/syslog based on field #5 and returns a 
	list of instances per unique field #5 name.

	Usage: 'python /path/to/parse_syslog.py /path/to/syslog'

	'''
	print usage.__doc__

def load_file(infile):
	'''
	Put file into memory.
	'''
	syslog=open(infile)
	lines = syslog.readlines()
	syslog.close()
	return lines

def get_field_name(orig_str):
	'''
	Returns field name striped of numbers and extra system attached characters.
	'''
	# 1. First split on ":", then split on ' ' to capture the 
	# 5th field.
	# 2. Remove '[,' '[,' and ':'
	_nm = orig_str.split(':')[2]
	_nm = _nm.split(' ')[2]
	digit = re.compile("[0-9]*",re.VERBOSE)
	clean_nm = digit.sub('',_nm)
	brackets = re.compile("[\[|\]]",re.VERBOSE)
	clean_nm = brackets.sub('',clean_nm)
	colon = re.compile(":$",re.VERBOSE)
	clean_nm = colon.sub('',clean_nm)
	return clean_nm

def find_uniq_field_names(lst):
	'''
	Parse the source field in each record.
	'''
	# List of field 5 terms.
	_field_5_names = []
	for line in lst:

			# strip extra characters from the field name
			clean_nm = get_field_name(line)
			
			# skip the empty uniq name
			if clean_nm != '':
				_field_5_names.append(clean_nm)

	# generate a unique list
	_uniq_field_5_names = set(_field_5_names)

	# do a case-insensitive sort
	__uniq_field_5_names = sorted(list(_uniq_field_5_names),key=str.lower) 
	return __uniq_field_5_names

def parse_list(uniq_lst, log):
	'''
	Generates list of counts per uniq field name.
	'''
	final_lst = dict() 
	for uniq in uniq_lst:
		cnt = 0
		for line in log:

			# strip extra characters from the field name
			clean_nm = get_field_name(line)
			
			if uniq == clean_nm:
				if uniq in final_lst:
					cnt = final_lst.get(uniq)
					cnt = cnt + 1
					final_lst[uniq]=cnt
				else:
					final_lst[uniq]=1

	# build a list object from dictionary
	_final_lst = list()
	for k,v in final_lst.items():
		_final_lst.append('{0}:{1}'.format(k,v))

	# now sort regardless of case
	_final_lst.sort(key=str.lower)

	return _final_lst

def display(final_lst):
	'''
	Formats the list for display.
	'''
	total = 0
	for item in final_lst:
		itm = item.split(':')
		total = total + int(itm[1])
		print '{:>10} {:<50}'.format(itm[1],itm[0]) 

	print '{:>10} {:<50}'.format(total,'Total') 

if __name__ == "__main__":

	if len(sys.argv) == 2:
		input = sys.argv[1]
		lines = load_file(input)
		uniq_names = find_uniq_field_names(lines)
		final_list = parse_list(uniq_names,lines)
		display(final_list)

	else:
		usage()


 
