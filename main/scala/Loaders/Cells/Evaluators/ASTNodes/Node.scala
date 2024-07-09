package Loaders.Cells.Evaluators.ASTNodes
import Loaders.Cells.Cell
import Loaders.Cells.Evaluators.Tokenizers.Tokenizer

trait Node
{
  def evaluate(table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[AnyVal]

  def parse(tokenizer: Tokenizer): Node
}
