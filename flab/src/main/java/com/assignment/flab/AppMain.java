package com.assignment.flab;

import com.assignment.flab.config.AppConfig;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletRegistration;

public class AppMain {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector(); // 커넥터 초기화. Tomcat9부터는 명시적으로 반드시 호출해야한다. 아니면 포트 설정이 안되서 작동 안된다.

        Context ctx = tomcat.addContext("", null); // 루트 경로, 정적 리소스 사용X

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        // 컨텍스트 초기화 시점에 서블릿 등록
        ctx.addServletContainerInitializer((c, servletContext) -> {
            // 이름이 "dispatcher"인 서블릿 등록.
            ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
            dispatcher.setLoadOnStartup(1); // 서블릿 서버 시작 시점에 로딩되도록 설정. 숫자가 낮을수록 더 먼저 로딩됨
            dispatcher.addMapping("/"); // 모든 요청을 DispatcherServlet으로 보냄
        }, null);

        tomcat.start();
        tomcat.getServer().await(); // 이게 있어야 서버가 계속 살아있음!
    }
}
