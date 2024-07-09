package OutputHandlers.OutputFormats
import Loaders.Cells.Cell

trait OutputFormat
{
  def translate(table: Vector[Vector[Cell]], args: Map[String, Vector[String]]): Vector[String]

  def getHeaders(args: Map[String, Vector[String]]): Boolean = { args.getOrElse("headers", false) match {
      case _: Boolean => false
      case _:Vector[String] => true
    }
  }
}
