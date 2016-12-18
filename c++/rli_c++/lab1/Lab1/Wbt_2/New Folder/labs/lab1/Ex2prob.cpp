#include<iostream.h>
void main()
{
	 int day;
	 char reply='Y';
	 
	 cout<<"Enter the day of the week as a number ";
	 cin>>day;
	 do
	 {
	 // Write the switch statement to display the name of the day 
	 // based on the day no.entered by the user. 
	 
	 	switch (day)
	 	{
	 		case 0 : {
		 				cout << "Sunday" << endl;
	 					break;
 					}
	 		case 1 : {
		 				cout << "Monday" << endl;
	 					break;
 					}
	 		case 2 : {cout << "Tuesday" << endl;
	 					break;
 					}
	 		case 3 : {
		 				cout << "Wednesday" << endl;
	 					break;
 					}
	 		case 4 : {
		 				cout << "Thursday" << endl;
	 					break;
 					}
	 		case 5 : {
		 				cout << "Friday" << endl;
	 					break;
 					}
	 		case 6 : {
		 				cout << "Saturday" << endl;
	 					break;
 					}
	 		default : cout << "Invalid number" << endl;
 		}
     
	 cout<<endl<<"Do you want to continue :";
	 cin>>reply; 
     }while(reply=='Y' || reply=='y');
}
