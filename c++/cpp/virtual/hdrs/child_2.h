#ifndef __CHILD_2_H__
#define __CHILD_2_H__

#include "parent.h"

class Child_2 : public Parent {

	private:
		char * jobTitle;

	public:
		Child_2(char *, char *, int);
		~Child_2();
		void printFullName();

};

Child_2::Child_2(char * f, char * l, int a) : Parent(f, l, a) {
	cout << "Starting Child_2 class" << endl;
}

Child_2::~Child_2() {
	cout << "Ending Child_2 class" << endl;
}

void Child_2::printFullName() {
	cout << "Child_2 Name: " << Child_2::getFName() << " " << Child_2::getLName() << endl;
}


#endif /*__CHILD_2_H__*/
