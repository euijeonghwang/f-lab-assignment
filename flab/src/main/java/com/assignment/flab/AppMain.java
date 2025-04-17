package com.assignment.flab;

import com.assignment.flab.config.AppConfig;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletRegistration;
import java.io.File;

public class AppMain {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context ctx = tomcat.addWebapp("", new File(".").getAbsolutePath());

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        ctx.addServletContainerInitializer((c, servletContext) -> {
            ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
            dispatcher.setLoadOnStartup(1);
            dispatcher.addMapping("/");
        }, null);

        tomcat.start();
        tomcat.getServer().await();
    }
}
