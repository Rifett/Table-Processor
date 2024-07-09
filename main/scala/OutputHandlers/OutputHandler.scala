package OutputHandlers
import Loaders.Cells.Cell
import OutputHandlers.OutputFormats.{CSVFormat, MarkdownFormat, OutputFormat}

import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.collection.immutable.Vector

case class OutputHandler(table: Vector[Vector[Cell]], args: Map[String, Vector[String]]) {
  def makeOutput(): Vector[String] = {
    val tableToOutput = formatTranslate()
    val outputFile: String = args.get("output-file").flatMap(vector => vector.headOption).getOrElse("")
    val stdout: Boolean = args.getOrElse("stdout", false) match {
      case _: Boolean => false
      case _: Vector[String] => true
    }

    (outputFile.nonEmpty, stdout) match {
      case (true, true) =>
        writeIntoFile(tableToOutput, outputFile)
        tableToOutput.foreach(println)
      case (true, false) =>
        writeIntoFile(tableToOutput, outputFile)
      case _ => tableToOutput.foreach(println)
    }
    tableToOutput
  }

  private def formatTranslate(): Vector[String] = {
    val format = args.get("format").flatMap(vector => vector.headOption).getOrElse("csv")
    formatsLibrary(format).translate(table, args)
  }

  private def writeIntoFile(whatToWrite: Vector[String], filePath: String): Unit = {
    val content = whatToWrite.mkString("\n")
    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
  }

  private val formatsLibrary: Map[String, OutputFormat] = Map(
    "csv" -> CSVFormat(),
    "md" -> MarkdownFormat()
  )
}
