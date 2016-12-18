#include <iostream>
#include <string>
#include "../headers/hello.h"

using namespace std;

Hello::Hello() {
	cout << "Starting Hello program" << endl;
	Hello::msg = "";
}

Hello::~Hello() {
	cout << "Ending Hello program" << endl;
	delete Hello::msg;
}

void Hello::sayMessage() {
	cout << Hello::msg << endl;
}

void Hello::setMessage(char * m) {
	Hello::msg = m;
}

char * Hello::getMessage() {
	return Hello::msg;
}

int main() {
	Hello * h = new Hello();
	h->setMessage("What's up, clif?\n");
	cout << h->getMessage();
	delete h;
	return 0;
}
