/*
 * Node.cpp
 *
 *  Created on: Mar 25, 2011
 *      Author: cbhuds
 */

#include <string>
#include "Node.h"

using namespace std;

namespace mylist {

Node::Node(string id) {

	this->_id = id;
	this->_next = 0;
}

string Node::getId() const {
	return _id;
}

Node *Node::getNext() const {
	return _next;
}

void Node::setId(string id) {
	this->_id = id;
}

void Node::setNext(Node *next) {
	this->_next = next;
}

Node::~Node() {
	// TODO Auto-generated destructor stub
}

}
