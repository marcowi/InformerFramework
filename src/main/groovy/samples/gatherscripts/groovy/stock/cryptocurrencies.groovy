package samples.gatherscripts.groovy.stock

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper


static def gather(URL url) {


    def apiResponse = new JsonSlurper().parseText(url.text)

    def e = [:]
    e.id = new Date().time
    e.content = [:]
    apiResponse.each { k, v ->
        e.content."${k}" = v.EUR
    }

    return new JsonBuilder([e]).toString()

}