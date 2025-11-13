# 🚀 JSON-RPC Calculator Demo Project

A comprehensive demonstration of JSON-RPC 2.0 protocol implementation featuring multiple server options (Python & Java) and a feature-rich Java client with an interactive sci-fi themed CLI interface.

## 📋 Table of Contents

- [Overview](#overview)
- [What is JSON-RPC?](#what-is-json-rpc)
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
  - [Python Server](#python-server)
  - [Java Server](#java-server)
  - [Java Client](#java-client)
- [API Documentation](#api-documentation)
- [Examples](#examples)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)

---

## 🎯 Overview

This project demonstrates a complete JSON-RPC 2.0 implementation for a calculator service. It showcases client-server architecture with:

- **Two Server Implementations**: Choose between Python or Java backend
- **Interactive Java Client**: Beautiful, user-friendly CLI with real-time server status monitoring
- **Full Calculator Operations**: Addition, subtraction, multiplication, and division
- **Production-Ready Features**: Error handling, server health checks, and comprehensive logging

Perfect for learning JSON-RPC protocol, understanding client-server communication, or as a starting point for your own RPC-based applications.

---

## 🔍 What is JSON-RPC?

**JSON-RPC** (JavaScript Object Notation Remote Procedure Call) is a stateless, light-weight remote procedure call (RPC) protocol that uses JSON to encode messages. It allows you to call functions on a remote server as if they were local functions.

### 📡 How JSON-RPC Works

```
┌─────────┐                                  ┌─────────┐
│ Client  │──── JSON-RPC Request ───────────>│ Server  │
│         │                                  │         │
│         │<─── JSON-RPC Response ───────────│         │
└─────────┘                                  └─────────┘
```

1. **Client** sends a JSON-formatted request to the server
2. **Server** processes the request and executes the method
3. **Server** returns a JSON-formatted response with the result or error

### 📝 JSON-RPC 2.0 Message Structure

#### Request Object
```json
{
  "jsonrpc": "2.0",           // Protocol version (required)
  "method": "methodName",     // Method to invoke (required)
  "params": [arg1, arg2],     // Method parameters (optional)
  "id": 1                     // Request identifier (required for non-notification)
}
```

#### Success Response
```json
{
  "jsonrpc": "2.0",           // Protocol version (required)
  "result": "returnValue",    // Method return value (required on success)
  "id": 1                     // Same as request id (required)
}
```

#### Error Response
```json
{
  "jsonrpc": "2.0",           // Protocol version (required)
  "error": {                  // Error object (required on error)
    "code": -32000,           // Error code (integer)
    "message": "Error msg"    // Error message (string)
  },
  "id": 1                     // Same as request id (required)
}
```

### ✅ Why Use JSON-RPC?

| Benefit | Description |
|---------|-------------|
| 🪶 **Lightweight** | Minimal overhead compared to SOAP or REST |
| 🔄 **Bidirectional** | Both client and server can initiate calls |
| 🌐 **Language Agnostic** | Works with any language that supports JSON |
| 📦 **Simple** | Easy to understand and implement |
| 🎯 **Precise** | Direct method calls with strong typing |
| 🔌 **Transport Independent** | Works over HTTP, WebSocket, TCP, etc. |

### 🆚 JSON-RPC vs REST

| Feature | JSON-RPC | REST |
|---------|----------|------|
| **Approach** | Method-oriented (RPC) | Resource-oriented |
| **Endpoints** | Single endpoint | Multiple endpoints |
| **Operations** | Method names | HTTP verbs (GET, POST, etc.) |
| **Payload** | Always JSON | Various formats |
| **Learning Curve** | Simpler | More concepts to learn |
| **Use Case** | Actions/Commands | CRUD operations |

### 🆚 JSON-RPC vs Java RMI

| Feature | JSON-RPC | Java RMI |
|---------|----------|----------|
| **Language Support** | Language-agnostic (any language) | Java-only (both client & server) |
| **Protocol** | Text-based (JSON) | Binary protocol |
| **Transport** | HTTP, WebSocket, TCP, etc. | Java-specific RMI protocol |
| **Firewall Friendly** | Yes (works over standard HTTP) | Often blocked by firewalls |
| **Debugging** | Easy (human-readable JSON) | Harder (binary format) |
| **Performance** | Good (JSON parsing overhead) | Better (native Java serialization) |
| **Object Support** | Primitives & JSON-compatible types | Full Java objects |
| **Platform** | Cross-platform | JVM-only |
| **Security** | HTTPS, standard web security | RMI security manager required |
| **Learning Curve** | Simple and straightforward | Steeper (RMI registry, stubs, etc.) |
| **Use Case** | Microservices, web APIs | Legacy Java systems |
| **Distributed** | Works across different platforms | Works only within Java ecosystem |

### 🎓 JSON-RPC in This Project

This demo implements JSON-RPC 2.0 with:
- ✅ **Full Protocol Compliance** - Proper request/response format
- ✅ **Error Handling** - Standard error codes and messages
- ✅ **Multiple Transports** - HTTP-based communication
- ✅ **Cross-Language** - Python server, Java server, Java client
- ✅ **Real-World Example** - Calculator service with four operations

---

## ✨ Features

### 🖥️ **Java Client** (Interactive CLI)
- **Sci-Fi Themed Interface**: Beautiful colored terminal output with Unicode symbols
- **Real-Time Server Status**: Automatic health checks with status indicators (ONLINE/OFFLINE/TIMEOUT)
- **Interactive & Command-Line Modes**: Use it interactively or as a one-shot command
- **Smart Error Handling**: Helpful hints and clear error messages
- **Request Visibility**: See JSON-RPC requests before they're sent
- **Session Statistics**: Track your calculation count

### 🐍 **Python Server**
- **Lightweight**: Minimal dependencies (jsonrpcserver + werkzeug)
- **Fast Setup**: Ready in seconds with `pip install`
- **Port**: Runs on `localhost:5000`
- **JSON-RPC 2.0 Compliant**: Full protocol support

### ☕ **Java Server**
- **Enterprise-Ready**: Built with Jetty and jsonrpc4j
- **Production Features**: Servlet-based architecture
- **Port**: Runs on `localhost:8080`
- **Robust Error Handling**: Proper exception management

### 🧮 **Calculator Operations**
- ✅ Addition
- ➖ Subtraction
- ✖️ Multiplication
- ➗ Division (with zero-division error handling)

---

## 📁 Project Structure

```
demo_project/
├── python-server/          # Python JSON-RPC server implementation
│   ├── python_server.py    # Main server file
│   ├── requirements.txt    # Python dependencies
│   └── test_*.py          # Test files
│
├── java-server/           # Java JSON-RPC server implementation
│   ├── src/main/java/
│   │   ├── JavaServer.java              # Jetty server setup
│   │   ├── CalculatorService.java       # Service interface
│   │   └── CalculatorServiceImpl.java   # Service implementation
│   └── pom.xml           # Maven configuration
│
├── java-client/          # Interactive Java client
│   ├── src/main/java/
│   │   └── CalculatorClient.java       # Client with CLI interface
│   └── pom.xml          # Maven configuration
│
└── README.md            # This file
```

---

## 🔧 Prerequisites

### For Python Server:
- Python 3.8 or higher
- pip (Python package manager)

### For Java Components:
- Java 17 or higher
- Maven 3.6 or higher

---

## 📦 Installation

### 1️⃣ Python Server Setup

```bash
cd python-server
pip install -r requirements.txt
```

### 2️⃣ Java Server Setup

```bash
cd java-server
mvn clean install
```

### 3️⃣ Java Client Setup

```bash
cd java-client
mvn clean package
```

---

## 🚀 Usage

### Python Server

**Start the server:**
```bash
cd python-server
python python_server.py
```

The server will start on `http://localhost:5000`

**Output:**
```
 * Running on http://localhost:5000
```

---

### Java Server

**Start the server:**
```bash
cd java-server
mvn exec:java -Dexec.mainClass="JavaServer"
```

Or run the compiled JAR:
```bash
java -cp target/java-server-1.0-SNAPSHOT.jar JavaServer
```

The server will start on `http://localhost:8080/calculator`

---

### Java Client

The Java client has **two modes**: Interactive and Command-line.

#### 🎮 Interactive Mode (Recommended)

```bash
cd java-client
java -jar target/java-client-1.0-SNAPSHOT.jar
```

Or with Maven:
```bash
mvn exec:java -Dexec.mainClass="CalculatorClient"
```

**Interactive Interface:**
```
▸ Initializing connection to server...
╔══════════════════════════════════════════════╗
║  ⚡ JSON-RPC POWERED CALCULATOR INTERFACE v2.0    ║
╚══════════════════════════════════════════════╝

● System Status: ONLINE
● Server Type: Python Server
● Server Host: localhost
● Server Port: 5000

● Available Operations:
  ▸ add      → Addition
  ▸ subtract → Subtraction
  ▸ multiply → Multiplication
  ▸ divide   → Division

Commands: 'help' for assistance | 'exit' or 'q' to quit
─────────────────────────────────────────────

┌─[OPERATION]
└──> add
┌─[VALUE-1]
└──> 42
┌─[VALUE-2]
└──> 8
▸ Processing quantum calculation...
▸ Sending JSON-RPC request...
  └─ {"method":"add","id":1,"jsonrpc":"2.0","params":[42,8]}
✓ RESULT: 42 + 8 = 50
```

**Interactive Commands:**
- `help` or `h` or `?` - Show help menu
- `exit` or `quit` or `q` - Exit the calculator
- Operation names: `add`, `subtract`, `multiply`, `divide`

#### ⚡ Command-Line Mode (One-Shot)

```bash
java -jar target/java-client-1.0-SNAPSHOT.jar <operation> <num1> <num2>
```

**Examples:**
```bash
# Addition
java -jar target/java-client-1.0-SNAPSHOT.jar add 10 5

# Subtraction
java -jar target/java-client-1.0-SNAPSHOT.jar subtract 25 5

# Multiplication
java -jar target/java-client-1.0-SNAPSHOT.jar multiply 7 3

# Division
java -jar target/java-client-1.0-SNAPSHOT.jar divide 20 4
```

---

## 📖 API Documentation

### JSON-RPC 2.0 Request Format

```json
{
  "jsonrpc": "2.0",
  "method": "add",
  "params": [10, 5],
  "id": 1
}
```

### Supported Methods

| Method     | Parameters | Returns | Description                    |
|------------|-----------|---------|--------------------------------|
| `add`      | [a, b]    | number  | Returns a + b                  |
| `subtract` | [a, b]    | number  | Returns a - b                  |
| `multiply` | [a, b]    | number  | Returns a × b                  |
| `divide`   | [a, b]    | number  | Returns a ÷ b (error if b = 0) |

### Response Format

**Success Response:**
```json
{
  "jsonrpc": "2.0",
  "result": 15,
  "id": 1
}
```

**Error Response (Division by Zero):**
```json
{
  "jsonrpc": "2.0",
  "error": {
    "code": -32000,
    "message": "Division by zero"
  },
  "id": 1
}
```

---

## 💡 Examples

### Example 1: Addition
**Request:**
```json
{"jsonrpc": "2.0", "method": "add", "params": [42, 8], "id": 1}
```
**Response:**
```json
{"jsonrpc": "2.0", "result": 50, "id": 1}
```

### Example 2: Division
**Request:**
```json
{"jsonrpc": "2.0", "method": "divide", "params": [100, 4], "id": 2}
```
**Response:**
```json
{"jsonrpc": "2.0", "result": 25.0, "id": 2}
```

### Example 3: Division by Zero Error
**Request:**
```json
{"jsonrpc": "2.0", "method": "divide", "params": [10, 0], "id": 3}
```
**Response:**
```json
{"jsonrpc": "2.0", "error": {"code": -32000, "message": "Division by zero"}, "id": 3}
```

---

## 🛠️ Technologies Used

### Python Server
- **jsonrpcserver** (v5.0.0+) - JSON-RPC 2.0 server implementation
- **werkzeug** (v2.0.0+) - WSGI web application library

### Java Server
- **jsonrpc4j** (v1.6) - JSON-RPC for Java
- **Eclipse Jetty** (v9.4.54) - Embedded HTTP server
- **Jackson** (v2.17.1) - JSON processing
- **Java Servlet API** (v3.1.0)

### Java Client
- **Java 17** - Latest LTS version
- **Java HTTP Client** - Built-in HTTP client (java.net.http)
- **org.json** (v20231013) - JSON parsing library
- **Maven** - Build and dependency management

---

## 🔄 Switching Between Servers

The Java client can connect to either server. To switch:

1. Open `java-client/src/main/java/CalculatorClient.java`
2. Find line 15:
   ```java
   private static final String SERVER_URL = PYTHON_SERVER;
   ```
3. Change to:
   ```java
   private static final String SERVER_URL = JAVA_SERVER;
   ```
4. Rebuild the client:
   ```bash
   mvn clean package
   ```

The client will automatically detect and display the server type on startup!

---

## 🎨 Client Features Highlights

### Server Status Indicators
- 🟢 **ONLINE** - Server is responding correctly
- 🔴 **OFFLINE** - Cannot connect to server
- 🟡 **TIMEOUT** - Server is slow to respond
- 🟡 **DEGRADED** - Server responding but with issues
- 🔴 **ERROR** - Unexpected server error

### Smart Error Messages
The client provides helpful hints for common issues:
- Invalid operations → Suggests using `help`
- Invalid numbers → Shows valid number formats
- Connection errors → Reminds to start the server

---

## 🧪 Testing

Both servers include test files in their respective directories:
- `python-server/test_*.py` - Python server tests
- Test the client by running it in interactive mode and trying all operations

---

## 📝 License

This is a demo project for educational purposes.

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

---

## 🌟 Acknowledgments

- JSON-RPC 2.0 Specification
- jsonrpcserver library maintainers
- jsonrpc4j library maintainers

---

**Made with ❤️ for learning JSON-RPC**

*Last updated: November 2025*
