import random
import socket
import struct

if __name__ == '__main__':
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as err:
        print('Error ', err.strerror)
        exit(-1)

    a = 1
    b = 2**17 - 1
    random.seed()

    my_numb = random.randint(a, b)

    try:
        s.send(struct.pack("!I", my_numb))
        answ = s.recv(1)
        print(answ.decode())

    except socket.error as err:
        print('Error ', err.strerror)
        exit(-2)

    s.close()
