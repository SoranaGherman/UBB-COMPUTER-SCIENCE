import threading
import time
import socket
import sys
import struct


def send_with_interval(cs):
    while True:
        mess = "PING"
        cs.send(mess.encode())
        t1 = time.time()
        mess_r = cs.recv(128).decode()
        t2 = time.time()
        if mess_r == mess:
            print(mess, 'in ', t2 - t1, 'seconds!')
        else:
            print('Not the same message')


if __name__ == '__main__':
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as err:
        print('Error', err.strerror)
        exit(-1)

    client_port = int(sys.argv[1])
    s.send(struct.pack("!I", client_port))

    th = threading.Timer(2, send_with_interval, args=(s,)).start()


