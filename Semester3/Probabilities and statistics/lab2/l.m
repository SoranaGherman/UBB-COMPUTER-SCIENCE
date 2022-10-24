n = 3;
p = 0.5;
x = 0;
c1 = pdf('bino', x, n, p);
fprintf(' %f ',c1);

xx = 1;
c2 = 1 -  pdf('bino', xx, n, p);
fprintf(' %f',c2);

y = 2;
c3 = cdf('bino', y, n, p);
fprintf(' %f ',c3);

yy = 1 - 0.01;
c4 = cdf('bino', yy, n, p);
fprintf(' %f ',c4);

c5 = 1 - cdf('bino', xx, n, p);
fprintf( ' %f ', c5);

heads = 0;
for k = 1 : n
    x = rand();
    if (x >= 0.5)
        fprintf("heads\n")
        heads = heads + 1;
    else
        fprintf("tails\n");
    end
end

fprintf(' %f ', heads);
fprintf(' %f ', binopdf(heads,n, p));
     