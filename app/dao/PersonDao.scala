package dao

import javax.inject.Inject

import models.Person
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

/**
 * Created by rajbharath on 23/06/15.
 */
class PersonDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val Persons = TableQuery[PersonsTable]

  def all(): Future[Seq[Person]] = db.run(Persons.result)

  def insert(person: Person): Future[Unit] = db.run(Persons += person).map { _ => () }

  private class PersonsTable(tag: Tag) extends Table[Person](tag, "PERSON") {

    def name = column[String]("NAME", O.PrimaryKey)

    def * = (name) <>(Person.apply _, Person.unapply _)
  }

}
