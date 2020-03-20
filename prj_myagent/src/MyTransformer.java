import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * 检测方法的执行时间
 * https://www.jianshu.com/p/f28dfbb2faa2
 */
public class MyTransformer implements ClassFileTransformer
{

    // @Override
    // public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
    // ProtectionDomain protectionDomain, byte[] classfileBuffer)
    // {
    // // java自带的方法不进行处理
    // if (className.startsWith("java") || className.startsWith("sun"))
    // {
    // return null;
    // }
    // className = className.replace("/", ".");
    // CtClass ctclass = null;
    // try
    // {
    // ctclass = ClassPool.getDefault().get(className);// 使用全称,用于取得字节码类<使用javassist>
    // for (CtMethod ctMethod : ctclass.getDeclaredMethods())
    // {
    // String methodName = ctMethod.getName();
    // String oldMethodName = methodName + "$old";// 新定义一个方法叫做比如sayHello$old
    // ctMethod.setName(oldMethodName);// sayHello --> sayHello$old
    //
    // // 创建新的方法，复制原来的方法，名字为原来的名字
    // CtMethod newMethod = CtNewMethod.copy(ctMethod, methodName, ctclass, null);
    //
    // // 构建新的方法体
    // StringBuilder bodyStr = new StringBuilder();
    // bodyStr.append("{");
    // bodyStr.append("System.out.println(\"==============Enter Method: " + className + "." + methodName
    // + " ==============\");");
    // bodyStr.append("long startTime = System.currentTimeMillis();");
    // bodyStr.append(oldMethodName + "($$);\n");// 调用原有代码，类似于method();($$)表示所有的参数
    // bodyStr.append("long endTime = System.currentTimeMillis();");
    // bodyStr.append("System.out.println(\"==============Exit Method: " + className + "." + methodName
    // + " Cost:\" +(endTime - startTime) +\"ms " + "===\");");
    // bodyStr.append("}");
    //
    // newMethod.setBody(bodyStr.toString());// 替换新方法
    // ctclass.addMethod(newMethod);// 增加新方法
    // }
    // return ctclass.toBytecode();
    // }
    // catch (Exception e)
    // {
    // e.printStackTrace();
    // }
    // return null;
    // }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer)
    {
        // java自带的方法不进行处理
        if (className.startsWith("java") || className.startsWith("sun"))
        {
            return null;
        }
        if (className.contains("AuthorizedManager"))
        {
            className = className.replace("/", ".");
            CtClass ctclass = null;
            try
            {
                ctclass = ClassPool.getDefault().get(className);// 使用全称,用于取得字节码类<使用javassist>
                for (CtMethod ctMethod : ctclass.getDeclaredMethods())
                {
                    String methodName = ctMethod.getName();
                    System.out.println("methodName=" + methodName);
                    if (methodName.equals("initialization") || methodName.equals("showAll"))
                    {
                        String oldMethodName = methodName + "$old";
                        ctMethod.setName(oldMethodName);

                        // 创建新的方法，复制原来的方法，名字为原来的名字
                        CtMethod newMethod = CtNewMethod.copy(ctMethod, methodName, ctclass, null);

                        // 构建新的方法体
                        StringBuilder bodyStr = new StringBuilder();
                        bodyStr.append("{");
                        bodyStr.append(
                                "System.out.println(\"====Enter Method: " + className + "." + methodName + " ===\");");
                        bodyStr.append("}");

                        newMethod.setBody(bodyStr.toString());// 替换新方法
                        ctclass.addMethod(newMethod);// 增加新方法
                    }
                }
                return ctclass.toBytecode();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if (className.contains("RXTXVersion"))
        {
            className = className.replace("/", ".");
            CtClass ctclass = null;
            try
            {
                ctclass = ClassPool.getDefault().get(className);// 使用全称,用于取得字节码类<使用javassist>
                for (CtMethod ctMethod : ctclass.getDeclaredMethods())
                {
                    String methodName = ctMethod.getName();
                    // System.out.println("methodName=" + methodName);
                    if (methodName.equals("getVersion"))
                    {
                        String oldMethodName = methodName + "$old";
                        ctMethod.setName(oldMethodName);

                        // 创建新的方法，复制原来的方法，名字为原来的名字
                        CtMethod newMethod = CtNewMethod.copy(ctMethod, methodName, ctclass, null);

                        // 构建新的方法体
                        StringBuilder bodyStr = new StringBuilder();
                        bodyStr.append("{");
                        bodyStr.append("return \"RXTX-2.2pre2\";");
                        bodyStr.append("}");

                        newMethod.setBody(bodyStr.toString());// 替换新方法
                        ctclass.addMethod(newMethod);// 增加新方法
                    }
                }
                return ctclass.toBytecode();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }
}