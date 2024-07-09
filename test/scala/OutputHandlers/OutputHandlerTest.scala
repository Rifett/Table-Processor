package OutputHandlers
import Loaders.CSVFileLoader
import munit._

import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.io.Source

class OutputHandlerTest extends FunSuite {
  test("CSV output format without headers") {
    val filePath = "Testing file.txt"
    val content = "1,100,=A1+B1\n2,1020,20"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader:CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val handler: OutputHandler = OutputHandler(table, args)
    val res = handler.makeOutput()
    assert(res.equals(Vector[String](
      "1,100,101",
      "2,1020,20")))

    Files.delete(Paths.get(filePath))
  }

  test("CSV output format with headers") {
    val filePath = "Testing file.txt"
    val content = "1,100,=A1+B1\n2,1020,20"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt"),
      "headers" -> Vector.empty
    )

    val loader:CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val handler: OutputHandler = OutputHandler(table, args)
    val res = handler.makeOutput()
    assert(res.equals(Vector[String](
      ",A,B,C",
      "1,1,100,101",
      "2,2,1020,20")))

    Files.delete(Paths.get(filePath))
  }

  test("Markdown output format without headers") {
    val filePath = "Testing file.txt"
    val content = "1,100,=A1+B1\n2,1020,20"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt"),
      "format" -> Vector("md")
    )

    val loader:CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val handler: OutputHandler = OutputHandler(table, args)
    val res = handler.makeOutput()
    assert(res.equals(Vector[String](
      "| 1 | 100 | 101 |",
      "| --- | --- | --- |",
      "| 2 | 1020 | 20 |")))

    Files.delete(Paths.get(filePath))
  }

  test("Markdown output format with headers") {
    val filePath = "Testing file.txt"
    val content = "1,100,=A1+B1\n2,1020,20"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt"),
      "format" -> Vector("md"),
      "headers" -> Vector.empty
    )

    val loader:CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val handler: OutputHandler = OutputHandler(table, args)
    val res = handler.makeOutput()
    assert(res.equals(Vector[String](
      "| | A | B | C",
      "| --- | --- | --- | --- |",
      "| 1 | 1 | 100 | 101 |",
      "| 2 | 2 | 1020 | 20 |")))

    Files.delete(Paths.get(filePath))
  }

  test("CSV output format with stdout argument") {
    val filePath = "Testing file.txt"
    val content = "1,100,=A1+B1\n2,1020,20"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt"),
      "stdout" -> Vector.empty
    )

    val loader:CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val handler: OutputHandler = OutputHandler(table, args)
    val res = handler.makeOutput()
    assert(res.equals(Vector[String](
      "1,100,101",
      "2,1020,20")))

    Files.delete(Paths.get(filePath))
  }

  test("CSV output format into a file that did not exist before") {
    val filePath = "Testing file.txt"
    val content = "1,100,=A1+B1\n2,1020,20"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt"),
      "output-file" -> Vector("Testing output file.txt")
    )

    val loader:CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val handler: OutputHandler = OutputHandler(table, args)
    handler.makeOutput()

    val source = Source.fromFile("Testing output file.txt")
    val res = source.getLines().toVector

    assert(res.equals(Vector[String](
      "1,100,101",
      "2,1020,20")))

    Files.delete(Paths.get("Testing output file.txt"))
    Files.delete(Paths.get(filePath))
  }

  test("CSV output format with both stdout and file output (it is not specified what to do in that case, so I decided to do both types of output at the same time)") {
    val filePath = "Testing file.txt"
    val content = "1,100,=A1+B1\n2,1020,20"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt"),
      "stdout" -> Vector.empty,
      "output-file" -> Vector("Testing output file.txt")
    )

    val loader:CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val handler: OutputHandler = OutputHandler(table, args)
    val stdoutRes = handler.makeOutput()

    assert(stdoutRes.equals(Vector[String](
      "1,100,101",
      "2,1020,20")))

    val source = Source.fromFile("Testing output file.txt")
    val fileRes = source.getLines().toVector
    assert(stdoutRes.equals(fileRes))

    Files.delete(Paths.get("Testing output file.txt"))
    Files.delete(Paths.get(filePath))
  }
}
