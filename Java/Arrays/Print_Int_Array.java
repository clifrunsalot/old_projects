class Print_Int_Array
{

  static void Print_Array_Of_Integers()
  {

    int[] ArrayOfInteger = {9,8,7,6,5,4,3,2,1};
    for (int i=0; i<= 8; i++)
    {
      System.out.print(ArrayOfInteger[i]);
      System.out.print(' ');
    }
  }

  static void Print_Array_Of_Strings()
  {

    String[] ArrayOfStrings = {"Hello","Good Bye","What","A","Life"};
    for (int i=0; i<= 4; i++)
    {
      System.out.print(ArrayOfStrings[i]);
      System.out.print(' ');
    }
  }

  static void Print_Array()
  {
    Print_Array_Of_Integers();
    Print_Array_Of_Strings();
  }

  public static void main(String[] args)
  {
    Print_Array();
  }

}