package Loaders.Cells.Evaluators
import Loaders.Cells.Cell

trait Evaluator
{
  def evaluate(toEvaluate: String, table: Vector[Vector[Cell]] = Vector(), visited: Set[String] = Set.empty): Option[AnyVal]
}
