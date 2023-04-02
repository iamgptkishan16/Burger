package brd.controller;

import brd.dao.entity.MasterCustomer;
import brd.dao.entity.TemporaryCustomer;
import brd.service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@Component
public class CheckerController {
    @Autowired
    ServiceClass serviceClass;

    @GetMapping(value = "approveCustomer")
    public String approveCustomerByChecker(@RequestParam("code") String code){
        TemporaryCustomer customer = serviceClass.getCustomerByCustomerCode(code);

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();

        MasterCustomer masterCustomer = new MasterCustomer();

        String statusTillNow = customer.getRecordStatus();   //temporary table vala status

        if(statusTillNow.equals("D")){
            //delete from temporary + master table
            serviceClass.setTemporaryToMaster(masterCustomer,customer);
            serviceClass.deleteTemporaryCustomer(customer.getCustomerCode());
            serviceClass.deleteMasterCustomer(masterCustomer);
        }else{

            customer.setRecordStatus("A");
            customer.setAuthorizedBy(name);
            customer.setAuthorizedDate(LocalDate.now());

            //temporary customer details into masterCustomer details
            serviceClass.setTemporaryToMaster(masterCustomer,customer);

            if(statusTillNow.equals("N"))  {
                // push record into master with status A and delete from temporary table
                serviceClass.insertCustomerIntoMaster(masterCustomer);
                serviceClass.deleteTemporaryCustomer(customer.getCustomerCode());
            }
            else if(statusTillNow.equals("M"))  {
                // temporary table remains same and masterTable gets updated with status A
                serviceClass.getCustomerModifiedByMaker(customer); //modify temporary customer
                serviceClass.getMasterCustomerModified(masterCustomer);
            }
        }
        return "checker";
    }


    @GetMapping(value = "rejectCustomer")
    public String rejectCustomerByChecker(@RequestParam("code") String code){
        TemporaryCustomer customer = serviceClass.getCustomerByCustomerCode(code);

        String statusTillNow = customer.getRecordStatus();

        if(statusTillNow.equals("N")){
            //NR
            customer.setRecordStatus("NR");
        } else if (statusTillNow.equals("M")) {
            //MR
            customer.setRecordStatus("MR");
        } else if (statusTillNow.equals("D")) {
            //DR
            customer.setRecordStatus("DR");
        }

        serviceClass.getCustomerModifiedByMaker(customer);

        return "checker";
    }
}
