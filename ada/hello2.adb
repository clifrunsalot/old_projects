with Ada.Text_IO; 
with Ada.Integer_text_IO;

procedure Hello2 is 
  Package TO renames Ada.Text_IO;
  package IO renames Ada.Integer_text_IO;
  i : Integer := 0;
begin

  TO.put_line("Loop with exit condition");
  looper:
  loop
    IO.put(i);
    TO.new_line;
    i := i + 1;
    exit looper when i > 5;
  end loop looper;
  
  TO.new_line;

  i := 0;
  TO.put_line("While loop");
  while_looper:
  while i <= 5 loop
    IO.put(i);
    TO.new_line;
    i := i + 1;
  end loop while_looper;

  
end Hello2;
