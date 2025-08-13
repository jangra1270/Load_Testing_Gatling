import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class AdvancedLoadTest extends Simulation {

  val httpProtocol = http
    .baseUrl("https://jsonplaceholder.typicode.com") // Target base URL
    .acceptHeader("application/json")

  val scn = scenario("Advanced Load Test")
    .exec(
      http("Get Posts")
      .get("/posts")
      .check(status.is(200))
    )
    .pause(1)

  setUp(
    scn.inject(
      rampUsers(50) during (30.seconds),  // Ramp up users
      constantUsersPerSec(20) during (60.seconds), // Constant load
      rampUsers(100) during (30.seconds)  // Stress peak
    )
  ).protocols(httpProtocol)
}
