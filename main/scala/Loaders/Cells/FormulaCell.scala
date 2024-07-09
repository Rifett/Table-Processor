package Loaders.Cells
import Loaders.Cells.Evaluators.IntFormulaEvaluator

case class FormulaCell(formula: String) extends Cell {
  override def evaluate(table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int] =
    IntFormulaEvaluator().evaluate(formula, table, visited)
}
