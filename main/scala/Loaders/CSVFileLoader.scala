package Loaders
import Loaders.Cells._
import scala.io.{BufferedSource, Source}

case class CSVFileLoader() extends Loader
{
  override def load(args: Map[String, Vector[String]]): Vector[Vector[Cell]] = {
    val separator = args.get("separator").flatMap(vector => vector.headOption).getOrElse(",")
    val inputFile = args.getOrElse("input-file", {
      throw new Exception("No input file provided!")
    }).head

    val source = Source.fromFile(inputFile)
    val lines: Vector[String] = source.getLines().toVector
    source.close()

    lines.map(line => line.split(separator).toVector.map(parseCell))
  }

  private def parseCell(cellValue: String): Cell = {
    val normalizedCell = cellValue.replaceAll("\\s", "")
    if (normalizedCell.isEmpty) EmptyCell()
    else if (normalizedCell.forall(_.isDigit)) NumberCell(normalizedCell.toInt)
    else FormulaCell(normalizedCell)
  }
}