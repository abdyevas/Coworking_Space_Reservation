package classloader;

import java.io.*;

public class CustomClassLoader extends ClassLoader {
    private String path;

    public CustomClassLoader(String path, ClassLoader parent) {
        super(parent);
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = path + name.replace('.', '/') + ".class";
        try (FileInputStream ins = new FileInputStream(classPath);) {
            byte[] classBytes = ins.readAllBytes();
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Error loading the class: " + name, e);
        }
    }
}
