package samples.gatherscripts.groovy.news

import groovy.json.JsonBuilder
import org.jsoup.Jsoup

import java.text.SimpleDateFormat

static def gather(URL url) {

    def feedResponseXML = new XmlSlurper().parseText(url.text)

    def items = feedResponseXML.channel.item
    def entries = []
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)

    items[0..5].each { entry ->

        def e = [:]

        e.id = entry.link.toString()

        //TODO: Feed returns Locale.English but should be German?
        e.date = sdf.parse(entry.pubDate.toString().replace("+0100", "+0000"))


        def pageContent = Jsoup.parse(entry.link.toURL().text)

        def district = pageContent.select('.html5-section .body > .polizeimeldung')[1].text()
        e.headline = "[$district] " + entry.title.toString()

        def textile = pageContent.select('.textile').first()
        def ps = textile.select('p')
        def text = ""
        ps.each {
            it.select('strong').remove()
            text += it.text() + "\n"
        }

        e.content = text
        entries.add(e)
    }

    return new JsonBuilder(entries).toString()
}