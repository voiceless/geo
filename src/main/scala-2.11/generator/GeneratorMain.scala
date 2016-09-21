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

  val random = new Random

  generateLabel(100, lebels)
  generateGrid(100, grids)

  def generateLabel(size: Int, file: File) {
    val writer = file.printWriter()
    writer.println("user_id,lon,lat")
    (1 to size).toStream.map(i => Label(i, random.nextDouble() * 1000, random.nextDouble() * 1000)).foreach(l => writer.println(l.toCsvString))
  }

  def generateGrid(size: Int, file: File) {
    val writer = file.printWriter()
    writer.println("tile_x,tile_y,distance_error")
    (1 to size).toStream.map(i => Grid(random.nextInt(), random.nextInt(), random.nextDouble() * 10)).foreach(g => writer.println(g.toScvString))
  }


}
