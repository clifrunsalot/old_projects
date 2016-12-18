#include <iostream>
#include <string>

//declare namespace for cout/cin
using namespace std;

//Base class: Ancester class for all others
class Base
{
	public:
		Base();
		~Base();
		void sayHello();
	private:
		const char * hello;
};

//Base class constructor
Base::Base() :
		hello("hello from Base")
{
	cout << "Calling Base Constructor" << endl;
}

//Base class destructor
Base::~Base()
{
	cout << "Calling Base Destructor" << endl;
}

//Base::sayHello method for printing the value of hello
void Base::sayHello()
{
	cout << Base::hello << endl;
}

//Derived1 class: decendent class
class Derived1 : public Base
{
	public:
		Derived1(int start, int end);
		~Derived1();
		void sayHello();
		int * getRange();
		void printRange(int r[]);
		int getSize();
	private:
		const char * hello;
		int * range;
		int size;
};

//Derived1 class constructor
Derived1::Derived1(int start, int end) :
		hello("hello from Derived1")
{
	cout << endl << "Calling Derived1 constructor" << endl;
	cout << "Populating int array inside constructor" << endl;
	Derived1::size = (end-start)+1;
	cout << "Size of array = " << Derived1::size << endl;
	range = new int[Derived1::size];
	for(int i=0; i<Derived1::size; i++) {
		Derived1::range[i] = start;
		start++;
	}
}

int Derived1::getSize()
{
	return Derived1::size;
}

//Derived1 class destructor
Derived1::~Derived1()
{
	cout << endl << "Calling Derived1 destructor" << endl;
	delete [] Derived1::range;
	cout << "Size of array = "
	<< (sizeof(Derived1::range)/sizeof(int)) << endl;
}

//Derived1::sayHello method for printing the value of hello
void Derived1::sayHello()
{
	cout << Derived1::hello << endl;
}

int * Derived1::getRange()
{
	cout << "Retrieving the int array" << endl;
	int * r;
	r = Derived1::range;
	return r;
}

void Derived1::printRange(int * r)
{
	cout << endl << "Here's the int array" << endl;
	cout <<         "********************" << endl;
	for(int i=0; i<Derived1::getSize(); i++) {
		/*
		Since a ptr was passed in, simply move the ptr and
		then dereference it before printing the next value
		in the array.
		 */
		cout << *(r+i) << endl;
	}
}

//Main
int main(int argc, char* argv[])
{
	cout << "declaring and creating Base object" << endl;
	Base * b = new Base();
	b->sayHello();
	cout << "declaring and creating Derived object" << endl;
	Derived1 * d = new Derived1(4,10);
	int * r;
	d->sayHello();
	r = d->getRange();
	d->printRange(r);
	return 0;
}

