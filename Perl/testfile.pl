#!/usr/bin/perl

@record = ();
$i = 0;

foreach $_ (`cat /etc/passwd`)
{
	$record[$i] = $_;
	$i++;
}

open(FIN,"> results");

foreach $_ (@record)
{
	print FIN $_;
}

close(FIN);

