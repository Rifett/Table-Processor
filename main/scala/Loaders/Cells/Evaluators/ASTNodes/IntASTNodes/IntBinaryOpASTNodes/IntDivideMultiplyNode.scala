package Loaders.Cells.Evaluators.ASTNodes.IntASTNodes.IntBinaryOpASTNodes

import Loaders.Cells.Cell
import Loaders.Cells.Evaluators.ASTNodes.IntASTNodes.{IntNode, IntNumberNode}
import Loaders.Cells.Evaluators.Tokenizers.Tokenizer

case class IntDivideMultiplyNode
  (private var left: Option[IntNode] = None, private var right: Option[IntNode] = None, private var op: String = "")
  extends IntNode
{
  override def parse(tokenizer: Tokenizer): IntNode = {
    var parsed: IntNode = IntNumberNode().parse(tokenizer)

    while (true) {
      if (tokenizer.getToken().equals("*")) {
        tokenizer.tokenJump()
        parsed = IntDivideMultiplyNode(Some(parsed), Some(IntNumberNode().parse(tokenizer)), "*")
      } else if (tokenizer.getToken().equals("/")) {
        tokenizer.tokenJump()
        parsed = IntDivideMultiplyNode(Some(parsed), Some(IntNumberNode().parse(tokenizer)), "/")
      } else
        return parsed
    }
    parsed
  }

  override def evaluate(table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int] = op match {
    case "*" => Some(left.get.evaluate(table, visited).get * right.get.evaluate(table, visited).get)
    case "/" => Some(left.get.evaluate(table, visited).get / right.get.evaluate(table, visited).get)
    case _ => throw new Exception("Attempt to evaluate unparsed node")
  }
}
