package generator

import model.{Grid, Label}

import scala.reflect.io.File
import scala.util.Random

/**
  * Created by pbezglasnyi on 20.09.2016.
  */
object GeneratorMain extends App {

  val lebels = File(args(0)).createFile()

  val grids = File(args(1)).createFile()

  val size = args(2).toInt

  val random = new Random

  generateLabel(size, lebels)
  generateGrid(grids)

  def generateLabel(size: Int, file: File) {
    val writer = file.printWriter()
    (1 to size).toStream.map(i => Label(i, random.nextDouble() * 360, random.nextDouble() * 360)).foreach(l => writer.println(l.toCsvString))
  }

  def generateGrid(file: File) {
    val writer = file.printWriter()
    (0 until 360).foreach(i => (0 until 360).map(j => Grid(i, j, random.nextDouble())).foreach(g => writer.println(g.toCsvString)))
  }
}
