#C:\perl\bin\perl.exe

use strict;
use warnings;
use File::Find;

# Class object for handling file objects
{
	package FileObject;

	sub new {
		my $class = shift;
		my $self = {};
		$self->{NAME} = undef;
		$self->{SHORTNAME} = undef;
		$self->{RELPATH} = undef;
		$self->{SIZE} = undef;
		$self->{PATH} = undef;
		$self->{MODTIME} = undef;
		bless($self, $class);
		return $self;
	}

	sub name {
		my $self = shift;
		if (@_) {
			$self->{NAME} = shift;
		}
		return $self->{NAME};
	}

	sub shortname {
		my $self = shift;
		if (@_) {
			$self->{SHORTNAME} = shift;
		}
		return $self->{SHORTNAME};
	}

	sub relpath {
		my $self = shift;
		if (@_) {
			$self->{RELPATH} = shift;
		}
		return $self->{RELPATH};
	}

	sub path {
		my $self = shift;
		if (@_) {
			$self->{PATH} = shift;
		}
		return $self->{PATH};
	}

	sub size {
		my $self = shift;
		if (@_) {
			$self->{SIZE} = shift;
		}
		return $self->{SIZE};
	}

	sub modtime {
		my $self = shift;
		if (@_) {
			$self->{MODTIME} = shift;
		}
		return $self->{MODTIME};
	}

	sub print {
		my $self = shift;
		print "$self->{NAME}\n";
		print "$self->{PATH}\n";
		print "$self->{RELPATH}\n";
		print "$self->{SHORTNAME}\n";
		print "$self->{SIZE}\n";
		print "$self->{MODTIME}\n";
	}

	1;
}

sub usage {
	print <<USE;

	Usage: dir_diffs <dir_1> <dir_2>
	where <dir_1> and <dir_2> are the two directories to compare.

USE
}

sub convert_to_dtg {
	my $epoch = $_[0];
	my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) =
                                            localtime($epoch);
	$year += 1900;
	sprintf ("%02d:%02d:%02d %02d/%02d/%04d", $hour, $min, $sec, $mon, $mday, $year);

}

($ARGV[0] && $ARGV[1]) or die &usage();

# Save args
my ($dir_1, $dir_2) = ($ARGV[0], $ARGV[1]);

# finds all file objects in dir
my %tmp_hash = ();
sub get_list {
	my $f = $File::Find::name;
	my $obj = FileObject->new();
	$obj->name($File::Find::name);
	$obj->shortname($_);
	$obj->path($File::Find::dir);
	my ($dev,$ino,$mode,$nlink,$uid,$gid,$rdev,$size,
	   $atime,$mtime,$ctime,$blksize,$blocks)
		= stat($f);
	$obj->size($size);
	$mtime = &convert_to_dtg($mtime);
	$obj->modtime($mtime);
	$tmp_hash{$f} = $obj;
}

# hashes for file objects
my (%dir_1, %dir_2) = ();

# populate dir_1 list
find(\&get_list, $dir_1);
%dir_1 = %tmp_hash;
foreach my $f (values %dir_1) {
	$f->relpath($dir_1);
}

%tmp_hash = ();

# populate dir_2 list
find(\&get_list, $dir_2);
%dir_2 = %tmp_hash;
foreach my $f (values %dir_2) {
	$f->relpath($dir_2);
}

# make a lookup table from dir_1
#my %seen = ();
#@seen{keys %dir_1} = ();
#my %iter = ();
#
#foreach my $f (keys %dir_2) {
#	if (exists $seen{$f}) {
#		$iter{$f} = $seen{$f};
#	}
#}

#print "\n\nDIR_1 ********************\n";
#print map {"$_->{SHORTNAME}, $_->{SIZE}, $_->{MODTIME}\n"}
#	map {$_->[0]}
#	sort {$a->[1] <=> $b->[1]}
#	map { [$_, $_->{SIZE}] }
#	values %dir_1;
#
#print "\nDIR_2 ********************\n";
#print map {"$_->{SHORTNAME}, $_->{SIZE}, $_->{MODTIME}\n"}
#	map {$_->[0]}
#	sort {$a->[1] <=> $b->[1]}
#	map { [$_, $_->{SIZE}] }
#	values %dir_2;

foreach my $f (values %dir_1) {
	$f->print();
}

foreach my $f (values %dir_2) {
	$f->print();
}
