package model;

public class City {

    private int cityId;
    private String city;
    private String country;

    public City(int cityId, String city, String countryName) {
        this.cityId = cityId;
        this.city = city;
        this.country = countryName;
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

    public String getCountryName() {
        return country;
    }

    public void setCountryName(String countryName) {
        this.country = countryName;
    }

    @Override
    public String toString(){
        return city;
    }
}
