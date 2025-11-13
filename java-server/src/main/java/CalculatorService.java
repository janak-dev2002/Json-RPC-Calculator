import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("/calculator")
public interface CalculatorService {
    int add(@JsonRpcParam(value="a") int a, @JsonRpcParam(value="b") int b);
    int subtract(@JsonRpcParam(value="a") int a, @JsonRpcParam(value="b") int b);
    int multiply(@JsonRpcParam(value="a") int a, @JsonRpcParam(value="b") int b);
    double divide(@JsonRpcParam(value="a") int a, @JsonRpcParam(value="b") int b);
}
