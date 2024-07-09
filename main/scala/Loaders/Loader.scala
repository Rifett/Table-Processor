package Loaders
import Loaders.Cells._

trait Loader
{
  def load(args : Map[String, Vector[String]] ): Vector[Vector[Cell]]
}
