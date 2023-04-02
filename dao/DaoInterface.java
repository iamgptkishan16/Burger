package brd.dao;

import brd.dao.entity.MasterCustomer;
import brd.dao.entity.TemporaryCustomer;
import brd.model.User;

import java.util.List;

public interface DaoInterface {
    public boolean register(User user,String role);

    public List<TemporaryCustomer> getAllCustomers();
    public void insertCustomerIntoTemporary(TemporaryCustomer temporaryCustomer);

    public void deleteTemporaryCustomer(String customerCode);

    public TemporaryCustomer getCustomerByCustomerCode(String customerCode);

    public void getCustomerModifiedByMaker(TemporaryCustomer customer);

    public void insertCustomerIntoMaster(MasterCustomer masterCustomer);

    public void getMasterCustomerModified(MasterCustomer customer);

    public void deleteMasterCustomer(MasterCustomer customer);

    public MasterCustomer getMasterCustomerByCustomerCode(String customerCode);

    public List<MasterCustomer> getAllMasterCustomers();

}
