import threading
import time
import socket
import sys
import struct


def send_with_interval(cs):
    while True:
        mess = "PING"
        cs.sendto(mess.encode(), ('localhost', 1234))
        t1 = time.time()
        mess_r, addr = cs.recvfrom(128)
        t2 = time.time()
        if mess_r.decode() == mess:
            print(mess, 'in ', t2 - t1, 'seconds!')
        else:
            print('Not the same message')


if __name__ == '__main__':
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    except socket.error as err:
        print('Error', err.strerror)
        exit(-1)

    th = threading.Timer(2, send_with_interval, args=(s,)).start()


