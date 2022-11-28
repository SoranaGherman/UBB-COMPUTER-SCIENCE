import socket
import threading
import struct
import time


if __name__ == '__main__':
    try:
        st = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        st.bind(('localhost', 1234))
    except socket.error as err:
        print('Error', err.strerror)
        exit(-1)

    while True:
        mess, addr = st.recvfrom(128)
        mess = mess.decode()
        print('Server received message:', mess)
        time.sleep(1)
        st.sendto(mess.encode(), addr)
