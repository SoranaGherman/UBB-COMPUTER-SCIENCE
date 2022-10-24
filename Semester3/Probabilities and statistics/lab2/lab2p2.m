n = input('Enter number of trials: ');
p = input('Enter the probability of success: ');

c1 = pdf('bino', 0:n, n, p);
fprintf('%d',c1);