package OutputHandlers.OutputFormats
import Loaders.Cells.Cell

case class MarkdownFormat() extends OutputFormat {
  override def translate(table: Vector[Vector[Cell]], args: Map[String, Vector[String]]): Vector[String] = {
    val includeHeaders: Boolean = getHeaders(args)

    val headerRow = "| | " + ('A' to ('A' + table.head.size - 1).toChar).mkString(" | ")


    val separatorRow = if (includeHeaders)
      "| --- | " + table.head.map(_ => "---").mkString(" | ") + " |"
    else
      "| " + table.head.map(_ => "---").mkString(" | ") + " |"


    val bodyRows = table.zipWithIndex.map { case (row, rowIndex) =>
      val rowNumber = (rowIndex + 1).toString
      val rowData = row.map(cell => cell.evaluate(table).map(_.toString).getOrElse("")).mkString(" | ")
      if (includeHeaders)
        s"| $rowNumber | $rowData |"
      else
        s"| $rowData |"
    }

    if (includeHeaders)
      headerRow +: separatorRow +: bodyRows
    else
      bodyRows.head +: separatorRow +: bodyRows.tail
  }
}
