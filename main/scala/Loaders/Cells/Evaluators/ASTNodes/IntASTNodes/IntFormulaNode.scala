package Loaders.Cells.Evaluators.ASTNodes.IntASTNodes
import Loaders.Cells.Cell
import Loaders.Cells.Evaluators.ASTNodes.IntASTNodes.IntBinaryOpASTNodes.IntPlusMinusNode
import Loaders.Cells.Evaluators.Tokenizers.Tokenizer

case class IntFormulaNode
  (private var node: Option[IntNode] = None)
  extends IntNode
{
  override def parse(tokenizer: Tokenizer): IntNode = {
    if (!tokenizer.getToken().equals("="))
      throw new Exception("Formula without equal sign at the beginning")
    tokenizer.tokenJump()
    node = Some(IntPlusMinusNode().parse(tokenizer))
    if (!tokenizer.isDone) throw new Exception("Wrongly formatted formula")
    this
  }

  override def evaluate(table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int] =
    node
      .getOrElse(throw new Exception("Attempt to evaluate unparsed node"))
      .evaluate(table, visited)
}
