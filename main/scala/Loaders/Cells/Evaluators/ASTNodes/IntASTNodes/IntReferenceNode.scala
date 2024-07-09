package Loaders.Cells.Evaluators.ASTNodes.IntASTNodes
import Loaders.Cells.Cell
import Loaders.Cells.Evaluators.Tokenizers.Tokenizer

case class IntReferenceNode(private var reference: Option[String] = None) extends IntNode
{
  override def parse(tokenizer: Tokenizer): IntNode = {
    tokenizer.getToken() match {
      case str if str.head.isUpper && str.tail.forall(_.isDigit) =>
        reference = Some(str)
        tokenizer.tokenJump()
        this
      case _ => IntBracketsNode().parse(tokenizer)
    }
  }

  override def evaluate(table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int] =
    if (visited.contains(reference.getOrElse(throw new Exception("Attempt to evaluate unparsed node"))))
      throw new Exception("Reference loop detected!")
    else {
      table
      .lift(
        reference
          .get.tail.toInt - 1).get
      .lift(
        reference
          .get.head - 'A').get.evaluate(table, visited + reference.get)
    }
}