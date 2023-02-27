package calculator.semantics

import calculator.ir._

def eval(expr: Expr): BigInt = expr match {
  case Num(i)              => i
  case Plus(left, right)   => eval(left) + eval(right)
  case Minus(left, right)  => eval(left) - eval(right)
  case Times(left, right)  => eval(left) * eval(right)
  case Divide(left, right) => eval(left) / eval(right)
}
