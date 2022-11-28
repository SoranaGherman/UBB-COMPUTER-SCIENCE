#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char** argv)
{
        int p2a, a2b, b2c, c2a, nr;
        mkfifo("fp2a", 0600);
        mkfifo("fa2b", 0600);
        mkfifo("fb2c", 0600);
        mkfifo("fc2a", 0600);

        int count=0;

        if(fork() == 0) //A
        {
                p2a = open("fp2a", O_RDONLY);
                a2b = open("fa2b", O_WRONLY);
                c2a = open("fc2a", O_RDONLY);
                read(p2a, &nr, sizeof(int));

                while(1)
                {


                        if(count >= 6) break;

                        nr += 1;
                        int cnr = nr, ok = 0;
                        if(nr % 6 == 0) ok = 1;

                        if(ok == 0)
                        {
                                while(cnr && ok == 0)
                                {
                                        if(cnr % 10 == 6) ok = 1;
                                        cnr = cnr / 10;
                                }
                        }

                        if(ok == 0)
                                printf("A: %d\n", nr);
                        else
                                {count++;
                                        printf("A: Boltz6\n");
                                }
                        write(a2b, &count, sizeof(int));
                        write(a2b, &nr, sizeof(int));
                        read(c2a, &count, sizeof(int));
                        read(c2a, &nr, sizeof(int));                                         
                }
                close(p2a);close(a2b);close(c2a);exit(0);
        }

        if(fork() == 0) //B
        {
                a2b = open("fa2b", O_RDONLY);
                b2c = open("fb2c", O_WRONLY);

                while(1)
                {
                        read(a2b, &count, sizeof(int));
                        read(a2b, &nr, sizeof(int));

                        if(count >= 6) break;

                        nr += 1;
                        int cnr = nr, ok = 0;
                        if(nr % 6 == 0) ok = 1;

                        if(ok == 0)
                        {
                                while(cnr && ok == 0)
                                {
                                        if(cnr % 10 == 6) ok = 1;
                                        cnr = cnr / 10;
                                }
                        }

                        if(ok == 0)
                                printf("B: %d\n", nr);
                        else
                                {
                                        count++;
                                        printf("B: Boltz6\n");
                                }
                        write(b2c, &count, sizeof(int));
                        write(b2c, &nr, sizeof(int));
                }close(a2b);close(b2c);exit(0);
        }

        if(fork() == 0) //C
        {
                b2c = open("fb2c", O_RDONLY);
                c2a = open("fc2a", O_WRONLY);

                while(1)
                {
                        read(b2c, &count, sizeof(int));
                        read(b2c, &nr, sizeof(int));

                        if(count >= 6) break;

                        nr += 1;
                        int cnr = nr, ok = 0;
                        if(nr % 6 == 0) ok = 1;

                        if(ok == 0)
                        {
                                while(cnr && ok == 0)
                                {
                                        if(cnr % 10 == 6) ok = 1;
                                        cnr = cnr / 10;
                                }
                        }

                        if(ok == 0)
                                printf("C: %d\n", nr);
                        else{
                                        count++;
                                        printf("C: Boltz6\n");
                                }
                        write(c2a, &count, sizeof(int));
                        write(c2a, &nr, sizeof(int));
                }
                close(b2c);close(c2a);exit(0);
        }

        printf("Enter a number:\n");
        scanf("%d", &nr);

        p2a = open("fp2a", O_WRONLY);
        write(p2a, &nr, sizeof(int));

        wait(0);wait(0);wait(0);
        unlink("fp2a");unlink("fa2b");unlink("fb2c");unlink("fc2a");
        return 0;
}
