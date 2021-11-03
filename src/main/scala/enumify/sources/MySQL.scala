package enumify.sources

import cats.effect.IO
import doobie.Transactor
import doobie.implicits._
import enumify.{Enum, Source}

object MySQL extends Source {
  def enums(xa: Transactor[IO]): IO[List[Enum]] =
    sql"""
      select column_name, column_type
      from information_schema.columns
      where data_type = 'enum'
        and table_schema not in ('information_schema', 'mysql', 'performance_schema')
      """
      .query[(String, String)]
      .to[List]
      .transact(xa)
      .map { asd =>
        asd.map {
          case (columnName, columnType) =>
            val values = columnType
              .drop(5) // remove "enum("
              .dropRight(1) // remove ")"
              .split(",")
              .map(_.drop(1).dropRight(1)) // remove single quotes
              .toList

            Enum(columnName, values)
        }
      }
}
