#include <iostream>
#include <fstream>

int main()
{
	int choice;

	std::ifstream ifile;
    ifile.open("question.txt");
	
	ifile >> choice;
	switch (choice) 
	{
		case 1:
			std::cout << "She is the perfect woman!.  Keith has a snow ball's chance in Hades with her.";
			break;
		case 2:
			std::cout << "She's OK if you like Blondes.";
			break;
		case 3:
			std::cout << "Nope, I prefer Brunettes.";
			break;
		default:
			std::cout << "For fear of immediate reprisal from the wife, I plead the fifth.";
			break;
	}
	
	
	ifile.close();
	   
    return 0;
}

