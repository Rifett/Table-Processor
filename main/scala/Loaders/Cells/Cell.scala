package Loaders.Cells

trait Cell {
  def evaluate(table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int]
}