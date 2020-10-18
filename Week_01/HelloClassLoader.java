package com.it.jvm.classloader;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 作业内容
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
    try {
        Class clazz = new HelloClassLoader().findClass("Hello");
        java.lang.reflect.Constructor<?> constructor = clazz.getConstructor();
        Object obj = constructor.newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(obj);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InputStream in = HelloClassLoader.class.getClassLoader().getResourceAsStream("Hello.xlass");
        byte[] bytes = new byte[0];
        try {
            bytes = new byte[in.available()];
            in.read(bytes);
            for (int i = 0; i<bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}