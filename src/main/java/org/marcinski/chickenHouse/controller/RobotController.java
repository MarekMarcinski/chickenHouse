package org.marcinski.chickenHouse.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Controller
public class RobotController {

    @GetMapping(value = {"/robots", "/robot", "/robot.txt", "/robots.txt"})
    public void robot(HttpServletResponse response) throws FileNotFoundException {

        ClassLoader classLoader = getClass().getClassLoader();

        String fileName = "robot.txt";
        try(InputStream resourceAsStream =
                    classLoader.getResourceAsStream(fileName)) {

            response.addHeader("Content-disposition", "filename=" + fileName);
            response.setContentType("text/plain");
            IOUtils.copy(resourceAsStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            throw new FileNotFoundException("File " + fileName + " not found.");
        }
    }
}