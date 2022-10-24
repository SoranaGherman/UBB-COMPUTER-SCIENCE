n = input('Enter number of trials: ');
p = input('Enter the probability of success: ');

plot(0:n, pdf('bino', 0:n, n, p), '*');
hold on
plot(0:0.01:n, cdf('bino', 0:0.01:n, n, p));
axis([-0.1, 3.1, -0.1, 1.1]);
hold off

