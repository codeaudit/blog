package blog.debug

import blog.model.Model
import blog.world.PartialWorld

/**
 * A sample generated by MHSampler.
 *
 * @author cberzan
 * @since Jun 23, 2014
 */
class MHSample(
  model: Model,
  world: PartialWorld,
  val changed: Boolean,
  val logProposalBackward: Double,
  val logProposalForward: Double,
  val logProbRatio: Double,
  val logAcceptRatio: Double)
  extends Sample(model, world) {

  override def toString = s"MHSample(changed: ${changed}, world: ${world})"

  def dump = {
    println(s"MHSample:")
    println(s"world: ${world}")
    println(s"changed: ${changed}")
    println(s"logProposalBackward: ${logProposalBackward}")
    println(s"logProposalForward: ${logProposalForward}")
    println(s"logProbRatio: ${logProbRatio}")
    println(s"logAcceptRatio: ${logAcceptRatio}")
    println
  }
}