package TableTransformers
import Loaders.Cells.{Cell, EmptyCell, NumberCell}

case class EvaluatingTransformer() extends Transformer
{
  override def transform(table: Vector[Vector[Cell]]): Vector[Vector[Cell]] = {
    table.map{
      row => row.map{ cell => cell.evaluate(table) match {
        case Some(value) => NumberCell(value)
        case None => EmptyCell()
      }}
    }
  }
}
