#include <iostream.h>
int main()
{
	int num1,num2,counter;
	cout<<"Enter the starting number of the range :";
	cin>>num1;
	cout<<"Enter the last number of the range :";
	cin>>num2;
   
   	// Write a for loop to display all even numbers between num1 and num2
   	for (counter=num1; counter<=num2; counter++)
   		{
	   		if(counter%2==0)
   		    cout<<counter<<endl;
		}
	return 0;
}











