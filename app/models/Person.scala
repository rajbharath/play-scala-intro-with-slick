package models

import play.api.libs.json.Json

/**
 * Created by rajbharath on 22/06/15.
 */
case class Person(name: String)

object Person {
  implicit val personFormat = Json.format[Person]
}
