import random
import socket
import struct
import threading

threads = []
my_number = random.randint(1, 2**17-1)


def worker(cs):
    print("Client has been connected!")
    print("Server number is ", my_number)
    number = cs.recv(4)
    number = struct.unpack("!I", number)[0]
    if number > my_number:
        cs.send(b'H')
    elif number < my_number:
        cs.send(b'S')
    else:
        cs.send(b'E')

    cs.close()


if __name__ == '__main__':
    try:
        rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind(('0.0.0.0', 1234))
        rs.listen(5)
    except socket.error as err:
        print('Error ', err.strerror)
        exit(-1)

    try:
        client_socket, caddr = rs.accept()
    except socket.error as err:
        print('Error ', err.strerror)
        exit(-1)

    t = threading.Thread(target=worker, args=(client_socket,))
    threads.append(t)
    t.start()

    for th in threads:
        th.join()
