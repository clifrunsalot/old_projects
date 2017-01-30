with Ada.Text_IO;

procedure my_types is


  --
  -- Regular types
  --
  type Integer_1 is range 1 .. 10;
  subtype Integer_2 is Integer_1 range 7 .. 9;
  
  package T_O renames Ada.Text_IO;
  package I_O is new Ada.Text_IO.Integer_IO(Integer_1);

  i : Integer_1 := 7; -- Ok, within range
  i2 : Integer_2 := i; -- OK if stays between 7 .. 9
  
  --
  -- User defined types
  --
  type Primary_Color is (RED,GREEN,BLUE);
  
  color : Primary_color := Primary_color'val(0);
  
begin

  T_O.put_line("Integer Type:");
  T_O.put("i => ");
  I_O.put(i);
  T_O.new_line;
  i := i + 1;
  T_O.put("i => ");
  I_O.put(i);
  T_O.new_line;
  T_O.put("i2 => ");
  I_O.put(i2);
  T_O.new_line;
  i2 := i + 1;
  T_O.put("i2 => ");
  I_O.put(i2);
  T_O.new_line;
  T_O.new_line;
  
  T_O.put_line("User-defined Types:");
  T_O.put(Primary_color'Image(color));
  T_O.put(Primary_color'Image(color));

end my_types;
