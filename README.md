# Fx File Transfer

A simple Java client-server application for uploading and downloading files over a network, using raw Sockets and a custom text-based protocol.

## Overview

This project demonstrates low-level network programming in Java: a server listens for connections, and a client connects to it to either download a file from the server or upload a file to it. Communication follows a strict protocol defined between the two programs.

## Architecture

- **FxServer**: listens on port 9090, accepts client connections, and handles `download`/`upload` requests.
- **FxClient**: connects to the server and sends a request based on user input (command-line arguments).

## Protocol

**Download:**

Client → Server : download <fileName>\n
Server → Client : NOT FOUND\n
or : OK <fileSize>\n + [file bytes]


**Upload:**

Client → Server : upload <fileName> <fileSize>\n + [file bytes]
Server → Client : STORED\n
or : FAILED\n


## How to run

1. Compile both files:

javac src/FxServer.java src/FxClient.java


2. Start the server:

java -cp src FxServer


3. In another terminal, run the client:

java -cp src FxClient d fileName.txt # download
java -cp src FxClient u fileName.txt # upload


Files to download must be placed in `ServerShare/`. Files to upload must be placed in `ClientShare/`.

## Technologies

- Java (Socket API, java.io streams)
- Git / GitHub