package Loaders.Cells.Evaluators
import Loaders.Cells._
import Tokenizers._
import ASTNodes.IntASTNodes._
import Loaders.Cells.Evaluators.ASTNodes.IntASTNodes.IntFormulaNode

case class IntFormulaEvaluator() extends IntEvaluator
{
  def evaluate(toEvaluate: String, table: Vector[Vector[Cell]], visited: Set[String] = Set.empty): Option[Int] = {
    tokenizer.init(toEvaluate)
    tokenizer.tokenJump()
    IntFormulaNode().parse(tokenizer).evaluate(table, visited)
  }

  private val tokenizer: FormulaTokenizer = FormulaTokenizer()
}
