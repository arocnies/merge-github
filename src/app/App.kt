package app

import com.anies.kithub.http.HttpEndpoint
import com.anies.kithub.http.launch
import kotlinext.js.js
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.InputEvent
import react.*
import react.dom.*

class App : RComponent<RProps, App.State>() {
    interface State : RState {
        var head: String?
        var base: String?
        var response: String?
    }

    override fun RBuilder.render() {
        div("App-header") {
            h2 {
                +"Create merge request for arocnies/Test-Repo-256"
            }
            div("branch-select") {
                // INPUT for head (from branch)
                input {
                    attrs.id = "head"
                    attrs.defaultValue = "dev"
                    attrs.onChangeFunction = { event ->
                        setState {
                            state.head = (event.target as HTMLInputElement).value
                        }
                    }
                }

                // INPUT for base (into branch)
                input {
                    attrs.id = "base"
                    attrs.defaultValue = "master"
                    attrs.onChangeFunction = { event ->
                        setState {
                            state.base = (event.target as HTMLInputElement).value
                        }
                    }
                }
            }
            button {
                attrs.id = "create-pull-request"
                attrs.onClickFunction = { event ->
                    launch {
                        val githubApi = HttpEndpoint("https://api.github.com")
                        val body = js {
                            title = "Amazing new feature"
                            body = "Please pull this in!"
                            head = "${state.head}"
                            base = "${state.base}"
                        }

                        println("Posting: $body")
                        val response = githubApi.request("POST", "/repos/arocnies/Test-Repo-256/pulls",
                                // headers = (), TODO Add auth headers. Maybe basic auth with token instead of pass.
                                body = body)
                        println("Response: $response")

                        setState {
                            this.response = response
                        }
                    }
                }
            }
        }
        p("App-intro") {


            // Button to create pull request
        }
        p("App-body") {
            // Link to pull request
            // Response.
        }
    }
}

fun RBuilder.app() = child(App::class) {}
