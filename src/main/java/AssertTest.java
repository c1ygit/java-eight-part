import java.net.SocketTimeoutException;

public class AssertTest {
    public static void main(String[] args) {
        int i = 0;
        for (; i < 5; i++) {
            System.out.println(i);
        }
        // 假设其他操作了i-1
        System.out.println(i);
        --i;
        System.out.println(i);
        assert i == 5 : "断言失败，当前i不等于5，后续代码不执行";
        System.out.println("hello");
    }

}
