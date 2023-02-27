package calculator.parser

import scala.language.implicitConversions
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import calculator.ir._

object CalcParseSpec extends Properties("Parser") {

  // some syntactic sugar for expressing parser tests
  extension (input: String)
    def ~>(output: Expr) = {
      val result = CalcParser(input)
      result.successful && result.get == output
    }

  property("numbers") = forAll { (n: Int) =>
    s"$n" ~> Num(n)
  }

  property("addition") = forAll { (n1: Int, n2: Int) =>
    s"$n1 + $n2" ~> (Plus(Num(n1), Num(n2)))
  }

  property("subtraction") = forAll { (n1: Int, n2: Int) =>
    s"$n1 - $n2" ~> (Minus(Num(n1), Num(n2)))
  }

  property("multiplication") = forAll { (n1: Int, n2: Int) =>
    s"$n1 * $n2" ~> (Times(Num(n1), Num(n2)))
  }

  property("division") = forAll { (n1: Int, n2: Int) =>
    s"$n1 / $n2" ~> (Divide(Num(n1), Num(n2)))
  }

  property("parentheses") = forAll { (n1: Int, n2: Int, n3: Int) =>
    s"($n1 + $n2) * $n3" ~> (Times(Plus(Num(n1), Num(n2)), Num(n3)))
  }
}
