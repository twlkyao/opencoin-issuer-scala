package org.opencoin.core.messages

case class RequestMintKeys(
    `type`: String = "request mint keys",
    message_reference: Int,
    mint_key_ids: List[BigInt], //for specific keys
    denominations: List[Int])