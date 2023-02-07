package com.example.krishi.data;

public class WeatherResponse {

    String city,temp,humidity,rainfall,feels_like,condition;
    int id;

    public WeatherResponse(String city, String temp, String humidity, String rainfall, String feels_like, String condition,int id) {
        this.city = city;
        this.temp = temp;
        this.humidity = humidity;
        this.rainfall = rainfall;
        this.feels_like = feels_like;
        this.condition = condition;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getRainfall() {
        return rainfall;
    }

    public void setRainfall(String rainfall) {
        this.rainfall = rainfall;
    }

    public String getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(String feels_like) {
        this.feels_like = feels_like;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

}
