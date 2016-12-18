/*
 * Hello.h
 *
 *  Created on: Mar 25, 2011
 *      Author: cbhuds
 */

#ifndef HELLO_H_
#define HELLO_H_

#include <iostream>
#include <string>

using namespace std;

namespace practice {

class Hello {
public:
	Hello();
	virtual ~Hello();
	void say(string msg);
};

}

#endif /* HELLO_H_ */
