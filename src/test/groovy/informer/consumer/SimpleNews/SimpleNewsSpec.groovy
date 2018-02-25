package informer.consumer.SimpleNews

import informer.Information
import informer.consumer.news.SimpleNews
import informer.sources.website.blog.BlogSource
import informer.utils.GatherScript
import spock.lang.Specification

class SimpleNewsSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test groovy gather"() {
        given:
            GatherScript gatherScript = new GatherScript()
            gatherScript.engine = "groovy"
            gatherScript.script = "src/test/groovy/testscripts/news/blog.groovy"

            BlogSource blogSource = new BlogSource()
            blogSource.name = "Testscript"
            blogSource.url = new URL("http://shiny.url")
            blogSource.gatherScript = gatherScript

            SimpleNews simpleNews = new SimpleNews([blogSource])
            List<Information> informationList = simpleNews.gather()

        expect:
           assert informationList[0].content.headline == "Cool headline"
           assert informationList[0].content.content == "Cool content"
           assert informationList[0].content.date == "01.01.1970"
    }

    void "test js gather"() {
        given:
            GatherScript gatherScript = new GatherScript()
            gatherScript.engine = "js"
            gatherScript.script = "src/test/groovy/testscripts/news/blog.js"

            BlogSource blogSource = new BlogSource()
            blogSource.name = "Testscript"
            blogSource.url = new URL("http://shiny.url")
            blogSource.gatherScript = gatherScript

            SimpleNews simpleNews = new SimpleNews([blogSource])
            List<Information> informationList = simpleNews.gather()

        expect:
            assert informationList[0].content.headline == "Cool headline"
            assert informationList[0].content.content == "Cool content"
            assert informationList[0].content.date == "01.01.1970"
    }


}
