#include <iostream>
#include <string>

#include "parent.h"

int main() {

	Parent * p = new Parent("clif","hudson",42);
	cout << p->getFName() << " " << p->getLName() << " " << p->getAge() << endl;
	delete p;

	return 0;

}
