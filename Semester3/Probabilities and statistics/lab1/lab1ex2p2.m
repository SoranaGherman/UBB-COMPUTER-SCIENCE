x = linspace(0,3);
f1 = x.^5/10;
f2 =  x .* sin(x);
f3 =  cos(x);
plot(x, f1, x, f2, x, f3);
legend('x.^5/10', ' x * sin(x)', 'cos(x)');
title('All combined')