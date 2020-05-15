package model;

public class Customer {

    private int customerId;
    private String customerName;
    private int addressId;
    private String address;
    private String address2;
    private int cityId;
//    private String country;
    private String postalCode;
    private String phone;



    public Customer(int customerId, String customerName, int addressId, String address, String address2, int cityId, String postalCode, String phone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
//        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public Customer() {

    }

//    public Customer(int customerId, String customerName, String address, String phone) {
//        this.customerId = customerId;
//        this.customerName = customerName;
//        this.address = address;
//        this.phone = phone;
//    }
//
//    public Customer(int customerId, String customerName, String address, int cityid, String zipCode, String phone) {
//        this.customerId = customerId;
//        this.customerName = customerName;
//        this.address = address;
//        this.cityId = cityId;
//        this.postalCode = zipCode;
//        this.phone = phone;
//    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getCity() {
        return cityId;
    }

    public void setCity(int cityId) {
        this.cityId = cityId;
    }

//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return customerName;
    }
}
