import socket
import struct
import random
import time

if __name__ == '__main__':
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as err:
        print('Error', err.strerror)
        exit(-1)

    a = 1
    b = 10
    finished = False
    answer = ""
    count_steps = 0
    my_num = 0

    mess = s.recv(1024).decode('ascii')
    print(mess)

    while not finished:
        my_num = random.randint(a, b)
        s.send(struct.pack('!I', my_num))
        answer = s.recv(1)
        count_steps += 1
        if answer == b'H':
            a = my_num
        elif answer == b'S':
            b = my_num
        elif answer == b'W' or answer == b'L':
            finished = True
        time.sleep(0.25)

    s.close()

    if answer == b'W':
        print('I am the winner with', my_num, 'in', count_steps, 'steps!')
    else:
        print('I lost!')

