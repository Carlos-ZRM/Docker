import socket
import struct


# Set up the server
UDP_IP = "0.0.0.0"  # Listen on all available network interfaces
UDP_PORT = 9999

# Create a UDP socket
server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# Bind the socket to the IP and port
server_socket.bind((UDP_IP, UDP_PORT))

print("UDP server listening on {}:{}".format(UDP_IP, UDP_PORT))

# Start listening for incoming messages
while True:
    data, addr = server_socket.recvfrom(1024)  # Receive up to 1024 bytes of data

    # Parse UDP header
    source_port, dest_port, length, checksum = struct.unpack('!HHHH', data[:8])

    print("Received data from {}: size {}: msg {} ".format(addr, length, data.decode()))


    response_message = "Received data from {}:{}".format(addr[0], addr[1])

        # Send the response back to the client
    server_socket.sendto(response_message.encode(), addr)
