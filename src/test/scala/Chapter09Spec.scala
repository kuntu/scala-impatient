import Chapter09._
import TestUtils.{printToTmpFile, runApp}
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source.fromFile

class Chapter09Spec extends FlatSpec with Matchers {

  "reverseLines" should "reverse lines in file" in {
    //given
    val file = printToTmpFile("reverseLines", """line 1
                                                |line 2
                                                |line 3
                                                |""".stripMargin)

    //when
    reverseLines(file)

    //then
    fromFile(file).mkString shouldBe """line 3
                                       |line 2
                                       |line 1
                                       |""".stripMargin
  }

  "replaceTabs" should "replace tabs with spaces using column boundaries" in {
    //given
    val file = printToTmpFile("replaceTabs", """text	text2	text3
                                               |	text	text2	text3
                                               |		text	text2	text3
                                               |""".stripMargin)

    //when
    replaceTabs(file, 2)

    //then
    fromFile(file).mkString shouldBe """text  text2 text3
                                       |  text  text2 text3
                                       |    text  text2 text3
                                       |""".stripMargin
  }

  "printLongWords" should "read a file and print all words longer than 12 chars" in {
    //given
    val file = printToTmpFile("printLongWords", "text toooooooolong text2 text3texttext2text3")

    //when
    val (exit, out, err) = runApp("PrintLongWordsApp", file.getAbsolutePath)

    //then
    exit shouldBe 0
    err shouldBe ""
    out shouldBe """toooooooolong
                   |text3texttext2text3
                   |""".stripMargin
  }

  "printNumbersStat" should "read numbers from file and print sum, average, min, max" in {
    //given
    val file = printToTmpFile("printNumbersStat", "1 1.2 2.34 -5 25.5 0.0 1.234")

    //when
    val (exit, out, err) = runApp("PrintNumbersStatApp", file.getAbsolutePath)

    //then
    exit shouldBe 0
    err shouldBe ""
    out shouldBe """sum:     26.274
                   |average: 3.753
                   |minimum: -5.000
                   |maximum: 25.500
                   |""".stripMargin
  }
}