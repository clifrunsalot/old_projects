#!c:\Program Files\perl\bin\perl.exe -w

# file - runlevel.pl
# usage - perl runlevel.pl <System_Log>
# purpose - extracts the runlevel and process from <System_Log> indefinitely.

use strict;

# copy command args into my_args array
my @my_args = @ARGV;

my $args_count = @ARGV;
my $file = " ";;

if($args_count == 1)
{
	# copy first command arg into $file
	$file = $my_args[0];

	if( ! -e "$file")
	{
		die "$file doesn't exist\n";
		exit(0);
	}
}
else
{
	die "Usage: runlevel.pl <path to System_Log>\n";
}

#variable for counting time
my $seconds = 0;
my $minutes = 0;

while(1)
{
	system "cls";
	check_log();
	
	my $seconds = sleep(60);
	
	if(($seconds%60) == 0)
	{
		$minutes += 1;
	}
}

sub check_log
{
	#open $file passed in
	open(INFILE,"<$file");
	
	print "Scanning System Log for updates.. \n";
	print "----------------------------------\n";
	print "$minutes minute(s) have passed\n\n";

	#loop thru file
	foreach $_ (<INFILE>)
	{
		#if line contains "Run Level", extract number after it
		# and print it.
		if(/Run Level/i)
		{
			my $runlevel = " ";
			
			# find the string of digits after the ":" and
			# put it into $runlevel
			($runlevel) = /Run Level:\s+(\d+)\s/ig;
			
			# print $runlevel to STDOUT
			print "\nRun Level:    ".$runlevel."\n";
		}
		
		#if line contains "Starting process", extract process name
		# and print it.
		if(/Starting process/i)
		{
			my $process = " ";
			
			# find the string of non-whitespace characters after the last "/"
			# and put it into $process
			($process) = /.{1,}\/(\S+)/ig;
			
			# print $process to STDOUT
			print "   Starting:     ".$process."\n";
		}
	}
	
	close(INFILE);
}

