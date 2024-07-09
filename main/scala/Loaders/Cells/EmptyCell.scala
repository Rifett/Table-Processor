package Loaders.Cells

case class EmptyCell() extends Cell {
  override def evaluate(table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int] = None
}
