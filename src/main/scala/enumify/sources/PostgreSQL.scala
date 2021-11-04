package enumify.sources

import cats.effect.IO
import doobie.{Get, Transactor}
import doobie.postgres.implicits._
import doobie.implicits._
import enumify.{Enum, Source}

object PostgreSQL extends Source {
  def enums(xa: Transactor[IO]): IO[List[Enum]] =
    sql"""
      select
        nspname,
        pg_type.typname,
        array_agg(pg_enum.enumlabel)
      from pg_type
      join pg_catalog.pg_namespace on pg_namespace.oid = pg_type.typnamespace
      join pg_enum on pg_type.oid = pg_enum.enumtypid
      group by nspname, typname;
      """
      .query[(String, String, List[String])]
      .to[List]
      .transact(xa)
      .map { rows =>
        rows.map {
          case (schema, name, values) =>
            Enum(schema, name, values.map(Enum.Value.apply))
        }
      }
}
