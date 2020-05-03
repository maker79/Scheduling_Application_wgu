package model;

public class City {

    private int cityId;
    private String city;
    private int countryName; // maybe change to countryName

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
        this.countryName = countryName;
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
        return countryName;
    }

    public void setCountryName(int countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString(){
        return city;
    }
}
