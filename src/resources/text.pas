program FullDemo;

var
  i, j, total: integer;
  message: string;
  flag: boolean;

procedure Display(msg: string);
begin
  writeln('> ', msg);
end;

function Factorial(n: integer): integer;
begin
  if n <= 1 then
    Factorial := 1
  else
    Factorial := n * Factorial(n - 1);
end;

begin
  total := 0;
  flag := true;

  for i := 1 to 5 do
  begin
    for j := 1 to 3 do
    begin
      total := total + i * j;
    end;
  end;

  if total > 50 then
  begin
    message := 'Result is large: ' + total;
    Display(message);
  end
  else
  begin
    message := 'Result is small';
    Display(message);
  end;

  { Test: print factorial of 5 }
  writeln('Factorial of 5 is ', Factorial(5));

  { Some more examples }
  flag := false;
  if not flag then
    writeln('Flag is false');

  case total of
    30: writeln('Exactly 30');
    60: writeln('Exactly 60');
  else
    writeln('Other value: ', total);
  end;

  { Unclosed comment test
  x := 123;

  { Unclosed string literal }
  message := 'unterminated string

end.