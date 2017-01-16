package fr.acinq.eclair.crypto

import fr.acinq.bitcoin.BinaryData
import fr.acinq.bitcoin.Crypto.{Point, Scalar}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GeneratorsSpec extends FunSuite {
  val base_secret = BinaryData("0x000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f")
  val per_commitment_secret = BinaryData("0x1f1e1d1c1b1a191817161514131211100f0e0d0c0b0a09080706050403020100")
  val base_point = Point(BinaryData("0x036d6caac248af96f6afa7f904f550253a0f3ef3f5aa2fe6838a95b216691468e2"))
  val per_commitment_point = Point(BinaryData("0x025f7117a78150fe2ef97db7cfc83bd57b2e2c0d0dd25eaf467a4a1c2a45ce1486"))

  test("derivation of key from basepoint and per-commitment-point") {
    val localKey = Generators.derivePubKey(base_point, per_commitment_point)
    assert(localKey.toBin == BinaryData("0x0235f2dbfaa89b57ec7b055afe29849ef7ddfeb1cefdb9ebdc43f5494984db29e5"))
  }

  test("derivation of secret key from basepoint secret and per-commitment-secret") {
    val localprivkey = Generators.derivePrivKey(Scalar(base_secret).copy(compressed = true), per_commitment_point)
    assert(localprivkey.toBin == BinaryData("0xcbced912d3b21bf196a766651e436aff192362621ce317704ea2f75d87e7be0f01"))
  }

  test("derivation of revocation key from basepoint and per-commitment-point") {
    val revocationkey = Generators.revocationPubKey(base_point, per_commitment_point)
    assert(revocationkey.toBin == BinaryData("0x02916e326636d19c33f13e8c0c3a03dd157f332f3e99c317c141dd865eb01f8ff0"))
  }

  test("derivation of revocation secret from basepoint-secret and per-commitment-secret") {
    val revocationprivkey = Generators.revocationPrivKey(Scalar(base_secret).copy(compressed = true), Scalar(per_commitment_secret).copy(compressed = true))
    assert(revocationprivkey.toBin == BinaryData("0xd09ffff62ddb2297ab000cc85bcb4283fdeb6aa052affbc9dddcf33b6107811001"))
  }
}