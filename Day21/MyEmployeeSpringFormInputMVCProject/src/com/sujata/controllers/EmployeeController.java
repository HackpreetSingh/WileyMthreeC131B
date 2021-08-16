package com.sujata.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sujata.bean.Employee;
import com.sujata.bean.EmployeePaySlip;
import com.sujata.model.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping("/")
	public ModelAndView welcomePageController() {
		return new ModelAndView("index");
	}
	
	@RequestMapping("/insertEmpPage")
	public ModelAndView insertEmpPageController(){
//		return new ModelAndView("insertEmp","command",new Employee());
		return new ModelAndView("insertEmp","emp",new Employee());
	}
	
	@ModelAttribute("empIds")
	public List<Integer> getAllIds(){
		return employeeService.getAllEmployees().stream()
				.map(Employee::getEmpId).collect(Collectors.toList());
	}
	
	@RequestMapping("/save")
	public ModelAndView insertEmployee(@ModelAttribute("emp") Employee employee/*HttpServletRequest request*/) {
		ModelAndView modelAndView=new ModelAndView();
		
//		Employee employee=new Employee();
//		employee.setEmpId(Integer.parseInt(request.getParameter("empID")));
//		employee.setEmpName(request.getParameter("empName"));
//		employee.setDesignation(request.getParameter("empDesig"));
//		employee.setDepartment(request.getParameter("empDeptt"));
//		employee.setEmpSalary(Integer.parseInt(request.getParameter("empSalary")));
		String message=null;
		if(employeeService.insertEmployee(employee))
			message="Employee Added Succesfully";
		else
			message="Employee Can't be Added";
		
		modelAndView.addObject("message", message);
		modelAndView.setViewName("output");
		
		return modelAndView;
	}
	
	@RequestMapping("/deleteEmpPage")
	public ModelAndView deleteEmpPageController() {
		return new ModelAndView("deleteEmp");
	}
	
	@RequestMapping("/delete")
	public ModelAndView deleteEmployee(@RequestParam("empId") int eid/*HttpServletRequest request*/) {
		ModelAndView modelAndView=new ModelAndView();
		String message=null;
//		if(employeeService.deleteEmployee(Integer.parseInt(request.getParameter("empId"))))
		if(employeeService.deleteEmployee(eid))
			message="Employee Record Deleted";
		else
			message="Employee Record Not Deleted";
		
		modelAndView.addObject("message", message);
		modelAndView.setViewName("output");
		
		return modelAndView;
	}
	
	@RequestMapping("/updateEmpPage")
	public ModelAndView updateEmployeePageController() {
		return new ModelAndView("updateEmp","command",new Employee());
	}
	
	@RequestMapping("/updateEmp")
	public ModelAndView updateEmployeeController(@ModelAttribute Employee employee/*HttpServletRequest request*/) {
//		int id=Integer.parseInt(request.getParameter("empId"));
//		int salary=Integer.parseInt(request.getParameter("empSalary"));
		
		String message=null;
		if(employeeService.UpdateEmployeeSalary(employee.getEmpId(), employee.getEmpSalary()))
			message="Employee Salary Updated Succesfully";
		else
			message="Employee Salary not Updated";
		
		return new ModelAndView("output", "message", message);
	}
	
	@RequestMapping("/searchEmpPage")
	public ModelAndView searchEmpPageController() {
		return new ModelAndView("searchEmp");
	}
	
	@RequestMapping("/searchEmp")
	public ModelAndView searchEmpController(HttpServletRequest request) {
		Employee employee=employeeService.searchEmployeeById(Integer.parseInt(request.getParameter("empId")));
		return new ModelAndView("showEmp", "employee", employee);
	}
	
	@RequestMapping("/generatePaySlipPage")
	public ModelAndView generatePaySlipPageController() {
		return new ModelAndView("inputEmpIdForPaySlip");
	}
	
	@RequestMapping("/payslip")
	public ModelAndView paysipController(HttpServletRequest request) {
		EmployeePaySlip payslip=employeeService.getPaySlip(Integer.parseInt(request.getParameter("empId")));
		
		return new ModelAndView("showPayslip", "payslip", payslip);
	}
	
	@RequestMapping("/displayAllEmps")
	public ModelAndView displayAllEmployeesController() {
		Collection<Employee> emps=employeeService.getAllEmployees();

		return new ModelAndView("displayAllEmployees", "employees", emps);
	}
}
