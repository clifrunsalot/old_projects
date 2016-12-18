#c:\perl\bin\perl.exe

print "\nusing subs\n";

sub Cow::speak
{
	print "a Cow goes moooo!\n";
}

sub Horse::speak
{
	print "a Horse goes neigh!\n";
}

sub Sheep::speak
{
	print "a Sheep goes baaah!\n";
}

Cow::speak;
Horse::speak;
Sheep::speak;

print "\nusing loops\n";

@pasture = qw(Cow Cow Horse Sheep Sheep);
foreach $animal(@pasture)
{
	&{$animal."::speak"};
}

print "\nusing arrows\n";

Cow->speak();
Horse->speak();
Sheep->speak();

foreach $animal(@pasture)
{
	$animal->speak();
}

print "\nusing classes in a pkg\n";

{
	package Cow1;
	sub new
	{
		my $class = shift;
		my $self = {};
		$self->{FIRSTNAME} = name::->new();
		$self->{SPEAK} = undef;
		bless $self, $class;
		return $self;
	}

	sub speak
	{
		my $self = shift;
		print "a $self->firstname->first goes mooo!\n";
	}

	sub firstname
	{
		my $self = shift;
		return $self->{FIRSTNAME};
	}
}

{
	package name;
	sub new
	{
		my $class = shift;
		my $self = {};
		$self->{FIRST} = undef;
		bless $self, $class;
		return $self;
	}

	sub first
	{
		my $self = shift;
		if(@_)
		{
			$self->{FIRST} = shift;
		}
		return $self->{FIRST};
	}
	
}

my $ref = Cow1::->new();
$ref::->firstname->name("larry");
$ref::->speak();



