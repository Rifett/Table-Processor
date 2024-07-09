package Loaders.Cells.Evaluators
import Loaders.Cells.Cell

trait IntEvaluator extends Evaluator
{
  override def evaluate(toEvaluate: String, table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int]
}
