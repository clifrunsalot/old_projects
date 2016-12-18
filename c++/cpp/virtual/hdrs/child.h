#ifndef __CHILD_H__
#define __CHILD_H__

#include "parent.h"

class Child : public Parent {

	private:
		char * jobTitle;

	public:
		Child(char *, char *, int);
		~Child();
		void printFullName();

};

Child::Child(char * f, char * l, int a) : Parent(f, l, a) {
	cout << "Starting Child class" << endl;
}

Child::~Child() {
	cout << "Ending Child class" << endl;
}

void Child::printFullName() {
	cout << "Child Name: " << Child::getFName() << " " << Child::getLName() << endl;
}


#endif /*__CHILD_H__*/
