package samples.gatherscripts.groovy.sport

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper


static def gather(URL url) {


    def apiResponse = new JsonSlurper().parseText(url.text)
    def simplifiedResults = simplifyResults(apiResponse)

    def e = [:]
    e.id = new Date().time
    e.content = simplifiedResults

    return new JsonBuilder([e]).toString()

}

static def simplifyResults(def results) {
    def simplifiedResults = []

    results.each { result ->
        def match = [:]
        match.date = result.MatchDateTime
        match.group = result.Group.GroupName
        match.team1 = [name: result.Team1.TeamName, points: result.MatchResults.collect{[(it.ResultName): it.PointsTeam1]}]
        match.team2 = [name: result.Team2.TeamName, points: result.MatchResults.collect{[(it.ResultName): it.PointsTeam2]}]
        simplifiedResults << match
    }


    return simplifiedResults
}