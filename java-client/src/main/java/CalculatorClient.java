import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class CalculatorClient {

    private static final String JAVA_SERVER = "http://localhost:8080/calculator";
    private static final String PYTHON_SERVER = "http://localhost:5000";

    private static final String SERVER_URL = PYTHON_SERVER;
    private static final HttpClient client = HttpClient.newHttpClient();
    
    // ANSI Color Codes for sci-fi styling
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String BRIGHT_CYAN = "\u001B[96m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String BRIGHT_MAGENTA = "\u001B[95m";
    private static final String GREEN = "\u001B[32m";
    private static final String BRIGHT_GREEN = "\u001B[92m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BRIGHT_YELLOW = "\u001B[93m";
    private static final String RED = "\u001B[31m";
    private static final String BRIGHT_RED = "\u001B[91m";
    private static final String BLUE = "\u001B[34m";
    private static final String BRIGHT_BLUE = "\u001B[94m";
    private static final String DIM = "\u001B[2m";
    private static final String BOLD = "\u001B[1m";


    private static String checkServerStatus() {
        try {
            // Send a simple JSON-RPC request to check if server is responding
            JSONObject pingRequest = new JSONObject();
            pingRequest.put("jsonrpc", "2.0");
            pingRequest.put("method", "add");
            JSONArray params = new JSONArray();
            params.put(0);
            params.put(0);
            pingRequest.put("params", params);
            pingRequest.put("id", 0);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL))
                    .header("Content-Type", "application/json")
                    .timeout(java.time.Duration.ofSeconds(2))
                    .POST(HttpRequest.BodyPublishers.ofString(pingRequest.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if we got a valid JSON-RPC response
            if (response.statusCode() == 200) {
                JSONObject responseJson = new JSONObject(response.body());
                if (responseJson.has("jsonrpc") && responseJson.getString("jsonrpc").equals("2.0")) {
                    return "ONLINE";
                }
            }
            return "DEGRADED";
        } catch (java.net.ConnectException e) {
            return "OFFLINE";
        } catch (java.net.http.HttpTimeoutException e) {
            return "TIMEOUT";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    private static String getStatusColor(String status) {
        switch (status) {
            case "ONLINE":
                return BRIGHT_GREEN;
            case "DEGRADED":
                return BRIGHT_YELLOW;
            case "TIMEOUT":
                return YELLOW;
            case "OFFLINE":
                return BRIGHT_RED;
            case "ERROR":
                return RED;
            default:
                return YELLOW;
        }
    }

    private static String getServerTypeFromUrl() {
        if (SERVER_URL.equals(JAVA_SERVER)) {
            return "Java Server";
        } else if (SERVER_URL.equals(PYTHON_SERVER)) {
            return "Python Server";
        } else {
            return "Custom Server";
        }
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            // Interactive mode - user interaction mode in here
            runInteractiveMode();
        } else if (args.length == 3) {
            // Command-line mode: operation num1 num2 - user not interaction mode, just command line args when starting the program
            String operation = args[0].toLowerCase();
            try {
                double num1 = Double.parseDouble(args[1]);
                double num2 = Double.parseDouble(args[2]);
                performCalculation(operation, num1, num2);
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format");
                printUsage();
                System.exit(1);
            }
        } else {
            printUsage();
            System.exit(1);
        }
    }

    private static void runInteractiveMode() {
        Scanner scanner = new Scanner(System.in);
        
        // Sci-fi styled header
        printHeader();
        
        int calculationCount = 0;

        while (true) {
            System.out.print(BRIGHT_CYAN + "┌─[" + BRIGHT_MAGENTA + "OPERATION" + BRIGHT_CYAN + "]" + RESET + "\n");
            System.out.print(BRIGHT_CYAN + "└──> " + RESET);
            String operation = scanner.nextLine().trim().toLowerCase();

            if (operation.equals("exit") || operation.equals("quit") || operation.equals("q")) {
                printGoodbye(calculationCount);
                break;
            }
            
            if (operation.equals("help") || operation.equals("h") || operation.equals("?")) {
                printHelp();
                continue;
            }

            if (!isValidOperation(operation)) {
                System.out.println(BRIGHT_RED + "⚠ ERROR: " + RESET + RED + "Unknown operation '" + operation + "'" + RESET);
                System.out.println(YELLOW + "💡 Hint: Type " + BRIGHT_YELLOW + "'help'" + YELLOW + " for available operations" + RESET);
                System.out.println();
                continue;
            }

            System.out.print(BRIGHT_BLUE + "┌─[" + BRIGHT_GREEN + "VALUE-1" + BRIGHT_BLUE + "]" + RESET + "\n");
            System.out.print(BRIGHT_BLUE + "└──> " + RESET);
            String num1Str = scanner.nextLine().trim();

            System.out.print(BRIGHT_BLUE + "┌─[" + BRIGHT_GREEN + "VALUE-2" + BRIGHT_BLUE + "]" + RESET + "\n");
            System.out.print(BRIGHT_BLUE + "└──> " + RESET);
            String num2Str = scanner.nextLine().trim();

            try {
                double num1 = Double.parseDouble(num1Str);
                double num2 = Double.parseDouble(num2Str);
                
                System.out.println(DIM + CYAN + "▸ Processing JSON-RPC calculation..." + RESET);
                performCalculation(operation, num1, num2);
                calculationCount++;
            } catch (NumberFormatException e) {
                System.out.println(BRIGHT_RED + "⚠ ERROR: " + RESET + RED + "Invalid numeric input detected" + RESET);
                System.out.println(YELLOW + "💡 Hint: Please enter valid numbers (e.g., 42, 3.14, -7)" + RESET);
            }

            System.out.println(DIM + "─────────────────────────────────────────────" + RESET);
            System.out.println();
        }

        scanner.close();
    }
    
    private static void printHeader() {
        URI uri = URI.create(SERVER_URL);
        String host = uri.getHost();
        int port = uri.getPort();

        System.out.println(DIM + CYAN + "▸ Initializing connection to server..." + RESET);
        String serverStatus = checkServerStatus();
        String statusColor = getStatusColor(serverStatus);

        System.out.println(BRIGHT_CYAN + "╔══════════════════════════════════════════════╗" + RESET);
        System.out.println(BRIGHT_CYAN + "║  " + BRIGHT_MAGENTA + BOLD + "⚡ JSON-RPC POWERED CALCULATOR INTERFACE v2.0" + RESET + BRIGHT_CYAN + "    ║" + RESET);
        System.out.println(BRIGHT_CYAN + "╚══════════════════════════════════════════════╝" + RESET);
        System.out.println();
        System.out.println(GREEN + "● System Status: " + statusColor + serverStatus + RESET);
        System.out.println(CYAN + "● Server Type: " + BRIGHT_CYAN + getServerTypeFromUrl() + RESET);
        System.out.println(CYAN + "● Server Host: " + BRIGHT_CYAN + host + RESET);
        System.out.println(CYAN + "● Server Port: " + BRIGHT_CYAN + port + RESET);

        // Show warning if server is not online
        if (!serverStatus.equals("ONLINE")) {
            System.out.println();
            System.out.println(BRIGHT_YELLOW + "⚠ WARNING: " + RESET + YELLOW + "Server is " + serverStatus + ". Calculations may fail." + RESET);
            System.out.println(YELLOW + "💡 Hint: Please start the server at " + SERVER_URL + RESET);
        }

        System.out.println();
        System.out.println(CYAN + "● Available Operations:" + RESET);
        System.out.println(YELLOW + "  ▸ " + RESET + "add      " + DIM + "→ Addition" + RESET);
        System.out.println(YELLOW + "  ▸ " + RESET + "subtract " + DIM + "→ Subtraction" + RESET);
        System.out.println(YELLOW + "  ▸ " + RESET + "multiply " + DIM + "→ Multiplication" + RESET);
        System.out.println(YELLOW + "  ▸ " + RESET + "divide   " + DIM + "→ Division" + RESET);
        System.out.println();
        System.out.println(DIM + "Commands: 'help' for assistance | 'exit' or 'q' to quit" + RESET);
        System.out.println(DIM + "─────────────────────────────────────────────" + RESET);
        System.out.println();
    }
    
    private static void printHelp() {
        System.out.println();
        System.out.println(BRIGHT_CYAN + "╔══════════════════════════════════════════════╗" + RESET);
        System.out.println(BRIGHT_CYAN + "║  " + BRIGHT_YELLOW + "📖 HELP & OPERATIONS GUIDE" + RESET + BRIGHT_CYAN + "              ║" + RESET);
        System.out.println(BRIGHT_CYAN + "╚══════════════════════════════════════════════╝" + RESET);
        System.out.println();
        System.out.println(BRIGHT_GREEN + "Available Operations:" + RESET);
        System.out.println(CYAN + "  add" + RESET + "      " + DIM + "→ Adds two numbers" + RESET);
        System.out.println(CYAN + "  subtract" + RESET + " " + DIM + "→ Subtracts second from first" + RESET);
        System.out.println(CYAN + "  multiply" + RESET + " " + DIM + "→ Multiplies two numbers" + RESET);
        System.out.println(CYAN + "  divide" + RESET + "   " + DIM + "→ Divides first by second" + RESET);
        System.out.println();
        System.out.println(BRIGHT_YELLOW + "Commands:" + RESET);
        System.out.println(MAGENTA + "  help, h, ?" + RESET + " " + DIM + "→ Show this help message" + RESET);
        System.out.println(MAGENTA + "  exit, quit, q" + RESET + DIM + " → Exit the calculator" + RESET);
        System.out.println();
        System.out.println(BRIGHT_BLUE + "Example Usage:" + RESET);
        System.out.println(DIM + "  1. Enter operation (e.g., 'add')" + RESET);
        System.out.println(DIM + "  2. Enter first number (e.g., '42')" + RESET);
        System.out.println(DIM + "  3. Enter second number (e.g., '8')" + RESET);
        System.out.println();
    }
    
    private static void printGoodbye(int calculations) {
        System.out.println();
        System.out.println(BRIGHT_MAGENTA + "╔══════════════════════════════════════════════╗" + RESET);
        System.out.println(BRIGHT_MAGENTA + "║  " + BRIGHT_CYAN + "⚡ SESSION TERMINATED" + RESET + BRIGHT_MAGENTA + "                     ║" + RESET);
        System.out.println(BRIGHT_MAGENTA + "╚══════════════════════════════════════════════╝" + RESET);
        System.out.println();
        System.out.println(GREEN + "● Total Calculations: " + BRIGHT_GREEN + calculations + RESET);
        System.out.println(CYAN + "● " + RESET + DIM + "Thank you for using JSON-RPC Powered Calculator" + RESET);
        System.out.println();
        System.out.println(YELLOW + "✨ Until next time, explorer! ✨" + RESET);
        System.out.println();
    }

    private static void performCalculation(String operation, double num1, double num2) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("jsonrpc", "2.0");
            requestBody.put("method", operation);

            JSONArray params = new JSONArray();
            params.put((int) num1);
            params.put((int) num2);
            requestBody.put("params", params);
            requestBody.put("id", 1);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Display the request being sent
            System.out.println(DIM + CYAN + "▸ Sending request..." + RESET);
            System.out.println(MAGENTA + "  └─ " + RESET + DIM + requestBody.toString() + RESET);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse and display the response
            JSONObject responseJson = new JSONObject(response.body());

            if (responseJson.has("result")) {
                Object result = responseJson.get("result");
                System.out.println(BRIGHT_GREEN + "✓ RESULT: " + RESET + CYAN + (int) num1 + " " + getOperationSymbol(operation) + " " + (int) num2 + " = " + BOLD + BRIGHT_YELLOW + result + RESET);
            } else if (responseJson.has("error")) {
                JSONObject error = responseJson.getJSONObject("error");
                System.out.println(BRIGHT_RED + "✗ ERROR: " + RESET + RED + error.getString("message") + RESET);
            } else {
                System.out.println(BRIGHT_RED + "⚠ Unexpected response: " + RESET + response.body());
            }

        } catch (java.net.ConnectException e) {
            System.out.println(BRIGHT_RED + "✗ CONNECTION ERROR" + RESET);
            System.out.println(RED + "Unable to connect to server at " + SERVER_URL + RESET);
            System.out.println(YELLOW + "💡 Hint: Please ensure the server is running." + RESET);
        } catch (Exception e) {
            System.out.println(BRIGHT_RED + "✗ SYSTEM ERROR: " + RESET + RED + e.getMessage() + RESET);
            e.printStackTrace();
        }
    }

    private static boolean isValidOperation(String operation) {
        return operation.equals("add") || operation.equals("subtract") ||
               operation.equals("multiply") || operation.equals("divide");
    }

    private static String getOperationSymbol(String operation) {
        switch (operation) {
            case "add": return "+";
            case "subtract": return "-";
            case "multiply": return "*";
            case "divide": return "/";
            default: return operation;
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  Interactive mode: java CalculatorClient");
        System.out.println("  Command-line mode: java CalculatorClient <operation> <num1> <num2>");
        System.out.println("\nOperations: add, subtract, multiply, divide");
        System.out.println("\nExamples:");
        System.out.println("  java CalculatorClient add 10 5");
        System.out.println("  java CalculatorClient subtract 25 5");
        System.out.println("  java CalculatorClient multiply 7 3");
        System.out.println("  java CalculatorClient divide 20 4");
    }
}
