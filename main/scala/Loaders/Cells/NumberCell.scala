package Loaders.Cells

case class NumberCell(value: Int) extends Cell {
  override def evaluate(table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int] = Some(value)
}
