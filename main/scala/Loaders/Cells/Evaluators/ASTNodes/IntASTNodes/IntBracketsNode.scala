package Loaders.Cells.Evaluators.ASTNodes.IntASTNodes

import Loaders.Cells.Cell
import Loaders.Cells.Evaluators.ASTNodes.IntASTNodes.IntBinaryOpASTNodes.IntPlusMinusNode
import Loaders.Cells.Evaluators.Tokenizers.Tokenizer

case class IntBracketsNode() extends IntNode
{
  override def parse(tokenizer: Tokenizer): IntNode = {
    if (tokenizer.getToken().equals("(")) {
      tokenizer.tokenJump()
      val expression = IntPlusMinusNode().parse(tokenizer)
      if (tokenizer.getToken().equals(")")) {
        tokenizer.tokenJump()
        return expression
      }
    }
    throw new Exception("Wrongly formatted expression")
  }

  override def evaluate(table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int] =
    throw new Exception("Attempt to evaluate brackets node, which should not be allowed")
}
