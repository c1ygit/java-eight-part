public class TempvalueTest {
    private static int tempint;
    private static char tempchar;

    private static boolean tempbool;

    public static void main(String[] args) {
        System.out.println(tempint);
        System.out.println(tempchar);
        System.out.println("是否等于 \\u0000：" + (tempchar == '\u0000')); // true
        System.out.println(tempbool);
    }

    public void testMethod() {
        // 局部变量必须要有初始值，并且不能被访问修饰符修饰
//        private int i;
//        int i;
        int i = 0;
        System.out.println(i);
    }
}
