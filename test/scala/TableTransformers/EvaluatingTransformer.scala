package TableTransformers
import Loaders.CSVFileLoader
import Loaders.Cells.{Cell, EmptyCell, NumberCell}
import munit._

import java.nio.file.{Files, Paths, StandardOpenOption}

class EvaluatingTransformerTest extends FunSuite{
  test("Test of standard behaviour") {
    val filePath = "Testing file.txt"
    val content = "1,,=A1+1"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = EvaluatingTransformer()
    val evaluatedTable: Vector[Vector[Cell]] = transformer.transform(table)
    val correctTable = Vector(Vector(NumberCell(1), EmptyCell(), NumberCell(2)))

    assert(evaluatedTable.equals(correctTable))

    Files.delete(Paths.get(filePath))
  }

  test("Test for empty cell in arithmetics detection") {
    val filePath = "Testing file.txt"
    val content = "1,,=A2+1"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = EvaluatingTransformer()
    intercept[Exception] {
      transformer.transform(table)
    }

    Files.delete(Paths.get(filePath))
  }

  test("Test for indirect empty cell in arithmetics detection") {
    val filePath = "Testing file.txt"
    val content = ",=A1,=A2+1"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = EvaluatingTransformer()
    intercept[Exception] {
      transformer.transform(table)
    }

    Files.delete(Paths.get(filePath))
  }

  test("Test for cyclic dependencies detection") {
    val filePath = "Testing file.txt"
    val content = "1,=A3+1,=A2+1"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = EvaluatingTransformer()
    intercept[Exception] {
      transformer.transform(table)
    }

    Files.delete(Paths.get(filePath))
  }

  test("Test for indirect cyclic dependencies detection") {
    val filePath = "Testing file.txt"
    val content = "=A2,=A3+1,=A2+1"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = EvaluatingTransformer()
    intercept[Exception] {
      transformer.transform(table)
    }

    Files.delete(Paths.get(filePath))
  }

  test("Test for plain empty cell reference detection") {
    val filePath = "Testing file.txt"
    val content = ",1,=A1"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = EvaluatingTransformer()
    val evaluatedTable: Vector[Vector[Cell]] = transformer.transform(table)
    val correctTable = Vector(Vector(EmptyCell(), NumberCell(1), EmptyCell()))

    assert(evaluatedTable.equals(correctTable))

    Files.delete(Paths.get(filePath))
  }

  test("Test for wrongly formatted formula part 1") {
    val filePath = "Testing file.txt"
    val content = "1,2,=A1A2"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = EvaluatingTransformer()
    intercept[Exception] {
      transformer.transform(table)
    }

    Files.delete(Paths.get(filePath))
  }

  test("Test for wrongly formatted formula part 2") {
    val filePath = "Testing file.txt"
    val content = "1,2,=A1+((A2)"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = EvaluatingTransformer()
    intercept[Exception] {
      transformer.transform(table)
    }

    Files.delete(Paths.get(filePath))
  }

  test("Test for wrongly formatted formula part 3") {
    val filePath = "Testing file.txt"
    val content = "1,2,A1+A2"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = EvaluatingTransformer()
    intercept[Exception] {
      transformer.transform(table)
    }

    Files.delete(Paths.get(filePath))
  }

  test("Test for wrongly formatted input number") {
    val filePath = "Testing file.txt"
    val content = "1,2Amogus,=A1+A2"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = EvaluatingTransformer()
    intercept[Exception] {
      transformer.transform(table)
    }

    Files.delete(Paths.get(filePath))
  }

  test("Test for parenthesis operation") {
    val filePath = "Testing file.txt"
    val content = "1,2,=3*(1+2)"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = new EvaluatingTransformer()
    val evaluatedTable: Vector[Vector[Cell]] = transformer.transform(table)
    val correctTable = Vector(Vector(NumberCell(1), NumberCell(2), NumberCell(9)))

    assert(evaluatedTable.equals(correctTable))

    Files.delete(Paths.get(filePath))
  }

  test("Test for proper operator precedence") {
    val filePath = "Testing file.txt"
    val content = "1,2,=2+3*4"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = new EvaluatingTransformer()
    val evaluatedTable: Vector[Vector[Cell]] = transformer.transform(table)
    val correctTable = Vector(Vector(NumberCell(1), NumberCell(2), NumberCell(14)))

    assert(evaluatedTable.equals(correctTable))

    Files.delete(Paths.get(filePath))
  }

  test("Test for proper operator precedence number 2") {
    val filePath = "Testing file.txt"
    val content = "1,2,=7-4-3"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = new EvaluatingTransformer()
    val evaluatedTable: Vector[Vector[Cell]] = transformer.transform(table)
    val correctTable = Vector(Vector(NumberCell(1), NumberCell(2), NumberCell(0)))

    assert(evaluatedTable.equals(correctTable))

    Files.delete(Paths.get(filePath))
  }

  test("Test for whitespaces enabling") {
    val filePath = "Testing file.txt"
    val content = "1 , 2   ,=  7 -   4  -3 "

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = new EvaluatingTransformer()
    val evaluatedTable: Vector[Vector[Cell]] = transformer.transform(table)
    val correctTable = Vector(Vector(NumberCell(1), NumberCell(2), NumberCell(0)))

    assert(evaluatedTable.equals(correctTable))

    Files.delete(Paths.get(filePath))
  }

  test("Test for all possible operations") {
    val filePath = "Testing file.txt"
    val content = "1, 2, = 3 + 2 * 3 - (6 / 3) "

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = new EvaluatingTransformer()
    val evaluatedTable: Vector[Vector[Cell]] = transformer.transform(table)
    val correctTable = Vector(Vector(NumberCell(1), NumberCell(2), NumberCell(7)))

    assert(evaluatedTable.equals(correctTable))

    Files.delete(Paths.get(filePath))
  }

  test("Test for empty input") {
    val filePath = "Testing file.txt"
    val content = ""

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader: CSVFileLoader = new CSVFileLoader
    val table = loader.load(args)
    val transformer: EvaluatingTransformer = new EvaluatingTransformer()
    val evaluatedTable: Vector[Vector[Cell]] = transformer.transform(table)
    val correctTable = Vector.empty

    assert(evaluatedTable.equals(correctTable))

    Files.delete(Paths.get(filePath))
  }
}
