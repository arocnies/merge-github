package index

import app.*
import kotlinext.js.*
import react.dom.*
import kotlin.browser.*

fun main(args: Array<String>) {
    require("src/index/index.css")
    require("src/app/App.css")

    render(document.getElementById("root")) {
        app()
    }
}
