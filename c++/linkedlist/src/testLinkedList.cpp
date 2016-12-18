/*
 * testLinkedList.cpp
 *
 *  Created on: Mar 25, 2011
 *      Author: cbhuds
 */

#include <iostream>
#include <string>

#include "Node.h"

using namespace std;
using namespace mylist;

int main() {

	Node * first = new Node("head");
	cout << first->getId() << endl;
	return 0;
}
