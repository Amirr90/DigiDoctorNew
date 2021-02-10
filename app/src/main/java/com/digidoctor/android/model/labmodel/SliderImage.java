package com.digidoctor.android.model.labmodel;

public class SliderImage {

    String sliderImage;

    @Override
    public String toString() {
        return "SliderImage{" +
                "sliderImage='" + sliderImage + '\'' +
                '}';
    }

    public String getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(String sliderImage) {
        this.sliderImage = sliderImage;
    }
}
