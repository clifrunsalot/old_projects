with Ada.Text_IO; 
with Ada.Integer_text_IO;

procedure loops_constructs is 

  Package T_O renames Ada.Text_IO;
  package I_O renames Ada.Integer_text_IO;
  i : Integer := 0;

begin

  T_O.put_line("Loop with exit conditI_On");
  looper:
  loop
    I_O.put(i);
    T_O.new_line;
    i := i + 1;
    exit looper when i > 5;
  end loop looper;
  
  T_O.new_line;

  i := 0;
  T_O.put_line("While loop");
  while_looper:
  while i <= 5 loop
    I_O.put(i);
    T_O.new_line;
    i := i + 1;
  end loop while_looper;
  
  T_O.put_line("For loop");
  for_looper:
  for i in integer range 0 .. 5 loop
    I_O.put(i);
    T_O.new_line;
  end loop for_looper;
  
end loops_constructs;
