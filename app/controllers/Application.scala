package controllers

import javax.inject.Inject

import dao.PersonDao
import models.Person
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

class Application @Inject()(dbConfigProvider: DatabaseConfigProvider, personDao: PersonDao) extends Controller {

  def index = Action.async {
    personDao.all().map { case (persons) => Ok(views.html.index(persons)) }
  }

  val personForm = Form(
    mapping(
      "name" -> text()
    )(Person.apply)(Person.unapply)
  )

  def addPerson = Action.async { implicit request =>
    val person: Person = personForm.bindFromRequest.get
    personDao.insert(person).map(_ => Redirect(routes.Application.index))
  }


}
