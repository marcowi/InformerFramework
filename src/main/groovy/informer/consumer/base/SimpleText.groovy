package informer.consumer.base

import informer.Information
import informer.consumer.Consumer
import informer.sources.Source

class SimpleText extends Consumer {

    Source source
    Object params

    SimpleText(Source source, Object params) {
        this.source = source
        this.params = params
    }

    @Override
    List<Information> gather() {
        gatherInformation(this.source, this.params)
    }
}
