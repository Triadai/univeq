package japgolly.univeq

import scalaz._
import UnivEq._

trait UnivEqScalaz {

  implicit def scalazEqualFromUnivEq[A: UnivEq]: Equal[A] =
    Equal.equalA

  @inline implicit def univEqDisj   [A: UnivEq, B: UnivEq]: UnivEq[A \/ B         ] = derive
  @inline implicit def univEqThese  [A: UnivEq, B: UnivEq]: UnivEq[A \&/ B        ] = force
  @inline implicit def univEqNel    [A: UnivEq]           : UnivEq[NonEmptyList[A]] = force

  @inline implicit def univEqOneAnd[F[_], A](implicit fa: UnivEq[F[A]], a: UnivEq[A]): UnivEq[OneAnd[F, A]] = derive

  def monoidSet[A: UnivEq]: Monoid[Set[A]] =
    new Monoid[Set[A]] {
      override def zero = Set.empty
      override def append(a: Set[A], b: => Set[A]) = a | b
    }
}

object UnivEqScalaz extends UnivEqExports
