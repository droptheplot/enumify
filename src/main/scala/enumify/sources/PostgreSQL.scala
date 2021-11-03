package enumify.sources

import cats.effect.IO
import doobie.Transactor
import doobie.postgres.implicits._
import doobie.implicits._
import enumify.{Enum, Source}

object PostgreSQL extends Source {
  def enums(xa: Transactor[IO]): IO[List[Enum]] =
    sql"""
      select t.typname as name,
             array_agg(e.enumlabel) as values
      from pg_type t
      join pg_enum e on t.oid = e.enumtypid
      join pg_catalog.pg_namespace n on n.oid = t.typnamespace
      group by name
      order by name;
      """
      .query[Enum]
      .to[List]
      .transact(xa)
}
