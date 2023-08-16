ThisBuild / scalaVersion := "3.3.0"

ThisBuild / organization := "com.clairvoyant.data.scalaxy"

ThisBuild / version := "1.0.0"

ThisBuild / credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  System.getenv("GITHUB_USERNAME"),
  System.getenv("GITHUB_TOKEN")
)

ThisBuild / publishTo := Some(
  "Github Repo" at s"https://maven.pkg.github.com/teamclairvoyant/data-scalaxy-ai/"
)

// ----- SCALAFIX ----- //

ThisBuild / semanticdbEnabled := true
ThisBuild / scalafixOnCompile := true

// ----- WARTREMOVER ----- //

ThisBuild / wartremoverErrors ++= Warts.allBut(
  Wart.GlobalExecutionContext,
  Wart.IterableOps,
  Wart.ScalaApp
)

// ----- TOOL VERSIONS ----- //

val openAIScalaClientVersion = "0.4.1"

// ----- TOOL DEPENDENCIES ----- //

val openAIScalaClientDependencies = Seq(
  "io.cequence" %% "openai-scala-client" % openAIScalaClientVersion
)

// ----- MODULE DEPENDENCIES ----- //

val rootDependencies = openAIScalaClientDependencies

// ----- SETTINGS ----- //

val rootSettings = Seq(
  libraryDependencies ++= rootDependencies
)

// ----- PROJECTS ----- //

lazy val `data-scalaxy-ai` = (project in file("."))
  .settings(rootSettings)
