package se.gigurra.gat.util

object Edit {
  implicit class EditCls[T](t: T) {
    def edit[A](f: T => A): T = {
      f(t)
      t
    }
  }
}
