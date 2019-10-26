package com.projectdemo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.projectdemo.config.Config;
import com.projectdemo.jasperreports.SimpleReportExporter;
import com.projectdemo.jasperreports.SimpleReportFiller;
import com.projectdemo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/")
public class UserController implements CommonController<User> {
	
	@Override
	public ModelAndView index() {
		return null;
	}
	@GetMapping("user")
	@Override
	public ModelAndView create() {
		return new ModelAndView("user/create");
	}

	@Override
	public String delete(String id) {
		return null;
	}

	@Override
	public ModelAndView edit(String id) {
		return null;
	}

	@Override
	public List<User> getAll() {
		return null;
	}

	@Override
	public User getOne(String id) {
		return null;
	}
    @PostMapping("/create-user")
	@Override
	public User store(@RequestBody User entity) {
		
		System.out.println(entity.getUsername());
		System.out.println("::::::::::::::::::::::::");

		return entity;
	}

	@Override
	public String update(HttpServletRequest request) {
		return null;
	}
	
	@Autowired
	SimpleReportFiller simpleReportFiller;
	
//	@Autowired
//	SimpleReportExporter simpleExporter;
	
	
	@GetMapping("/test")
	public String test(HttpServletRequest request) {		
		SimpleReportExporter simpleExporter= new SimpleReportExporter();
		simpleReportFiller.setReportFileName("employeeEmailReport.jrxml");
		simpleReportFiller.compileReport();

		simpleReportFiller.setReportFileName("employeeReport.jrxml");
		simpleReportFiller.compileReport();

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("title", "Employee Report Example");
		parameters.put("minSalary", 15000.0);
		parameters.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");

		simpleReportFiller.setParameters(parameters);
		simpleReportFiller.fillReport();
		simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());

		simpleExporter.exportToPdf("employeeReport.pdf", "baeldung");
		simpleExporter.exportToXlsx("employeeReport.xlsx", "Employee Data");
		simpleExporter.exportToCsv("employeeReport.csv");
		simpleExporter.exportToHtml("employeeReport.html");
		return null;
	}

}
