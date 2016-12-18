#!/usr/bin/perl

use strict;
use warnings;

my ($prev_ver, $latest_ver) = (0, 0);

# file attributes
my %file_hash = ();

# list of files
my @file_list = ();

while(<DATA>) {

	if (not /^$/) {
		my @breakup = split(/@/,$_);
		my @list = split(/,/, $breakup[3]);

		print "list: @list\n";

		my %file = ();

		my ($deleted, $checked_out, $only_one) = (0, 0, 0);
		my @versions = ();
		$deleted     = grep {/\bD\b/} @list;
		$checked_out = grep {/\b[A|O]\b/} @list;
		$only_one    = (grep {/\b\d+\b/} @list == 1) ?  $list[0] : 0;
		@versions    = grep {/\b\d+\b/} @list;

		$file{deleted}     = $deleted; 
		$file{checked_out} = $checked_out;
		$file{only_one}    = $only_one;
		$file{versions}    = @versions;

		print "deleted:     $file{deleted}\n";
		print "checked out: $file{checked_out}\n";
		print "versions:    $file{versions}\n";
		print "only_one:    $file{only_one}\n\n\n";
		
		print "size: $file{versions}\n";
		
	}

}


__DATA__

fcccme_reuse@/main/rfw_int_b2e/rfw_dev_b2e@test.java@1, 2, 3, O@
fcccme_reuse@/main/rfw_int_b2e/rfw_dev_b2e@test1.java@O@
fcccme_reuse@/main/rfw_int_b2e/rfw_dev_b2e@test2.java@A@
fcccme_reuse@/main/rfw_int_b2e/rfw_dev_b2e@test3.java@4, 5, 6, 7, D@
fcccme_reuse@/main/rfw_int_b2e/rfw_dev_b2e@test4.java@1, 2, 3, O@
fcccme_reuse@/main/rfw_int_b2e/rfw_dev_b2e@test.java@9, 10, 11@
fcccme_reuse@/main/rfw_int_b2e/rfw_dev_b2e@test.java@2@


