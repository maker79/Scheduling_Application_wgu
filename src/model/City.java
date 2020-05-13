package model;

public class City {

    private int cityId;
    private String city;
    private int countryId; // maybe change to countryName

    public City() {
    }

    public City(int cityId) {
        this.cityId = cityId;
    }

    public City(int cityId, String city) {
        this.cityId = cityId;
        this.city = city;
    }

    public City(int cityId, String city, int countryName) {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryName;
    }

    public City(String city) {
        this.city = city;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountryName() {
        return countryId;
    }

    public void setCountryName(int countryName) {
        this.countryId = countryName;
    }

    @Override
    public String toString(){
        return city;
    }
}
