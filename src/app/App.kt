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
                    attrs.value = state.head ?: "dev"
                    attrs.onChangeFunction = { event ->
                        val text = (event.target as HTMLInputElement).value
                        setState {
                            head = text
                        }
                    }
                }

                // INPUT for base (into branch)
                input {
                    attrs.id = "base"
                    attrs.value = state.base ?: "master"
                    attrs.onChangeFunction = { event ->
                        val text = (event.target as HTMLInputElement).value
                        setState {
                            base = text
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

                        println("Posting: ${JSON.stringify(body)}")

                        val response = githubApi.requestWithAuth(method = "POST",
                                url = "/repos/arocnies/Test-Repo-256/pulls",
                                username = "arocnies",
                                body = JSON.stringify(body),
                                password = "PUT GITHUB TOKEN HERE")

                        println("Response: ${JSON.stringify(response)}")

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
