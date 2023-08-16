package com.clairvoyant.data.scalaxy.ai

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.typesafe.config.ConfigFactory
import io.cequence.openaiscala.domain.ModelId
import io.cequence.openaiscala.domain.settings.CreateCompletionSettings
import io.cequence.openaiscala.service.OpenAIServiceFactory

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}

object GenAIApp extends App {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  implicit val materializer: Materializer = Materializer(ActorSystem())

  private val openAIScalaClientConfig = ConfigFactory.load("openai-scala-client.conf")
  private val openAIService = OpenAIServiceFactory(openAIScalaClientConfig)

  private val text =
    """
      |Extract the name and mailing address from this email:
      |Dear Kelly,
      |It was great to talk to you at the seminar. I thought Jane's talk was quite good.
      |Thank you for the book. Here's my address 2111 Ash Lane, Crestview CA 92002
      |Best,
      |Maya
      |""".stripMargin

  Await.result(
    openAIService
      .createCompletion(
        text,
        settings = CreateCompletionSettings(
          model = ModelId.text_davinci_003
        )
      )
      .map(completion => println(completion.choices.map(_.text).mkString)),
    Duration.Inf
  )

  openAIService.close()

}
