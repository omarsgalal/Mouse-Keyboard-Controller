import socket
import json
from pynput.mouse import Button, Controller

def get_ip():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    try:
        # doesn't even have to be reachable
        s.connect(('10.255.255.255', 1))
        IP = s.getsockname()[0]
    except Exception:
        IP = '127.0.0.1'
    finally:
        s.close()
    return IP

HOST = get_ip()
PORT = 0

mouse = Controller()

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    port = s.getsockname()[1]
    print("IP: ", HOST)
    print("port: ", port)
    s.listen()
    conn, addr = s.accept()
    with conn:
        print('Connected by', addr)
        while True:
            data = conn.recv(1024)
            if not data:
                break
            data = json.loads(data.decode("utf-8"))
            if data['type'] == 'mouse_move':
                mouse.move(data['dx'], data['dy'])
            elif data['type'] == 'mouse_left_press':
                mouse.press(Button.left)
            elif data['type'] == 'mouse_left_release':
                mouse.release(Button.left)
            elif data['type'] == 'mouse_right_press':
                mouse.press(Button.right)
            elif data['type'] == 'mouse_right_release':
                mouse.release(Button.right)
            elif data['type'] == 'click':
                mouse.click(Button.left, 1)

            conn.sendall(bytes('ACK\n',encoding="utf-8"))