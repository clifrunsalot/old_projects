#!/usr/local/bin/perl -w


#
#open file passed in
#
open(INFILE,"h/AFATDS/data/scratch/System_Log");

#
#loop thru file
#
while(<INFILE>)
{
	#
	#if line contains "Run Level", extract number after it
	# and print it.
	#
	if(/Run Level/i)
	{
		s/Run Level:\s+(\d+)\s+/$1/;
		print "Run Level: ".$1."\n";
	}
	
	#
	#if line contains "Starting process", extract process name
	# and print it.
	#
	if(/Starting process/i)
	{
		s/\/h\/AFATDS\/bin\/(\S+)+\//$1/;
		print "   Starting: ".$1."\n";
	}
}
