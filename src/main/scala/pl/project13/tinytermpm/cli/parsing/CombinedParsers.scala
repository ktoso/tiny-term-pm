package pl.project13.tinytermpm.cli.parsing

import util.parsing.combinator.{JavaTokenParsers}


trait CombinedParsers { this: JavaTokenParsers =>

  def combinedParser(left: String*)(right: String*) = {

    println("right = " + right)
    println("left = " + left)

    val combined = for {
      l <- left
      r <- right
    } yield combine(l, r)

    println("combined = " + combined)


    val firstParser = Parser(combined.drop(1).head)
    combined.foldLeft(firstParser) { (l, r) => l | Parser(r) }
  }

  private def combine = (l: String, r: String) => List(l, r).mkString(" ")

}
