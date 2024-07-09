package Loaders.Cells.Evaluators.Tokenizers

trait Tokenizer {
  def tokenJump(): Unit

  def getToken(): String

  def isDone: Boolean
}
