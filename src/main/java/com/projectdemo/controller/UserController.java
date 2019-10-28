package com.projectdemo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.projectdemo.jasperreports.SimpleReportExporter;
import com.projectdemo.jasperreports.SimpleReportFiller;
import com.projectdemo.model.User;

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
		SimpleReportExporter simpleExporter = new SimpleReportExporter();

		simpleReportFiller.setReportFileName("userReport.jrxml");
		simpleReportFiller.compileReport();

		Map<String, Object> parameters = new HashMap<>();

		simpleReportFiller.setParameters(parameters);
		simpleReportFiller.fillReport();
		simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());

		simpleExporter.exportToPdf("userReport.pdf", "olonsoft");
		return null;
	}

	@Autowired
	private ServletContext servletContext;

	@GetMapping("/pdf")
	public String pdf(HttpServletResponse response) {
		response.setContentType("application/pdf");
		try {
			SimpleReportExporter simpleExporter = new SimpleReportExporter();

			simpleReportFiller.setReportFileName("userReport.jrxml");
			simpleReportFiller.compileReport();

			Map<String, Object> parameters = new HashMap<>();

			simpleReportFiller.setParameters(parameters);
			simpleReportFiller.fillReport();
			simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());

			simpleExporter.exportToPdf("userReport.pdf", "olonsoft");

			File file = new File("src/main/resources/reports/userReport.pdf");
			response.setHeader("Content-Type", servletContext.getMimeType(file.getName()));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"userReport.pdf\"");
			Files.copy(file.toPath(), response.getOutputStream());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/pdf/{fileName:.+}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> download(@PathVariable("fileName") String fileName) throws IOException {
		System.out.println("Calling Download:- " + fileName);
		ClassPathResource pdfFile = new ClassPathResource("reports/" + fileName);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Content-Disposition", "filename=" + fileName);
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		headers.setContentLength(pdfFile.contentLength());
		ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
				new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);
		return response;

	}
	
	@GetMapping("/pdf-with-parameters/{username}")
	public String pdfWithParameter(@PathVariable String username, HttpServletResponse response) {
		response.setContentType("application/pdf");
		try {
			SimpleReportExporter simpleExporter = new SimpleReportExporter();

			simpleReportFiller.setReportFileName("userReportByParameter.jrxml");
			simpleReportFiller.compileReport();

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("username", username);
			simpleReportFiller.setParameters(parameters);
			simpleReportFiller.fillReport();
			simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());

			simpleExporter.exportToPdf("userReportByParameter.pdf", "olonsoft");

			File file = new File("src/main/resources/reports/userReportByParameter.pdf");
			response.setHeader("Content-Type", servletContext.getMimeType(file.getName()));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"userReportByParameter.pdf\"");
			Files.copy(file.toPath(), response.getOutputStream());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

}
