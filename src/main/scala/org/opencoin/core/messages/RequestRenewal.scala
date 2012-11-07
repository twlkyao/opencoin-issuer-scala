package org.opencoin.core.messages

import org.opencoin.core.token.{Blank,Coin}

case class RequestRenewal(
    `type`: String ="request renewal",
    message_reference: Int,
    transaction_reference: Int,
    coins: List[Coin],
    tokens: List[Blank])