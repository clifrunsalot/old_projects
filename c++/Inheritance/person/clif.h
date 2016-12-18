#ifndef CLIF_H
#define CLIF_H

class Date
{
	int month, day, year;
public:
	Date(int mo, int da, int yr);
	void msg(char * m);
};

Date::Date(int mo, int da, int yr)
{
	month = mo;
	day = da;
	year = yr;
}

void Date::msg(char * msg)
{
	char * pMsg = msg;
	do 
	{
		std::cout << *(pMsg++);
	}
	while (*pMsg);
	std::cout << ": Today's date is " << month << "/"
				<< day << "/" << year;
	
}
#endif
