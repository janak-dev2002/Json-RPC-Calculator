import com.googlecode.jsonrpc4j.JsonRpcParam;

public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public int add(@JsonRpcParam(value="a") int a, @JsonRpcParam(value="b") int b) {
        return a + b;
    }

    @Override
    public int subtract(@JsonRpcParam(value="a") int a, @JsonRpcParam(value="b") int b) {
        return a - b;
    }

    @Override
    public int multiply(@JsonRpcParam(value="a") int a, @JsonRpcParam(value="b") int b) {
        return a * b;
    }

    @Override
    public double divide(@JsonRpcParam(value="a") int a, @JsonRpcParam(value="b") int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return (double) a / b;
    }
}
