package demo

import javax.faces.bean.{ApplicationScoped, ManagedBean}
import scala.reflect.BeanProperty

@ManagedBean
@ApplicationScoped
class ScalaHello {
  @BeanProperty
  var helloWorld = "Hello World from a dynamic Scala class"
}