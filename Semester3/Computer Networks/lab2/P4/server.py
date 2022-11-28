import socket
import threading
import struct
import time

client_count = 0
merge_list = []
my_lock = threading.Lock()
threads = []


def merge(arr, l, m, r):
    n1 = m - l + 1
    n2 = r - m

    L = [0] * (n1)
    R = [0] * (n2)

    for i in range(0, n1):
        L[i] = arr[l + i]

    for j in range(0, n2):
        R[j] = arr[m + 1 + j]

    i = 0
    j = 0
    k = l

    while i < n1 and j < n2:
        if L[i] <= R[j]:
            arr[k] = L[i]
            i += 1
        else:
            arr[k] = R[j]
            j += 1
        k += 1

    while i < n1:
        arr[k] = L[i]
        i += 1
        k += 1

    while j < n2:
        arr[k] = R[j]
        j += 1
        k += 1


def mergeSort(arr, l, r):
    if l < r:
        m = l + (r - l) // 2

        mergeSort(arr, l, m)
        mergeSort(arr, m + 1, r)
        merge(arr, l, m, r)


def worker(cs):
    global client_count, merge_list
    finish = False
    print('Client nr', client_count, 'has entered the game!')
    message = 'Hello client nr' + str(client_count) + '!'
    cs.send(bytes(message, 'ascii'))

    while not finish:
        n = cs.recv(4)
        n = struct.unpack("!I", n)[0]
        if n == 0:
            finish = True

        if n != 0:
            for i in range(n):
                x = cs.recv(4)
                x = struct.unpack("!I", x)[0]

                my_lock.acquire()
                merge_list.append(x)
                my_lock.release()

        print(merge_list)
        my_lock.acquire()
        if n != 0:
            mergeSort(merge_list, 0, len(merge_list)-1)
            my_lock.release()

        cs.send(struct.pack("!I", len(merge_list)))
        for nr in merge_list:
            cs.send(struct.pack("!I", nr))

    cs.close()
    time.sleep(1)
    print('Thread ', client_count, 'ended!')
    exit(0)


if __name__ == '__main__':
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.bind(('0.0.0.0', 55555))
        s.listen(5)

    except socket.error as err:
        print('Error', err.strerror)
        exit(-1)

    while True:  # for each client we have a thread
        client_socket, addr = s.accept()
        t = threading.Thread(target=worker, args=(client_socket,))
        threads.append(t)
        client_count += 1
        t.start()

