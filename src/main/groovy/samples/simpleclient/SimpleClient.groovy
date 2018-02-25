package samples.simpleclient

import informer.Information
import informer.consumer.base.SimpleText
import informer.consumer.news.SimpleNews
import informer.consumer.weather.SimpleWeather
import informer.sources.website.blog.BlogSource
import informer.sources.website.generic.GenericWebsiteSource
import informer.sources.website.weather.WeatherSource
import informer.utils.GatherScript

class SimpleClient {


    static void main(String[] args) {

        List<Information> gatheredInformationList = []

        BlogSource blogPolizeiBerlin = new BlogSource()
        blogPolizeiBerlin.name = "Polizeimeldungen Berlin"
        blogPolizeiBerlin.url = new URL("https://www.berlin.de/polizei/polizeimeldungen/index.php/rss")
        blogPolizeiBerlin.gatherScript = new GatherScript(engine: "groovy", script: "src/main/groovy/samples/gatherscripts/groovy/news/polizeiberlin.groovy")

        BlogSource blogGolem = new BlogSource()
        blogGolem.name = "Golem"
        blogGolem.url = new URL("https://rss.golem.de/rss.php?feed=RSS2.0")
        blogGolem.gatherScript = new GatherScript(engine: "groovy", script: "src/main/groovy/samples/gatherscripts/groovy/news/golem.groovy")

        BlogSource blogHeise = new BlogSource()
        blogHeise.name = "Heise"
        blogHeise.url = new URL("https://www.heise.de/newsticker/heise-atom.xml")
        blogHeise.gatherScript = new GatherScript(engine: "groovy", script: "src/main/groovy/samples/gatherscripts/groovy/news/heise.groovy")

        SimpleNews simpleNews = new SimpleNews([blogPolizeiBerlin, blogGolem, blogHeise])

        WeatherSource weatherBerlin = new WeatherSource()
        weatherBerlin.name = "Wetter Berlin"
        weatherBerlin.url = new URL("http://htc2.accu-weather.com/widget/htc2/weather-data.asp?location=13086,DE&metric=1&langId=9")
        weatherBerlin.gatherScript = new GatherScript(engine: "groovy", script: "src/main/groovy/samples/gatherscripts/groovy/weather/accuweather.groovy")

        SimpleWeather simpleWeather = new SimpleWeather([weatherBerlin])

        GenericWebsiteSource cryptoStock = new GenericWebsiteSource()
        cryptoStock.name = "Crypto"
        cryptoStock.url = new URL("https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=EUR")
        cryptoStock.gatherScript = new GatherScript(engine: "groovy", script: "src/main/groovy/samples/gatherscripts/groovy/stock/cryptocurrencies.groovy")

        SimpleText simpleCrypto = new SimpleText(cryptoStock, cryptoStock.url)


        GenericWebsiteSource bundesliga = new GenericWebsiteSource()
        bundesliga.name = "Bundesliga Spieltag"
        bundesliga.url = new URL("https://www.openligadb.de/api/getmatchdata/bl1")
        bundesliga.gatherScript = new GatherScript(engine: "groovy", script: "src/main/groovy/samples/gatherscripts/groovy/sport/bundesligaresults.groovy")

        SimpleText simpleBundesliga = new SimpleText(bundesliga, bundesliga.url)

        def runs = 0
        while (runs < 2) {
            println "########################### run $runs"
            List<Information> informationList = simpleNews.gather()
            informationList += simpleWeather.gather()
            informationList += simpleCrypto.gather()
            informationList += simpleBundesliga.gather()

            informationList.each { information ->
                if (!gatheredInformationList.find {it.source == information.source && it.id == information.id}) {
                    gatheredInformationList << information


                    if (information.source instanceof BlogSource) {
                        println "$information.source.name | $information.content.headline"
                    } else if (information.source instanceof WeatherSource) {
                        println "$information.source.name | Temp: $information.content.temperature°C Humidity: $information.content.humidity Condition: $information.content.weathertext"
                    } else if (information.source instanceof GenericWebsiteSource) {
                        println "$information.source.name | Content: $information.content"
                    }

                }
            }
            println ""

            sleep(1000)
            runs++
        }

    }

}
