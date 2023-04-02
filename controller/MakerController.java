package brd.controller;

import brd.dao.entity.MasterCustomer;
import brd.dao.entity.TemporaryCustomer;
import brd.model.User;
import brd.service.ServiceClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;


@Controller
@Component
public class MakerController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ServiceClass serviceClass;

    //open sign up page
    @GetMapping(value="/signUp")
    public String signUp(@ModelAttribute("signupdto") User users, Model model)
    {
        User user= new User();
        model.addAttribute("signUp",user);
        return "signUp";
    }

    // register new user in users and authorities table by encoding password and return signup success page
    @GetMapping(value="/process-signup")
    public String processSignup(User users, BindingResult result, Model model)
    {
        //Encoding password
        String encodePassword=passwordEncoder.encode(users.getPassword());
        users.setPassword(encodePassword);
        //save the data into db
        serviceClass.register(users,users.getUserRole());

        return "signUpSuccess";
    }

    //perform login operation as per the role basis, if role = maker -> open maker page, if role = checker -> open checker page
    @GetMapping(value = "loginForm")
    public String getLogin(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        model.addAttribute("userName",name);
        if (auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("MAKER"))) {
            return "maker";
        }
        return "checker";
    }

    //if maker clicks on add customer button open a form for customer
    @GetMapping(value = "addCustomer")
    public String addCustomerAction(@ModelAttribute("customer") TemporaryCustomer temporaryCustomer, Model model){
        TemporaryCustomer temporaryCustomer1 = new TemporaryCustomer();
        model.addAttribute("customerForm",temporaryCustomer1);
        return "customerForm";
    }


    //after submitting the customer form insert same customer into temporary table
    @PostMapping(value="/process-customerForm")
    public String processSignup(@Valid @ModelAttribute("customer") TemporaryCustomer temporaryCustomer, BindingResult result, Model model)
    {
        if(result.hasErrors()){
            return "customerForm";
        }
        temporaryCustomer.setRecordStatus("N");
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        temporaryCustomer.setCreatedBy(name);
        temporaryCustomer.setCreatedDate(LocalDate.now());

        //save the data into db
        serviceClass.insertCustomerIntoTemporary(temporaryCustomer);
        model.addAttribute("userName",name);
        return "maker";
    }

    //after clicking on delete button for any row in maker page
    @GetMapping(value = "/deleteCustomer")
    public String deleteCustomer(@RequestParam("code") String code,Model model){
        //N,M,NR,MR,A
        TemporaryCustomer customer =serviceClass.getCustomerByCustomerCode(code);
        String customerStatus =customer.getRecordStatus();

        if(customerStatus.equals("N") || customerStatus.equals("M") || customerStatus.equals("NR") || customerStatus.equals("MR") || customerStatus.equals("DR")){
            //hard delete temporaryTable
            serviceClass.deleteTemporaryCustomer(code);
        } else if (customerStatus.equals("A")) {
            customer.setRecordStatus("D");
            serviceClass.getCustomerModifiedByMaker(customer);
        }
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        model.addAttribute("userName",name);
        return "maker";
    }

    //open update form to modify a customer by checker
    @GetMapping(value = "/modifyCustomer")
    public String modifyCustomerForm(@RequestParam("code") String code, ModelMap model){
        TemporaryCustomer customer =serviceClass.getCustomerByCustomerCode(code);
        model.addAttribute("customerCode",customer.getCustomerCode());
        model.addAttribute("customerName", customer.getCustomerName());
        model.addAttribute("customerAddress1", customer.getCustomerAddress1());
        model.addAttribute("customerAddress2", customer.getCustomerAddress2());
        model.addAttribute("customerPinCode", customer.getCustomerPinCode());
        model.addAttribute("emailAddress", customer.getEmailAddress());
        model.addAttribute("contactNumber", customer.getContactNumber());
        model.addAttribute("primaryContactPerson", customer.getPrimaryContactPerson());
        model.put("customer", customer);
        return "updateForm";
    }

    //modify the customer
    @PostMapping(path="/modifyCustomer")
    public String updateCustomer(@Valid @ModelAttribute("customer") TemporaryCustomer customer,BindingResult result,Model model){
        if(result.hasErrors()){
            return "updateForm";
        }
        TemporaryCustomer customer1 = serviceClass.getCustomerByCustomerCode(customer.getCustomerCode());

        String recordStatus = customer1.getRecordStatus();

        customer1.setCustomerName(customer.getCustomerName());
        customer1.setCustomerAddress1(customer.getCustomerAddress1());
        customer1.setCustomerAddress2(customer.getCustomerAddress2());
        customer1.setCustomerPinCode(customer.getCustomerPinCode());
        customer1.setEmailAddress(customer.getEmailAddress());
        customer1.setContactNumber(customer.getContactNumber());
        customer1.setPrimaryContactPerson(customer.getPrimaryContactPerson());
        customer1.setActiveInactiveFlag(customer.getActiveInactiveFlag());

        // N -> N
        // M -> M (temp update, master same A)
        // NR -> N (temp update, master same A)
        // MR -> M (temp update, master same A)
        // A -> M (temp update, master same A)

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        customer1.setModifiedBy(name);
        customer1.setModifiedDate(LocalDate.now());

        if(recordStatus.equals("M") ||recordStatus.equals("MR") || recordStatus.equals("A"))
            customer1.setRecordStatus("M");
        else if (recordStatus.equals("N") || recordStatus.equals("NR")) {
            customer1.setRecordStatus("N");
        }else if (recordStatus.equals("DR")){
            customer1.setRecordStatus("D");
        }

        serviceClass.getCustomerModifiedByMaker(customer1);

        model.addAttribute("userName",name);

        return "maker";
    }

    // master table se fetch details into temporary table
    @GetMapping(value = "editCustomer")
    public String editCustomer(@RequestParam("code") String code,Model model){
        MasterCustomer masterCustomer = serviceClass.getMasterCustomerByCustomerCode(code);
        TemporaryCustomer temporaryCustomer = new TemporaryCustomer();
        serviceClass.setMasterToTemporary(temporaryCustomer,masterCustomer);

        serviceClass.insertCustomerIntoTemporary(temporaryCustomer);
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        model.addAttribute("userName",name);
        return "maker";
    }

    @GetMapping(value = "returnBack")
    public String returnBackToHomePage(){
        return "maker";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        System.out.println("handleException - Catching: " + ex.getClass().getSimpleName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exception");
        modelAndView.addObject("name", "Controller Exception Handler");
        return modelAndView;
    }
}
