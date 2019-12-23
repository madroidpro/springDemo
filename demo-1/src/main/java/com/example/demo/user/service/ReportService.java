package com.example.demo.user.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.demo.user.User;
import com.example.demo.user.UserDaoService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {
	
	@Autowired
	private UserDaoService users;

	public String generateReport(String type) throws FileNotFoundException, JRException {
		String path= "C:\\Madhusudhan\\springboot\\reports";
		List<User> user = users.findAll();
		File file = ResourceUtils.getFile("classpath:report.jrxml");
	        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
	        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(user);
	        Map<String, Object> parameters = new HashMap<>();
	        parameters.put("createdBy", "HND6KOR");
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
	        if (type.equalsIgnoreCase("html")) {
	            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\users.html");
	        }
	        if (type.equalsIgnoreCase("pdf")) {
	            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\users.pdf");
	        }

	        return "report generated in path : " + path;
		
	}
}
