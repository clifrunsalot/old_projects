#include <iostream>
#include <string>
#include "child.h"
#include "child_2.h"

using namespace std;

int main () {

	Child * c = new Child("joann","hudson",42);
	Child_2 * c2 = new Child_2("sirena","hudson",13);

	cout << c->getFName() << " " << c->getLName() << " " << c->getAge() << endl;
	c->printFullName();

	cout << c2->getFName() << " " << c2->getLName() << " " << c2->getAge() << endl;
	c2->printFullName();
	
	delete c;
	delete c2;
	return 0;
}
