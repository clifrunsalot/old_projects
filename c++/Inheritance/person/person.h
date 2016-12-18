#ifndef person_h
#define person_h

#include <iostream>

using namespace std;

class Person {
    private:
        string fname;
        string mname;
        string lname;
        int age;
    public:
		Person(string fn, string mn, string ln, int ag);
		~Person();
		string getFName();
		string getMName();
		string getLName();
		int getAge();
};

Person::Person(string fn, string mn, string ln, int ag) {
    fname = fn;
    mname = mn;
    lname = ln;
    age = ag;
}

Person::~Person() {
	
}

string Person::getFName() {
    return fname;
}

string Person::getMName() {
    return mname;
}

string Person::getLName() {
    return lname;
}

int Person::getAge() {
    return age;
}

#endif
