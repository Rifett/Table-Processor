package Loaders.Cells.Evaluators.Tokenizers
import scala.util.matching.Regex

//Initial method must be called before usage of the class
case class FormulaTokenizer() extends Tokenizer
{
  def tokenJump(): Unit = {
    curTok = tokens
      .getOrElse(throw new Exception("Usage of tokenJump without of initialization"))
      .lift(curTokIndex)
      .getOrElse(throw new Exception("Tokenizer overflow"))
    curTokIndex += 1
  }

  def getToken(): String = if (curTok.isEmpty())
    throw new Exception("Usage of getToken without of initialization")
  else
    curTok

  def init(formula: String): Unit = {
    tokens = Some(formulaPattern.findAllIn(formula).toVector.appended("EOF"))
  }

  override def isDone: Boolean = curTokIndex.equals(tokens.get.size)

  private var tokens: Option[Vector[String]] = None

  private val formulaPattern: Regex = """([A-Z]+\d+|\d+|[+\-*/=()])""".r

  private var curTokIndex = 0

  private var curTok: String = ""
}