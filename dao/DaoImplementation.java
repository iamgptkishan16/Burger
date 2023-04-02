package brd.dao;

import brd.dao.entity.MasterCustomer;
import brd.dao.entity.TemporaryCustomer;
import brd.model.User;
import brd.session.SessionUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoImplementation implements DaoInterface {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean register(User user, String role) {
        String query = "insert into users values (?,?,?)";
        String query2 = "insert into authorities values(?,?)";
        int result = 0;
        if (role.equals("maker")) {
            result = this.jdbcTemplate.update(query, user.getUsername(), user.getPassword(), 1);
            this.jdbcTemplate.update(query2, user.getUsername(), "MAKER");
        } else {
            result = this.jdbcTemplate.update(query, user.getUsername(), user.getPassword(), 1);
            this.jdbcTemplate.update(query2, user.getUsername(), "CHECKER");
        }
        return result > 0;
    }

    @Override
    public List<TemporaryCustomer> getAllCustomers(){
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

    @Override
    public List<MasterCustomer> getAllMasterCustomers(){
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

    @Override
    public void insertCustomerIntoTemporary(TemporaryCustomer temporaryCustomer){
        Session session = SessionUtil.getSession();
        Transaction txt = session.beginTransaction();
        session.save(temporaryCustomer);
        txt.commit();
        session.close();
    }
    @Override
    public void deleteTemporaryCustomer(String customerCode){
        Session session = SessionUtil.getSession();
        Transaction txt = session.beginTransaction();
        Query query = session.createQuery("delete from TemporaryCustomer where customerCode = ?1");
        query.setParameter(1,customerCode);
        query.executeUpdate();
        txt.commit();
        session.close();
    }
    @Override
    public void deleteMasterCustomer(MasterCustomer customer){
        Session session = SessionUtil.getSession();
        Transaction txt = session.beginTransaction();
        session.delete(customer);
        txt.commit();
        session.close();
    }

    @Override
    public TemporaryCustomer getCustomerByCustomerCode(String customerCode){
        Session session = SessionUtil.getSession();
        Transaction txt = session.beginTransaction();
        Query query = session.createQuery("from TemporaryCustomer where customerCode = ?1");
        query.setParameter(1,customerCode);
        TemporaryCustomer customer = (TemporaryCustomer) query.getResultList().get(0);
        txt.commit();
        session.close();
        return customer;
    }
    @Override
    public MasterCustomer getMasterCustomerByCustomerCode(String customerCode){
        Session session = SessionUtil.getSession();
        Transaction txt = session.beginTransaction();
        Query query = session.createQuery("from MasterCustomer where customerCode = ?1");
        query.setParameter(1,customerCode);
        MasterCustomer customer = (MasterCustomer) query.getResultList().get(0);
        txt.commit();
        session.close();
        return customer;
    }

    @Override
    public void getCustomerModifiedByMaker(TemporaryCustomer customer){
        Session session = SessionUtil.getSession();
        Transaction txt = session.beginTransaction();
        session.update(customer);
        txt.commit();
        session.close();
    }
    @Override
    public void getMasterCustomerModified(MasterCustomer customer){
        Session session = SessionUtil.getSession();
        Transaction txt = session.beginTransaction();
        session.update(customer);
        txt.commit();
        session.close();
    }

    @Override
    public void insertCustomerIntoMaster(MasterCustomer masterCustomer){
        Session session = SessionUtil.getSession();
        Transaction txt = session.beginTransaction();
        session.save(masterCustomer);
        txt.commit();
        session.close();
    }
}
