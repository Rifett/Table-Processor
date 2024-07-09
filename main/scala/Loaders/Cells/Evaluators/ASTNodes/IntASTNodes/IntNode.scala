package Loaders.Cells.Evaluators.ASTNodes.IntASTNodes
import Loaders.Cells.Cell
import Loaders.Cells.Evaluators.ASTNodes.Node
import Loaders.Cells.Evaluators.Tokenizers.Tokenizer

trait IntNode extends Node
{
  override def evaluate(table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int]

  override def parse(tokenizer: Tokenizer): IntNode
}
