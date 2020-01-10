package com.luban.proxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 手写动态代理
 * <p>
 * UserDao proxy = (UserDao) ProxyUtil.getInstance(new UserDaoImpl());
 *
 * @author Kyrie
 * @create 2020-01-09 17:24
 */
public class ProxyUtil {

    /**
     * content --->string
     * .java  io
     * .class
     * .new   反射----》class
     */

    /**
     * 参数target         目标对象   如 UserDaoImpl implements UserDao
     * 返回值Object      生成的代理对象
     */
    public static Object getInstance(Object target) {

        // 1、content --->string 生成字符串
        Object proxy = null;
        // interface com.luban.dao.UserDao
        Class targetInf = target.getClass().getInterfaces()[0];
        Method methods[] = targetInf.getDeclaredMethods();
        String line = "\n";
        String tab = "\t";
        // UserDao
        String infName = targetInf.getSimpleName();
        String content = "";
        String packageContent = "package com.google;" + line;
        String importContent = "import " + targetInf.getName() + ";" + line;
        String clazzFirstLineContent = "public class $Proxy implements " + infName + "{" + line;
        String filedContent = tab + "private " + infName + " target;" + line;
        String constructorContent = tab + "public $Proxy (" + infName + " target){" + line
                + tab + tab + "this.target =target;"
                + line + tab + "}" + line;
        String methodContent = "";
        for (Method method : methods) {
            String returnTypeName = method.getReturnType().getSimpleName();
            String methodName = method.getName();
            // Sting.class String.class
            Class args[] = method.getParameterTypes();
            String argsContent = "";
            String paramsContent = "";
            int flag = 0;
            for (Class arg : args) {
                String temp = arg.getSimpleName();
                //String
                //String p0,Sting p1,
                argsContent += temp + " p" + flag + ",";
                paramsContent += "p" + flag + ",";
                flag++;
            }
            if (argsContent.length() > 0) {
                argsContent = argsContent.substring(0, argsContent.lastIndexOf(","));
                paramsContent = paramsContent.substring(0, paramsContent.lastIndexOf(","));
            }

            methodContent += tab + "public " + returnTypeName + " " + methodName + "(" + argsContent + ") {" + line
                    + tab + tab + "System.out.println(\"hello啊啊啊\");" + line;
                    if("void".equals(returnTypeName)){
                        methodContent +=tab + tab + "target." + methodName + "(" + paramsContent + ");" + line
                                + tab + "}" + line;
                    }
                    else {
                        methodContent +=tab + tab + "return target." + methodName + "(" + paramsContent + ");" + line
                                + tab + "}" + line;
                    }
        }
        content = packageContent + importContent + clazzFirstLineContent + filedContent + constructorContent + methodContent + "}";

        // 2、.java  io 将字符串写到一个.java文件
        File file = new File("g:\\com\\google\\$Proxy.java");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.flush();
            fw.close();


            // 3、.class 将java文件编译成.class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
            Iterable units = fileMgr.getJavaFileObjects(file);
            JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
            t.call();
            fileMgr.close();

            // 4、.new 创建代理类的对象
            URL[] urls = new URL[]{new URL("file:g:\\\\")};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class clazz = urlClassLoader.loadClass("com.google.$Proxy");
            Constructor constructor = clazz.getConstructor(targetInf);
            proxy = constructor.newInstance(target);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return proxy;
    }
}
