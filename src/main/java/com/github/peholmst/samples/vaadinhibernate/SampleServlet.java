package com.github.peholmst.samples.vaadinhibernate;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebServlet;

@VaadinServletConfiguration(ui = SampleUI.class, productionMode = false)
@WebServlet(urlPatterns = "/*")
public class SampleServlet extends VaadinServlet {
}
