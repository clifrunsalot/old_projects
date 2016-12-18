/*
 * Node.h
 *
 *  Created on: Mar 25, 2011
 *      Author: cbhuds
 */

#ifndef NODE_H_
#define NODE_H_

#include <string>
#include <iostream>

using namespace std;

namespace mylist {

class Node {

private:
	string _id;
	Node * _next;

public:
	Node(string id);
	virtual ~Node();
	string getId() const;
	Node *getNext() const;
	void setId(string id);
	void setNext(Node *next);

};

}

#endif /* NODE_H_ */
