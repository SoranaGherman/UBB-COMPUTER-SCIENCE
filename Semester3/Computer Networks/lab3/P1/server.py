import socket
import threading
import struct
import time


def worker(cs):
    nr_r = client_socket.recv(4)
    nr = struct.unpack("!I", nr_r)[0]

    while True:
        mess = cs.recv(128).decode()
        print('Server received message:', mess)
        time.sleep(1)
        cs.send(mess.encode())


if __name__ == '__main__':
    try:
        st = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        st.bind(('localhost', 1234))
        st.listen(7)
    except socket.error as err:
        print('Error', err.strerror)
        exit(-1)


    while True:
        client_socket, addr = st.accept()
        print('Client has joined on addr', addr)
        th = threading.Thread(target=worker, args=(client_socket,))
        th.start()
