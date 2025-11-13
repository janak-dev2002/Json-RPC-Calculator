from werkzeug.wrappers.request import Request
from werkzeug.wrappers.response import Response
from werkzeug.serving import run_simple
from jsonrpcserver import method, Success, Error, dispatch

@method
def add(a, b):
    return Success(a + b)

@method
def subtract(a, b):
    return Success(a - b)

@method
def multiply(a, b):
    return Success(a * b)

@method
def divide(a, b):
    if b == 0:
        return Error(-32000, "Division by zero")
    return Success(a / b)

def application(environ, start_response):
    request = Request(environ)
    print(f"Received request: {request.get_data().decode()}")
    response_data = dispatch(request.get_data().decode())
    response = Response(response_data, mimetype="application/json")
    return response(environ, start_response)

if __name__ == "__main__":
    run_simple("localhost", 5000, application)
