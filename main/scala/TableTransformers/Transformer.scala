package TableTransformers
import Loaders.Cells.Cell
import scala.collection.immutable.Vector

trait Transformer {
def transform(table: Vector[Vector[Cell]]): Vector[Vector[Cell]]
}
