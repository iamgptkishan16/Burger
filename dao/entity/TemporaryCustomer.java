package brd.dao.entity;

import brd.dao.entity.enums.Flag;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "customer_temporary_khushi")
public class TemporaryCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int customerId;//Y
    @Column(unique = true)
//    @Pattern(regexp = "[^a-zA-Z0-9]*$",message = "Customer code is not valid !")
    @NotNull(message = "Invalid Id. Please enter Customer code")
    private String customerCode;//Y
    @Size(max = 20, min = 3, message = "Invalid Name, Size should be between 3 to 20")
    @NotEmpty(message = "Please enter customer name")
    private String customerName;//Y
    @NotEmpty
    private String customerAddress1;//Y
    private String customerAddress2;//N
    @Digits(integer = 6, fraction = 0)
    private int customerPinCode;//Y
    @Email(message = "Invalid EmailId.Please enter proper EmailId")
    @NotEmpty(message = "Please enter your EmailId")
    private String emailAddress;//Y
    @Size(max = 10, min = 10, message = "Invalid Contact number, Size should be 10")
    @NotEmpty(message = "Please enter contact number")
    private String contactNumber;//N
    private String primaryContactPerson;//Y
    private String recordStatus;//Y
    @NotNull
    private Flag activeInactiveFlag;//Y
    private String createdBy;//Y
    private LocalDate createdDate;//Y
    private String modifiedBy;//N
    private LocalDate modifiedDate;//N
    private String authorizedBy;//N
    private LocalDate authorizedDate;//N
    

    public TemporaryCustomer() {    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress1() {
        return customerAddress1;
    }

    public void setCustomerAddress1(String customerAddress1) {
        this.customerAddress1 = customerAddress1;
    }

    public String getCustomerAddress2() {
        return customerAddress2;
    }

    public void setCustomerAddress2(String customerAddress2) {
        this.customerAddress2 = customerAddress2;
    }

    public int getCustomerPinCode() {
        return customerPinCode;
    }

    public void setCustomerPinCode(int customerPinCode) {
        this.customerPinCode = customerPinCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPrimaryContactPerson() {
        return primaryContactPerson;
    }

    public void setPrimaryContactPerson(String primaryContactPerson) {
        this.primaryContactPerson = primaryContactPerson;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Flag getActiveInactiveFlag() {
        return activeInactiveFlag;
    }

    public void setActiveInactiveFlag(Flag activeInactiveFlag) {
        this.activeInactiveFlag = activeInactiveFlag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(String authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public LocalDate getAuthorizedDate() {
        return authorizedDate;
    }

    public void setAuthorizedDate(LocalDate authorizedDate) {
        this.authorizedDate = authorizedDate;
    }


    //to string method

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerCode='" + customerCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress1='" + customerAddress1 + '\'' +
                ", customerAddress2='" + customerAddress2 + '\'' +
                ", customerPinCode=" + customerPinCode +
                ", emailAddress='" + emailAddress + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", primaryContactPerson='" + primaryContactPerson + '\'' +
                ", recordStatus='" + recordStatus + '\'' +
                ", activeInactiveFlag=" + activeInactiveFlag +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", modifiedDate=" + modifiedDate +
                ", authorizedBy='" + authorizedBy + '\'' +
                ", authorizedDate=" + authorizedDate +
                '}';
    }
}
