#!c:\perl\bin\perl.exe -w

# file - chg_ref.pl
# usage - perl chg_ref.pl <file> <new_ref>
# purpose - This tool changes the existing urls in <file> to <new_ref>. More specifically,
#           the string from here --> http://www.something.com/ <---- to here is 
#           replaced with <new_ref>. Any characters after the third "/" will not
#           be altered.
#

use strict;

# copy command args into my_args array
my @my_args = @ARGV;

# copy first command arg into $file
my $file = $my_args[0];

# copy second command arg into $new_ref
my $new_ref = $my_args[1];

# open $file for reading
open(INFILE,"<$file");

# variable for holding lines read in
my $line = "empty";

# loop:  for each line read in
foreach $line (<INFILE>)
{
	# copy $line into $_
	$_ = $line;
	
	# find url between double quotes
	# replace "http://www.something.com/" with $new_ref
	$_ =~ s/http:\/\/.{1,}?\//$new_ref\//;
	
	# print results to STDOUT
	# this can be redirected to another file
	print "$_";
	
}

# close $file
close(INFILE);