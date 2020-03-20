public class MyAgentTest
{
    public static void main(String[] args) throws InterruptedException
    {
        MyAgentTest mat = new MyAgentTest();
        mat.test();
        Thread.sleep((long) (Math.random() * 10));// 随机暂停0-10ms
    }

    public void test() throws InterruptedException
    {
        System.out.println("MyAgentTest::test");
        Thread.sleep((long) (Math.random() * 100));// 随机暂停0-100ms
    }
}