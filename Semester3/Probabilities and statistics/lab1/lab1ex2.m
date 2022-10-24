subplot(2, 2, 1)
x = linspace(0, 3);
f1=x.^5/10;
plot(x,f1)
title('Subplot1')



subplot(2, 2, 2)
f2=x.*sin(x);
plot(x,f2)
title('Subplot2')



subplot(2, 2, 3)
f3=cos(x);
plot(x,f3)
title('Subplot3')


