package brd.service;

import brd.dao.DaoInterface;
import brd.dao.entity.MasterCustomer;
import brd.dao.entity.TemporaryCustomer;
import brd.model.User;
import brd.session.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceClass {
    @Autowired
    DaoInterface dao;
    public boolean register(User user, String role){
        return dao.register(user,role);
    }

    public void insertCustomerIntoTemporary(TemporaryCustomer temporaryCustomer){dao.insertCustomerIntoTemporary(temporaryCustomer);}

    public void deleteTemporaryCustomer(String customerCode){ dao.deleteTemporaryCustomer(customerCode);}

    public TemporaryCustomer getCustomerByCustomerCode(String customerCode){return dao.getCustomerByCustomerCode(customerCode);}

    public void getCustomerModifiedByMaker(TemporaryCustomer customer){dao.getCustomerModifiedByMaker(customer);}

    public void insertCustomerIntoMaster(MasterCustomer masterCustomer){dao.insertCustomerIntoMaster(masterCustomer);}

    public void getMasterCustomerModified(MasterCustomer customer){dao.getMasterCustomerModified(customer);}

    public void deleteMasterCustomer(MasterCustomer customer){dao.deleteMasterCustomer(customer);}

    public void setTemporaryToMaster(MasterCustomer masterCustomer,TemporaryCustomer customer){
        masterCustomer.setCustomerId(customer.getCustomerId());
        masterCustomer.setCustomerCode(customer.getCustomerCode());
        masterCustomer.setCustomerName(customer.getCustomerName());
        masterCustomer.setCustomerAddress1(customer.getCustomerAddress1());
        masterCustomer.setCustomerAddress2(customer.getCustomerAddress2());
        masterCustomer.setCustomerPinCode(customer.getCustomerPinCode());
        masterCustomer.setEmailAddress(customer.getEmailAddress());
        masterCustomer.setContactNumber(customer.getContactNumber());
        masterCustomer.setPrimaryContactPerson(customer.getPrimaryContactPerson());
        masterCustomer.setRecordStatus(customer.getRecordStatus());
        masterCustomer.setActiveInactiveFlag(customer.getActiveInactiveFlag());
        masterCustomer.setCreatedBy(customer.getCreatedBy());
        masterCustomer.setCreatedDate(customer.getCreatedDate());
        masterCustomer.setModifiedBy(customer.getModifiedBy());
        masterCustomer.setModifiedDate(customer.getModifiedDate());
        masterCustomer.setAuthorizedBy(customer.getAuthorizedBy());
        masterCustomer.setAuthorizedDate(customer.getAuthorizedDate());

    }

    public void setMasterToTemporary(TemporaryCustomer masterCustomer,MasterCustomer customer){
        masterCustomer.setCustomerId(customer.getCustomerId());
        masterCustomer.setCustomerCode(customer.getCustomerCode());
        masterCustomer.setCustomerName(customer.getCustomerName());
        masterCustomer.setCustomerAddress1(customer.getCustomerAddress1());
        masterCustomer.setCustomerAddress2(customer.getCustomerAddress2());
        masterCustomer.setCustomerPinCode(customer.getCustomerPinCode());
        masterCustomer.setEmailAddress(customer.getEmailAddress());
        masterCustomer.setContactNumber(customer.getContactNumber());
        masterCustomer.setPrimaryContactPerson(customer.getPrimaryContactPerson());
        masterCustomer.setRecordStatus(customer.getRecordStatus());
        masterCustomer.setActiveInactiveFlag(customer.getActiveInactiveFlag());
        masterCustomer.setCreatedBy(customer.getCreatedBy());
        masterCustomer.setCreatedDate(customer.getCreatedDate());
        masterCustomer.setModifiedBy(customer.getModifiedBy());
        masterCustomer.setModifiedDate(customer.getModifiedDate());
        masterCustomer.setAuthorizedBy(customer.getAuthorizedBy());
        masterCustomer.setAuthorizedDate(customer.getAuthorizedDate());

    }

    public MasterCustomer getMasterCustomerByCustomerCode(String customerCode){return dao.getMasterCustomerByCustomerCode(customerCode);}
    public List<TemporaryCustomer> getAllTemporaryCustomers(){

//        return dao.getAllCustomers();

        Session session = SessionUtil.getSession();
        Transaction txt = session.beginTransaction();
        try {
            List<TemporaryCustomer> customersList = session.createQuery("from TemporaryCustomer", TemporaryCustomer.class).getResultList();
            txt.commit();
            return customersList;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
    public List<MasterCustomer> getAllMasterCustomers(){

//        return dao.getAllMasterCustomers();

        Session session = SessionUtil.getSession();
        Transaction txt = session.beginTransaction();
        try {
            List<MasterCustomer> customersList = session.createQuery("from MasterCustomer", MasterCustomer.class).getResultList();
            txt.commit();
            return customersList;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
}
