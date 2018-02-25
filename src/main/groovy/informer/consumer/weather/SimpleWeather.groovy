package informer.consumer.weather

import informer.Information
import informer.consumer.Consumer
import informer.sources.website.weather.WeatherSource

class SimpleWeather extends Consumer {

    List<WeatherSource> sourceList

    SimpleWeather(List<WeatherSource> sourceList) {
        this.sourceList = sourceList
    }

    @Override
    List<Information> gather() {
        List<Information> informationList = []

        sourceList.each { source ->
            informationList += gatherInformation(source, [source.url])
        }

        return informationList
    }

}
