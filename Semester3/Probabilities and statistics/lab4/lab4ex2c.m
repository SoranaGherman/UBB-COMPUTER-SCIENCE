clear all;
clc;

n = input('trials=');
p = input('p in (0,1)=');
for j = 1:n
    x(j) = 0;
    while rand>=p
       x(j) = x(j) + 1;
    end
end

y = sum(x);
clear x
clear y

N = input('nr of simulations = ');
for i=1:N
    for j=1:n
        x(j) = 0;
        while rand >= p
            x(j) = x(j)+1;
        end
    end
    y(i) = sum(x);
end


k = 0:100;
u_y = unique(y);
p_k = nbinpdf(k,n,p);
n_y = hist(x, length(u_y));
plot(u_y, n_y/N, '*', k, p_k, 'ro')
legend('Neg bin distr distribution', 'Simulations')
    
