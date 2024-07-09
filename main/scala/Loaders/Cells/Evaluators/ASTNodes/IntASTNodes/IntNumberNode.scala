package Loaders.Cells.Evaluators.ASTNodes.IntASTNodes
import Loaders.Cells.Cell
import Loaders.Cells.Evaluators.Tokenizers.Tokenizer

case class IntNumberNode(private var value: Option[Int] = None) extends IntNode
{
  override def parse(tokenizer: Tokenizer): IntNode = {
    tokenizer.getToken() match {
      case str if str.forall(_.isDigit) =>
        value = Some(str.toInt)
        tokenizer.tokenJump()
        this
      case _ => IntReferenceNode().parse(tokenizer)
    }
  }

  override def evaluate(table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int] =
  if (value.isDefined)
    value
  else
    throw new Exception("Attempt to evaluate unparsed node")
}