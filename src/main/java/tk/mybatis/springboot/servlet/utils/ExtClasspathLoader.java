package tk.mybatis.springboot.servlet.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ExtClasspathLoader {
    private static final Logger LOG = LoggerFactory.getLogger(ExtClasspathLoader.class);

    private static final String JAR_SUFFIX = ".jar";
    private static final String ZIP_SUFFIX = ".zip";

    /**
     * URLClassLoader的addURL方法
     */
    private static Method addURL = initAddMethod();

    /**
     * Application Classloader
     */
    private static URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();

    /**
     * 初始化addUrl 方法.
     *
     * @return 可访问addUrl方法的Method对象
     */
    private static Method initAddMethod() {
        try {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            add.setAccessible(true);
            return add;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过filepath加载文件到classpath。
     *
     * @param file 文件路径
     */
    private static void addURL(File file) throws Exception {
        addURL.invoke(classloader, file.toURI().toURL());
    }

    /**
     * load Resource by Dir
     *
     * @param file dir
     */
    public static void loadResource(File file) throws Exception {
        // 资源文件只加载路径
        LOG.info("load Resource of dir : " + file.getAbsolutePath());
        if (file.isDirectory()) {
            addURL(file);
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                for (File tmp : subFiles) {
                    loadResource(tmp);
                }
            }
        }
    }

    /**
     * load Classpath by Dir
     *
     * @param file Dir
     */
    public static void loadClasspath(File file) throws Exception {
        LOG.info("load Classpath of dir : " + file.getAbsolutePath());
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                for (File subFile : subFiles) {
                    loadClasspath(subFile);
                }
            }
        } else {
            if (file.getAbsolutePath().endsWith(JAR_SUFFIX) || file.getAbsolutePath().endsWith(ZIP_SUFFIX)) {
                addURL(file);
            }
        }
    }
}
