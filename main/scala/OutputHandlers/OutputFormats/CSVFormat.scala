package OutputHandlers.OutputFormats
import Loaders.Cells.Cell

case class CSVFormat() extends OutputFormat
{
  override def translate(table: Vector[Vector[Cell]], args: Map[String, Vector[String]]): Vector[String] = {
    val separator = args.get("output-separator").flatMap(vector => vector.headOption).getOrElse(",")
    val includeHeaders: Boolean = getHeaders(args)

    val headerRow = if (includeHeaders) {
      separator + ('A' to ('A' + table.head.size - 1).toChar).map(_.toString).mkString(separator)
    } else {
      ""
    }

    val bodyRows = table.zipWithIndex.map { case (row, rowIndex) =>
      val rowNumber = (rowIndex + 1).toString
      val rowData = row.map(cell => cell.evaluate(table).map(_.toString).getOrElse("")).mkString(separator)
      if (includeHeaders)
        s"$rowNumber,$rowData"
      else
        s"$rowData"
    }

    if (includeHeaders)
      headerRow +: bodyRows
    else
      bodyRows
  }
}
