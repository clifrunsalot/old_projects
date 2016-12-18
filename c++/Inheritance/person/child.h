#ifndef child_h
#define child_h

#include "person.h"

using namespace std;

class Child : public Person {
	private:
		string dadfname;
		string momfname;
	public:
		Child(string fn,
				string mn,
				string ln,
				int ag,
				string dad,
				string mom);
		~Child();
		string getDad();
		string getMom();
};

Child::Child(string fn,
				string mn,
				string ln,
				int ag,
				string dad,
				string mom) : Person (fn, mn, ln, ag) {
				
	dadfname = dad;
	momfname = mom;
}

Child::~Child() {
}

string Child::getDad() {
	return dadfname;
}

string Child::getMom() {
	return momfname;
}

#endif
