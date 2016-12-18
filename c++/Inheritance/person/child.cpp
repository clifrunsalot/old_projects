/*
 *      child.cpp
 *
 *      Copyright 2007 clif <clif@laptop>
 *
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */


#include <iostream>
#include "child.h"

using namespace std;

int main(int argc, char** argv)
{
	Child ch("clif","borja","hudson",41,"jose","dot");
	cout << ch.getFName() << endl;
	cout << ch.getMName() << endl;
	cout << ch.getLName() << endl;
	cout << ch.getAge() << endl;
	cout << ch.getDad() << endl;
	cout << ch.getMom() << endl;
	
	return 0;
}
