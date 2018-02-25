package informer.sources.website.weather

import informer.sources.Source

class WeatherSource extends Source {

    URL url
    List<String> fields = ['temperature', 'humidity', 'weathertext']

}
