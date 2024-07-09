package Loaders
import munit._
import java.nio.file.{Paths, Files, StandardOpenOption, StandardCopyOption}
import Cells._

//Only resulting classes are tested, because all other are inner classes (not "public" ones), so, as it was told on the lectures,
//We can omit testing non-public things
class CSVFileLoaderTest extends FunSuite {
  test("Test load method with standard separator") {
    val filePath = "Testing file.txt"
    val content = "1,,=A1+1"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader:CSVFileLoader = new CSVFileLoader
    val result = loader.load(args)
    val rightVal = Vector(Vector( NumberCell(1), EmptyCell(), FormulaCell("=A1+1") ))
    assert(result.equals(rightVal))

    Files.delete(Paths.get(filePath))
  }

  test("Test load method with comma as a separator") {
    val filePath = "Testing file.txt"
    val content = "1,,=A1+1"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "separator" -> Vector(","),
      "input-file" -> Vector("Testing file.txt")
    )

    val loader:CSVFileLoader = new CSVFileLoader
    val result = loader.load(args)
    val rightVal = Vector(Vector( NumberCell(1), EmptyCell(), FormulaCell("=A1+1") ))
    assert(result.equals(rightVal))

    Files.delete(Paths.get(filePath))
  }

  test("Test load method with custom separator") {
    val filePath = "Testing file.txt"
    val content = "1//=A1+1"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "separator" -> Vector("/"),
      "input-file" -> Vector("Testing file.txt")
    )

    val loader:CSVFileLoader = new CSVFileLoader
    val result = loader.load(args)
    val rightVal = Vector(Vector( NumberCell(1), EmptyCell(), FormulaCell("=A1+1") ))
    assert(result.equals(rightVal))

    Files.delete(Paths.get(filePath))
  }

  test("Test load method without input file argument") {
    val filePath = "Testing file.txt"
    val content = "1//=A1+1"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map.empty

    val loader:CSVFileLoader = new CSVFileLoader

    intercept[Exception] {
      loader.load(args)
    }

    Files.delete(Paths.get(filePath))
  }

  test("Test load with an empty file") {
    val filePath = "Testing file.txt"
    val content = ""

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )
    val loader:CSVFileLoader = new CSVFileLoader
    val result = loader.load(args)
    assert(result.equals(Vector.empty))

    Files.delete(Paths.get(filePath))
  }

  test("Test file with more than one line") {
    val filePath = "Testing file.txt"
    val content = "1,,=A1+1\n1,2,3"

    Files.write(Paths.get(filePath), content.getBytes, StandardOpenOption.CREATE)

    val args: Map[String, Vector[String]] = Map(
      "input-file" -> Vector("Testing file.txt")
    )

    val loader:CSVFileLoader = new CSVFileLoader
    val result = loader.load(args)
    val rightVal = Vector(
      Vector( NumberCell(1), EmptyCell(), FormulaCell("=A1+1") ),
      Vector( NumberCell(1), NumberCell(2), NumberCell(3) ))
    assert(result.equals(rightVal))

    Files.delete(Paths.get(filePath))
  }
}