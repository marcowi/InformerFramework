package informer.sources

import informer.utils.GatherScript

abstract class Source {

    String name
    GatherScript gatherScript

    abstract List<String> fields

}
