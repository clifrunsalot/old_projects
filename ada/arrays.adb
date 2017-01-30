with Ada.text_io;

procedure arrays is

  type Range_Type is range 0 .. 5;
  package T_O renames Ada.Text_IO;
  package I_O is new Ada.Text_IO.Integer_IO(Range_Type);
  
begin

  for A in Range_Type loop
    I_O.put(Item => A);
    if A < Range_Type'last then
      T_O.put(",");
    else
      T_O.new_line;
    end if;
  end loop;
  
end arrays;
