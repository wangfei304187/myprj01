import java.lang.instrument.Instrumentation;

public class MyAgent
{
    public static void premain(String args, Instrumentation inst)
    {
        System.out.println("Hi, I'm agent!");
        inst.addTransformer(new MyTransformer());
    }
}