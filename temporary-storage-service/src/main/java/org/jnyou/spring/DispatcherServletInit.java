package org.jnyou.spring;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class DispatcherServletInit extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    protected void initStrategies(ApplicationContext context) {
//        DispatcherServlet.initMultipartResolver(context);
//        DispatcherServlet.initLocaleResolver(context);
//        DispatcherServlet.initThemeResolver(context);
//        DispatcherServlet.initHandlerMappings(context);
//        DispatcherServlet.initHandlerAdapters(context);
//        DispatcherServlet.initHandlerExceptionResolvers(context);
//        DispatcherServlet.initRequestToViewNameTranslator(context);
//        DispatcherServlet.initViewResolvers(context);
//        DispatcherServlet.initFlashMapManager(context);
    }
}
