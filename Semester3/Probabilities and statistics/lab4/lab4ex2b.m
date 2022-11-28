clear all;
clc;

p = input('p in (0,1)=');
N = input('nr of sim:');
x = 0;
while rand >= p
    x = x + 1;
end

clear x
for i = 1:N
    x(i) = 0;
    while rand >= p
        x(i) = x(i) + 1;
    end
end

k = 0:20;
u_x = unique(x);
p_k = geopdf(k,p);
n_x = hist(x, length(u_x));
plot(u_x, n_x/N, '*', k, p_k, 'ro')
legend('Geom distribution', 'Simulations')
