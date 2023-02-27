package calculator.semantics

import scala.language.implicitConversions
import org.scalacheck._
import org.scalacheck.Prop.{forAll, BooleanOperators, throws}
import Gen._
import Arbitrary.arbitrary
import calculator.ir._

object CalcInterpreterSpec extends Properties("Evaluator") {

  // some syntactic sugar for expressing interpreter tests
  extension (input: Expr)
    def ~>(output: BigInt) = {
      val result = eval(input)
      result == output
    }

    def ~/~>[T <: Throwable](c: Class[T]) = {
      throws(c) { eval(input) }
    }

  property("numbers") = forAll { (n: BigInt) =>
    Num(n) ~> n
  }

  property("addition") = forAll { (n1: BigInt, n2: BigInt) =>
    (Plus(Num(n1), Num(n2))) ~> (n1 + n2)
  }

  property("subtraction") = forAll { (n1: BigInt, n2: BigInt) =>
    (Minus(Num(n1), Num(n2))) ~> (n1 - n2)
  }

  property("multiplication") = forAll { (n1: BigInt, n2: BigInt) =>
    (Times(Num(n1), Num(n2))) ~> (n1 * n2)
  }

  property("division") = forAll { (n1: BigInt, n2: BigInt) =>
    if (n2 == 0) then
      (Divide(Num(n1), Num(n2))) ~/~> classOf[ArithmeticException]
    else (Divide(Num(n1), Num(n2))) ~> (n1 / n2)
  }
}
