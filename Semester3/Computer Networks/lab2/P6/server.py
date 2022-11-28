import random
import socket
import threading
import struct
import time

threads = []
myLock = threading.Lock()
client_guessed = False
e = threading.Event()
e.clear()  # the internal flag of that event class object is set to false
a = 1
b = 10
my_num = random.randint(a, b)
winner_thread = 0
client_count = 0


def worker(cs):
    global my_num, myLock, client_guessed, e, winner_thread, client_count

    print('Client nr', client_count, 'has entered the game!')
    message = 'Hello client nr' + str(client_count) + '!'
    cs.send(bytes(message, 'ascii'))

    while not client_guessed:
        client_num = cs.recv(4)
        client_num = struct.unpack("!I", client_num)[0]

        if client_num < my_num:
            cs.send(b'H')
        elif client_num > my_num:
            cs.send(b'S')
        else:
            myLock.acquire()  # we modify global data
            client_guessed = True
            winner_thread = threading.get_ident()
            myLock.release()

    if client_guessed:
        if winner_thread == threading.get_ident():
            cs.send(b'W')
            print('The winner is', cs.getpeername())  # ip addr
            e.set()  # reset the game
        else:
            cs.send(b'L')

    time.sleep(1)
    cs.close()


def resetSrv():
    global threads, client_guessed, e, my_num, winner_thread, client_count
    while True:
        e.wait()
        for thr in threads:
            thr.join()
        print('All threads are finished now!')
        e.clear()
        myLock.acquire()
        threads = []
        client_guessed = False
        winner_thread = -1
        my_num = random.randint(a, b)
        client_count = 0
        print('Server number: ', my_num)
        myLock.release()


if __name__ == '__main__':
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.bind(('0.0.0.0', 1234))
        s.listen(5)

    except socket.error as err:
        print('Error', err.strerror)
        exit(-1)

    th = threading.Thread(target=resetSrv, daemon=True)  # continues to run in the background
    th.start()

    while True:  # for each client we have a thread
        client_socket, addr = s.accept()
        t = threading.Thread(target=worker, args=(client_socket,))
        threads.append(t)
        client_count += 1
        t.start()
