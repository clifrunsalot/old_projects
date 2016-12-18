#ifndef __PARENT_H__
#define __PARENT_H__

using namespace std;

class Parent {

	private:
		char * fName;
		char * lName;
		int age;

	public:
		Parent(char *, char *, int);
		~Parent();
		char * getFName();
		void setFName(char *);
		char * getLName();
		void setLName(char *);
		int getAge();
		void setAge(int a);
		virtual void printFullName() = 0;
};
		

Parent::Parent(char * f, char * l, int a) {
	cout << "Starting Parent class" << endl;
	Parent::fName = f;
	Parent::lName = l;
	Parent::age = a;
}

Parent::~Parent() {
	cout << "Ending Parent class" << endl;
}
	
char * Parent::getFName() {
	return fName;
}

void Parent::setFName(char * fn) {
	Parent::fName = fn;
}

char * Parent::getLName() {
	return lName;
}

void Parent::setLName(char * ln) {
	Parent::lName = ln;
}

int Parent::getAge() {
	return Parent::age;
}

void Parent::setAge(int a) {
	Parent::age = a;
}


#endif /*__PARENT__*/
