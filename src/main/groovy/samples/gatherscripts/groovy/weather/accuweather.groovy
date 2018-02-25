package samples.gatherscripts.groovy.weather

import groovy.json.JsonBuilder


static def gather(URL url) {


    def feedResponseXML = new XmlSlurper().parseText(url.text)

    def currentConditions = feedResponseXML.currentconditions

    def e = [:]
    e.id = new Date().time
    e.temperature = currentConditions.temperature.toString()
    e.humidity = currentConditions.humidity.toString()
    e.weathertext = currentConditions.weathertext.toString()

    return new JsonBuilder([e]).toString()

}
