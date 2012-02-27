package triana.bundle;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by IntelliJ IDEA.
 * User: Ian Harvey
 * Date: 27/02/2012
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */

public class ClassRegister extends URLClassLoader {

    File[] addonRoots;
    HashMap<String, Class> addons = new HashMap<String, Class>();
    ArrayList<Class> services = new ArrayList<Class>();

    public ClassRegister(URL[] urls, ClassLoader classLoader) {
        super(urls, classLoader);

        services.add(Executor.class);

        try {
            findJars();
        } catch (Exception e) {
            e.printStackTrace();
        }
        findFiles();
        System.out.println("\nFound " + addons.size() + " addon(s).\n");
    }

    private void findFiles() {

    }

    private void findJars() throws ClassNotFoundException, URISyntaxException {
        URL[] urls = this.getURLs();
        for (URL url : urls) {
            findJars(new File(url.toURI()));
        }
    }

    public Object getWorkflowExecutor(String classType) {
        return addons.get(classType);
    }

    private void findJars(File dir) {
        addonRoots = dir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return !file.isHidden() && file.getName().endsWith(".jar");
            }
        });
        System.out.println("Found " + addonRoots.length + " jars in addon folder.");

        URL[] urls = new URL[addonRoots.length];
        for (int i = 0; i < addonRoots.length; i++) {
            File addonRoot = addonRoots[i];
            try {
                urls[i] = addonRoot.toURI().toURL();
                addURL(urls[i]);
            } catch (MalformedURLException ignored) {
            }
        }

        for (File addonRoot : addonRoots) {
            try {
                JarFile jarFile = new JarFile(addonRoot);
                ZipEntry entry = jarFile.getEntry("META-INF/services/");
                if (entry != null) {
                    System.out.println("Found META-INF/services/ folder");
                    for (Class service : services) {
                        ZipEntry e = jarFile.getEntry("META-INF/services/" + service.getCanonicalName());
                        if (e != null) {
                            InputStream zin = jarFile.getInputStream(e);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(zin));
                            String line;
                            List<String> done = new ArrayList<String>();
                            while ((line = reader.readLine()) != null) {
                                // check if the class is in this jar
                                ZipEntry sp = jarFile.getEntry(line.replace(".", "/") + ".class");
                                if (sp != null) {
                                    try {
                                        if (!done.contains(line)) {

                                            System.out.println("Registering addon class : " + line);
                                            Class cls = this.loadClass(line);
                                            if (service.isAssignableFrom(cls)) {
                                                Object prov = cls.newInstance();

                                                addons.put(service.getCanonicalName(), cls);
                                            }
                                            done.add(line);
                                        }
                                    } catch (Exception e1) {
                                        System.out.println("Exception thrown trying to load service provider class " + line);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("no entries in META-INF/services folder");
                }
            } catch (IOException ignored) {
                System.out.println("IOException : " + addonRoot.getName());
            }
        }
    }

//   private void findFiles(File dir) {
//       File[] addons = dir.listFiles(new FileFilter() {
//           public boolean accept(File file) {
//               return !file.isHidden() && !file.isDirectory() &&
//file.getName().endsWith(".class");
//           }
//       });
//
//       FileClassLoader loader = new FileClassLoader();
//       for (File file : addons) {
//           Class newClass = loader.createClass(file);
//
//           if (newClass != null) {
//               runClass(newClass, file);
//           } else {
//               System.out.println("Not running " + file.getName());
//           }
//       }
//   }

    private void runClass(Class newClass, File file) {
        if (newClass != null) {
            System.out.println("\nGot class " + newClass.getName() + "from " + file.getName());

            Class[] interfaces = newClass.getInterfaces();

            if (interfaces.length == 0) {
                System.out.println(newClass.getCanonicalName() + " has no interfaces listed.");
            }

            for (Class anInterface : interfaces) {
                System.out.println("Class implements " +
                        anInterface.getCanonicalName());
                if
                        (anInterface.getCanonicalName().equals("TriPeg.WorkflowExecutor")) {
                    System.out.println("***Found a workflow addon!!***");
//                    for(Method method : newClass.getDeclaredMethods()){
//                        System.out.println("Found method " + method.toGenericString() + " in class " + newClass.getCanonicalName());
//                    }
                    addons.put(anInterface.getCanonicalName(), newClass);
                }
            }
            runMain(newClass, file);
        }
    }

    private void runMain(Class newClass, File file) {
        try {
            Method main = newClass.getDeclaredMethod("main", String[].class);
            main.invoke(newClass, (String) null);
        } catch (NoSuchMethodException e) {
            System.out.println("main() method not found in " + file.getName());
        } catch (InvocationTargetException e) {
            System.out.println("Failed to run main() method in " +
                    file.getName());
        } catch (IllegalAccessException e) {
            System.out.println("Wrong permissions for main() method in" + file.getName());
        }
    }
}